package com.ms.eureka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注意：@EnableEurekaServer注解就可以让应用变为Eureka服务器
 * 这是因为spring boot封装了Eureka Server，让你可以嵌入到应用中直接使用。
 * 至于真正的EurekaServer是Netflix公司的开源项目，也是可以单独下载使用的。
 * @author yuejing
 * @date 2017年2月16日 下午6:38:44
 */
@EnableEurekaServer
@SpringBootApplication
public class SpringCloudEurekaApplication implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(SpringCloudEurekaApplication.class);

	/*@Autowired
	RabbitMessagingTemplate rabbitMessagingTemplate;*/

	public static void main(String[] args) {
		logger.debug("######## SpringCloudEureka应用启动开始 ########");
		SpringApplication.run(SpringCloudEurekaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.debug("######## SpringCloudEureka应用启动完成 ########");
		/*Map<String,Object> map = new HashMap<>();
		map.put("msg","SpringCloudEureka应用启动");
		rabbitMessagingTemplate.convertAndSend("directExchange","queue.foo", JSON.toJSONString(map));
		map.clear();
		map.put("msg","SpringCloudEureka应用启动成功");
		rabbitMessagingTemplate.convertAndSend("directExchange","queue.bar", JSON.toJSONString(map));*/
	}
}
