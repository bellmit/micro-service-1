package com.module.comm.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.web.util.HtmlUtils;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getHeader(String name) {
		return HtmlUtils.htmlEscape(super.getHeader(name));
		//return StringEscapeUtils.escapeHtml4(super.getHeader(name));
	}

	@Override
	public String getQueryString() {
		return HtmlUtils.htmlEscape(super.getQueryString());
		//return StringEscapeUtils.escapeHtml4(super.getQueryString());
	}

	@Override
	public String getParameter(String name) {
		return HtmlUtils.htmlEscape(super.getParameter(name));
		//return StringEscapeUtils.escapeHtml4(super.getParameter(name));
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if(values != null) {
			int length = values.length;
			String[] escapseValues = new String[length];
			for(int i = 0; i < length; i++){
				//escapseValues[i] = StringEscapeUtils.escapeHtml4(values[i]);
				escapseValues[i] = HtmlUtils.htmlEscape(values[i]);
			}
			return escapseValues;
		}
		return super.getParameterValues(name);
	}

}