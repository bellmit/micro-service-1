package com.frame.sys.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frame.sys.pojo.SysDict;
import com.frame.sys.service.SysDictService;
import com.monitor.api.ApiInfo;
import com.monitor.api.ApiParam;
import com.monitor.api.ApiRes;
import com.system.comm.model.Orderby;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 字典值
 * @author yuejing
 * @date 2016年1月29日 下午9:31:59
 * @version V1.0.0
 */
@RestController
public class SysDictController {

	private final static Logger LOGGER = LoggerFactory.getLogger(SysDictController.class);
	@Autowired
	private SysDictService sysDictService;

	/**
	 * 获取对象
	 * @param params
	 * @return
	 */
	@RequestMapping(name = "字典-字典获取对象", value = "/sysDict/get")
	@ApiInfo(params = {
			@ApiParam(name="类型编码", code="typeCode", clazz=String.class, value="ROLE_INFO"),
			@ApiParam(name="字典值编码", code="dictId", clazz=String.class, value="roleIdClientManager")
	},
	response = {
			@ApiRes(name="响应码", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=Object.class, value=""),
			@ApiRes(name="字典编号", code="dictId", pCode="body", clazz=String.class, value="roleIdClientManager"),
			@ApiRes(name="分类编号", code="typeCode", pCode="body", clazz=String.class, value="ROLE_INFO")
	}
			)
	public ResponseFrame get(String typeCode, String dictId) {
		try {
			ResponseFrame frame = new ResponseFrame();
			frame.setBody(sysDictService.get(typeCode, dictId));
			frame.setCode(ResponseCode.SUCC.getCode());
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(name = "字典-保存或修改", value = "/sysDict/saveOrUpdate")
	public ResponseFrame saveOrUpdate(SysDict sysDict) {
		try {
			ResponseFrame frame = sysDictService.saveOrUpdate(sysDict);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/sysDict/pageQuery")
	public ResponseFrame pageQuery(SysDict sysDict, String orderby) {
		try {
			/*SysDict sysDict = FrameMapUtil.getBean(map, SysDict.class);
			sysDict.setPage(FrameMapUtil.getInteger(map, "page"));
			sysDict.setSize(FrameMapUtil.getInteger(map, "size"));
			String orderbyString = FrameMapUtil.getString(map, "orderby");*/
			if(FrameStringUtil.isNotEmpty(orderby)) {
				List<Orderby> orderbys = FrameJsonUtil.toList(orderby, Orderby.class);
				sysDict.setOrderbys(orderbys);
			}
			ResponseFrame frame = sysDictService.pageQuery(sysDict);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/sysDict/delete")
	public ResponseFrame delete(String dictId) {
		try {
			ResponseFrame frame = sysDictService.delete(dictId);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/sysDict/findProvince")
	public ResponseFrame findProvince() {
		try {
			ResponseFrame frame = new ResponseFrame();
			List<SysDict> data = sysDictService.findProvince();
			frame.setBody(data);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/sysDict/findCity")
	public ResponseFrame findCity(String province) {
		try {
			ResponseFrame frame = new ResponseFrame();
			List<SysDict> data = sysDictService.findCity(province);
			frame.setBody(data);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/sysDict/findArea")
	public ResponseFrame findArea(String city) {
		try {
			ResponseFrame frame = new ResponseFrame();
			List<SysDict> data = sysDictService.findArea(city);
			frame.setBody(data);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}