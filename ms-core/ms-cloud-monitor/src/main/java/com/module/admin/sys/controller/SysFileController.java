package com.module.admin.sys.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.module.admin.BaseController;
import com.module.admin.sys.pojo.SysFile;
import com.module.admin.sys.pojo.SysUser;
import com.module.admin.sys.service.SysFileService;
import com.module.admin.sys.utils.SysFileUtil;
import com.system.comm.utils.FrameFileUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.comm.utils.FrameTimeUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * sys_file的Controller
 * @author yuejing
 * @date 2015-05-12 20:57:58
 * @version V1.0.0
 */
@Controller
public class SysFileController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SysFileController.class);

	@Autowired
	private SysFileService sysFileService;

	/**
	 * 上传附件
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sysFile/f-json/upload", method = RequestMethod.POST)
	@ResponseBody
	public void uploadMoneyFile(HttpServletRequest request, HttpServletResponse response, String filename, Integer type, String sourceType) {
		ResponseFrame frame = new ResponseFrame();
		SysUser user = getSessionUser(request);
		logger.info(String.format("%s 用户上传附件!", user.getNickname()));
		String[] exts = new String[] { "doc", "docx", "txt", "xls", "xlsx", "jar", "war", "zip", "rar", "jpg", "png", "gif", "bmp" };
		response.setCharacterEncoding("utf-8");
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//获得文件
		if(FrameStringUtil.isEmpty(filename)) {
			filename = "file";
		}
		MultipartFile multipartFile = multipartRequest.getFile(filename);
		//获得原始文件名
		String orgName = multipartFile.getOriginalFilename();
		String url = null;
		String fileId = null;
		String fileExt = orgName.substring(orgName.lastIndexOf(".") + 1, orgName.length());
		String sysName = null;
		Float fileSize = null;
		//为jpg||png||gif||bmp
		if(FrameStringUtil.isEqualArrSin(fileExt, exts)) {
			InputStream inputStream = null;
			try {
				inputStream = multipartFile.getInputStream();

				String savePath = SysFileUtil.getUploadFileDir();
				Long dateStr = System.currentTimeMillis();
				String dateDir = FrameTimeUtil.parseString(FrameTimeUtil.getTime(), FrameTimeUtil.FMT_YYYY_MM_DD);
				sysName = dateStr + new Random().nextInt(1000) + "." + fileExt;
				String newFilePath = type + File.separator + dateDir + File.separator;
				url = type + "/" + dateDir + "/" + sysName;
				FrameFileUtil.createDir(savePath + newFilePath);
				File uploadFile = new File(savePath, newFilePath + sysName);
				multipartFile.transferTo(uploadFile);

				//获取文件大小
				fileSize = (float) (uploadFile.length() / 1024);
				
				SysFile sysFile = new SysFile();
				sysFile.setType(type);
				sysFile.setOrgName(orgName);
				sysFile.setSysName(sysName);
				sysFile.setUrl(url);
				sysFile.setFileType(fileExt);
				sysFile.setFileSize(fileSize);
				sysFileService.save(sysFile);
				fileId = sysFile.getFileId();
				frame.setCode(ResponseCode.SUCC.getCode());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				frame.setCode(ResponseCode.FAIL.getCode());
			} finally {
				if(inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		} else {
			frame.setCode(-3);
			frame.setMessage("上传文件的格式异常");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fileId", fileId);
		//可以上传的文件扩展
		StringBuffer extStr = new StringBuffer();
		for (String ext : exts) {
			extStr.append(ext).append(";");
		}
		String extStr1 = extStr.substring(0, extStr.length()-1);
		map.put("exts", extStr1);
		map.put("orgName", orgName);
		map.put("sysName", sysName);
		map.put("fileSize", fileSize);
		//map.put("maxSize", UploadFileCons.IMG_MAX/1024 + "M");
		//文件地址
		map.put("url", url);
		
		/*if("kindEditor".equals(sourceType)) {
			map = new HashMap<String, Object>();
			map.put("error", 0);
			map.put("url", ConfigCons.static_url + url);
			writerJson(response, map);
			return;
		}*/
		frame.setBody(map);
		writerJson(response, frame);
	}

	/**
	 * 下载文件
	 * @param request
	 * @param response
	 * @param path
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysFile/f-view/download")
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