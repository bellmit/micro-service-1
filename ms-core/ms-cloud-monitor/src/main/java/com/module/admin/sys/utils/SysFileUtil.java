package com.module.admin.sys.utils;

import java.io.File;

import com.module.admin.sys.enums.SysConfigCode;
import com.module.admin.sys.service.SysConfigService;
import com.module.comm.constants.ConfigCons;
import com.system.comm.utils.FrameSpringBeanUtil;

public class SysFileUtil {
	
	/**
	 * 获取保存文件的前面的绝对路径
	 * @param context
	 * @return
	 */
	public static String getUploadFileDir() {
		SysConfigService configService = FrameSpringBeanUtil.getBean(SysConfigService.class);
		/*String realPath = Constant.realpath;
		String fileDir = realPath.substring(0, realPath.lastIndexOf(File.separator));
		fileDir = fileDir + File.separator + ConfigCons.fileProject + File.separator;
		File dirFile = new File(fileDir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}*/
		String realPath = configService.getValue(SysConfigCode.PRJ_FILE_PATH);
		String fileDir = null;
		if(realPath == null) {
			realPath = ConfigCons.realpath;
			fileDir = realPath.substring(0, realPath.lastIndexOf(File.separator));
		} else {
			fileDir = realPath;
		}
		fileDir = fileDir + File.separator;
		File dirFile = new File(fileDir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		return fileDir;
	}
	

	/**
	 * 获取预览文件的路径
	 * @return
	 */
	public static String getPreviewFileDir() {
		String fileDir = ConfigCons.realpath;
		fileDir = fileDir + File.separator + "preview" + File.separator;
		File dirFile = new File(fileDir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		return fileDir;
	}
}
