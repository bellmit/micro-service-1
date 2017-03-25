package com.system.comm.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json操作
 * @author  duanbin
 * @date    2014年11月8日 下午5:17:36 
 * @version 1.0.0
 */
public class FrameJsonUtil {

private static final Logger LOGGER = Logger.getLogger(FrameJsonUtil.class);
	
	private static ObjectMapper mapper;
	
	private static ObjectMapper getMapperInstance() {
		return getMapperInstance(false);
	}
	private static synchronized ObjectMapper getMapperInstance(boolean createNew) {
        if (createNew) {
        	ObjectMapper m = new ObjectMapper();
            m.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        	return m;
        } else if (mapper == null) {
            mapper = new ObjectMapper();
        }
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FrameTimeUtil.FMT_DEFAULT);
		mapper.setDateFormat(simpleDateFormat);
        return mapper;
    }

	/**
	 * 将对象转为字符串
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if(obj == null) {
			return null;
		}
		try {
			ObjectMapper mapper = getMapperInstance();
			StringWriter sw = new StringWriter();
			JsonGenerator gen = new JsonFactory().createGenerator(sw);
			mapper.writeValue(gen, obj);
			return sw.toString();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 将字符串转为对象
	 * @param string
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T>T toObject(String string, Class<?> cls) {
		if(FrameStringUtil.isEmpty(string)) {
			return null;
		}
		try {
			ObjectMapper mapper = getMapperInstance();
			//mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, Boolean.TRUE);
			return (T) mapper.readValue(string, cls);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 将字符串转为Map对象
	 * @param string
	 * @return 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T>T toMap(String string) {
		if(FrameStringUtil.isEmpty(string)) {
			return null;
		}
		try {
			ObjectMapper mapper = getMapperInstance();
			JavaType type = mapper.getTypeFactory().constructType(Map.class);
			return (T) mapper.readValue(string, type);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 转换为List
	 * @param string
	 * @param cls		为List集合里面的类型
	 * @return
	 */
	public static <T>T toList(String string, Class<?> cls) {
		if(FrameStringUtil.isEmpty(string)) {
			return null;
		}
		try {
			ObjectMapper mapper = getMapperInstance();
			JavaType type = mapper.getTypeFactory().constructParametricType(List.class, cls);
			return mapper.readValue(string, type);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 将字符串转为JsonNode对象
	 * @param string
	 * @return
	 */
	public static JsonNode toJsonNode(String string) {
		if(FrameStringUtil.isEmpty(string)) {
			return null;
		}
		try {
			ObjectMapper mapper = getMapperInstance();
			return mapper.readTree(string);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
}