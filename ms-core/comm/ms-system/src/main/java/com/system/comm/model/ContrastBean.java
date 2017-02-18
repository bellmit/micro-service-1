package com.system.comm.model;

import java.io.Serializable;

/**
 * FrameBeanUtil中对比使用的数据保存的实体
 * @author 岳静
 * @date 2017年1月23日 下午2:34:43 
 * @version V1.0
 */
@SuppressWarnings("serial")
public class ContrastBean implements Serializable {

	//属性的名称
	private String property;
	//对象1的值
	private Object value1;
	//对象2的值
	private Object value2;

	public ContrastBean() {
		super();
	}

	public ContrastBean(String property, Object value1, Object value2) {
		super();
		this.property = property;
		this.value1 = value1;
		this.value2 = value2;
	}

	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Object getValue1() {
		return value1;
	}
	public void setValue1(Object value1) {
		this.value1 = value1;
	}
	public Object getValue2() {
		return value2;
	}
	public void setValue2(Object value2) {
		this.value2 = value2;
	}
}