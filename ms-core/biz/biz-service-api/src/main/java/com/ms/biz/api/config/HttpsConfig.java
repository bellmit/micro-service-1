package com.ms.biz.api.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.ms.env.Env;

@Configuration
public class HttpsConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addAdditionalTomcatConnectors(createHttpConnector());
		return tomcat;
	}

	private Connector createHttpConnector() {
		Integer port = Integer.valueOf(env.getProperty(Env.HTTP_PORT.getCode()));
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(port);
		connector.setSecure(false);
		return connector;
	}
}
