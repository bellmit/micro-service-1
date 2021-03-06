package com.task.schedule.manager.task;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import com.system.comm.enums.Boolean;
import com.system.comm.utils.FrameHttpUtil;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameMapUtil;
import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.comm.utils.FrameTimeUtil;
import com.system.threadpool.FrameThreadAction;
import com.task.schedule.comm.enums.Config;
import com.task.schedule.comm.enums.JobCalltype;
import com.task.schedule.comm.enums.JobLogStatus;
import com.task.schedule.comm.utils.RuleVerifyUtil;
import com.task.schedule.comm.utils.SendMailUtil;
import com.task.schedule.comm.utils.SignUtil;
import com.task.schedule.manager.pojo.TaskJob;
import com.task.schedule.manager.pojo.TaskJobLog;
import com.task.schedule.manager.pojo.TaskProject;
import com.task.schedule.manager.service.SysConfigService;
import com.task.schedule.manager.service.TaskJobLogService;

public class ExecJobTask extends FrameThreadAction {

	private static final long serialVersionUID = -4514045785524480607L;
	private static final Logger LOGGER = Logger.getLogger(ExecJobTask.class);

	private TaskProject taskProject;
	private TaskJob taskJob;
	private String time;
	private TaskJobLogService taskJobLogService;

	public ExecJobTask(TaskProject taskProject, TaskJob taskJob, String time, TaskJobLogService taskJobLogService) {
		this.taskProject = taskProject;
		this.taskJob = taskJob;
		this.time = time;
		this.taskJobLogService = taskJobLogService;
	}

	@Override
	protected void compute() {
		try {
			//增加参数校验规则
			String link = taskJob.getLink();
			StringBuilder postParams = new StringBuilder();
			Map<String, Object> params = SignUtil.signParams(taskProject);
			Iterator<Entry<String, Object>> entryKeyIterator = params.entrySet().iterator();
			while (entryKeyIterator.hasNext()) {
				Entry<String, Object> e = entryKeyIterator.next();
				postParams.append("&").append(e.getKey()).append("=").append(e.getValue().toString());
			}
			/*if(postParams.length() > 0) {
				postParams.setCharAt(postParams.length() - 1, ' ');
			}*/
			String content = null;
			//调用方式
			if(JobCalltype.MS.getCode() == taskJob.getCalltype().intValue()) {
				//微服务
				try {
					String serviceId = taskJob.getLink().substring(0, taskJob.getLink().indexOf(":"));
					String url = taskJob.getLink().substring(taskJob.getLink().indexOf(":") + 1);

					LoadBalancerClient loadBalancer = FrameSpringBeanUtil.getBean(LoadBalancerClient.class);
					ServiceInstance instance = loadBalancer.choose(serviceId);
					String baseUri = instance.getUri().toString();
					link = baseUri + url;
					//http或https的形式
					content = FrameHttpUtil.post(link, params);
				} catch (RuntimeException e) {
					LOGGER.error("微服务未启动或地址有误");
				}
			} else {
				//http或https的形式
				content = FrameHttpUtil.post(link, params);
			}

			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("\n" + time + "-调用任务 ID【" + taskJob.getId() + "】名称【" + taskJob.getName() + "】\n请求地址: " + link);
			}
			//根据响应的内容做相应的记录
			Integer status = JobLogStatus.NORMAL.getCode();
			String toMails = taskProject.getRecemail();
			if(FrameStringUtil.isEmpty(content)) {
				status = JobLogStatus.ERROR.getCode();
				content = JobLogStatus.ERROR.getName();

				//处理是否需要发邮件
				if(Boolean.TRUE.getCode() == taskProject.getIsrecemail().intValue()
						&& Boolean.TRUE.getCode()  == taskJob.getIsfailmail().intValue()) {
					if(LOGGER.isInfoEnabled()) {
						LOGGER.info("调用任务【" + taskJob.getName() + "】请求失败，发送邮件");
					}
					if(FrameStringUtil.isNotEmpty(toMails)) {
						//发送失败邮件
						StringBuilder title = new StringBuilder();
						title.append(time).append("-调用任务【").append(taskProject.getName()).append("-").append(taskJob.getName()).append("】失败!!!");
						StringBuilder mailContent = new StringBuilder();
						mailContent.append("项目名称：").append(taskProject.getName()).append("<br/>");
						mailContent.append("任务名称：").append(taskJob.getName()).append("<br/>");
						mailContent.append("任务描述：").append(taskJob.getRemark()).append("<br/>");
						mailContent.append("调用时间：").append(time).append("<br/>");
						mailContent.append("调用地址：").append(link).append("<br/>");
						mailContent.append("请求参数：").append(postParams.toString()).append("<br/>");
						mailContent.append("错误原因：可能是接口地址不通，或网络不通");

						SysConfigService configService = FrameSpringBeanUtil.getBean(SysConfigService.class);
						SendMailUtil.sendMail(configService.getValue(Config.MAIL_SMTP), configService.getValue(Config.MAIL_FROM), configService.getValue(Config.MAIL_USERNAME), configService.getValue(Config.MAIL_PASSWORD),
								toMails, title.toString(), mailContent.toString());
					}
				}
			} else {
				//调度成功
				if(FrameStringUtil.isNotEmpty(content) && RuleVerifyUtil.isHttpResultSendMail(content)) {
					//根据返回结果发邮件
					try {
						Map<String, Object> map = FrameJsonUtil.toMap(content);
						String isSendMail = FrameMapUtil.getString(map, "isSendMail");
						if("true".equals(isSendMail)) {
							String mailTitle = FrameMapUtil.getString(map, "mailTitle");
							String mailContent = FrameMapUtil.getString(map, "mailContent");

							SysConfigService configService = FrameSpringBeanUtil.getBean(SysConfigService.class);
							SendMailUtil.sendMail(configService.getValue(Config.MAIL_SMTP), configService.getValue(Config.MAIL_FROM), configService.getValue(Config.MAIL_USERNAME), configService.getValue(Config.MAIL_PASSWORD),
									toMails, mailTitle, mailContent);
						}
					} catch (Exception e) {
						//不需要发邮件
						LOGGER.error("配置发送邮件的格式错误!" + e.getMessage());
					}
				}
			}

			link = link + " | params=" + postParams.toString();
			taskJobLogService.save(new TaskJobLog(taskJob.getId(), FrameTimeUtil.parseDate(time), status, link, content));
		} catch (Exception e) {
			LOGGER.error("执行任务异常: " + e.getMessage(), e);
		}
	}
}
