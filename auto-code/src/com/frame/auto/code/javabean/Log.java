package com.frame.auto.code.javabean;

import java.io.*;
import java.util.*;
import java.sql.Timestamp;

import com.frame.auto.code.constants.Config;

/**
 * <p>
 * Title: 代码自动生成工具-文件操作
 * </p>
 * <p>
 * Description: 主要应用于oracle,sqlserver数据库数据基本操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author hellion
 * @version 1.0
 */

public class Log {

	public static final int LEVEL_DEBUG = 0;
	public static final int LEVEL_INFO = 1;
	public static final int LEVEL_ERROR = 2;
	private static Timestamp time = new Timestamp(System.currentTimeMillis());
	private static StringBuffer infoBuffer = new StringBuffer(200);

	public Log() {
	}

	public static void log(int i, int level) {
		log("" + i, level);
	}

	public static void log(String info, int level) {
		if (level >= Config.getInstance().getLogLevel()) {
			logf(info, level);
		}
	}

	public static void log(String info) {
		log(info, 0);
	}

	public static void log(int info) {
		log(info, 0);
	}

	synchronized private static void logf(String info, int level) {
		infoBuffer.delete(0, infoBuffer.length());
		time.setTime(System.currentTimeMillis());
		infoBuffer.append("[").append(level).append("]")
				.append(time.toString()).append("\t").append(info);
		Config.getInstance().getLogArea().append("\r\n");
		Config.getInstance().getLogArea().append(infoBuffer.toString());
		Log.writeFile(infoBuffer.toString());
	}

	public static void log(Exception e) {
		e.printStackTrace();
		Log.log(e.getMessage(), Log.LEVEL_ERROR);
		if (Config.getInstance().getLogLevel() <= Log.LEVEL_ERROR) {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(Config.getInstance().getPath()
						+ "log" + File.separator + "log.txt", true);
				e.printStackTrace(new PrintStream(fos));
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (fos != null)
					try {
						fos.close();
					} catch (Exception es) {
						es.printStackTrace();
					}
			}
		}
	}

	private static void writeFile(String message) {
		createDictory(Config.getInstance().getPath() + "log");
		Log.writeFile(message, Config.getInstance().getPath() + "log"
				+ File.separator + "log.txt");
	}

	public static void writeFile(String message, String path) {
		writeFile(message, path, true);
	}

	public static void writeFile(String message, String path, boolean append) {
		OutputStreamWriter os = null;
		try {
			os = new OutputStreamWriter(new FileOutputStream(path, append),
					"UTF-8");
			os.write((message + System.getProperty("line.separator")));
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读文件.
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static List readFile(String filename) throws IOException {
		return readFile(filename, 0, Integer.MAX_VALUE);
	}

	/**
	 * 读文件
	 * 
	 * @param filename
	 * @param beginPos
	 *            开始行位置
	 * @param endPos
	 *            结束行位置
	 * @return
	 * @throws IOException
	 */
	public static List readFile(String filename, int beginPos, int endPos)
			throws IOException {
		List list = new ArrayList(100);
		BufferedReader reader = null;
		String s = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(new FileInputStream(filename))));
			int pos = 0;
			while ((s = reader.readLine()) != null && pos >= beginPos
					&& pos++ < endPos) {
				list.add(s);
			}
		} catch (IOException e) {
			Log.log(e);
			throw e;
		}
		return list;
	}

	/**
	 * 递归构建目录
	 * 
	 * @param path
	 *            目录路径
	 * @return
	 */
	public static void createDictory(String path) {
		File f = new File(path);
		File ft = f;
		while (!f.exists()) {
			ft = f;
			f = f.getParentFile();
		}
		if (f != ft) {
			ft.mkdir();
			createDictory(path);
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPathFile
	 *            准备复制的文件源
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名
	 * @return
	 */
	public static void copyFile(String oldPathFile, String newPathFile) {
		createDictory(newPathFile.substring(0,
				newPathFile.lastIndexOf('/') > 0 ? newPathFile.lastIndexOf('/')
						: newPathFile.lastIndexOf('\\')));
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // 文件存在时
				inStream = new FileInputStream(oldPathFile); // 读入原文件
				fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inStream.close();
			} catch (Exception ex) {

			}
			try {
				fs.close();
			} catch (Exception ex) {

			}

		}
	}

	/**
	 * cp整个目录下面的文件和目录到对应的目录
	 * 
	 * @param oldDic
	 *            例如：d:/temp/ 注意：需要带'/'
	 * @param newDic
	 */
	public static void copyDic(String oldDic, String newDic) {
		copyDic(oldDic, newDic, null);
	}

	/**
	 * cp整个目录下面的文件和目录到对应的目录
	 * 
	 * @param oldDic
	 *            例如：d:/temp/ 注意：需要带'/'
	 * @param newDic
	 * @param excName
	 *            排除的目录或者文件名称,可以用正则表达式
	 */
	public static void copyDic(String oldDic, String newDic, String excName) {
		createDictory(newDic);
		String[] fileNames = new File(oldDic).list();
		for (int i = 0; fileNames != null && i < fileNames.length; i++) {
			if (excName != null && fileNames[i].matches(excName)) {
				continue;
			}
			if (new File(oldDic + fileNames[i]).isFile()) {
				copyFile(oldDic + fileNames[i], newDic + fileNames[i]);
			} else {// 递归cp子目录
				copyDic(oldDic + fileNames[i] + "/", newDic + fileNames[i]
						+ "/", excName);
			}
		}
	}
}