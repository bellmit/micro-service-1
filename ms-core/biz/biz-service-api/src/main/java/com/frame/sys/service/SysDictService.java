package com.frame.sys.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.sys.enums.SysDictTypeCode;
import com.frame.sys.pojo.SysDict;
import com.system.handle.model.ResponseFrame;

@Component
public interface SysDictService {

	public SysDict get(String typeCode, String dictId);
	public void initTable();
	public ResponseFrame saveOrUpdate(SysDict sysDict);
	public ResponseFrame pageQuery(SysDict sysDict);
	public ResponseFrame delete(String dictId);
	public ResponseFrame deleteByTypeCode(String typeCode);
	public String getValue(SysDictTypeCode sysDictTypeCode, String dictId);
	public String getValue(String typeCode, String dictId);

	/**
	 * 获取省份
	 * @return
	 */
	public List<SysDict> findProvince();
	/**
	 * 根据省份编号获取城市列表
	 * @param province
	 * @return
	 */
	public List<SysDict> findCity(String province);
	/**
	 * 根据城市编号获取地区列表
	 * @param city
	 * @return
	 */
	public List<SysDict> findArea(String city);
}