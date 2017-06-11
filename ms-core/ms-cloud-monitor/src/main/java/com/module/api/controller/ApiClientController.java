package com.module.api.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.module.admin.sys.utils.SysFileUtil;
import com.module.api.service.ApiClientService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 客户端调用的接口
 * @author 岳静
 * @date 2016年3月4日 下午6:22:39 
 * @version V1.0
 */
@RestController
public class ApiClientController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiClientController.class);
	
	@Autowired
	private ApiClientService apiClientService;

	/**
	 * 获取要发布的项目
	@RequestMapping(value = "/auth/client/findRelease")
	@ResponseBody
	public void clientFindRelease(HttpServletRequest request, HttpServletResponse response,
			String clientId) {
		ResponseFrame frame = null;
		try {
			frame = apiClientService.findRelease(clientId);
		} catch (Exception e) {
			LOGGER.error("获取要发布的项目异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
	 */

	/**
	 * 修改客户端发布项目成功
	 * status: 状态[10待发布、20发布中、30发布失败、40发布成功]
	 */
	@RequestMapping(value = "/api/client/updateRelease")
	@ResponseBody
	public ResponseFrame clientUpdateRelease(HttpServletRequest request, HttpServletResponse response,
			String clientId, Integer prjId, String version, Integer status, String statusMsg) {
		ResponseFrame frame = null;
		try {
			frame = apiClientService.updateRelease(clientId, prjId, version, status, statusMsg);
		} catch (Exception e) {
			LOGGER.error("修改客户端发布项目成功异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		return frame;
	}

	/**
	 * 修改客户端发送的心跳
	 */
	@RequestMapping(value = "/api/client/heartbeat")
	@ResponseBody
	public ResponseFrame heartbeat(HttpServletRequest request, HttpServletResponse response,
			String clientId) {
		ResponseFrame frame = null;
		try {
			frame = apiClientService.updateHeartbeat(clientId);
		} catch (Exception e) {
			LOGGER.error("修改客户端发送的心跳异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		return frame;
	}

	/**
	 * 下载项目
	 */
	@RequestMapping(value = "/api/client/download")
	public void download(HttpServletRequest request, HttpServletResponse response, String url) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String downLoadPath = SysFileUtil.getUploadFileDir() + url;
		//String downLoadPath = ctxPath;

		long fileLength = new File(downLoadPath).length();
		int separator = url.lastIndexOf(File.separator);
		if(separator == -1) {
			separator = url.lastIndexOf("/");
		}
		response.setHeader("Content-disposition", "attachment; filename=" + url.substring(separator + 1));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
	}
}