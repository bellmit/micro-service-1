package com.module.admin.ms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.module.admin.ms.pojo.MsConfigValue;

/**
 * ms_config_value的Dao
 * @author yuejing
 * @date 2017-04-21 16:02:31
 * @version V1.0.0
 */
public interface MsConfigValueDao {

	public abstract void save(MsConfigValue msConfigValue);

	public abstract void update(MsConfigValue msConfigValue);

	public abstract void delete(@Param("configId")Integer configId);

	public abstract MsConfigValue get(@Param("configId")Integer configId, @Param("code")String code);

	public abstract List<MsConfigValue> findByConfigId(@Param("configId")Integer configId);
	
}
