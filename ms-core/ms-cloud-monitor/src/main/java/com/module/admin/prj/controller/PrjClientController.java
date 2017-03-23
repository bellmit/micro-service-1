package com.module.admin.prj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.module.admin.BaseController;
import com.module.admin.cli.pojo.CliInfo;
import com.module.admin.prj.enums.PrjClientStatus;
import com.module.admin.prj.pojo.PrjClient;
import com.module.admin.prj.pojo.PrjInfo;
import com.module.admin.prj.pojo.PrjVersion;
import com.module.admin.prj.service.PrjClientService;
import com.module.admin.prj.service.PrjInfoService;
import com.module.admin.prj.service.PrjVersionService;
import com.module.comm.utils.ClientUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * prj_client的Controller
 * @author yuejing
 * @date 2016-10-20 17:54:59
 * @version V1.0.0
 */
@Controller
public class PrjClientController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(PrjClientController.class);

	@Autowired
	private PrjClientService prjClientService;
	@Autowired
	private PrjInfoService prjInfoService;
	@Autowired
	private PrjVersionService prjVersionService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/prjClient/f-view/manager")
	public String manger(HttpServletRequest request) {
		return "admin/prj/client-manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/prjClient/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			PrjClient prjClient) {
		ResponseFrame frame = null;
		try {
			frame = prjClientService.pageQuery(prjClient);
		} catch (Exception e) {
			LOGGER.error("分页获取信息异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}

	/**
	 * 跳转到编辑页[包含新增和编辑]
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/prjClient/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer prjId, String clientId) {
		if(prjId != null) {
			modelMap.put("prjClient", prjClientService.get(prjId, clientId));
		}
		return "admin/prj/client-edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/prjClient/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
			PrjClient prjClient) {
		ResponseFrame frame = null;
		try {
			frame = prjClientService.saveOrUpdate(prjClient);
		} catch (Exception e) {
			LOGGER.error("保存异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/prjClient/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			Integer prjId, String clientId) {
		ResponseFrame frame = null;
		try {
			frame = prjClientService.delete(prjId, clientId);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}

	/**
	 * 发布到所有客户端
	 * @return
	 */
	@RequestMapping(value = "/prjClient/f-json/releaseAll")
	@ResponseBody
	public void releaseAll(HttpServletRequest request, HttpServletResponse response,
			Integer prjId) {
		ResponseFrame frame = new ResponseFrame();
		try {
			PrjInfo pi = prjInfoService.get(prjId);
			PrjVersion version = prjVersionService.get(prjId, pi.getReleaseVersion());
			if(version == null) {
				frame.setCode(-2);
				frame.setMessage("还没有设置要发布的版本噢");
				writerJson(response, frame);
				return;
			}
			List<CliInfo> clients = prjClientService.findByPrjId(prjId);
			StringBuffer errorBuffer = new StringBuffer();
			for (CliInfo cliInfo : clients) {
				try {
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					paramsMap.put("prjId", pi.getPrjId());
					paramsMap.put("code", pi.getCode());
					paramsMap.put("name", pi.getName());
					paramsMap.put("version", pi.getReleaseVersion());
					//下载路径
					paramsMap.put("pathUrl", version.getPathUrl());
					//容器类型
					paramsMap.put("container", pi.getContainer());
					//执行的shell命令
					if(FrameStringUtil.isNotEmpty(cliInfo.getShellScript())) {
						paramsMap.put("shellScript", cliInfo.getShellScript());
					} else {
						paramsMap.put("shellScript", pi.getShellScript());
					}
					ResponseFrame clientFrame = ClientUtil.post(cliInfo.getClientId(), cliInfo.getToken(), cliInfo.getIp(), cliInfo.getPort(),
							"/project/release", paramsMap);
					if(ResponseCode.SUCC.getCode() == clientFrame.getCode().intValue()) {
						//修改为发布中
						prjClientService.updateStatus(cliInfo.getClientId(), prjId, PrjClientStatus.ING.getCode(), null);
					} else {
						//处理发布不成功的情况
						errorBuffer.append("客户端[").append(cliInfo.getIp()).append(":").append(cliInfo.getPort()).append("]发布异常; ");
					}
				} catch (Exception e) {
					LOGGER.error("发布到客户端[" + cliInfo.getIp() + ":" + cliInfo.getPort() + "]异常" + e.getMessage(), e);
					errorBuffer.append("客户端[").append(cliInfo.getIp()).append(":").append(cliInfo.getPort()).append("]网络不通; ");
				}
				
			}
			if(errorBuffer.length() == 0) {
				frame.setCode(ResponseCode.SUCC.getCode());
			} else {
				frame.setCode(-404);
				frame.setMessage(errorBuffer.toString());
			}
		} catch (Exception e) {
			LOGGER.error("发布到所有客户端异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}

	/**
	 * 跳转到编辑页[包含新增和编辑]
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/prjClient/f-view/shell")
	public String shell(HttpServletRequest request, ModelMap modelMap, Integer prjId, String clientId) {
		if(prjId != null) {
			modelMap.put("prjClient", prjClientService.get(prjId, clientId));
			PrjInfo prjInfo = prjInfoService.get(prjId);
			modelMap.put("prjShellScript", prjInfo.getShellScript());
		}
		return "admin/prj/client-shell";
	}
	
	/**
	 * 修改shell
	 * @return
	 */
	@RequestMapping(value = "/prjClient/f-json/updateShellScript")
	@ResponseBody
	public void updateShellScript(HttpServletRequest request, HttpServletResponse response,
			Integer prjId, String clientId, String shellScript) {
		ResponseFrame frame = null;
		try {
			frame = prjClientService.updateShellScript(clientId, prjId, shellScript);
		} catch (Exception e) {
			LOGGER.error("修改shell异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
}