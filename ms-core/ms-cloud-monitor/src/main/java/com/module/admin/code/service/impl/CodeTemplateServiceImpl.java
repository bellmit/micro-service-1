package com.module.admin.code.service.impl;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.code.dao.CodeTemplateDao;
import com.module.admin.code.enums.CodeTemplateType;
import com.module.admin.code.pojo.CodePrj;
import com.module.admin.code.pojo.CodeTemplate;
import com.module.admin.code.service.CodePrjService;
import com.module.admin.code.service.CodeTemplateService;
import com.ms.env.Env;
import com.ms.env.EnvUtil;
import com.system.comm.model.Page;
import com.system.comm.utils.FrameFileUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * code_template的Service
 * @author yuejing
 * @date 2017-07-27 09:06:22
 * @version V1.0.0
 */
@Component
public class CodeTemplateServiceImpl implements CodeTemplateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodeTemplateServiceImpl.class);
	@Autowired
	private CodeTemplateDao codeTemplateDao;
	@Autowired
	private CodePrjService codePrjService;

	@Override
	public ResponseFrame saveOrUpdate(CodeTemplate codeTemplate) {
		ResponseFrame frame = new ResponseFrame();
		String pathDir = EnvUtil.get(Env.CODE_TEMPLATE_PATH);
		String path = File.separator + codeTemplate.getCode() + File.separator;
		FrameFileUtil.createDir(pathDir + path);
		String pathname = path + codeTemplate.getName();
		try {
			FrameFileUtil.writeFile(codeTemplate.getContent(), new File(pathDir + pathname));
		} catch (Exception e) {
			LOGGER.error("创建文件失败");
			frame.setCode(-2);
			frame.setMessage("创建文件失败");
			return frame;
		}
		codeTemplate.setPath(pathname);
		CodeTemplate org = get(codeTemplate.getCode(), codeTemplate.getName());
		if(org == null) {
			CodePrj codePrj = new CodePrj();
			codePrj.setCode(codeTemplate.getCode());
			codePrj.setPrjId(codeTemplate.getPrjId());
			codePrj.setName(codeTemplate.getCode());
			codePrj.setUserId(codeTemplate.getUserId());
			codePrjService.saveOrUpdate(codePrj);
			codeTemplateDao.save(codeTemplate);
		} else {
			codeTemplateDao.update(codeTemplate);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public CodeTemplate get(String code, String name) {
		return codeTemplateDao.get(code, name);
	}

	@Override
	public ResponseFrame pageQuery(CodeTemplate codeTemplate) {
		codeTemplate.setDefPageSize();
		ResponseFrame frame = new ResponseFrame();
		int total = codeTemplateDao.findCodeTemplateCount(codeTemplate);
		List<CodeTemplate> data = null;
		if(total > 0) {
			data = codeTemplateDao.findCodeTemplate(codeTemplate);
			for (CodeTemplate ct : data) {
				ct.setTypeName(CodeTemplateType.getText(ct.getType()));
			}
		}
		Page<CodeTemplate> page = new Page<CodeTemplate>(codeTemplate.getPage(), codeTemplate.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public ResponseFrame delete(String code, String name) {
		ResponseFrame frame = new ResponseFrame();
		CodeTemplate codeTemplate = get(code, name);
		if(codeTemplate != null) {
			codeTemplateDao.delete(code, name);
			//删除文件
			FrameFileUtil.deleteFile(codeTemplate.getPath());
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public List<CodeTemplate> findByCode(String code) {
		return codeTemplateDao.findByCode(code);
	}
}
