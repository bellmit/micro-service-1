package com.monitor.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.Tracer;

import com.monitor.log.core.MsLogLevel;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameSpringBeanUtil;


public class MsLog {
	
	private Logger logger;
	private Tracer tracer;
	
	private MsLog(Logger logger) {
		super();
		this.logger = logger;
		this.tracer = FrameSpringBeanUtil.getBean(Tracer.class);
	}

	public static MsLog getMsLog(Class<?> clazz) {
		Logger logger = LoggerFactory.getLogger(clazz);
		return new MsLog(logger);
	}
	
	public void info(Object object) {
		info(FrameJsonUtil.toString(object));
	}
	public void info(String msg) {
		logger.info(msg);
		if(tracer != null) {
			tracer.addTag(MsLogLevel.INFO.toString(), msg);
		}
	}
	public void error(String msg) {
		logger.error(msg);
		if(tracer != null) {
			tracer.addTag(MsLogLevel.ERROR.toString(), msg);
		}
	}
	
	public static void main(String[] args) {
		MsLog log = MsLog.getMsLog(MsLog.class);
		log.info("test");
	}
}
