package com.module.code.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.module.admin.code.utils.CodeFileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
public class CreateFileTest {
	
	@Before
	public void setUp() throws Exception {
		
	}
	@After
	public void tearDown() {
	}

	@Test
	public void create() {
		//数据源对象
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("message", "用户列表");
		//String template = "D:\\git\\micro-service\\ms-core\\ms-cloud-monitor\\src\\main\\resources\\template\\code\\hello.ftl";//模板文件的地址
		String template = "/template/code/hello.ftl";//模板文件的地址
		String path = "E:\\UserList.doc";//生成的word文档的输出地址
		CodeFileUtil.process(root, template, path);
	}
}