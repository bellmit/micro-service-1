package com.module.admin.code.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.code.pojo.CodeTemplate;

/**
 * code_template的Dao
 * @author yuejing
 * @date 2017-07-27 09:06:22
 * @version V1.0.0
 */
public interface CodeTemplateDao {

	public abstract void save(CodeTemplate codeTemplate);

	public abstract void update(CodeTemplate codeTemplate);

	public abstract void delete(@Param("code")String code, @Param("name")String name);

	public abstract CodeTemplate get(@Param("code")String code, @Param("name")String name);

	public abstract List<CodeTemplate> findCodeTemplate(CodeTemplate codeTemplate);
	
	public abstract int findCodeTemplateCount(CodeTemplate codeTemplate);

	public abstract List<CodeTemplate> findByCode(@Param("code")String code);
}
