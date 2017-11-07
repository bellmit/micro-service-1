package com.ms.zuul.filter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class ErrorFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
    	//pre、post和error。分别代表前置过滤，后置过滤和异常过滤
        return "error";
    }

    @Override
    public int filterOrder() {
    	//过滤器顺序
        return 10;
    }

    @Override
    public boolean shouldFilter() {
    	//代表这个过滤器是否生效
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        LOGGER.error("this is a ErrorFilter : {}", throwable.getCause().getMessage());
        ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ctx.set("error.exception", throwable.getCause());
        
        /*ctx.getResponse().setContentType("text/html;charset=UTF-8");
		ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
        ctx.setResponseBody(FrameJsonUtil.toString(frame));*/
        return null;
    }
}