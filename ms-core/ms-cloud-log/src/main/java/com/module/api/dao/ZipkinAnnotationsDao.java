package com.module.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import zipkin.storage.mysql.internal.generated.tables.ZipkinAnnotations;

/**
 * ZipkinAnnotations的Dao
 * @author yuejing
 * @date 2017-06-21 14:43:51
 * @version V1.0.0
 */
public interface ZipkinAnnotationsDao {

	public abstract ZipkinAnnotations get(@Param("prjId")Integer prjId, @Param("code")String code);

	public abstract List<ZipkinAnnotations> findPrjDs(ZipkinAnnotations zipkinAnnotations);
	
	public abstract List<ZipkinAnnotations> findByPrjId(@Param("prjId")Integer prjId);
}
