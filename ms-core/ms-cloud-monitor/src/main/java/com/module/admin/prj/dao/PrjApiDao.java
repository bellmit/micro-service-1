package com.module.admin.prj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.prj.pojo.PrjApi;

/**
 * prj_api的Dao
 * @author yuejing
 * @date 2017-05-18 16:00:41
 * @version V1.0.0
 */
public interface PrjApiDao {

	public abstract void save(PrjApi prjApi);

	public abstract void update(PrjApi prjApi);

	public abstract void delete(@Param("prjId")Integer prjId);

	public abstract PrjApi get(@Param("prjId")Integer prjId, @Param("path")String path);

	public abstract List<PrjApi> findPrjApi(PrjApi prjApi);
	
	public abstract int findPrjApiCount(PrjApi prjApi);

	public abstract void updateIsUse(@Param("prjId")Integer prjId, @Param("path")String path, @Param("isUse")Integer isUse);
}
