package com.module.admin.prj.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.prj.pojo.PrjInfo;
import com.system.comm.model.KvEntity;
import com.system.handle.model.ResponseFrame;

/**
 * prj_info的Service
 * @author yuejing
 * @date 2016-10-19 15:56:37
 * @version V1.0.0
 */
@Component
public interface PrjInfoService {
	
	/**
	 * 保存或修改
	 * @param prjInfo
	 * @return
	 */
	public ResponseFrame saveOrUpdate(PrjInfo prjInfo);
	
	/**
	 * 根据prjId获取对象
	 * @param prjId
	 * @return
	 */
	public PrjInfo get(Integer prjId);

	/**
	 * 分页获取对象
	 * @param prjInfo
	 * @return
	 */
	public ResponseFrame pageQuery(PrjInfo prjInfo);
	
	/**
	 * 根据prjId删除对象
	 * @param prjId
	 * @return
	 */
	public ResponseFrame delete(Integer prjId);

	public List<KvEntity> findKvAll();

	/**
	 * 根据编号获取项目名称
	 * @param prjId
	 * @return
	 */
	public String getName(Integer prjId);

	public PrjInfo getCode(String code);
}
