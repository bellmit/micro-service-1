package com.module.admin.ms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.module.admin.ms.pojo.MsConfig;

/**
 * ms_config的Dao
 * @author yuejing
 * @date 2017-04-21 16:02:31
 * @version V1.0.0
 */
public interface MsConfigDao {

	public abstract void save(MsConfig msConfig);

	public abstract void update(MsConfig msConfig);

	public abstract void delete(@Param("configId")Integer configId);

	public abstract MsConfig get(@Param("configId")Integer configId);

	public abstract List<MsConfig> findMsConfig(MsConfig msConfig);
	
	public abstract int findMsConfigCount(MsConfig msConfig);

	public abstract List<MsConfig> findUse();

	public abstract List<MsConfig> findAll();
}
