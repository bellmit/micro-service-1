package com.system.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.system.comm.utils.FrameMapUtil;
import com.system.rmi.data.RmiClientDataService;
import com.system.rmi.data.impl.RmiClientDataServiceImpl;
import com.system.rmi.model.RmiModel;
import com.system.rmi.service.impl.RmiDataCenterServiceImpl;

/**
 * 客户端使用
 * @author yuejing
 *
 */
public class RmiConsumer {

	private static final Logger LOGGER = Logger.getLogger(RmiProvider.class);

	/**
	 * 用于处理数据
	 */
	private static RmiClientDataService dataService;

	/**
	 * 所有rmi节点信息<br>
	 * 注意: key为clsFullName
	 */
	private static Map<String, List<RmiModel>> rmiMap = new HashMap<String, List<RmiModel>>();
	/**
	 * 是否开启监控rmi变化
	 */
	private static boolean isRmiMonitor = false;

	/**
	 * rmi服务集合
	 */
	public static List<RmiModel> rmiServers = new ArrayList<RmiModel>();

	/**
	 * 初始化
	 * @param dataService
	 * @param rdc	数据中心的RMI的对象
	 */
	public static void init(List<Map<String, Object>> rmiServers) {
		if(rmiServers == null || rmiServers.size() == 0) {
			return;
		}
		RmiClientDataService dataService = new RmiClientDataServiceImpl();
		RmiConsumer.dataService = dataService;
		String clsFullName = RmiDataCenterServiceImpl.class.getName();
		for (Map<String, Object> map : rmiServers) {
			RmiConsumer.rmiServers.add(new RmiModel(FrameMapUtil.getString(map, "host"), FrameMapUtil.getInteger(map, "port"), clsFullName));
		}
		watchNode();
	}

	public RmiConsumer() {
	}

	private static List<RmiModel> getClsFullName(String clsFullName) {
		List<RmiModel> rmiList = rmiMap.get(clsFullName);
		return rmiList == null ? new ArrayList<RmiModel>() : rmiList;
	}
	private static void removeRmiModel(RmiModel rmiModel) {
		List<RmiModel> list = getClsFullName(rmiModel.getClsFullName());
		list.remove(rmiModel);
		rmiMap.put(rmiModel.getClsFullName(), list);
	}


	/**
	 * 查找 RMI 服务
	 * @param clsFullName
	 * @return
	 */
	public <T extends Remote> T lookup(String clsFullName) {
		T service = null;
		List<RmiModel> rmiList = getClsFullName(clsFullName);
		int size = rmiList.size();
		if (size > 0) {
			RmiModel rmiModel;
			if (size == 1) {
				rmiModel = rmiList.get(0); // 若 urlList 中只有一个元素，则直接获取该元素
				//LOGGER.info("using only url: {" + rmiModel.getUrl() + "}");
			} else {
				// 若 urlList 中存在多个元素，则随机获取一个元素
				rmiModel = rmiList.get(ThreadLocalRandom.current().nextInt(size));
				//LOGGER.info("using random url: {" + rmiModel.getUrl() + "}");
			}
			// 从 JNDI 中查找 RMI 服务
			try {
				service = lookupService(rmiModel);
			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				// 若连接中断，则使用 urlList 中第一个 RMI 地址来查找（这是一种简单的重试方式，确保不会抛出异常）
				LOGGER.error("重试1次: ConnectException -> url: {" + rmiModel.getUrl() + "}");
				rmiList = getClsFullName(rmiModel.getClsFullName());
				if (rmiList.size() != 0) {
					try {
						Thread.sleep(1000);
						rmiModel = rmiList.get(0);
						return lookupService(rmiModel);
					} catch (Exception e1) {
						// 若连接中断，则使用 urlList 中第一个 RMI 地址来查找（这是一种简单的重试方式，确保不会抛出异常）
		                LOGGER.error("停止重试: ConnectException -> url: {" + rmiModel.getUrl() + "}");
		                removeRmiModel(rmiModel);
					}
				}
			}
		}
		return service;
	}

	// 在 JNDI 中查找 RMI 远程服务对象
	@SuppressWarnings("unchecked")
	private <T> T lookupService(RmiModel rmiModel) throws MalformedURLException, RemoteException, NotBoundException {
		T remote = (T) Naming.lookup(rmiModel.getUrl());
		/*try {
            remote = (T) Naming.lookup(rmiModel.getUrl());
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            if (e instanceof ConnectException) {
                // 若连接中断，则使用 urlList 中第一个 RMI 地址来查找（这是一种简单的重试方式，确保不会抛出异常）
                LOGGER.error("ConnectException -> url: {" + rmiModel.getUrl() + "}");
            }
            LOGGER.error("异常: " + e.getMessage(), e);
        }*/
		return remote;
	}

	/**
	 * 观察 节点下所有子节点是否有变化
	 */
	private synchronized static void watchNode() {
		if(isRmiMonitor) {
			return;
		}
		isRmiMonitor = true;
		LOGGER.info("启动检测rmi客户端检测");
		List<RmiModel> rmiList = dataService.list();
		dealRmiList(rmiList);
		ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
		//线程，每隔8秒调用一次
		Runnable runnable = new Runnable() {
			public void run() {
				try {
					//检测项目的信息，并写入数据库
					dealRmiList(dataService.list());
				} catch (Exception e) {
					LOGGER.error("客户端检测rmi服务异常: " + e.getMessage());
				}
			}
		};
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间 
		service.scheduleAtFixedRate(runnable, 20, 8, TimeUnit.SECONDS);
	}

	private static void dealRmiList(List<RmiModel> rmiList) {
		if(rmiList.size() == 0) {
			LOGGER.error("客户端没有检测到rmi服务");
			return;
		}
		for (RmiModel rmiModel : rmiList) {
			List<RmiModel> cldList = getClsFullName(rmiModel.getClsFullName());
			if(cldList == null) {
				cldList = new ArrayList<RmiModel>();
			}
			boolean isExist = false;
			for (RmiModel rm : cldList) {
				if(rmiModel.getUrl().equals(rm.getUrl())) {
					isExist = true;
					break;
				}
			}
			if(!isExist) {
				cldList.add(rmiModel);
				rmiMap.put(rmiModel.getClsFullName(), cldList);
			}
		}
	}
}
