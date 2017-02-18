package com.system.serializer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.system.comm.utils.FrameTimeUtil;

/**
 * 日期格式以yyyy-MM-dd HH:mm:ss这种形式展示
 * 		使用：在属性的get方法上面加 @JsonSerialize(using = JsonDateDefaultSerializer.class)
 * @author yuejing
 * @email  yuejing0129@163.com 
 * @date   2014年10月23日 下午2:31:24 
 * @version 1.0.0
 */
public class JsonDateDefaultSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(FrameTimeUtil.parseString(value));
	}
}