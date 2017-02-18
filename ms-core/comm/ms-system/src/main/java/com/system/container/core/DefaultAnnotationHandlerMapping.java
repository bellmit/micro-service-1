package com.system.container.core;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.container.annotation.Handle;
import com.system.container.annotation.RequestMapping;
import com.system.container.model.UrlMappingModel;

public class DefaultAnnotationHandlerMapping {

	private static final Logger LOGGER = Logger.getLogger(DefaultAnnotationHandlerMapping.class);

	private final Map<Class<?>, RequestMapping> cachedMappings = new HashMap<Class<?>, RequestMapping>();
	private final Map<String, UrlMappingModel> handlerMap = new LinkedHashMap<String, UrlMappingModel>();
	private PathMatcher pathMatcher = new AntPathMatcher();

	//private boolean useDefaultSuffixPattern = true;

	//Register all handlers found in the current ApplicationContext.
	public void detectHandlers() throws BeansException {
		//取得容器中的搜有bean
		String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(FrameSpringBeanUtil.getApplicationContext(), Object.class);
		/*String[] beanNames = (this.detectHandlersInAncestorContexts ?
				BeanFactoryUtils.beanNamesForTypeIncludingAncestors(FrameSpringBeanUtil.getApplicationContext(), Object.class) :
					FrameSpringBeanUtil.getApplicationContext().getBeanNamesForType(Object.class));*/

		// Take any bean name that we can determine URLs for.
		for (String beanName : beanNames) {
			//取得每个bean可以处理的url
			Map<String, UrlMappingModel> urlMap = determineUrlsForHandler(beanName);
			if (urlMap != null && urlMap.size() > 0) {
				// URL paths found: Let's consider it a handler.
				//注册，将url与controller的映射关系注册到handlerMap中
				registerHandler(urlMap);
			}
			else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Rejected bean name '" + beanName + "': no URL paths identified");
				}
			}
		}
	}

	protected Map<String, UrlMappingModel> determineUrlsForHandler(String beanName) {
		ApplicationContext context = FrameSpringBeanUtil.getApplicationContext();
		Class<?> handlerType = context.getType(beanName);
		//取得该bean类级别的RequestMapping注解
//		RequestMapping mapping = context.findAnnotationOnBean(beanName, RequestMapping.class);
//		if (mapping != null) {
//			// @RequestMapping found at type level
//			this.cachedMappings.put(handlerType, mapping);
//			//Set<String> urls = new LinkedHashSet<String>();
//			String[] typeLevelPatterns = mapping.value();
//			if (typeLevelPatterns.length > 0) {
//				// @RequestMapping specifies paths at type level
//				//获取方法中RequestMapping中定义的URL。（RequestMapping可以定义在类上，也可以定义在方法上）
//				//String[] methodLevelPatterns = determineUrlsForHandlerMethods(handlerType);
//				Map<String, UrlMappingModel> methodLevelPatterns = determineUrlsForHandlerMethods(handlerType, beanName);
//				Iterator<Entry<String, UrlMappingModel>> entryKeyIterator = methodLevelPatterns.entrySet().iterator();
//				while (entryKeyIterator.hasNext()) {
//					Entry<String, UrlMappingModel> e = entryKeyIterator.next();
//					UrlMappingModel umm = e.getValue();
//					String methodLevelPattern = e.getKey();
//					for (String typeLevelPattern : typeLevelPatterns) {
//						if (!typeLevelPattern.startsWith("/")) {
//							typeLevelPattern = "/" + typeLevelPattern;
//						}
//						//将类级别定义的URL与方法级别定义的URL合并（合并规则后面再详解），合并后添加到该bean可以处理的URL集合中
//						//String combinedPattern = getPathMatcher().combine(typeLevelPattern, methodLevelPattern);
//						String combinedPattern = combine(typeLevelPattern, methodLevelPattern);
//						addUrlsForPath(methodLevelPatterns, combinedPattern, beanName, umm.getMethod());
//						/*for (String methodLevelPattern : methodLevelPatterns) {
//							String combinedPattern = getPathMatcher().combine(typeLevelPattern, methodLevelPattern);
//							addUrlsForPath(list, combinedPattern);
//						}*/
//						//将类级别定义的URL添加到该bean可以处理的URL集合中
//						addUrlsForPath(methodLevelPatterns, typeLevelPattern, beanName, umm.getMethod());//(list, typeLevelPattern);
//					}
//				}
//				/*for (String typeLevelPattern : typeLevelPatterns) {
//					if (!typeLevelPattern.startsWith("/")) {
//						typeLevelPattern = "/" + typeLevelPattern;
//					}
//					//将类级别定义的URL与方法级别定义的URL合并（合并规则后面再详解），合并后添加到该bean可以处理的URL集合中
//					for (String methodLevelPattern : methodLevelPatterns) {
//						String combinedPattern = getPathMatcher().combine(typeLevelPattern, methodLevelPattern);
//						addUrlsForPath(list, combinedPattern);
//					}
//					//将类级别定义的URL添加到该bean可以处理的URL集合中
//					addUrlsForPath(list, typeLevelPattern);
//				}*/
//				return methodLevelPatterns;
//			}
//			else {
//				// actual paths specified by @RequestMapping at method level
//				//如果类级别的RequestMapping没有指定URL，则返回方法中RequestMapping定义的URL
//				return determineUrlsForHandlerMethods(handlerType, beanName);
//			}
//		}
		//else 
		if (AnnotationUtils.findAnnotation(handlerType, Handle.class) != null) {
			// @RequestMapping to be introspected at method level
			//如果类级别没有定义RequestMapping，但是定义了Controller注解，将返回方法中RequestMapping定义的URL   
			return determineUrlsForHandlerMethods(handlerType, beanName);
		}
		else {
			//类级别即没有定义RequestMapping，也没有定义Controller，则返回null
			return null;
		}
	}




	protected Map<String, UrlMappingModel> determineUrlsForHandlerMethods(Class<?> handlerType, final String beanName) {
		//final Set<String> urls = new LinkedHashSet<String>();
		final Map<String, UrlMappingModel> map = new HashMap<String, UrlMappingModel>();
		//类型有可能是代理类，如果是代理类，则取得它的所有接口
		Class<?>[] handlerTypes =
				Proxy.isProxyClass(handlerType) ? handlerType.getInterfaces() : new Class<?>[]{handlerType};
				for (Class<?> currentHandlerType : handlerTypes){
					//依次处理该类的所有方法
					ReflectionUtils.doWithMethods(currentHandlerType, new ReflectionUtils.MethodCallback() {

						@Override
						public void doWith(Method method) {
							//取得方法界别的RequestMapping
							RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
							if (mapping != null) {
								//获取可以处理的URL
								String[] mappedPaths = mapping.value();
								//将这些URL放入到可处理的URL集合中
								for (String mappedPath : mappedPaths) {
									addUrlsForPath(map, mappedPath, beanName, method.getName());
								}
							}
						}

					});
				}
				return map;
				//return StringUtils.toStringArray(urls);
	}

	public PathMatcher getPathMatcher() {
		return this.pathMatcher;
	}

	protected void addUrlsForPath(Map<String, UrlMappingModel> map, String path, String beanName, String method) {
		//urls.add(path);
		//处理url
		if(path.indexOf("/") == -1) {
			//得到内容为 adsdf 没有/，则在前面将Handle的名称补全
			path = "/" + beanName + "/" + path;
		}
		map.put(path, new UrlMappingModel(path, beanName, method));
		/*if (this.useDefaultSuffixPattern && path.indexOf('.') == -1 && !path.endsWith("/")) {
			//urls.add(path + ".*");
			//urls.add(path + "/");
			map.put(path + ".*", new UrlMappingModel(path + ".*", beanName, method));
			map.put(path + "/", new UrlMappingModel(path + "/", beanName, method));
		}*/
	}

	protected void registerHandler(Map<String, UrlMappingModel> map) throws BeansException, IllegalStateException {
		Iterator<Entry<String, UrlMappingModel>> entryKeyIterator = map.entrySet().iterator();
		while (entryKeyIterator.hasNext()) {
			Entry<String, UrlMappingModel> e = entryKeyIterator.next();
			UrlMappingModel umm = e.getValue();
			registerHandler(umm);
		}
		/*for (String urlPath : urlPaths) {
			registerHandler(urlPath, beanName);
		}*/
	}

	protected void registerHandler(UrlMappingModel umm) throws BeansException, IllegalStateException {
		Object resolvedHandler = umm.getBeanName();

		// Eagerly resolve handler if referencing singleton via name.
		/*if (!this.lazyInitHandlers && handler instanceof String) {
			String handlerName = (String) handler;
			if (getApplicationContext().isSingleton(handlerName)) {
				resolvedHandler = getApplicationContext().getBean(handlerName);
			}
		}*/

		UrlMappingModel mappedHandler = this.handlerMap.get(umm.getUrl());
		if (mappedHandler != null) {
			if (mappedHandler.getBeanName() != resolvedHandler) {
				throw new IllegalStateException(
						"Cannot map " + getHandlerDescription(umm.getBeanName()) + " to URL path [" + umm.getUrl() +
						"]: There is already " + getHandlerDescription(mappedHandler) + " mapped.");
			}
		}
		else {
			/*if (urlPath.equals("/")) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Root mapping to " + getHandlerDescription(handler));
				}
				setRootHandler(resolvedHandler);
			}
			else if (urlPath.equals("/*")) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Default mapping to " + getHandlerDescription(handler));
				}
				setDefaultHandler(resolvedHandler);
			}
			else {*/
			this.handlerMap.put(umm.getUrl(), umm);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Mapped URL path [" + umm.getUrl() + "] onto " + getHandlerDescription(umm.getBeanName()));
			}
			//}
		}
	}

	private String getHandlerDescription(Object handler) {
		return "handler " + (handler instanceof String ? "'" + handler + "'" : "of type [" + handler.getClass() + "]");
	}

	public Map<Class<?>, RequestMapping> getCachedMappings() {
		return cachedMappings;
	}

	public Map<String, UrlMappingModel> getHandlerMap() {
		return handlerMap;
	}


	@Deprecated
	public String combine(String pattern1, String pattern2) {
		if (!StringUtils.hasText(pattern1) && !StringUtils.hasText(pattern2)) {
			//如果两个URL都为空，那么返回空
			return "";
		}
		else if (!StringUtils.hasText(pattern1)) {
			//如果第一个为空，返回第二个
			return pattern2;
		}
		else if (!StringUtils.hasText(pattern2)) {
			//如果第二个为空，则返回第一个
			return pattern1;
		}
		//else if (match(pattern1, pattern2)) {
        //                //如果两个URL匹配，则返回第二个
		//	return pattern2;
		//}
		else if (pattern1.matches(pattern2)) {
			//如果两个URL匹配，则返回第二个
			return pattern2;
		}
		else if (pattern1.endsWith("/*")) {
			if (pattern2.startsWith("/")) {
				// /hotels/* + /booking -> /hotels/booking
				return pattern1.substring(0, pattern1.length() - 1) + pattern2.substring(1);
			}
			else {
				// /hotels/* + booking -> /hotels/booking
				return pattern1.substring(0, pattern1.length() - 1) + pattern2;
			}
		}
		else if (pattern1.endsWith("/**")) {
			if (pattern2.startsWith("/")) {
				// /hotels/** + /booking -> /hotels/**/booking
				return pattern1 + pattern2;
			}
			else {
				// /hotels/** + booking -> /hotels/**/booking
				return pattern1 + "/" + pattern2;
			}
		}
		else {
			int dotPos1 = pattern1.indexOf('.');
			if (dotPos1 == -1) {
				// simply concatenate the two patterns
				if (pattern1.endsWith("/") || pattern2.startsWith("/")) {
					return pattern1 + pattern2;
				}
				else {
					return pattern1 + "/" + pattern2;
				}
			}
			String fileName1 = pattern1.substring(0, dotPos1);
			String extension1 = pattern1.substring(dotPos1);
			String fileName2;
			String extension2;
			int dotPos2 = pattern2.indexOf('.');
			if (dotPos2 != -1) {
				fileName2 = pattern2.substring(0, dotPos2);
				extension2 = pattern2.substring(dotPos2);
			}
			else {
				fileName2 = pattern2;
				extension2 = "";
			}
			String fileName = fileName1.endsWith("*") ? fileName2 : fileName1;
			String extension = extension1.startsWith("*") ? extension2 : extension1;

			return fileName + extension;
		}
	}
}