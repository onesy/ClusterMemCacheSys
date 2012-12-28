package org.onesy.ErrorProcessor;

import java.util.LinkedList;

/**
 * 本类含有异常队列，并且本类为单例
 * @author onesy
 *
 */
public class ErrorRememberer {
	
	private static ErrorRememberer self;
	//异常队列
	private static volatile LinkedList<ErrorExceptionBean> ErrExceptionsList = new LinkedList<ErrorExceptionBean>();
	
	private ErrorRememberer(){
		
	}
	
	public static synchronized ErrorRememberer getInstance(){
		if(null == ErrorRememberer.self){
			ErrorRememberer.self = new ErrorRememberer();
		}
		return ErrorRememberer.self;
	}

	public static LinkedList<ErrorExceptionBean> getErrExceptionsList() {
		return ErrExceptionsList;
	}

	public static void setErrExceptionsList(LinkedList<ErrorExceptionBean> errExceptionsList) {
		ErrExceptionsList = errExceptionsList;
	}

}
