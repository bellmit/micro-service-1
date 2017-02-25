package com.frame.auto.code.javabean;

/**
 * <p>Title:Java 代码自动生成工具</p>
 * <p>Description: 主要应用于oracle,sqlserver数据库数据基本操作</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author hellion
 * @version 1.0
 */

public class StringUtil {

  private StringUtil() {
  }
  /**
   * 将第一个字母转换为大写
   * @param s
   * @return
   */
  public static String setFirstCharUpcase(String s){
    if(s==null||s.length()<1) return s;
    char[] c= s.toCharArray();
    if(c.length>0){
      if(c[0]>='a'&&c[0]<='z')c[0]=(char)((short)(c[0])-32);
    }
    return String.valueOf(c);
  }
  
  public static String setFirstCharLowcase(String s){
	    if(s==null||s.length()<1) return s;
	    char[] c= s.toCharArray();
	    if(c.length>0){
	      if(c[0]>='A'&&c[0]<='Z')c[0]=(char)((short)(c[0])+32);
	    }
	    return String.valueOf(c);
	  }

	public static boolean isEmpty(String a){
		return a==null||a.trim().length()<1;
	}
}