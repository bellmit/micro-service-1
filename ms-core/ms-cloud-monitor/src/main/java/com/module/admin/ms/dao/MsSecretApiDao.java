package com.module.admin.ms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.ms.pojo.MsSecretApi;

/**
 * ms_secret的Dao
 * @author yuejing
 * @date 2017-06-02 15:44:56
 * @version V1.0.0
 */
public interface MsSecretApiDao {

	public abstract void save(MsSecretApi msSecretApi);

	public abstract void delete(@Param("cliId")String cliId, @Param("prjId")Integer prjId, @Param("url")String url);

	public abstract MsSecretApi get(@Param("cliId")String cliId, @Param("prjId")Integer prjId, @Param("url")String url);

	public abstract List<MsSecretApi> findMsSecretApi(MsSecretApi msSecretApi);
	
	public abstract int findMsSecretApiCount(MsSecretApi msSecretApi);

	public abstract List<MsSecretApi> findByCliId(@Param("cliId")String cliId);
}
