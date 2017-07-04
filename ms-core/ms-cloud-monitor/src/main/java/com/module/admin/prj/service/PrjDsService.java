package com.module.admin.prj.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.prj.pojo.PrjDs;
import com.system.comm.model.KvEntity;
import com.system.handle.model.ResponseFrame;

/**
 * prj_ds的Service
 * @author yuejing
 * @date 2017-06-21 14:43:52
 * @version V1.0.0
 */
@Component
public interface PrjDsService {
	
	/**
	 * 保存或修改
	 * @param prjDs
	 * @return
	 */
	public ResponseFrame saveOrUpdate(PrjDs prjDs);
	
	/**
	 * 根据code获取对象
	 * @param prjId 
	 * @param code
	 * @return
	 */
	public PrjDs get(Integer prjId, String code);

	/**
	 * 分页获取对象
	 * @param prjDs
	 * @return
	 */
	public ResponseFrame pageQuery(PrjDs prjDs);
	
	/**
	 * 根据prjId、code删除对象
	 * @param prjId
	 * @param code
	 * @return
	 */
	public ResponseFrame delete(Integer prjId, String code);

	public List<KvEntity> findKvByPrjId(Integer prjId);
}
