package com.module.admin.code.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.code.pojo.CodeCreate;

/**
 * code_create的Dao
 * @author yuejing
 * @date 2017-07-27 09:06:22
 * @version V1.0.0
 */
public interface CodeCreateDao {

	public abstract void save(CodeCreate codeCreate);

	public abstract void update(CodeCreate codeCreate);

	public abstract void delete(@Param("id")Integer id);

	public abstract CodeCreate get(@Param("id")Integer id);

	public abstract List<CodeCreate> findCodeCreate(CodeCreate codeCreate);
	
	public abstract int findCodeCreateCount(CodeCreate codeCreate);

	public abstract void updateFinish(@Param("id")Integer id, @Param("download")String download);
}
