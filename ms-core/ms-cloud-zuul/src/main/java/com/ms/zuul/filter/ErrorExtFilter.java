package com.ms.zuul.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class ErrorExtFilter extends SendErrorFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorExtFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 30;    // 大于ErrorFilter的值
    }

    @Override
    public boolean shouldFilter() {
    	// 判断：仅处理来自post过滤器引起的异常
        RequestContext ctx = RequestContext.getCurrentContext();
        ZuulFilter failedFilter = (ZuulFilter) ctx.get("failed.filter");
        LOGGER.info("进入扩展异常");
        if(failedFilter != null && failedFilter.filterType().equals("post")) {
            return true;
        }
        return false;
    }

}
