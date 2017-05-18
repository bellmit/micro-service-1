package com.ms.biz.api.config;


import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.ms.biz.api.utils.MonitorUtil;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

@Configuration // 该注解类似于spring配置文件
public class MonitorConfig {
	
	private final Logger LOGGER = LoggerFactory.getLogger(MonitorConfig.class);

    @Autowired
    private Environment env;

    /**
     * 配置事务管理器
     */
    @Bean
    public MonitorUtil monitor() throws Exception {
    	LOGGER.info("发送系统的API可请求的方法");
    	MonitorUtil.clientId = env.getProperty("client.monitor.id");
    	MonitorUtil.sercret = env.getProperty("client.monitor.token");
    	MonitorUtil.serverHost = env.getProperty("client.monitor.server.host");
    	
    	String code = env.getProperty("spring.application.name");
    	List<Map<String, String>> data = findAll();
    	/*for (Map<String, String> map : data) {
			System.out.println(FrameJsonUtil.toString(map));
		}*/
    	Map<String, Object> paramsMap = new HashMap<String, Object>();
    	paramsMap.put("code", code);
    	paramsMap.put("detailString", FrameJsonUtil.toString(data));
    	ResponseFrame frame = MonitorUtil.post("/api/prjApi/saveBatch", paramsMap);
    	if(ResponseCode.SUCC.getCode() == frame.getCode().intValue()) {
    		LOGGER.info("成功更新API信息到Monitor!");
    	}
    	return new MonitorUtil();
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
	public List<Map<String, String>> findAll() {
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
}
