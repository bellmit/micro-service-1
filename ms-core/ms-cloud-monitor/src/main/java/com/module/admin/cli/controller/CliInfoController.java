package com.module.admin.cli.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.module.admin.BaseController;
import com.module.admin.cli.pojo.CliInfo;
import com.module.admin.cli.service.CliInfoService;
import com.module.admin.sys.pojo.SysUser;
import com.system.comm.model.Orderby;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * cli_info的Controller
 * @author yuejing
 * @date 2016-10-20 17:55:37
 * @version V1.0.0
 */
@Controller
public class CliInfoController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CliInfoController.class);

	@Autowired
	private CliInfoService cliInfoService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cliInfo/f-view/manager")
	public String manger(HttpServletRequest request) {
		return "admin/cli/info-manager";
	}
	/**
	 * 跳转到监控页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cliInfo/f-view/monitor")
	public String monitor(HttpServletRequest request) {
		return "admin/cli/info-monitor";
	}
	
	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/cliInfo/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			CliInfo cliInfo, String sourceType) {
		ResponseFrame frame = null;
		try {
			if("monitor".equals(sourceType)) {
				List<Orderby> orderbys = new ArrayList<Orderby>();
				orderbys.add(new Orderby("activityStatus", "desc", 0));
				cliInfo.setOrderbys(orderbys);
			}
			frame = cliInfoService.pageQuery(cliInfo);
		} catch (Exception e) {
			LOGGER.error("分页获取信息异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
	/**
	 * 获取信息列表
	 * @return
	 */
	@RequestMapping(value = "/cliInfo/f-json/find")
	@ResponseBody
	public void find(HttpServletRequest request, HttpServletResponse response,
			CliInfo cliInfo) {
		ResponseFrame frame = null;
		try {
			frame = cliInfoService.find(cliInfo);
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
	@RequestMapping(value = "/cliInfo/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, String clientId) {
		if(clientId != null) {
			modelMap.put("cliInfo", cliInfoService.get(clientId));
		}
		return "admin/cli/info-edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/cliInfo/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
			CliInfo cliInfo) {
		ResponseFrame frame = null;
		try {
			SysUser user = getSessionUser(request);
			cliInfo.setUserId(user.getUserId());
			frame = cliInfoService.saveOrUpdate(cliInfo);
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
	@RequestMapping(value = "/cliInfo/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			String clientId) {
		ResponseFrame frame = null;
		try {
			frame = cliInfoService.delete(clientId);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}
}
