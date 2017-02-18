package com.system.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.system.rmi.model.RmiModel;
import com.system.rmi.service.DataCenterService;
import com.system.rmi.service.RmiDataCenterService;
import com.system.rmi.service.impl.RmiDataCenterServiceImpl;

/**
 * 服务提供者<br>
 * 来发布 RMI 服务，并将 RMI 地址注册到 redis中。
 * @author yuejing
 *
 */
public class RmiProvider {

	private static final Logger LOGGER = Logger.getLogger(RmiProvider.class);

	/**
	 * 用于处理数据
	 */
	private DataCenterService dataService;
	/**
	 * 是否开启监控rmi变化
	 */
	private static boolean isRmiMonitor = false;

	public RmiProvider(DataCenterService dataService) {
		this.dataService = dataService;
		watchNode(dataService);
	}

	/**
	 * 发布 RMI 服务并注册 RMI 地址到 redis 中
	 * @param remote
	 * @param host
	 * @param port
	 * @return
	 */
	public String publish(Remote remote, String host, Integer port) {
		String url = null;
		try {
			String clsFullName = remote.getClass().getName();
			url = String.format("rmi://%s:%d/%s", host, port, clsFullName);
			//LocateRegistry.createRegistry(port);
			Naming.rebind(url, remote);
			LOGGER.info("publish rmi service (url: " + url + ")");
			RmiModel model = new RmiModel(url, host, port, clsFullName);
			dataService.add(model);
		} catch (RemoteException | MalformedURLException e) {
			LOGGER.error("异常: " + e.getMessage(), e);
		}
		return url;
	}

	/**
	 * 观察 节点服务是否正常
	 */
	private synchronized void watchNode(final DataCenterService dataService) {
		if(isRmiMonitor) {
			return;
		}
		LOGGER.info("启动检测rmi服务端检测");
		isRmiMonitor = true;
		ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
		//线程，每隔8秒调用一次
		Runnable runnable = new Runnable() {
			public void run() {
				List<RmiModel> list = dataService.list();
				for (RmiModel rmiModel : list) {
					//检测项目的信息，并写入数据库
					RmiConsumer consumer = new RmiConsumer();
					//如果要从另一台启动了RMI注册服务的机器上查找hello实例
					Remote remote = consumer.lookup(rmiModel.getClsFullName());
					if(remote == null) {
						//服务终止了
						LOGGER.error("服务url{" + rmiModel.getUrl() + "}, 终止了");
						dataService.del(rmiModel.getUrl());
					}
				}
			}
		};
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间 
		service.scheduleAtFixedRate(runnable, 20, 8, TimeUnit.SECONDS);
	}

	/**
	 * 发布数据中心的RMI
	 * @param host
	 * @param port
	 * @throws RemoteException
	 */
	public void publishDataCenter(String host, int port) throws RemoteException {
		//发布数据中心RMI
		RmiDataCenterService rdcService = new RmiDataCenterServiceImpl();
		LocateRegistry.createRegistry(port);
		publish(rdcService, host, port);
		
		/*String url = null;
		try {
			String clsFullName = rdcService.getClass().getName();
			url = String.format("rmi://%s:%d/%s", host, port, clsFullName);
			//LocateRegistry.createRegistry(port);
			Naming.rebind(url, rdcService);
			LOGGER.info("publish rmi service (url: " + url + ")");
			RmiModel model = new RmiModel(url, host, port, clsFullName);
			
			//dataService.add(model);
		} catch (RemoteException | MalformedURLException e) {
			LOGGER.error("异常: " + e.getMessage(), e);
		}*/
	}
}
