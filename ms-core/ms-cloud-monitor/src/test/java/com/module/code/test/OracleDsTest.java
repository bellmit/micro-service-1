package com.module.code.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.module.admin.code.core.DbDs;
import com.module.admin.code.core.OracleDs;
import com.module.admin.code.core.model.Table;

@RunWith(SpringJUnit4ClassRunner.class)  
//@SpringBootTest(classes = MsCloudMonitorApplication.class)  
//@WebAppConfiguration
public class OracleDsTest {
	
	private DbDs dbDs;
	
	// 在@Test标注的测试方法之前运行
	@Before
	public void setUp() throws Exception {
		// 初始化测试用例类中由Mockito的注解标注的所有模拟对象
		MockitoAnnotations.initMocks(this);
		

		dbDs = new OracleDs();
		String driverClass = "oracle.jdbc.driver.OracleDriver";
		String jdbcUrl = "jdbc:oracle:thin:@10.201.224.175:1521:orcl";
		String username = "crm";
		String password = "crm";
		String dbName = "crm";
		dbDs.init(driverClass, jdbcUrl, username, password, dbName);
		
		//sysDictTypeDao = Mockito.mock(SysDictTypeDao.class);

		/*sdt = new SysDictType();
			sdt.setCode("12");*/

		// 设置模拟对象的返回预期值
		//when(sysDictTypeDao.get(code)).thenReturn(sdt);
		// 用模拟对象创建被测类对象
		//sysDictTypeService = new SysDictTypeServiceImpl();
		//sysDictTypeService = Mockito.mock(SysDictTypeServiceImpl.class);
	}

	@After
	public void tearDown() {
	}

	@Test
	public void getTableInfo() {
		// 执行测试
		Table table = dbDs.getTable("client_register");
		
		// 验证更新是否成功
		assertEquals(table.getName(), "client_register");
		// 验证模拟对象的fetchPerson(1)方法是否被调用了一次
		//verify(personDAO).fetchPerson(1);
		/*// 得到一个抓取器
			ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
			// 验证模拟对象的update()是否被调用一次，并抓取调用时传入的参数值
			verify(personDAO).update(personCaptor.capture());
			// 获取抓取到的参数值
			Person updatePerson = personCaptor.getValue();
			// 验证调用时的参数值
			assertEquals("David", updatePerson.getPersonName());
			// asserts that during the test, there are no other calls to the mock object.
			// 检查模拟对象上是否还有未验证的交互
			verifyNoMoreInteractions(personDAO);*/
	}
}
