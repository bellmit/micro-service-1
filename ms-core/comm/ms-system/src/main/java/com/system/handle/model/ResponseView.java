package com.system.handle.model;


import java.io.Serializable;

/**
 * 响应视图实体
 * @author yuejing
 * @date 2016年1月29日 下午9:29:33
 * @version V1.0.0
 */
public class ResponseView implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //重定向的url
    private String view;

    public ResponseView(String view) {
    	this.view = view;
    }

	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
}
