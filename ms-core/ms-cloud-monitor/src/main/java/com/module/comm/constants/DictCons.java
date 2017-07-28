package com.module.comm.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.module.admin.cli.enums.CliInfoStatus;
import com.module.admin.code.enums.CodeTemplateType;
import com.module.admin.prj.enums.PrjDsType;
import com.module.admin.prj.enums.PrjInfoContainer;
import com.module.admin.prj.enums.PrjInfoStatus;
import com.module.admin.prj.enums.PrjMonitorMonitorStatus;
import com.module.admin.prj.enums.PrjMonitorType;
import com.module.admin.sys.enums.SysUserStatus;
import com.module.admin.tts.enums.JobLogStatus;
import com.module.admin.tts.enums.JobStatus;
import com.module.admin.tts.enums.ProjectSign;
import com.system.comm.model.KvEntity;

/**
 * 字典常量
 * @author  duanbin
 * @date    2016年5月27日 下午1:55:23 
 * @version 1.0.0
 */
public class DictCons {
	
	private static final Logger LOGGER = Logger.getLogger(DictCons.class);

	private static Map<String, Object> dictMap = new HashMap<String, Object>();
	
	/**
	 * 初始化权限需要的信息
	 * @param servletContext
	 */
	public static void init(ServletContext servletContext) {
		DictCons.addValue(com.system.comm.enums.Boolean.KEY	, com.system.comm.enums.Boolean.getList(), servletContext);

		DictCons.addValue(SysUserStatus.KEY, 		SysUserStatus.getList(), servletContext);

		DictCons.addValue(PrjInfoStatus.KEY, 		PrjInfoStatus.getList(), servletContext);
		DictCons.addValue(PrjInfoContainer.KEY, 	PrjInfoContainer.getList(), servletContext);
		DictCons.addValue(PrjMonitorType.KEY, 		PrjMonitorType.getList(), servletContext);
		DictCons.addValue(PrjMonitorMonitorStatus.KEY, PrjMonitorMonitorStatus.getList(), servletContext);
		
		DictCons.addValue(CliInfoStatus.KEY, 	CliInfoStatus.getList(), servletContext);
		
		DictCons.addValue(ProjectSign.KEY, 	ProjectSign.getList(), servletContext);
		DictCons.addValue(JobStatus.KEY			, JobStatus.getList(), servletContext);
		DictCons.addValue(JobLogStatus.KEY		, JobLogStatus.getList(), servletContext);
		
		DictCons.addValue(PrjDsType.KEY		, PrjDsType.getList(), servletContext);

		DictCons.addValue(CodeTemplateType.KEY	, CodeTemplateType.getList(), servletContext);
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("========================= 初始化字典信息成功 ===========================");
		}
	}
	
	/**
	 * 添加字典信息
	 * @param key
	 * @param list
	 * @param servletContext
	 */
	public static void addValue(String key, List<KvEntity> list, ServletContext servletContext) {
		Map<String, String> valueMap = new HashMap<String, String>();
		for (KvEntity kvEntity : list) {
			valueMap.put(kvEntity.getCode(), kvEntity.getValue());
		}
		dictMap.put(key, valueMap);
		dictMap.put(key + "_list", list);
		servletContext.setAttribute(key, list);
	}
	
	/**
	 * 根据值获取显示值
	 * @param key
	 * @param value
	 * @return
	 */
	public static String getValue(String key, Object value) {
		if(value == null) {
			return null;
		}
		return getMap(key).get(value.toString());
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getMap(String key) {
		return (Map<String, String>) dictMap.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public static List<KvEntity> getList(String key) {
		return (List<KvEntity>) dictMap.get(key + "_list");
	}
}