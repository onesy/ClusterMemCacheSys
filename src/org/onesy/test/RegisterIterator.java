package org.onesy.test;

import org.onesy.MessageHandler.MessageHandlerBase;
import org.onesy.tools.SolidConfigure;

public class RegisterIterator {

	public static void IteratorRegister() {
		for (int i = 0; i < SolidConfigure.HandlerKeys.length; i++) {
			if ( null != MessageHandlerBase.HandlerRegister.get(SolidConfigure.HandlerKeys[i])){
				
			}
		}
	}

}
