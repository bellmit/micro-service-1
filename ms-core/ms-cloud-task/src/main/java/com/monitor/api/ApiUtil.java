package com.monitor.api;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.monitor.MonitorCons;
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
public class ApiUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiUtil.class);

	/** API测试访问当前项目的ClientId */
	public static String prjId;
	/** API测试访问当前项目的token */
	public static String prjToken;

	/**
	 * 请求服务端的api
	 * @param url
	 * @param paramsMap
	 * @return
	 * @throws IOException 
	 */
	public static ResponseFrame post(String url, Map<String, Object> paramsMap) throws IOException {
		String time = String.valueOf(System.currentTimeMillis());
		paramsMap.put("clientId", MonitorCons.clientId);
		paramsMap.put("time", time);
		paramsMap.put("sign", AuthUtil.auth(MonitorCons.clientId, time, MonitorCons.sercret));
		String result = FrameHttpUtil.post(MonitorCons.serverHost + url, paramsMap);
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
			Map<String, String> map = new HashMap<String, String>();
			List<Map<String, String>> params = new ArrayList<Map<String, String>>();
			Map<String, String> item = new HashMap<String, String>();
			item.put("name", "客户编号");
			item.put("code", "clientId");
			item.put("value", prjId);
			item.put("clazz", String.class.getName());
			item.put("required", "true");
			item.put("isShow", "0");
			params.add(item);
			item = new HashMap<String, String>();
			item.put("name", "客户token");
			item.put("code", "token");
			item.put("value", prjToken);
			item.put("clazz", String.class.getName());
			item.put("required", "true");
			item.put("isShow", "0");
			params.add(item);
			List<Map<String, String>> response = new ArrayList<Map<String, String>>();
			for (Annotation annotation : anns) {
				if(annotation instanceof RequestMapping) {
					RequestMapping rm = (RequestMapping) annotation;
					StringBuffer path = new StringBuffer();
					for (String str : rm.value()) {
						path.append(str).append(";");
					}
					if(path.length() > 0) {
						path.setCharAt(path.length() - 1, ' ');
						map.put("name", rm.name());
						map.put("path", path.toString().trim());
						map.put("method", value.getMethod().toString());
						/*String methodName = value.getMethod().getName();
						Method method = null;
						Class clazz = value.getMethod().getDeclaringClass();
						try {
							method = clazz.getDeclaredMethod(methodName, value.getMethod().getParameterTypes());
						} catch (Exception e1) {
							LOGGER.error("获取类中的方法异常: " + e1.getMessage());
						}
				        String[] parameterNames = getMethodParameterNamesByAsm4(clazz, method);
				        System.out.println(FrameJsonUtil.toString(parameterNames));*/
					}
				} else if(annotation instanceof ApiInfo) {
					ApiInfo ai = (ApiInfo) annotation;
					ApiParam[] aps = ai.params();
					for (ApiParam ap : aps) {
						item = new HashMap<String, String>();
						item.put("name", ap.name());
						item.put("code", ap.code());
						item.put("clazz", ap.clazz().getName());
						item.put("value", ap.value());
						item.put("required", String.valueOf(ap.required()));
						item.put("isShow", "1");
						params.add(item);
					}

					ApiRes[] ars = ai.response();
					for (ApiRes ar : ars) {
						item = new HashMap<String, String>();
						item.put("name", ar.name());
						item.put("code", ar.code());
						item.put("pCode", ar.pCode());
						item.put("clazz", ar.clazz().getName());
						item.put("value", ar.value());
						response.add(item);
					}
				}
			}
			if(map.size() > 0) {
				map.put("params", FrameJsonUtil.toString(params));
				map.put("response", FrameJsonUtil.toString(response));
				data.add(map);
			}
		}
		return data;
	}

	/**
	 * 获取指定类指定方法的参数名
	 * @param clazz 要获取参数名的方法所属的类
	 * @param method 要获取参数名的方法
	 * @return 按参数顺序排列的参数名列表，如果没有参数，则返回null
	 */
	public static String[] getMethodParameterNamesByAsm4(Class<?> clazz, final Method method) {
		final Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes == null || parameterTypes.length == 0) {
			return null;
		}
		final Type[] types = new Type[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			types[i] = Type.getType(parameterTypes[i]);
		}
		final String[] parameterNames = new String[parameterTypes.length];

		String className = clazz.getName();
		int lastDotIndex = className.lastIndexOf(".");
		className = className.substring(lastDotIndex + 1) + ".class";
		InputStream is = clazz.getResourceAsStream(className);
		try {
			ClassReader classReader = new ClassReader(is);
			classReader.accept(new ClassVisitor(Opcodes.ASM4) {
				@Override
				public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
					// 只处理指定的方法
					Type[] argumentTypes = Type.getArgumentTypes(desc);
					if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {
						return null;
					}
					return new MethodVisitor(Opcodes.ASM4) {
						@Override
						public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
							// 静态方法第一个参数就是方法的参数，如果是实例方法，第一个参数是this
							if (Modifier.isStatic(method.getModifiers())) {
								parameterNames[index] = name;
							}
							else if (index > 0 && index <= parameterNames.length) {
								parameterNames[index - 1] = name;
							}
						}
					};

				}
			}, 0);
		} catch (IOException e) {
			LOGGER.error("获取方法参数名称异常: " + e.getMessage());
		}
		return parameterNames;
	}

	/**
	 * 初始化监控的API
	 * @param appName	为微服务的serviceId
	 * @throws IOException
	 */
	public static void init(final String appName) {
		List<Map<String, String>> data = findAll();
		/*for (Map<String, String> map : data) {
					System.out.println(FrameJsonUtil.toString(map));
				}*/
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("code", appName);
		paramsMap.put("detailString", FrameJsonUtil.toString(data));
		try {
			ResponseFrame frame = ApiUtil.post("/api/prjApi/saveBatch", paramsMap);
			if(ResponseCode.SUCC.getCode() == frame.getCode().intValue()) {
				LOGGER.info("成功更新API信息到Monitor!");
			} else {
				LOGGER.info("更新API信息到Monitor失败: " + frame.getMessage());
			}
		} catch (Exception e) {
			LOGGER.error("更新API信息到Monitor异常: " + e.getMessage(), e);
		}
	}
}