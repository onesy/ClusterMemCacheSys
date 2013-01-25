package org.onesy.test;

import org.onesy.tools.VariableRegisterUtil;

public class VariableRegisterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		VariableRegisterUtil.getInstance().RegisterSET("key", "value");
		System.out.println(VariableRegisterUtil.getInstance().RegisterGet("key", String.class));

	}

}
