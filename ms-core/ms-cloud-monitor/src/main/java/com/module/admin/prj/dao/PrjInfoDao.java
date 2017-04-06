package com.module.admin.prj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.prj.pojo.PrjInfo;
import com.system.comm.model.KvEntity;

/**
 * prj_info的Dao
 * @author yuejing
 * @date 2016-10-19 15:56:37
 * @version V1.0.0
 */
public interface PrjInfoDao {

	public abstract void save(PrjInfo prjInfo);

	public abstract void update(PrjInfo prjInfo);

	public abstract void delete(@Param("prjId")Integer prjId);

	public abstract PrjInfo get(@Param("prjId")Integer prjId);

	public abstract List<PrjInfo> findPrjInfo(PrjInfo prjInfo);
	
	public abstract int findPrjInfoCount(PrjInfo prjInfo);

	public abstract List<KvEntity> findKvAll();

	public abstract PrjInfo getCode(@Param("code")String code);
}
