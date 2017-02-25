package com.frame.auto.code.main;

import com.frame.auto.code.constants.Config;
import com.frame.auto.code.create.CreateJava;
import com.frame.auto.code.create.CreateJsp;
import com.frame.auto.code.javabean.Log;
import com.frame.auto.code.javabean.TableManager;
import com.frame.auto.code.javabean.TableManagerFactory;
import com.frame.auto.code.model.Table;

/**
 * 生成Mybatis+SpringMVC
 * @author yuejing
 * @date 2013-8-13 下午11:30:17
 * @version V1.0.0
 */
public class MakeSource extends Thread {
	int flag = 0;// 是否运行代码，0:运行，1:停止运行

	public MakeSource() {

	}

	public void run() {
		String[] tables = Config.getInstance().getTableList();
		Table table = null;
		if (tables != null && tables.length > 0) {
			Log.log("==开始生成文件,选定的数据表个数为：" + tables.length + "...",
					Log.LEVEL_INFO);
			//			CreateXML.getInstance().setPath(Config.getInstance().getPath(),Config.getInstance().getPackagePath());
			for (int i = 0; i < tables.length && flag == 0; i++) {
				Log.log((i + 1) + "/" + tables.length + ",当前操作表：" + tables[i],
						Log.LEVEL_INFO);
				TableManager t = TableManagerFactory.getTableManager();
				table = t.getTableInfo(tables[i]);
				Log.log("开始生成JAVA源码...", Log.LEVEL_INFO);
				CreateJava.getInstance().createJava(Config.getInstance().getPath(),
						Config.getInstance().getPackagePath(), table);
				Log.log("开始生成JSP源码...", Log.LEVEL_INFO);
				CreateJsp.getInstance().createJsp(
						Config.getInstance().getPath(),
						Config.getInstance().getPackagePath(), table);
				//				Log.log("开始生成JS源码...", Log.LEVEL_INFO);
				//				CreateJs.getInstance().createJs(
				//						Config.getInstance().getPath(),
				//						Config.getInstance().getPackagePath(), table);
				//				Log.log("开始生成hibernate xml...", Log.LEVEL_INFO);
				//				CreateXML.getInstance().createHibernate(table);
				//				Log.log("开始生成i18n properties...", Log.LEVEL_INFO);
				//				CreateXML.getInstance().createI18n(table);
			}
			//			Log.log("开始生成struts xml...", Log.LEVEL_INFO);
			//TODO
			//			CreateXML.getInstance().createStruts(tables);
			//			Log.log("开始生成spring xml...", Log.LEVEL_INFO);
			//TODO
			//			CreateXML.getInstance().createSpring(tables);

			/*Log.log("开始copy 公共配置文件...", Log.LEVEL_INFO);
			CreateCommons.getInstance().createCommons(
					Config.getInstance().getPath(),
					Config.getInstance().getPackagePath());*/

			Log.log("====完成生成所有源码====", Log.LEVEL_INFO);
		} else {
			Log.log("请至少选择一个被操作的数据库表", Log.LEVEL_ERROR);
		}
	}

	public void setFlag(int f) {
		this.flag = f;
	}
}