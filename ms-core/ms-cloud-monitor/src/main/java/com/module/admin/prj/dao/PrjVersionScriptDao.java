package com.module.admin.prj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.prj.pojo.PrjVersionScript;

/**
 * prj_version_script的Dao
 * @author yuejing
 * @date 2017-07-04 09:46:06
 * @version V1.0.0
 */
public interface PrjVersionScriptDao {

	public abstract void save(PrjVersionScript prjVersionScript);

	public abstract void update(PrjVersionScript prjVersionScript);

	public abstract void delete(@Param("pvsId")Integer pvsId);

	public abstract PrjVersionScript get(@Param("pvsId")Integer pvsId);

	public abstract List<PrjVersionScript> findPrjVersionScript(PrjVersionScript prjVersionScript);
	
	public abstract int findPrjVersionScriptCount(PrjVersionScript prjVersionScript);

	public abstract void updateIsUp(@Param("pvsId")Integer pvsId, @Param("isUp")Integer isUp);
}
