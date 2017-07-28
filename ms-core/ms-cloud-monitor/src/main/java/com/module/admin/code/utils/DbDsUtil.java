package com.module.admin.code.utils;

import com.module.admin.code.core.DbDs;
import com.module.admin.code.core.MysqlDs;
import com.module.admin.code.core.OracleDs;
import com.module.admin.prj.enums.PrjDsType;
import com.module.admin.prj.pojo.PrjDs;

public class DbDsUtil {

	public static DbDs getDbds(PrjDs ds, String dbName) {
		DbDs dbDs = null;
		if(PrjDsType.MYSQL.getCode().equals(ds.getType())) {
			dbDs = new MysqlDs();
		} else if(PrjDsType.ORACLE.getCode().equals(ds.getType())) {
			dbDs = new OracleDs();
		}
		dbDs.init(ds.getDriverClass(), ds.getUrl(), ds.getUsername(), ds.getPassword(), dbName);
		return dbDs;
	}
}
