package com.module.admin.prj.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.module.admin.prj.pojo.PrjApi;
import com.module.admin.prj.pojo.PrjMonitor;
import com.module.admin.prj.service.PrjApiService;
import com.module.admin.prj.service.PrjInfoService;
import com.module.admin.prj.service.PrjMonitorService;
import com.system.auth.AuthUtil;
import com.system.comm.model.KvEntity;
import com.system.comm.utils.FrameHttpUtil;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameMapUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * prj_api的Controller
 * @author yuejing
 * @date 2016-11-30 13:30:00
 * @version V1.0.0
 */
@Controller
public class PrjApiController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrjApiController.class);

	@Autowired
	private PrjApiService prjApiService;
	@Autowired
	private PrjInfoService prjInfoService;
	@Autowired
	private PrjMonitorService prjMonitorService;

	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/prjApi/f-view/manager")
	public String manger(HttpServletRequest request, ModelMap modelMap) {
		//获取所有服务
		List<KvEntity> prjInfos = prjInfoService.findKvAll();
		modelMap.put("prjInfos", prjInfos);
		return "admin/prj/api-manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/prjApi/f-json/pageQuery")
	@ResponseBody
	public void pageQuery(HttpServletRequest request, HttpServletResponse response,
			PrjApi prjApi) {
		ResponseFrame frame = null;
		try {
			frame = prjApiService.pageQuery(prjApi);
		} catch (Exception e) {
			LOGGER.error("分页获取信息异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}

	@RequestMapping(name = "查看API详情", value = "/prjApi/f-view/dtl")
	public String dtl(HttpServletRequest request, ModelMap modelMap,
			Integer prjId, String path) {
		//获取所有服务
		PrjApi prjApi = prjApiService.get(prjId, path);
		modelMap.put("prjApi", prjApi);
		if(prjApi != null) {
			List<Map<String, String>> params = FrameJsonUtil.toList(prjApi.getParams(), Map.class);
			modelMap.put("params", params);
			List<Map<String, String>> response = FrameJsonUtil.toList(prjApi.getResponse(), Map.class);
			/*Map<String, Object> data = new HashMap<String, Object>();
			for (Map<String, String> map : response) {
				String code = FrameMapUtil.getString(map, "code");
				String pCode = FrameMapUtil.getString(map, "pCode");
				if(FrameStringUtil.isEmpty(pCode)) {
					for (Map<String, String> cldMap : response) {
						String cldPCode = FrameMapUtil.getString(cldMap, "pCode");
						if(code.equals(cldPCode)) {
							
						}
					}
					//一级类型
					data.put(code, "");
				}
			}*/
			modelMap.put("response", response);
		}
		return "admin/prj/api-dtl";
	}

	@RequestMapping(value = "/prjApi/f-json/request")
	@ResponseBody
	public void request(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramsMap = getParamsMap(request);
		String monitorPrjId = FrameMapUtil.getString(paramsMap, "monitorPrjId");
		if(FrameStringUtil.isNotEmpty(monitorPrjId)) {
			paramsMap.remove("monitorPrjId");
		} else {
			writerJson(response, new ResponseFrame(-2, "没有对应的API信息"));
			return;
		}
		String monitorPath = FrameMapUtil.getString(paramsMap, "monitorPath");
		if(FrameStringUtil.isNotEmpty(monitorPath)) {
			paramsMap.remove("monitorPath");
		} else {
			writerJson(response, new ResponseFrame(-2, "没有对应的API信息"));
			return;
		}
		PrjMonitor monitor = prjMonitorService.getService(monitorPrjId);
		if(monitor == null) {
			writerJson(response, new ResponseFrame(-3, "服务没有启动"));
			return;
		}
		String url = "http://" + monitor.getRemark() + monitorPath;
		String clientId = FrameMapUtil.getString(paramsMap, "clientId");
		String time = String.valueOf(System.currentTimeMillis());
		String sercret = FrameMapUtil.getString(paramsMap, "token");
		paramsMap.put("clientId", clientId);
		paramsMap.put("time", time);
		paramsMap.put("sign", AuthUtil.auth(clientId, time, sercret));
		if(FrameStringUtil.isNotEmpty(FrameMapUtil.getString(paramsMap, "token"))) {
			//移除token
			paramsMap.remove("token");
		}
		ResponseFrame apiFrame = post(url, paramsMap);
		StringBuffer requestUrl = new StringBuffer(url);
		if(paramsMap.size() > 0) {
			requestUrl.append("?");
			Iterator<Entry<String, Object>> entryKeyIterator = paramsMap.entrySet().iterator();
			while (entryKeyIterator.hasNext()) {
				Entry<String, Object> e = entryKeyIterator.next();
				requestUrl.append(e.getKey()).append("=").append(e.getValue());
				requestUrl.append("&");
			}
			requestUrl.setCharAt(requestUrl.length() - 1, ' ');
		}
		ResponseFrame frame = new ResponseFrame();
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("result", apiFrame);
		body.put("requestUrl", requestUrl.toString().trim());
		frame.setBody(body);
		frame.setSucc();
		writerJson(response, frame);
	}

	private static ResponseFrame post(String url, Map<String, Object> params) {
		try {
			String result = FrameHttpUtil.post(url, params);
			return FrameJsonUtil.toObject(result, ResponseFrame.class);
		} catch (Exception e) {
			LOGGER.error("调用接口异常: " + e.getMessage(), e);
		}
		return null;
	}
}