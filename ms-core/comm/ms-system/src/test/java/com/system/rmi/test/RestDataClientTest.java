package com.system.rmi.test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.rmi.RmiConsumer;
import com.system.rmi.service.RmiDataCenterService;
import com.system.rmi.service.impl.RmiDataCenterServiceImpl;

public class RestDataClientTest {
	public static void main(String[] args) throws RemoteException {
		//注册rmi服务
		//初始化客户端的数据中心
		List<Map<String, Object>> rmiServers = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("host", "127.0.0.1");
		map.put("port", 1099);
		rmiServers.add(map);
		RmiConsumer.init(rmiServers);

		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			RmiConsumer consumer = new RmiConsumer();
			//如果要从另一台启动了RMI注册服务的机器上查找hello实例
			RmiDataCenterService service = consumer.lookup(RmiDataCenterServiceImpl.class.getName());
			if(service == null) {
				//调用远程方法
				System.out.println("没有发现rmi服务");
			} else {
				//调用远程方法
				//String param = "{str}";
				System.out.println("第" + (i + 1) + "次: " + service.list().size());
			}
		}

		long end = System.currentTimeMillis();
		long diff = end - start;
		System.out.println("耗时: " + diff + "ms");
	}
}