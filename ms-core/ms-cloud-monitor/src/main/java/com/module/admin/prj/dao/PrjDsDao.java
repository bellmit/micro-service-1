package com.module.admin.prj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.prj.pojo.PrjDs;

/**
 * prj_ds的Dao
 * @author yuejing
 * @date 2017-06-21 14:43:51
 * @version V1.0.0
 */
public interface PrjDsDao {

	public abstract void save(PrjDs prjDs);

	public abstract void update(PrjDs prjDs);

	public abstract void delete(@Param("prjId")Integer prjId, @Param("code")String code);

	public abstract PrjDs get(@Param("prjId")Integer prjId, @Param("code")String code);

	public abstract List<PrjDs> findPrjDs(PrjDs prjDs);
	
	public abstract int findPrjDsCount(PrjDs prjDs);

	public abstract List<PrjDs> findByPrjId(@Param("prjId")Integer prjId);
}
