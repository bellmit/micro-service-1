package com.system.comm.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File Util
 * @author duanbin
 * @date 2016年3月23日 上午10:38:24 
 * @version V1.0
 */
public class FrameFileUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FrameFileUtil.class);

	/**
	 * 创建指定路径的文件夹[不存在,则创建]
	 * @param destDirName
	 */
	public static void createDir(String destDirName) {
		File dir = new File(destDirName);
		if(dir.exists()) {
			LOGGER.info("目录" + destDirName + "已存在!");
		} else {
			if(!destDirName.endsWith(File.separator)) {
				destDirName = destDirName + File.separator;
			}
			//创建目录
			dir.mkdirs();
		}
	}

	/**
	 * 读取文件[不存在,则创建]
	 * @param destFileName 文件名
	 * @return 创建成功返回true,否则返回false
	 * @throws IOException
	 */
	public static File readFile(String destFileName) throws IOException {
		File file = new File(destFileName);
		if (file.exists()) {
			LOGGER.info("目标文件已存在: " + destFileName);
			return file;
		}
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				LOGGER.info("创建目录文件所在的目录失败!");
			}
		}
		//创建目标文件
		if (file.createNewFile()) {
			LOGGER.info("创建单个文件成功: " + destFileName);
		} else {
			LOGGER.info("创建单个文件失败: " + destFileName);
		}
		return file;
	}

	/**
	 * 读取文件为byte[]
	 * @param destFileName
	 * @return
	 */
	public static byte[] readFileBytes(String destFileName) {
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(destFileName));
			int len = bufferedInputStream.available();
			byte[] bytes = new byte[len];
			int r = bufferedInputStream.read(bytes);
			if (len != r) {
				bytes = null;
				LOGGER.error("读取文件不正确");
			}
			bufferedInputStream.close();
			return bytes;
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}
		catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 根据目录获取下面为指定文件名的文件
	 * @param dir
	 * @param filename
	 * @return
	 */
	public static List<String> getFiles(String dir, String filename) {
		List<String> list = new ArrayList<String>();
		Map<String, List<String>> map = getDirFile(dir);
		List<String> allList = map.get("files");
		if(allList == null) {
			list = new ArrayList<String>();
		} else {
			for (String string : allList) {
				if(string.endsWith(File.separator + filename)) {
					list.add(string);
				}
			}
		}
		return list;
	}

	/**
	 * 获取目录下的文件和文件夹
	 * @param dir
	 * @return
	 */
	public static Map<String, List<String>> getDirFile(String dir) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		File file = new File(dir);
		getDirFileDtl(file, map);
		return map;
	}
	/*
	 * 获取目录的指定内容
	 * @param file
	 * @param map
	 */
	private static void getDirFileDtl(File file, Map<String, List<String>> map) {
		File[] t = file.listFiles();
		if(t == null) {
			return;
		}
		for(int i = 0; i < t.length; i++){
			//判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止
			if(t[i].isDirectory()){
				List<String> dirs = map.get("dirs");
				if(dirs == null) {
					dirs = new ArrayList<String>();
				}
				dirs.add(t[i].getAbsolutePath());
				//System.out.println(t[i].getAbsolutePath()+"\n");
				map.put("dirs", dirs);
				getDirFileDtl(t[i], map);
			} else{
				List<String> files = map.get("files");
				if(files == null) {
					files = new ArrayList<String>();
				}
				files.add(t[i].getAbsolutePath());
				//System.out.println(t[i].getAbsolutePath()+"\n");
				map.put("files", files);
				getDirFileDtl(t[i], map);
			}
		}
	}

	/**
	 * 读取文件的内容到字符串
	 * @param path
	 * @return
	 */
	public static String readFileString(String path) {
		StringBuffer buffer = new StringBuffer();
		try {
			InputStream is = new FileInputStream(path);
			//用来保存每行读取的内容
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			//读取第一行
			line = reader.readLine();
			//如果 line 为空说明读完了
			while (line != null) {
				//将读到的内容添加到 buffer 中
				buffer.append(line);
				//添加换行符
				buffer.append("\n");
				//读取下一行
				line = reader.readLine();
			}
			reader.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * 读取http地址的文件到指定位置
	 * @param url
	 * @param savePath
	 * @param saveName
	 * @return
	 */
	public static File readFile(String url, String savePath, String saveName) {
		try {
			//new一个URL对象
			URL u = new URL(url);
			//打开链接
			HttpURLConnection conn = (HttpURLConnection)u.openConnection();
			//设置请求方式为"GET"
			conn.setRequestMethod("GET");
			//超时响应时间为5秒
			conn.setConnectTimeout(5 * 1000);
			//通过输入流获取图片数据
			InputStream inStream = conn.getInputStream();
			//得到图片的二进制数据，以二进制封装得到数据，具有通用性
			byte[] data = readFileBytes(inStream);

			//new一个文件对象用来保存图片，默认保存当前工程根目录
			createDir(savePath);
			File file = new File(savePath + saveName);
			if(file.exists()) {
				file.delete();
			}
			file = new File(savePath + saveName);
			//创建输出流
			FileOutputStream outStream = new FileOutputStream(file);
			//写入数据
			outStream.write(data);
			//关闭输出流
			outStream.close();
			return file;
		} catch (IOException e) {
			LOGGER.error("读取异常", e);
		}
		return null;
	}

	/**
	 * 将InputStream读取到byte数组中
	 * @param inputStream
	 * @return
	 * @throws IOException 
	 */
	private static byte[] readFileBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
		//创建一个Buffer字符串  
		byte[] buffer = new byte[1024];  
		//每次读取的字符串长度，如果为-1，代表全部读取完毕  
		int len = 0;  
		//使用一个输入流从buffer里把数据读取出来  
		while( (len = inputStream.read(buffer)) != -1 ) {  
			//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
			outStream.write(buffer, 0, len);  
		}  
		//关闭输入流  
		inputStream.close();  
		//把outStream里的数据写入内存  
		return outStream.toByteArray();
	}

	/**
	 * 写入内容到文件
	 * @param content
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static boolean writeFile(String content, File file) throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o= null;
		try {
			o = new FileOutputStream(file);
			o.write(content.getBytes());
			o.close();
			flag = true;
		} catch (Exception e) {
			LOGGER.error("写入内容失败: " + e.getMessage(), e);
		}finally{
			if(mm != null){
				mm.close();
			}
		}
		return flag;
	}
	
	/**
     * 读取文件最后N行 
     * 根据换行符判断当前的行数，
     * 使用统计来判断当前读取第N行
     * @param path		文件路径
     * @param numRead 	读取的行数
     * @return List<String>
     */
    public static List<String> readLastNLine(String path, int numRead) {
    	File file = new File(path);
        // 定义结果集
        List<String> result = new ArrayList<String>();
        //行数统计
        long count = 0;
        
        // 排除不可读状态
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return null;
        }
        
        // 使用随机读取
        RandomAccessFile fileRead = null;
        try {
            //使用读模式
            fileRead = new RandomAccessFile(file, "r");
            //读取文件长度
            long length = fileRead.length();
            //如果是0，代表是空文件，直接返回空结果
            if (length == 0L) {
                return result;
            } else {
                //初始化游标
                long pos = length - 1;
                while (pos > 0) {
                    pos--;
                    //开始读取
                    fileRead.seek(pos);
                    //如果读取到\n代表是读取到一行
                    if (fileRead.readByte() == '\n') {
                        //使用readLine获取当前行
                        String line = fileRead.readLine();
                        //保存结果
                        result.add(new String(line.getBytes("ISO-8859-1"), "UTF-8"));
                        //打印当前行
                        //System.out.println(line);
                        
                        //行数统计，如果到达了numRead指定的行数，就跳出循环
                        count++;
                        if (count == numRead) {
                            break;
                        }
                    }
                }
                if (pos == 0) {
                    fileRead.seek(0);
                    result.add(fileRead.readLine());
                }
            }
        }
        catch (IOException e) {
        	LOGGER.error("读取文件异常: " + e.getMessage());
        } finally {
            if (fileRead != null) {
                try {
                    //关闭资源
                    fileRead.close();
                } catch (Exception e) {
                }
            }
        }
        Collections.reverse(result);
        return result;
    }

	public static void main(String[] args) {
		/*String dir = "D:/project/csm/csm-core";
		List<String> list = getFiles(dir, "pom.xml");
		for (String path : list) {
			System.out.println(path);
		}*/
		/*String path = "D:\\project\\csm\\csm-core\\functions\\window\\csm-window-api\\pom.xml";
		String content = readFileString(path);
		System.out.println(content);*/

		String url = "http://static.52jingya.com/bbs/talk-cont/2016/01/15/483b39df87a6536b8a4dba2d5892c833.jpg";
		String savePath = "C:\\img\\";
		String saveName = "a.jpg";
		readFile(url, savePath, saveName);

	}
}