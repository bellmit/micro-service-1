package com.frame.test.dao;

import org.springframework.stereotype.Component;

import com.frame.test.pojo.Test;
import com.system.comm.utils.FrameNoUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.dao.BaseDao;
import com.system.dao.sql.QuerySql;

@Component
public class TestDao extends BaseDao {

	public void save(Test test) {
		if(FrameStringUtil.isEmpty(test.getId())) {
			test.setId(FrameNoUtil.uuid());
		}
		super.save(test);
	}
	
	public Test get(String id) {
		QuerySql querySql = new QuerySql(Test.class);
		return super.get(querySql);
	}
}
