package com.system.container.core;

import java.util.Map;

import org.apache.log4j.Logger;

import com.system.container.model.UrlMappingModel;

/**
 * 注解等的容器
 * @author yuejing
 * @date 2017年1月26日 下午7:36:46
 * @version V1.0.0
 */
public class Container {
	
	private static final Logger LOGGER = Logger.getLogger(Container.class);
	private DefaultAnnotationHandlerMapping defaultAnnotationHandlerMapping;
	private Map<String, UrlMappingModel> urlMappings = null;
	private static Container container;
	private Container() {
	}
	public static synchronized Container getInstance() {
		if(container == null) {
			container = new Container();
		}
		return container;
	}
	
	public void start() {
		//启动URL映射的注解等
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("启动Container容器");
		}
		defaultAnnotationHandlerMapping = new DefaultAnnotationHandlerMapping();
		defaultAnnotationHandlerMapping.detectHandlers();
		
		defaultAnnotationHandlerMapping.getCachedMappings();
		urlMappings = defaultAnnotationHandlerMapping.getHandlerMap();
	}

	public Map<String, UrlMappingModel> getUrlMappings() {
		return urlMappings;
	}
}