package com.module.admin.code.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.code.pojo.CodeTemplate;
import com.system.handle.model.ResponseFrame;

/**
 * code_template的Service
 * @author yuejing
 * @date 2017-07-27 09:06:22
 * @version V1.0.0
 */
@Component
public interface CodeTemplateService {
	
	/**
	 * 保存或修改
	 * @param codeTemplate
	 * @return
	 */
	public ResponseFrame saveOrUpdate(CodeTemplate codeTemplate);
	
	/**
	 * 根据code获取对象
	 * @param code
	 * @param name 
	 * @return
	 */
	public CodeTemplate get(String code, String name);

	/**
	 * 分页获取对象
	 * @param codeTemplate
	 * @return
	 */
	public ResponseFrame pageQuery(CodeTemplate codeTemplate);
	
	/**
	 * 根据code删除对象
	 * @param code
	 * @param name 
	 * @return
	 */
	public ResponseFrame delete(String code, String name);

	public List<CodeTemplate> findByCode(String code);
}
