package com.module.admin.prj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.prj.pojo.PrjVersion;
import com.system.comm.model.KvEntity;

/**
 * prj_version的Dao
 * @author yuejing
 * @date 2016-10-19 15:55:36
 * @version V1.0.0
 */
public interface PrjVersionDao {

	public abstract void save(PrjVersion prjVersion);

	public abstract void update(PrjVersion prjVersion);

	public abstract void delete(@Param("prjId")Integer prjId, @Param("version")String version);

	public abstract PrjVersion get(@Param("prjId")Integer prjId, @Param("version")String version);

	public abstract List<PrjVersion> findPrjVersion(PrjVersion prjVersion);
	
	public abstract int findPrjVersionCount(PrjVersion prjVersion);

	/**
	 * 将所有版本修改为未发布
	 */
	public abstract void updateNotRelease();

	public abstract int getCountByIsRelease(@Param("prjId")Integer prjId, @Param("isRelease")Integer isRelease);

	public abstract List<KvEntity> findKvAll();

	public abstract List<PrjVersion> findByPrjId(@Param("prjId")Integer prjId);
}
