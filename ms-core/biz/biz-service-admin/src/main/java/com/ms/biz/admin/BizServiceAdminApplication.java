package com.ms.biz.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@ComponentScan("com.*")
@SpringBootApplication
public class BizServiceAdminApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BizServiceAdminApplication.class, args);
		
		/*AsyncRestTemplate template = new AsyncRestTemplate();  
	    //调用完后立即返回（没有阻塞）  
	    ListenableFuture<ResponseEntity<User>> future = template.getForEntity("http://localhost:9080/spring4/api", User.class);  
	    //设置异步回调  
	    future.addCallback(new ListenableFutureCallback<ResponseEntity<User>>() {  
	        @Override  
	        public void onSuccess(ResponseEntity<User> result) {  
	            System.out.println("======client get result : " + result.getBody());  
	        }  
	  
	        @Override  
	        public void onFailure(Throwable t) {  
	            System.out.println("======client failure : " + t);  
	        }  
	    });  
	    System.out.println("==no wait"); */ 
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BizServiceAdminApplication.class);
    }
}
