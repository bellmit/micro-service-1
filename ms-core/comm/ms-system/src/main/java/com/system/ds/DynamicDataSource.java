package com.system.ds;

import java.util.Map;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 实现数据源路由
 * @author yuejing
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
 
	private Map<Object, Object> targetDataSources;
    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDsName();
    }

	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		this.targetDataSources = targetDataSources;
		super.setTargetDataSources(targetDataSources);
	}

	public Map<Object, Object> getTargetDataSources() {
		return targetDataSources;
	}
}