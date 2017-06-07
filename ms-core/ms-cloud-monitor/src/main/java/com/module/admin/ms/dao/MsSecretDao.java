package com.module.admin.ms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.module.admin.ms.pojo.MsSecret;

/**
 * ms_secret的Dao
 * @author yuejing
 * @date 2017-06-02 15:44:56
 * @version V1.0.0
 */
public interface MsSecretDao {

	public abstract void save(MsSecret msSecret);

	public abstract void update(MsSecret msSecret);

	public abstract void delete(@Param("cliId")String cliId);

	public abstract MsSecret get(@Param("cliId")String cliId);

	public abstract List<MsSecret> findMsSecret(MsSecret msSecret);
	
	public abstract int findMsSecretCount(MsSecret msSecret);

	public abstract List<MsSecret> findUse();
}
