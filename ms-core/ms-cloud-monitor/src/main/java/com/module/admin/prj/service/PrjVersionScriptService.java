package com.module.admin.prj.service;

import org.springframework.stereotype.Component;

import com.module.admin.prj.pojo.PrjVersionScript;
import com.system.handle.model.ResponseFrame;

/**
 * prj_version_script的Service
 * @author yuejing
 * @date 2017-07-04 09:46:06
 * @version V1.0.0
 */
@Component
public interface PrjVersionScriptService {
	
	/**
	 * 保存或修改
	 * @param prjVersionScript
	 * @return
	 */
	public ResponseFrame saveOrUpdate(PrjVersionScript prjVersionScript);
	
	/**
	 * 根据pvsId获取对象
	 * @param pvsId
	 * @return
	 */
	public PrjVersionScript get(Integer pvsId);

	/**
	 * 分页获取对象
	 * @param prjVersionScript
	 * @return
	 */
	public ResponseFrame pageQuery(PrjVersionScript prjVersionScript);
	
	/**
	 * 根据pvsId删除对象
	 * @param pvsId
	 * @return
	 */
	public ResponseFrame delete(Integer pvsId);

	public void updateIsUp(Integer pvsId, Integer isUp);
}
