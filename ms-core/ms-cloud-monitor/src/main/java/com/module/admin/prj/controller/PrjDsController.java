package com.module.admin.prj.controller;

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
import com.module.admin.prj.pojo.PrjDs;
import com.module.admin.prj.service.PrjDsService;
import com.module.admin.sys.pojo.SysUser;
import com.module.comm.druid.DsUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 数据源的Controller
 * @author yuejing
 * @date 2016-10-20 17:54:59
 * @version V1.0.0
 */
@Controller
public class PrjDsController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrjDsController.class);

	@Autowired
	private PrjDsService prjDsService;

	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/prjDs/f-view/manager")
	public String manger(HttpServletRequest request) {
		return "admin/prj/ds-manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/prjDs/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			PrjDs prjDs) {
		ResponseFrame frame = null;
		try {
			frame = prjDsService.pageQuery(prjDs);
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
	@RequestMapping(value = "/prjDs/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer prjId, String code) {
		if(prjId != null && code != null) {
			modelMap.put("prjDs", prjDsService.get(prjId, code));
		}
		return "admin/prj/ds-edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/prjDs/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
			PrjDs prjDs) {
		ResponseFrame frame = null;
		try {
			SysUser user = getSessionUser(request);
			prjDs.setUserId(user.getUserId());
			frame = prjDsService.saveOrUpdate(prjDs);
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
	@RequestMapping(value = "/prjDs/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			Integer prjId, String code) {
		ResponseFrame frame = null;
		try {
			frame = prjDsService.delete(prjId, code);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}

	@RequestMapping(value = "/prjDs/f-json/test")
	@ResponseBody
	public void test(HttpServletRequest request, HttpServletResponse response,
			PrjDs prjDs) {
		ResponseFrame frame = null;
		try {
			if(FrameStringUtil.isEmpty(prjDs.getType()) || FrameStringUtil.isEmpty(prjDs.getDriverClass())) {
				prjDs = prjDsService.get(prjDs.getPrjId(), prjDs.getCode());
			}
			DsUtil ds = new DsUtil();
			ds.init(prjDs.getDriverClass(), prjDs.getUrl(),
					prjDs.getUsername(), prjDs.getPassword(),
					prjDs.getInitialSize(), prjDs.getMaxIdle(),
					prjDs.getMinIdle());
			frame = ds.test(prjDs.getTestSql());
		} catch (Exception e) {
			LOGGER.error("test异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}
}