package com.system.handle;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.container.core.Container;
import com.system.container.model.UrlMappingModel;

/**
 * 业务处理类
 * @author yuejing
 * @date 2016年1月29日 下午4:24:53
 * @version V1.0.0
 */
@Component
public class Dispatcher {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object process(String uri, Map<String, String> params) throws Exception {
		//public Object process(String uri, String body) throws Exception {
		Map<String, UrlMappingModel> urlMap = Container.getInstance().getUrlMappings();
		UrlMappingModel umm = null;
		int methodIndex = uri.indexOf("?");
		if(methodIndex != -1) {
			umm = urlMap.get(uri.substring(0, methodIndex));
		} else {
			umm = urlMap.get(uri);
		}
		if(umm == null) {
			return null;
		}
		BaseHandle handle = FrameSpringBeanUtil.getBean(umm.getBeanName());
		Class clazz = handle.getClass();
		Method m = clazz.getMethod(umm.getMethod(), Map.class);
		Object object = m.invoke(handle, params);
		return object;
		
		/*String[] uriArr = uri.split("/");
		int uriLen = uriArr.length;
		//实体名称
		String beanName = uriArr[uriLen - 2];
		//处理的方法
		String beanMethodName = uriArr[uriLen - 1];
		BaseHandle handle = FrameSpringBeanUtil.getBean(beanName + "HandleImpl");

		int methodIndex = beanMethodName.indexOf("?");
		if(methodIndex != -1) {
			beanMethodName = beanMethodName.substring(0, methodIndex);
		}
		Class clazz = handle.getClass();
		Method m = clazz.getMethod(beanMethodName, Map.class);
		Object object = m.invoke(handle, params);
		return object;*/
	}
}
