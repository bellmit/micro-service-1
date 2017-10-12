package com.ms.biz.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class MsHiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsHiApplication.class, args);
	}

	private static final Logger LOG = Logger.getLogger(MsHiApplication.class.getName());


	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
    Tracer tracer;

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@RequestMapping("/hi")
	public String callHome() throws IOException{
		tracer.addTag("operator", "forezp");
		LOG.log(Level.INFO, "calling trace service-hi  ");
		//return FrameHttpUtil.get("http://localhost:3200/miya");
		return restTemplate.getForObject("http://localhost:3200/miya", String.class);
	}
	@RequestMapping("/info")
	public String info(){
		tracer.addTag("operator","forezp");
		LOG.log(Level.INFO, tracer.getCurrentSpan().traceIdString());
		LOG.log(Level.INFO, "calling trace service-hi ");

		return "i'm service-hi";

	}

	@Bean
	public AlwaysSampler defaultSampler(){
		return new AlwaysSampler();
	}
}
