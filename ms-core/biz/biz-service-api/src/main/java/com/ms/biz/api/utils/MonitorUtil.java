package com.ms.biz.api.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.system.auth.AuthUtil;
import com.system.comm.utils.FrameHttpUtil;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 请求服务端的工具类
 * @author yuejing
 * @date 2016年10月21日 下午7:57:10
 * @version V1.0.0
 */
public class MonitorUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorUtil.class);
	
	public static String clientId;
	public static String sercret;
	public static String serverHost;

	/**
	 * 请求服务端的api
	 * @param url
	 * @param paramsMap
	 * @return
	 * @throws IOException 
	 */
	public static ResponseFrame post(String url, Map<String, Object> paramsMap) throws IOException {
		String time = String.valueOf(System.currentTimeMillis());
		paramsMap.put("clientId", clientId);
		paramsMap.put("time", time);
		paramsMap.put("sign", AuthUtil.auth(clientId, time, sercret));
		String result = FrameHttpUtil.post(serverHost + url, paramsMap);
		return FrameJsonUtil.toObject(result, ResponseFrame.class);
	}
	
	/**
	 * 获取所有请求的路径<br>
	 * 注意: 
	 * 	map.get("name")		文字描叙
	 * 	map.get("path")		请求路径
	 * 	map.get("method")	方法的描叙字符串
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String, String>> findAll() {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		AbstractHandlerMethodMapping mapping = FrameSpringBeanUtil.getBean(AbstractHandlerMethodMapping.class);
		Map<RequestMappingInfo, HandlerMethod> hmMap = mapping.getHandlerMethods();
		Iterator<Entry<RequestMappingInfo, HandlerMethod>> entryKeyIterator = hmMap.entrySet().iterator();
		while (entryKeyIterator.hasNext()) {
			Entry<RequestMappingInfo, HandlerMethod> e = entryKeyIterator.next();
			HandlerMethod value = e.getValue();
			Annotation[] anns = value.getMethod().getDeclaredAnnotations();
			for (Annotation annotation : anns) {
				if(annotation instanceof RequestMapping) {
					RequestMapping rm = (RequestMapping) annotation;
					StringBuffer path = new StringBuffer();
					for (String str : rm.value()) {
						path.append(str).append(";");
					}
					if(path.length() > 0) {
						path.setCharAt(path.length() - 1, ' ');
						Map<String, String> map = new HashMap<String, String>();
						map.put("name", rm.name());
						map.put("path", path.toString().trim());
						map.put("method", value.getMethod().toString());
						data.add(map);
					}
				}
			}
		}
		return data;
	}
	
	/**
	 * 初始化监控的API
	 * @param appName	为微服务的serviceId
	 * @throws IOException
	 */
	public static void init(String appName) {
		List<Map<String, String>> data = findAll();
    	/*for (Map<String, String> map : data) {
			System.out.println(FrameJsonUtil.toString(map));
		}*/
    	Map<String, Object> paramsMap = new HashMap<String, Object>();
    	paramsMap.put("code", appName);
    	paramsMap.put("detailString", FrameJsonUtil.toString(data));
    	try {
			ResponseFrame frame = MonitorUtil.post("/api/prjApi/saveBatch", paramsMap);
			if(ResponseCode.SUCC.getCode() == frame.getCode().intValue()) {
				LOGGER.info("成功更新API信息到Monitor!");
			}
		} catch (Exception e) {
			LOGGER.error("更新API信息到Monitor异常: " + e.getMessage(), e);
		}
	}
}