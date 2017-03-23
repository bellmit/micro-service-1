package com.system.deserialize;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.system.comm.utils.FrameTimeUtil;

/**
 * 日期格式以yyyy-MM-dd这种形式展示
 * 		在对象的属性的set方法上使用：@JsonDeserialize(using = JsonDateYMdDeserialize.class)
 * @author yuejing
 * @email  yuejing0129@163.com 
 * @date   2014年10月23日 下午2:31:24 
 * @version 1.0.0
 */
public class JsonDateYMdDeserialize extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return FrameTimeUtil.parseDate(jp.getText(), FrameTimeUtil.FMT_YYYY_MM_DD);
	}
}