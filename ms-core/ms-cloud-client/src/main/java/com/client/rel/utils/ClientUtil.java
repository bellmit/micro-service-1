package com.client.rel.utils;

import java.io.File;

import com.client.ConfigCons;
import com.system.comm.utils.FrameFileUtil;

public class ClientUtil {

	/**
	 * 获取上传文件的目录<br>
	 * 如：/home/www/1/
	 * @param prjId
	 * @return
	 */
	public static String getPrjPath(Integer prjId) {
		String prjPath = ConfigCons.versionDir + File.separator + ConfigCons.clientId + File.separator + prjId + File.separator;
		FrameFileUtil.createDir(prjPath);
		return prjPath;
	}
}
