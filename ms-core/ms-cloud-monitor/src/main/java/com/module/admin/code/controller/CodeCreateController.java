package com.module.admin.code.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import com.module.admin.code.core.DbDs;
import com.module.admin.code.pojo.CodeCreate;
import com.module.admin.code.service.CodeCreateService;
import com.module.admin.code.utils.DbDsUtil;
import com.module.admin.prj.pojo.PrjDs;
import com.module.admin.prj.service.PrjDsService;
import com.module.admin.sys.pojo.SysUser;
import com.ms.env.Env;
import com.ms.env.EnvUtil;
import com.system.comm.model.KvEntity;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 源码项目生成源码表的Controller
 * @author yuejing
 * @date 2016-10-20 17:54:59
 * @version V1.0.0
 */
@Controller
public class CodeCreateController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodeCreateController.class);
	@Autowired
	private CodeCreateService codeCreateService;
	@Autowired
	private PrjDsService prjDsService;

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/codeCreate/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			CodeCreate codeCreate) {
		ResponseFrame frame = null;
		try {
			frame = codeCreateService.pageQuery(codeCreate);
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
	@RequestMapping(value = "/codeCreate/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap,
			Integer id, Integer prjId) {
		if(id != null) {
			modelMap.put("codeCreate", codeCreateService.get(id));
		}
		//获取项目对应的数据源
		List<KvEntity> dsList = prjDsService.findKvByPrjId(prjId);
		modelMap.put("dsList", dsList);
		return "admin/code/create-edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/codeCreate/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
			CodeCreate codeCreate) {
		ResponseFrame frame = null;
		try {
			SysUser user = getSessionUser(request);
			codeCreate.setUserId(user.getUserId());
			frame = codeCreateService.saveOrUpdate(codeCreate);
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
	@RequestMapping(value = "/codeCreate/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			Integer id) {
		ResponseFrame frame = null;
		try {
			frame = codeCreateService.delete(id);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}
	@RequestMapping(value = "/codeCreate/f-json/findTables")
	@ResponseBody
	public void findTables(HttpServletRequest request, HttpServletResponse response,
			String dsCode, Integer prjId, String dbName) {
		ResponseFrame frame = new ResponseFrame();
		try {
			PrjDs ds = prjDsService.get(prjId, dsCode);
			if(ds == null) {
				frame.setCode(-2);
				frame.setMessage("不存在数据源");
				writerJson(response, frame);
				return;
			}
			DbDs dbDs = DbDsUtil.getDbds(ds, dbName);
			List<String> tables = dbDs.findAllTableName();
			dbDs.close();
			frame.setBody(tables);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("获取所有表名异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}
	
	@RequestMapping(value = "/codeCreate/f-view/download")
	public void download(HttpServletRequest request, HttpServletResponse response, Integer id) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		CodeCreate cc = codeCreateService.get(id);
		String url = cc.getDownload();
		String downLoadPath = EnvUtil.get(Env.CODE_SOURCE_PATH) + url;
		//String downLoadPath = ctxPath;

		long fileLength = new File(downLoadPath).length();
		int separator = url.lastIndexOf(File.separator);
		if(separator == -1) {
			separator = url.lastIndexOf("/");
		}
		response.setHeader("Content-disposition", "attachment; filename=" + url.substring(separator + 1));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
	}
}