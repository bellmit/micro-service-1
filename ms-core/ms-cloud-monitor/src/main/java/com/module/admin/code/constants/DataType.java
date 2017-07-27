package com.module.admin.code.constants;

import java.util.HashMap;
/**
 * <p>Title: 代码自动生成工具-数据库数据类型常量定义</p>
 * <p>Description: 主要应用于oracle,sqlserver数据库数据基本操作</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author hellion
 * @version 1.0
 */

public  class  DataType {
  public static final int DATA_INT=0;//整数类型
  public static final int DATA_FLOAT=1;//浮点数类型
  public static final int DATA_String=2;//字符串类型
  public static final int DATA_TIME=3;//日期,时间类型
  public static final int DATA_LONG=4;//长整数类型
//对应数据类型的java定义符号,用于代码生成.
  public static final String[] DATA_TYPE=new String[]{"Integer","Double","String","Date","Long"};
  public static final String[] DATA_TYPE_FULL=new String[]{"java.lang.Integer","java.lang.Double","java.lang.String","java.util.Date","java.lang.Long"};

  public static DataType _instance = new DataType();
  private HashMap<String, Object> _TYPE_MAP;//数据库类型与java类型对应表
  private DataType() {
    _TYPE_MAP = new HashMap<String, Object>();
    _TYPE_MAP.put("number",new Integer(DataType.DATA_LONG));
    _TYPE_MAP.put("numberb",new Integer(DataType.DATA_LONG));
    _TYPE_MAP.put("numberf",new Integer(DataType.DATA_FLOAT));
    _TYPE_MAP.put("varchar2",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("date",new Integer(DataType.DATA_TIME));
    //以下为SQLServer类型
    _TYPE_MAP.put("tinyint",new Integer(DataType.DATA_INT));
    _TYPE_MAP.put("smallint",new Integer(DataType.DATA_INT));
    _TYPE_MAP.put("int",new Integer(DataType.DATA_INT));
    _TYPE_MAP.put("bigint",new Integer(DataType.DATA_INT));
    _TYPE_MAP.put("numeric",new Integer(DataType.DATA_LONG));
    _TYPE_MAP.put("numericb",new Integer(DataType.DATA_LONG));
    _TYPE_MAP.put("numericf",new Integer(DataType.DATA_FLOAT));
    _TYPE_MAP.put("money",new Integer(DataType.DATA_FLOAT));
    _TYPE_MAP.put("float",new Integer(DataType.DATA_FLOAT));
    _TYPE_MAP.put("decimal",new Integer(DataType.DATA_FLOAT));
    _TYPE_MAP.put("smallmoney",new Integer(DataType.DATA_FLOAT));
    _TYPE_MAP.put("text",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("ntext",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("varbinary",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("varchar",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("binary",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("char",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("nvarchar",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("nchar",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("ntext",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("bit",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("smalldatetime",new Integer(DataType.DATA_TIME));
    _TYPE_MAP.put("datetime",new Integer(DataType.DATA_TIME));
    _TYPE_MAP.put("timestamp",new Integer(DataType.DATA_TIME));
    //以下为mysql数据类型
    _TYPE_MAP.put("mediumint",new Integer(DataType.DATA_INT));
    _TYPE_MAP.put("date",new Integer(DataType.DATA_TIME));
    _TYPE_MAP.put("time",new Integer(DataType.DATA_TIME));
    _TYPE_MAP.put("year",new Integer(DataType.DATA_TIME));
    _TYPE_MAP.put("binary",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("tinyblob",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("tinytext",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("blob",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("mediumblob",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("mediumtext",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("longblob",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("longtext",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("enum",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("set",new Integer(DataType.DATA_String));

    //以下为DB2数据类型
    _TYPE_MAP.put("integer",new Integer(DataType.DATA_INT));
    _TYPE_MAP.put("real",new Integer(DataType.DATA_FLOAT));
    _TYPE_MAP.put("double",new Integer(DataType.DATA_FLOAT));
    _TYPE_MAP.put("decimal",new Integer(DataType.DATA_FLOAT));
    _TYPE_MAP.put("char",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("varchar for bit data",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("long varchar",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("clob",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("graphic",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("vargraphic",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("long vargraphic",new Integer(DataType.DATA_String));
    _TYPE_MAP.put("dbclob",new Integer(DataType.DATA_String));
    
  }

  public static DataType getInstance(){
    return _instance;
  }
  /**
   * 取对应java类型的描述
   * @param dataType
   * @return
   */
  public static String getDescription(int dataType){
    if(dataType>=DATA_TYPE.length||dataType<0)return "";
    return DataType.DATA_TYPE[dataType];
  }

  /**
   * 取数据库类型对应的java类型定义 默认用String
   * @param type
   * @return
   */
  public int getTypeMap(String type){
    Integer r = (Integer)(this._TYPE_MAP.get(type));
    if(r!=null) return r.intValue();
    else return DataType.DATA_String;
  }


}