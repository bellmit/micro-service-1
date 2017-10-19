package com.monitor.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.Tracer;

import com.monitor.log.core.MsLogLevel;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameSpringBeanUtil;


public class MsLog {
	
	private static final String CONFIG_ERROR = "----->>>>> 请新增LogConfig记录日志的文件 =======";
	private Logger logger;
	private Tracer tracer;
	
	private MsLog(Logger logger) {
		super();
		this.logger = logger;
	}
	
	private Tracer getTracer() {
		if(this.tracer == null) {
			this.tracer = FrameSpringBeanUtil.getBean(Tracer.class);
		}
		return this.tracer;
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
		if(getTracer() != null) {
			getTracer().addTag(MsLogLevel.INFO.toString(), msg);
		} else {
			logger.error(CONFIG_ERROR);
		}
	}
	public void error(String msg) {
		logger.error(msg);
		if(getTracer() != null) {
			getTracer().addTag(MsLogLevel.ERROR.toString(), msg);
		} else {
			logger.error(CONFIG_ERROR);
		}
	}
	
	public static void main(String[] args) {
		MsLog log = MsLog.getMsLog(MsLog.class);
		log.info("test");
	}
}
