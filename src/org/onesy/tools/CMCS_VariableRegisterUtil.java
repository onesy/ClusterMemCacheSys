package org.onesy.tools;

import java.util.concurrent.ConcurrentHashMap;

public class CMCS_VariableRegisterUtil {
	
	//在此注册全局变量，如果不再此注册的全局变量均必须使用protected
	
	private static ConcurrentHashMap<Object, Object> CMCS_RegisterHashMap = new ConcurrentHashMap<Object, Object>();
	
	public void Register(Object key, Object value){
		
	}

	public static ConcurrentHashMap<Object, Object> getCMCS_RegisterHashMap() {
		return CMCS_RegisterHashMap;
	}

	public static void setCMCS_RegisterHashMap(ConcurrentHashMap<Object, Object> cMCS_RegisterHashMap) {
		CMCS_RegisterHashMap = cMCS_RegisterHashMap;
	}

}