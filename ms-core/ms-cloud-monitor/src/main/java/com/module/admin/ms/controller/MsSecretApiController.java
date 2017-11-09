package com.module.admin.ms.controller;

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
import com.module.admin.ms.pojo.MsSecretApi;
import com.module.admin.ms.service.MsSecretApiService;
import com.module.admin.ms.service.MsSecretService;
import com.module.admin.prj.service.PrjInfoService;
import com.system.comm.model.KvEntity;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 密钥的API的Controller
 * @author yuejing
 * @date 2016-10-20 17:55:37
 * @version V1.0.0
 */
@Controller
public class MsSecretApiController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MsSecretApiController.class);

	@Autowired
	private MsSecretService msSecretService;
	@Autowired
	private MsSecretApiService msSecretApiService;
	@Autowired
	private PrjInfoService prjInfoService;

	@RequestMapping(value = "/msSecretApi/f-view/manager")
	public String manger(HttpServletRequest request, ModelMap modelMap,
			String cliId) {
		modelMap.put("msSecret", msSecretService.get(cliId));
		List<KvEntity> prjInfos = prjInfoService.findKvAll();
		modelMap.put("prjInfos", prjInfos);
		return "admin/ms/msSecretApi-manager";
	}

	@RequestMapping(value = "/msSecretApi/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			MsSecretApi msSecretApi) {
		ResponseFrame frame = null;
		try {
			frame = msSecretApiService.pageQuery(msSecretApi);
		} catch (Exception e) {
			LOGGER.error("分页获取信息异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}

	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/msSecretApi/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap) {
		List<KvEntity> prjInfos = prjInfoService.findKvAll();
		modelMap.put("prjInfos", prjInfos);
		/*MsSecretApi msSecretApi = msSecretApiService.get(cliId);
		modelMap.put("msSecretApi", msSecretApi);*/
		return "admin/ms/msSecretApi-edit";
	}

	@RequestMapping(value = "/msSecretApi/f-json/save")
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response,
			MsSecretApi msSecretApi, String urls) {
		ResponseFrame frame = new ResponseFrame();
		try {
			List<String> urlList = FrameStringUtil.toArray(urls, ";");
			if(urlList.size() == 0) {
				frame.setCode(-2);
				frame.setMessage("请传入API地址");
				writerJson(response, frame);
				return;
			}
			/*if(msSecretApi.getPrjId() != null && msSecretApi.getPrjId().intValue() != 0) {
				PrjInfo prjInfo = prjInfoService.get(msSecretApi.getPrjId());
				if(prjInfo == null) {
					frame.setCode(-3);
					frame.setMessage("请选择项目");
					writerJson(response, frame);
					return;
				}
				msSecretApi.setPrjCode(prjInfo.getCode());
			} else {
				msSecretApi.setPrjCode("0");
			}*/
			for (String url : urlList) {
				msSecretApi.setUrl(url);
				msSecretApiService.save(msSecretApi);
			}
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("保存异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}

	@RequestMapping(value = "/msSecretApi/f-json/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response,
			String cliId, Integer prjId, String url) {
		ResponseFrame frame = null;
		try {
			frame = msSecretApiService.delete(cliId, prjId, url);
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			frame = new ResponseFrame();
			frame.setCode(ResponseCode.FAIL.getCode());
		}
		writerJson(response, frame);
	}

}