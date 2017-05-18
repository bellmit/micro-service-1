package com.module.admin.prj.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.prj.pojo.PrjApi;
import com.system.handle.model.ResponseFrame;

/**
 * prj_api的Service
 * @author yuejing
 * @date 2017-05-18 16:00:41
 * @version V1.0.0
 */
@Component
public interface PrjApiService {
	
	/**
	 * 保存或修改
	 * @param prjApi
	 * @return
	 */
	public ResponseFrame saveOrUpdate(PrjApi prjApi);
	
	/**
	 * 根据prjId和path获取对象
	 * @param prjId
	 * @param path
	 * @return
	 */
	public PrjApi get(Integer prjId, String path);

	/**
	 * 分页获取对象
	 * @param prjApi
	 * @return
	 */
	public ResponseFrame pageQuery(PrjApi prjApi);
	
	/**
	 * 根据prjId删除对象
	 * @param prjId
	 * @return
	 */
	public ResponseFrame delete(Integer prjId);

	public void saveBatch(List<PrjApi> prjApis);
}
