package com.module.admin.code.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.code.dao.CodePrjDao;
import com.module.admin.code.pojo.CodePrj;
import com.module.admin.code.service.CodePrjService;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseFrame;
import com.system.handle.model.ResponseCode;

/**
 * code_prj的Service
 * @author yuejing
 * @date 2017-07-27 09:06:22
 * @version V1.0.0
 */
@Component
public class CodePrjServiceImpl implements CodePrjService {

	@Autowired
	private CodePrjDao codePrjDao;
	
	@Override
	public ResponseFrame saveOrUpdate(CodePrj codePrj) {
		ResponseFrame frame = new ResponseFrame();
		CodePrj org = get(codePrj.getCode());
		if(org == null) {
			codePrjDao.save(codePrj);
		} else {
			codePrjDao.update(codePrj);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public CodePrj get(String code) {
		return codePrjDao.get(code);
	}

	@Override
	public ResponseFrame pageQuery(CodePrj codePrj) {
		codePrj.setDefPageSize();
		ResponseFrame frame = new ResponseFrame();
		int total = codePrjDao.findCodePrjCount(codePrj);
		List<CodePrj> data = null;
		if(total > 0) {
			data = codePrjDao.findCodePrj(codePrj);
		}
		Page<CodePrj> page = new Page<CodePrj>(codePrj.getPage(), codePrj.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(String code) {
		ResponseFrame frame = new ResponseFrame();
		codePrjDao.delete(code);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
}
