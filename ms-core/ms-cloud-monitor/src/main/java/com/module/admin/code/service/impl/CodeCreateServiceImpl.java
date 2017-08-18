package com.module.admin.code.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.code.core.DbDs;
import com.module.admin.code.core.model.Table;
import com.module.admin.code.dao.CodeCreateDao;
import com.module.admin.code.enums.CodeCreateStatus;
import com.module.admin.code.enums.CodeTemplateType;
import com.module.admin.code.pojo.CodeCreate;
import com.module.admin.code.pojo.CodePrj;
import com.module.admin.code.pojo.CodeTemplate;
import com.module.admin.code.service.CodeCreateService;
import com.module.admin.code.service.CodePrjService;
import com.module.admin.code.service.CodeTemplateService;
import com.module.admin.code.utils.CodeFileUtil;
import com.module.admin.code.utils.DbDsUtil;
import com.module.admin.prj.pojo.PrjDs;
import com.module.admin.prj.service.PrjDsService;
import com.ms.env.Env;
import com.ms.env.EnvUtil;
import com.system.comm.model.Page;
import com.system.comm.utils.FrameStringUtil;
import com.system.comm.utils.FrameTimeUtil;
import com.system.comm.utils.FrameZipUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * code_create的Service
 * @author yuejing
 * @date 2017-07-27 09:06:22
 * @version V1.0.0
 */
@Component
public class CodeCreateServiceImpl implements CodeCreateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodeCreateServiceImpl.class);
	@Autowired
	private CodeCreateDao codeCreateDao;
	@Autowired
	private PrjDsService prjDsService;
	@Autowired
	private CodePrjService codePrjService;
	@Autowired
	private CodeTemplateService codeTemplateService;
	
	@Override
	public ResponseFrame saveOrUpdate(CodeCreate codeCreate) {
		ResponseFrame frame = new ResponseFrame();
		if(codeCreate.getStatus() == null) {
			codeCreate.setStatus(CodeCreateStatus.WAIT.getCode());
		}
		if(codeCreate.getId() == null) {
			codeCreateDao.save(codeCreate);
		} else {
			codeCreateDao.update(codeCreate);
		}
		
		//生成源码
		createSource(codeCreate);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	private void createSource(CodeCreate codeCreate) {
		CodePrj cp = codePrjService.get(codeCreate.getCode());
		PrjDs ds = prjDsService.get(cp.getPrjId(), codeCreate.getDsCode());
		DbDs dbDs = DbDsUtil.getDbds(ds, codeCreate.getDbName());
		List<String> tableList = FrameStringUtil.toArray(codeCreate.getTables(), ",");
		List<CodeTemplate> cts = codeTemplateService.findByCode(codeCreate.getCode());
		String createPathDir = EnvUtil.get(Env.CODE_SOURCE_PATH);
		String dateString = FrameTimeUtil.parseString(FrameTimeUtil.getTime(), FrameTimeUtil.FMT_YYYYMMDDHHMMSS);
		String createPath = File.separator + codeCreate.getCode() + File.separator + dateString;
		for (String tableName : tableList) {
			Table table = dbDs.getTable(tableName);
			for (CodeTemplate template : cts) {
				String suffix = "." + CodeTemplateType.getText(template.getType());
				String curCreatePath = createPath + File.separator;
				if(CodeTemplateType.JAVA.getCode() == template.getType().intValue() ||
						CodeTemplateType.XML.getCode() == template.getType().intValue()) {
					String pk = codeCreate.getPackagePath() + "." + template.getPackageName();
					String fileSuffix = template.getSuffix();
					curCreatePath += "src" + File.separator + CodeFileUtil.getFolder(pk) + File.separator + table.getClassName() + FrameStringUtil.setFirstCharUpcase(fileSuffix) + suffix;
				} else {
					curCreatePath += "webapp" + File.separator + table.getBeanName() + "-" + template.getPackageName() + suffix;
				}
				CodeFileUtil.process(getDescMap(table, codeCreate.getPackagePath()), EnvUtil.get(Env.CODE_TEMPLATE_PATH) + template.getPath(), createPathDir + curCreatePath);
			}
		}
		dbDs.close();
		
		//修改为生成完成  生成zip
		String download = createPath + ".zip";
		String sourcePath = createPathDir + createPath;
		String zipPath = createPathDir + File.separator + codeCreate.getCode() + File.separator + dateString + ".zip";
		boolean flag = FrameZipUtil.createZip(sourcePath, zipPath);
		if(flag) {
			codeCreateDao.updateFinish(codeCreate.getId(), download);
			LOGGER.info("文件打包成功!");
		} else {
			LOGGER.info("文件打包失败!");
		}
		codeCreateDao.updateFinish(codeCreate.getId(), download);
	}
	
	/**
	 * 获取描叙信息
	 * @param table
	 * @param packagePath 
	 * @param moduleName 
	 * @return
	 */
	private Map<String, Object> getDescMap(Table table, String packagePath) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", "autoCode");
		map.put("dateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("version", "V1.0.0");
		map.put("tablename", table.getName());
		map.put("className", table.getClassName());
		map.put("packagePath", packagePath);
		map.put("table", table);
		String moduleName = packagePath.substring(packagePath.lastIndexOf('.') + 1);
		map.put("moduleName", moduleName);
		map.put("currentDate", FrameTimeUtil.getTime());
		return map;
	}
	@Override
	public CodeCreate get(Integer id) {
		return codeCreateDao.get(id);
	}

	@Override
	public ResponseFrame pageQuery(CodeCreate codeCreate) {
		codeCreate.setDefPageSize();
		ResponseFrame frame = new ResponseFrame();
		int total = codeCreateDao.findCodeCreateCount(codeCreate);
		List<CodeCreate> data = null;
		if(total > 0) {
			data = codeCreateDao.findCodeCreate(codeCreate);
		}
		Page<CodeCreate> page = new Page<CodeCreate>(codeCreate.getPage(), codeCreate.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(Integer id) {
		ResponseFrame frame = new ResponseFrame();
		codeCreateDao.delete(id);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
}
