package com.module.admin.code.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.code.pojo.CodeCreate;
import com.system.handle.model.ResponseFrame;

/**
 * code_create的Service
 * @author yuejing
 * @date 2017-07-27 09:06:22
 * @version V1.0.0
 */
@Component
public interface CodeCreateService {
	
	/**
	 * 保存或修改
	 * @param codeCreate
	 * @return
	 */
	public ResponseFrame saveOrUpdate(CodeCreate codeCreate);
	
	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public CodeCreate get(Integer id);

	/**
	 * 分页获取对象
	 * @param codeCreate
	 * @return
	 */
	public ResponseFrame pageQuery(CodeCreate codeCreate);
	
	/**
	 * 根据id删除对象
	 * @param id
	 * @return
	 */
	public ResponseFrame delete(Integer id);
}
