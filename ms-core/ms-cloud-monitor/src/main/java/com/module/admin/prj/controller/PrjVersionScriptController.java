package com.module.admin.prj.controller;

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
import com.module.admin.prj.pojo.PrjDs;
import com.module.admin.prj.pojo.PrjVersionScript;
import com.module.admin.prj.service.PrjDsService;
import com.module.admin.prj.service.PrjVersionScriptService;
import com.module.admin.sys.pojo.SysUser;
import com.module.comm.druid.DsUtil;
import com.system.comm.enums.Boolean;
import com.system.comm.model.KvEntity;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 项目版本的脚本的Controller
 * @author yuejing
 * @date 2016-10-20 17:54:59
 * @version V1.0.0
 */
@Controller
public class PrjVersionScriptController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrjVersionScriptController.class);

	@Autowired
	private PrjVersionScriptService prjVersionScriptService;
	@Autowired
	private PrjDsService prjDsService;

	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/prjVersionScript/f-view/manager")
	public String manger(HttpServletRequest request) {
		return "admin/prj/versionScript-manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/prjVersionScript/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			PrjVersionScript prjVersionScript) {
		ResponseFrame frame = null;
		try {
			frame = prjVersionScriptService.pageQuery(prjVersionScript);
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
	@RequestMapping(value = "/prjVersionScript/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer pvsId, Integer prjId) {
		if(pvsId != null) {
			modelMap.put("prjVersionScript", prjVersionScriptService.get(pvsId));
		}
		//获取项目对应的数据源
		List<KvEntity> dsList = prjDsService.findKvByPrjId(prjId);
		modelMap.put("dsList", dsList);
		return "admin/prj/versionScript-edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/prjVersionScript/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
			PrjVersionScript prjVersionScript) {
		ResponseFrame frame = null;
		try {
			SysUser user = getSessionUser(request);
			prjVersionScript.setUserId(user.getUserId());
			frame = prjVersionScriptService.saveOrUpdate(prjVersionScript);
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
	@RequestMapping(value = "/prjVersionScript/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			Integer pvsId) {
		ResponseFrame frame = null;
		try {
			frame = prjVersionScriptService.delete(pvsId);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}

	@RequestMapping(value = "/prjVersionScript/f-json/exec")
	@ResponseBody
	public void exec(HttpServletRequest request, HttpServletResponse response,
			Integer pvsId, String type) {
		ResponseFrame frame = new ResponseFrame();
		try {
			int succNum = 0;
			int failNum = 0;
			String failSql = "";
			PrjVersionScript pvs = prjVersionScriptService.get(pvsId);
			PrjDs prjDs = prjDsService.get(pvs.getPrjId(), pvs.getDsCode());
			String[] sqls = null;
			if("up".equals(type)) {
				if(FrameStringUtil.isEmpty(pvs.getUpSql())) {
					frame.setCode(-2);
					frame.setMessage("没有可执行的sql");
					writerJson(response, frame);
					return;
				}
				sqls = pvs.getUpSql().split(";");
				prjVersionScriptService.updateIsUp(pvsId, Boolean.TRUE.getCode());
			} else {
				if(FrameStringUtil.isEmpty(pvs.getCallbackSql())) {
					frame.setCode(-2);
					frame.setMessage("没有可执行的sql");
					writerJson(response, frame);
					return;
				}
				sqls = pvs.getCallbackSql().split(";");
				prjVersionScriptService.updateIsUp(pvsId, Boolean.FALSE.getCode());
			}
			DsUtil ds = new DsUtil();
			ds.init(prjDs.getDriverClass(), prjDs.getUrl(),
					prjDs.getUsername(), prjDs.getPassword(),
					prjDs.getInitialSize(), prjDs.getMaxIdle(),
					prjDs.getMinIdle());
			for (String sql : sqls) {
				if(FrameStringUtil.isEmpty(sql)) {
					continue;
				}
				ResponseFrame sqlFrame = ds.exec(sql);
				if(ResponseCode.SUCC.getCode() == sqlFrame.getCode().intValue()) {
					succNum ++;
				} else {
					failNum ++;
					failSql += sql + ";";
				}
			}
			ds.close();
			String body = "成功:" + succNum + "条;<br/>失败:" + failNum + "条;<br/>" + failSql;
			frame.setSucc();
			frame.setBody(body);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}
}