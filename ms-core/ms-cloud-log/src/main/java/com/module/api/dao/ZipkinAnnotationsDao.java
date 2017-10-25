package com.module.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.api.pojo.ZipkinAnnotations;

/**
 * ZipkinAnnotations的Dao
 * @author yuejing
 * @date 2017-06-21 14:43:51
 * @version V1.0.0
 */
public interface ZipkinAnnotationsDao {

	public abstract List<ZipkinAnnotations> findByAKeyATimestamp(
			@Param("aKey")String aKey, @Param("beginTime")Long beginTime, @Param("endTime")Long endTime);
}
