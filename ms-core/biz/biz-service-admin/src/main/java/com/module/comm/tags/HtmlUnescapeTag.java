package com.module.comm.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.util.HtmlUtils;

import com.system.comm.utils.FrameStringUtil;

/**
 * 显示文本
 * 根据类型和ID显示文本
 * @author yuejing
 * @date 2013-8-29 下午6:40:13
 * @version V1.0.0
 */
public class HtmlUnescapeTag extends TagSupport {

	private static final long serialVersionUID = 7293280902947614510L;

	private String value;

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			if(FrameStringUtil.isEmpty(value)) {
				out.print("");
				return SKIP_BODY;
			}
			out.print(HtmlUtils.htmlUnescape(value));
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
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}