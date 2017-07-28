package com.module.admin.code.service;

import org.springframework.stereotype.Component;

import com.module.admin.code.pojo.CodePrj;
import com.system.handle.model.ResponseFrame;

/**
 * code_prj的Service
 * @author yuejing
 * @date 2017-07-27 09:06:22
 * @version V1.0.0
 */
@Component
public interface CodePrjService {
	
	/**
	 * 保存或修改
	 * @param codePrj
	 * @return
	 */
	public ResponseFrame saveOrUpdate(CodePrj codePrj);
	
	/**
	 * 根据code获取对象
	 * @param code
	 * @return
	 */
	public CodePrj get(String code);

	/**
	 * 分页获取对象
	 * @param codePrj
	 * @return
	 */
	public ResponseFrame pageQuery(CodePrj codePrj);
	
	/**
	 * 根据code删除对象
	 * @param code
	 * @return
	 */
	public ResponseFrame delete(String code);
}
