package com.module.admin.prj.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.prj.pojo.PrjVersion;
import com.system.comm.model.KvEntity;
import com.system.handle.model.ResponseFrame;

/**
 * prj_version的Service
 * @author yuejing
 * @date 2016-10-19 15:55:36
 * @version V1.0.0
 */
@Component
public interface PrjVersionService {
	
	/**
	 * 保存或修改
	 * @param prjVersion
	 * @return
	 */
	public ResponseFrame saveOrUpdate(PrjVersion prjVersion);
	
	/**
	 * 根据prjId获取对象
	 * @param prjId
	 * @return
	 */
	public PrjVersion get(Integer prjId, String version);

	/**
	 * 分页获取对象
	 * @param prjVersion
	 * @return
	 */
	public ResponseFrame pageQuery(PrjVersion prjVersion);
	
	/**
	 * 根据prjId删除对象
	 * @param prjId
	 * @return
	 */
	public ResponseFrame delete(Integer prjId, String version);

	public List<KvEntity> findKvAll();

	public List<PrjVersion> findByPrjId(Integer prjId);
}
