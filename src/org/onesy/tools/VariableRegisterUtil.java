package org.onesy.tools;

import java.util.concurrent.ConcurrentHashMap;

public class VariableRegisterUtil {
	
	private static volatile VariableRegisterUtil variableRegisterUtil = null;
	
	//在此注册全局变量，如果不再此注册的全局变量均必须使用protected
	
	private static ConcurrentHashMap<Object, Object> CMCS_RegisterHashMap = new ConcurrentHashMap<Object, Object>();
	
	public static VariableRegisterUtil getInstance(){
		if(VariableRegisterUtil.variableRegisterUtil == null){
			synchronized (VariableRegisterUtil.class) {
				if(VariableRegisterUtil.variableRegisterUtil == null){
					VariableRegisterUtil.variableRegisterUtil = new VariableRegisterUtil();
				}
			}
		}
		return VariableRegisterUtil.variableRegisterUtil;
	}
	
	private VariableRegisterUtil(){
		//none code here now
	}
	
	public void RegisterSET(Object key, Object value){
		VariableRegisterUtil.getCMCS_RegisterHashMap().put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public <clazz> clazz RegisterGet(Object key, @SuppressWarnings("rawtypes") Class clazz) {
		return (clazz)VariableRegisterUtil.getCMCS_RegisterHashMap().get(key);
	}

	public static ConcurrentHashMap<Object, Object> getCMCS_RegisterHashMap() {
		return CMCS_RegisterHashMap;
	}

	public static void setCMCS_RegisterHashMap(ConcurrentHashMap<Object, Object> cMCS_RegisterHashMap) {
		CMCS_RegisterHashMap = cMCS_RegisterHashMap;
	}

}
