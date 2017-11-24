package com.client.rel.data;

import java.util.HashMap;
import java.util.Map;

import com.client.rel.model.ExecShell;

public class ExecShellFailData {

	private static Map<String, ExecShell> data = new HashMap<String, ExecShell>();
	
	public static Map<String, ExecShell> data() {
		return data;
	}
	
	public static void add(ExecShell execShell) {
		if(data.get(execShell.getVersion()) == null) {
			data.put(execShell.getVersion(), execShell);
		}
	}
	
	public static void del(String version) {
		if(data.get(version) != null) {
			data.remove(version);
		}
	}
}
