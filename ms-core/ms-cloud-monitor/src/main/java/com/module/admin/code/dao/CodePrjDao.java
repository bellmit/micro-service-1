package com.module.admin.code.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.module.admin.code.pojo.CodePrj;

/**
 * code_prj的Dao
 * @author yuejing
 * @date 2017-07-27 09:06:22
 * @version V1.0.0
 */
public interface CodePrjDao {

	public abstract void save(CodePrj codePrj);

	public abstract void update(CodePrj codePrj);

	public abstract void delete(@Param("code")String code);

	public abstract CodePrj get(@Param("code")String code);

	public abstract List<CodePrj> findCodePrj(CodePrj codePrj);
	
	public abstract int findCodePrjCount(CodePrj codePrj);
}
