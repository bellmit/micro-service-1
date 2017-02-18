package com.system.rmi.test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.system.rmi.RmiConsumer;
import com.system.rmi.RmiProvider;
import com.system.rmi.service.DataCenterService;
import com.system.rmi.test.service.impl.ServerDataCenterServiceImpl;

public class RestServiceServerTest {

	public static void main(String[] args) throws RemoteException {
		DataCenterService data = new ServerDataCenterServiceImpl();
		String[] locations = {"classpath*:/applicationContext.xml"};
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(locations);
		context.start();
		
		RmiProvider provider = new RmiProvider(data);
		//发布数据中心的RMI服务
		provider.publishDataCenter("127.0.0.1", 1099);
		
		/*//发布接口统计
		RestDataService restDataService = new RestDataServiceImpl();
		provider.publish(restDataService, ConfigCons.restRmiHost, ConfigCons.restRmiPort);*/
		

		//初始化客户端的数据中心
		List<Map<String, Object>> rmiServers = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("host", "127.0.0.1");
		map.put("port", 1099);
		rmiServers.add(map);
		RmiConsumer.init(rmiServers);

		System.out.println("初始rmi服务成功!");
	}
}
