package com.module.admin.prj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.cli.pojo.CliInfo;
import com.module.admin.prj.pojo.PrjClient;
import com.module.admin.prj.pojo.PrjInfo;

/**
 * prj_client的Dao
 * @author yuejing
 * @date 2016-10-20 17:54:59
 * @version V1.0.0
 */
public interface PrjClientDao {

	public abstract void save(PrjClient prjClient);

	public abstract void update(PrjClient prjClient);

	public abstract void delete(@Param("prjId")Integer prjId, @Param("version")String version, @Param("clientId")String clientId);

	public abstract PrjClient get(@Param("prjId")Integer prjId, @Param("version")String version, @Param("clientId")String clientId);

	public abstract List<PrjClient> findPrjClient(PrjClient prjClient);
	
	public abstract int findPrjClientCount(PrjClient prjClient);

	public abstract List<PrjInfo> findByClientId(@Param("clientId")String clientId, @Param("status")Integer status);

	public abstract void updateStatus(@Param("clientId")String clientId, @Param("prjId")Integer prjId,
			@Param("version")String version, @Param("status")Integer status, @Param("statusMsg")String statusMsg);

	public abstract List<CliInfo> findByPrjId(@Param("prjId")Integer prjId, @Param("version")String version, @Param("clientId")String clientId);

	public abstract void updateShellScript(@Param("clientId")String clientId, @Param("prjId")Integer prjId,
			@Param("version")String version,
			@Param("shellScript")String shellScript);

	public abstract PrjClient getLastByPrjIdClientId(@Param("prjId")Integer prjId,
			@Param("clientId")String clientId);

	public abstract List<PrjClient> findByPrjIdVersion(@Param("prjId")Integer prjId,
			@Param("version")String version);
}
