package com.module.admin.cli.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.cli.pojo.CliInfo;

/**
 * cli_info的Dao
 * @author yuejing
 * @date 2016-10-20 17:55:37
 * @version V1.0.0
 */
public interface CliInfoDao {

	public abstract void save(CliInfo cliInfo);

	public abstract void update(CliInfo cliInfo);

	public abstract void delete(@Param("clientId")String clientId);

	public abstract CliInfo get(@Param("clientId")String clientId);

	public abstract List<CliInfo> findCliInfo(CliInfo cliInfo);
	
	public abstract int findCliInfoCount(CliInfo cliInfo);

	public abstract List<CliInfo> find(CliInfo cliInfo);

	public abstract void updateActivityTime(@Param("clientId")String clientId);

	public abstract void updateActivityStatusError(@Param("activityStatus")Integer activityStatus);

	public abstract void updateActivityStatusNormal(@Param("activityStatus")Integer activityStatus);

	public abstract List<CliInfo> findAll();
}
