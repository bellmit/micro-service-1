package com.system.comm.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.system.comm.utils.no.ShareCodeCore;
import com.system.comm.utils.no.SnowflakeIdWorker;

/**
 * 用于生成唯一主键<br>
 * public static void main(String[] args) {
		String u = uuid();
		System.out.println(u);
		System.out.println(timeRandom(null));
		System.out.println(timeRandom(FrameTimeUtil.parseDate("2015-12-12 12:12:12")));
		System.out.println(timeRandom(null));
		System.out.println(timeRandom(null));
	}
 * @author 岳静
 * @date 2016年3月2日 下午4:42:26 
 * @version V1.0
 */
public class FrameNoUtil {

	private static SnowflakeIdWorker idWorker;

	/**
	 * 获取没有-号的uuid<br/>
	 * 例：2fb9b164d1694de08ff479893db3cd63
	 * @return
	 */
	public static String uuid() {
		return uuidFull().replaceAll("-", "");
	}

	/**
	 * 获取完整的UUID<br/>
	 * 例：2fb9b164-d169-4de0-8ff4-79893db3cd63
	 * @return
	 */
	public static String uuidFull() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 根据当前日期和5为随机数生成唯一编号(生成的长度为18位数字)
	 * @param date
	 * @return
	 */
	public static String timeRandom() {
		return timeRandom(null);
	}

	/**
	 * 根据日期和5为随机数生成唯一编号(生成的长度为18位数字)
	 * @param date
	 * @return
	 */
	public static String timeRandom(Date date) {
		int numLen = 5;
		if(date == null) {
			date = FrameTimeUtil.getTime();
		}
		long time = date.getTime();
		int num = (int)(100000 * ThreadLocalRandom.current().nextDouble());
		String random = String.valueOf(num);
		int rLen = random.length();
		if(rLen < numLen) {
			for (int i = 0; i < numLen - rLen; i++) {
				int cldNum = (int)(10 * ThreadLocalRandom.current().nextDouble());
				random = random + cldNum;
			}
		}
		String no = String.valueOf(time) + random;
		return no;
	}

	/**
	 * Twitter的分布式自增ID算法snowflake
	 * @return
	 */
	public synchronized static Long sw() {
		if(idWorker == null) {
			idWorker = new SnowflakeIdWorker();
		}
		long id = idWorker.nextId();
		return id;
	}
	
	/**
	 * 根据ID获取分享码
	 * @param id
	 * @return
	 */
	public static String shareCode(long id) {
		return ShareCodeCore.toSerialCode(id);
	}
	
	/**
	 * 更具code获取源id
	 * @param code
	 * @return
	 */
	public static long shareId(String code) {
		return ShareCodeCore.codeToId(code);
	}

	public static void main(String[] args) throws InterruptedException {
		/*final int length = 60000;
		final Map<Long, Integer> data = new HashMap<Long, Integer>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < length; i++) {
					Long no = FrameNoUtil.sw();
					if(data.get(no) == null) {
						data.put(no, 1);
					} else {
						data.put(no, data.get(no) + 1);
						System.out.println(no);
					}
				}
				System.out.println("size = " + data.size());
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < length; i++) {
					Long no = FrameNoUtil.sw();
					if(data.get(no) == null) {
						data.put(no, 1);
					} else {
						data.put(no, data.get(no) + 1);
						System.out.println(no);
					}
				}
				System.out.println("size = " + data.size());
			}
		}).start();*/

	}
}