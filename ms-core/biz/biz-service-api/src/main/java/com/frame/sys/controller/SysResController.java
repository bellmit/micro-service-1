package com.frame.sys.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frame.sys.pojo.SysRes;
import com.frame.sys.service.SysResService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 资源
 * @author yuejing
 * @date 2016年1月29日 下午9:31:59
 * @version V1.0.0
 */
@RestController
public class SysResController {

	private final static Logger LOGGER = Logger.getLogger(SysResController.class);
	@Autowired
	private SysResService sysResService;

	/**
	 * 保存
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/sysRes/save")
	public ResponseFrame save(SysRes res) {
		try {
			/*SysRes res = new SysRes();
			res.setResId(FrameMapUtil.getString(map, "resId"));
			res.setType(FrameMapUtil.getInteger(map, "type"));
			res.setParentResId(FrameMapUtil.getString(map, "parentResId"));
			res.setName(FrameMapUtil.getString(map, "name"));
			res.setShowName(FrameMapUtil.getString(map, "showName"));
			res.setUrl(FrameMapUtil.getString(map, "url"));
			res.setRemark(FrameMapUtil.getString(map, "remark"));
			res.setOrderby(FrameMapUtil.getInteger(map, "orderby"));
			res.setAddUserId(FrameMapUtil.getString(map, "addUserId"));*/
			ResponseFrame frame = sysResService.save(res);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}