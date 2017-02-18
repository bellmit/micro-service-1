package com.frame.sys.service;

import org.springframework.stereotype.Component;

import com.frame.sys.pojo.SysDictType;

@Component
public interface SysDictTypeService {

	public SysDictType get(String code);

	public void initTable();
}