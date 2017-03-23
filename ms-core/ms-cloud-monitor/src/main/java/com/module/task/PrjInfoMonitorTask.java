package com.module.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.cloud.client.ServiceInstance;

import com.module.admin.prj.enums.PrjInfoContainer;
import com.module.admin.prj.enums.PrjInfoStatus;
import com.module.admin.prj.enums.PrjMonitorMonitorStatus;
import com.module.admin.prj.enums.PrjMonitorType;
import com.module.admin.prj.pojo.PrjInfo;
import com.module.admin.prj.pojo.PrjMonitor;
import com.module.admin.prj.service.PrjInfoService;
import com.module.admin.prj.service.PrjMonitorService;
import com.module.api.service.ServiceService;
import com.system.comm.enums.Boolean;
import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.comm.utils.FrameTimeUtil;

/**
 * 任务调度
 * @author yuejing
 * @date 2016年10月22日 上午9:58:59
 * @version V1.0.0
 */
public class PrjInfoMonitorTask {

	private static final Logger LOGGER = Logger.getLogger(PrjInfoMonitorTask.class);
	//private static Map<Integer, java.lang.Boolean> failSendEmails = new HashMap<Integer, java.lang.Boolean>();

	public void run() {
		LOGGER.info("========================= 初始化客户端的任务 - 成功 ===========================");
		ScheduledExecutorService service = Executors.newScheduledThreadPool(100);
		//final PrjMonitorService monitorService = FrameSpringBeanUtil.getBean(PrjMonitorService.class);
		final PrjInfoService prjInfoService = FrameSpringBeanUtil.getBean(PrjInfoService.class);
		final PrjMonitorService prjMonitorService = FrameSpringBeanUtil.getBean(PrjMonitorService.class);
		final ServiceService serviceService = FrameSpringBeanUtil.getBean(ServiceService.class);
		//线程，每隔5秒调用一次
		Runnable runnable = new Runnable() {
			public void run() {
				//判断客户端的心跳是否失败
				List<String> list = serviceService.serviceNames();
				List<Integer> prjmIds = new ArrayList<Integer>();
				for (String serviceId : list) {
					//新增项目
					PrjInfo prjInfo = new PrjInfo();
					//项目编码
					prjInfo.setCode(serviceId);
					//名称
					prjInfo.setName(serviceId);
					//描叙
					prjInfo.setRemark("暂无");
					//添加人
					prjInfo.setUserId(1);
					//发布的版本号
					prjInfo.setReleaseVersion("暂无");
					//发布的版本时间
					prjInfo.setReleaseTime(FrameTimeUtil.getTime());
					//状态[10正常、20停用]
					prjInfo.setStatus(PrjInfoStatus.NORMAL.getCode());
					//容器类型[10tomcat、20自定义服务、100其它]
					prjInfo.setContainer(PrjInfoContainer.LINUX_CUST.getCode());
					//shell脚本
					//prjInfo.setShellScript("暂无");
					prjInfoService.saveOrUpdate(prjInfo);
					List<ServiceInstance> sis = serviceService.serviceList(serviceId);
					for (ServiceInstance si : sis) {
						//新增监控的服务
						PrjMonitor prjMonitor = new PrjMonitor();
						//项目编号
						prjMonitor.setPrjId(prjInfo.getPrjId());
						//监控类型[10服务、20数据库、30缓存、40其它]
						prjMonitor.setType(PrjMonitorType.WEB.getCode());
						//描叙
						prjMonitor.setRemark(si.getHost() + ":" + si.getPort());
						//是否检测
						prjMonitor.setMonitorIs(Boolean.TRUE.getCode());
						//检测状态[10正常、20异常]
						prjMonitor.setMonitorStatus(PrjMonitorMonitorStatus.NORMAL.getCode());
						prjMonitor.setMonitorTime(FrameTimeUtil.getTime());
						//检测地址
						prjMonitor.setMonitorUrl(si.getUri().toString());
						prjMonitorService.saveOrUpdate(prjMonitor);
						prjmIds.add(prjMonitor.getPrjmId());
					}
				}
				//将非这些的监控服务修改为未启动
				for (Integer prjmId : prjmIds) {
					PrjMonitor pm = prjMonitorService.get(prjmId);
					dealMonitor(pm);
				}
			}
			
			private void dealMonitor(PrjMonitor pm) {
				/*String link = pm.getMonitorUrl();
				Map<String, Object> params = new HashMap<String, Object>();
				String content = null;
				try {
					content = FrameHttpUtil.post(link, params);
				} catch (IOException e) {
					LOGGER.error("请求异常: " + e.getMessage());
				}
				
				//根据响应的内容做相应的记录
				if(FrameStringUtil.isNotEmpty(content) && content.trim().equalsIgnoreCase(pm.getMonitorSuccStr().trim())) {
					//监听成功

					java.lang.Boolean isSendEmail = failSendEmails.get(pm.getPrjmId());
					if(PrjMonitorMonitorStatus.ERROR.getCode() == pm.getMonitorStatus() &&
							isSendEmail != null && isSendEmail == true) {
						//之前监听失败，这次成功，发送回复邮件
						String time = FrameTimeUtil.getStrTime();
						StringBuilder title = new StringBuilder();
						title.append("检测项目【").append(pm.getPrjName()).append("-").append(pm.getTypeName()).append("】恢复成功!");
						StringBuilder mailContent = new StringBuilder();
						mailContent.append("项目名称：").append(pm.getPrjName()).append("<br/>");
						mailContent.append("监听类型：").append(pm.getTypeName()).append("<br/>");
						mailContent.append("描述：").append(pm.getRemark()).append("<br/>");
						mailContent.append("调用时间：").append(time).append("<br/>");
						mailContent.append("调用地址：").append(link).append("<br/>");
						mailContent.append("内容：项目已自动恢复成功");
						
						String toMails = pm.getMonitorFailEmail();

						SysConfigService configService = FrameSpringBeanUtil.getBean(SysConfigService.class);
						FrameMailUtil theMail = new FrameMailUtil(configService.getValue(SysConfigCode.MAIL_SMTP),
								configService.getValue(SysConfigCode.MAIL_FROM),
								configService.getValue(SysConfigCode.MAIL_USERNAME),
								configService.getValue(SysConfigCode.MAIL_PASSWORD));
						// 抄送人邮件地址
						String copyto = "";
						theMail.send(toMails, copyto, title.toString(), mailContent.toString());
						//设置为不需要发送了
						failSendEmails.put(pm.getPrjmId(), false);
					}

					//修改monitorTime和monitorStatus，修改monitorFailNum为0
					monitorService.updateMonitorSucc(pm.getPrjmId());
				} else {
					//监听失败
					int monitorFailNum = pm.getMonitorFailNum().intValue();
					monitorFailNum ++;
					
					//修改monitorTime和monitorStatus，修改monitorFailNum + 1
					monitorService.updateMonitorFail(pm.getPrjmId(), monitorFailNum);

					//判断上次发送邮件时间是否在范围内，处理是否需要发邮件
					Date mfsTime = pm.getMonitorFailSendTime();
					if(mfsTime == null) {
						mfsTime = FrameTimeUtil.getTime();
					}
					long diffNum = FrameTimeUtil.getDateDiff(mfsTime, FrameTimeUtil.getTime(), 1);
					if(pm.getMonitorFailSendTime() == null ||
							( diffNum > pm.getMonitorFailSendInterval().intValue() &&
							pm.getMonitorFailNum() != null && monitorFailNum >= pm.getMonitorFailNumRemind().intValue()
							)) {

						if(LOGGER.isInfoEnabled()) {
							LOGGER.info("检测【" + pm.getPrjName() + "-" + pm.getTypeName() + "】请求URL[ " + pm.getMonitorUrl() + " ]失败，发送邮件");
						}
						String time = FrameTimeUtil.getStrTime();
						//发送失败邮件
						StringBuilder title = new StringBuilder();
						title.append("检测项目【").append(pm.getPrjName()).append("-").append(pm.getTypeName()).append("】失败!!!");
						StringBuilder mailContent = new StringBuilder();
						mailContent.append("项目名称：").append(pm.getPrjName()).append("<br/>");
						mailContent.append("监听类型：").append(pm.getTypeName()).append("<br/>");
						mailContent.append("描述：").append(pm.getRemark()).append("<br/>");
						mailContent.append("调用时间：").append(time).append("<br/>");
						mailContent.append("调用地址：").append(link).append("<br/>");
						mailContent.append("错误原因：可能是接口地址不通，或网络不通");
						
						String toMails = pm.getMonitorFailEmail();

						SysConfigService configService = FrameSpringBeanUtil.getBean(SysConfigService.class);
						FrameMailUtil theMail = new FrameMailUtil(configService.getValue(SysConfigCode.MAIL_SMTP),
								configService.getValue(SysConfigCode.MAIL_FROM),
								configService.getValue(SysConfigCode.MAIL_USERNAME),
								configService.getValue(SysConfigCode.MAIL_PASSWORD));
						// 抄送人邮件地址
						String copyto = "";
						boolean bool = theMail.send(toMails, copyto, title.toString(), mailContent.toString());
						//设置为发送过邮件
						failSendEmails.put(pm.getPrjmId(),  true);
						
						if(bool) {
							//处理其它业务（将monitorFailNum，重置为0，monitorFailSendTime修改为最新时间）
							monitorService.updateMonitorFailSendInfo(pm.getPrjmId());
						}
					}
				}*/

			}
		};
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		service.scheduleAtFixedRate(runnable, 10, 5, TimeUnit.SECONDS);
	}

}
