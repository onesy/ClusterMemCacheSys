package org.onesy.test;

import org.onesy.tools.CMCS_VariableRegisterUtil;

public class VariableRegisterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CMCS_VariableRegisterUtil.getInstance().RegisterSET("key", "value");
		System.out.println(CMCS_VariableRegisterUtil.getInstance().RegisterGet("key", String.class));

	}

}
