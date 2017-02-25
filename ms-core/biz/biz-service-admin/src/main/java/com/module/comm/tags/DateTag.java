package com.module.comm.tags;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.system.comm.utils.FrameStringUtil;
import com.system.comm.utils.FrameTimeUtil;

/**
 * 显示文本
 * 根据类型和ID显示文本
 * @author duanbin
 * @date 2016-5-29 下午6:40:13
 * @version V1.0.0
 */
public class DateTag extends TagSupport {

	private static final long serialVersionUID = 7293280902947614510L;

	private Date value;
	private String pattern;

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			if(value == null) {
				out.print("");
				return SKIP_BODY;
			}
			String string = null;
			if(FrameStringUtil.isEmpty(pattern)) {
				string = FrameTimeUtil.getSimpleTime(value);
			} else if("age".equals(pattern)) {
				//获取年龄
				string = String.valueOf(FrameTimeUtil.getAge(value));
			} else {
				string = FrameTimeUtil.parseString(value, pattern);
			}
			out.print(string);
		} catch(Exception e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public void release() {
		super.release();
		this.value = null;
		this.pattern = null;
	}

	public Date getValue() {
		return value;
	}
	public void setValue(Date value) {
		this.value = value;
	}

	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}