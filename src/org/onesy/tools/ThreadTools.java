package org.onesy.tools;

import org.onesy.MQ.MQThreadsManagementD;

public class ThreadTools {
	
	public static synchronized void PutIntoErrorList(Thread runnable, MQThreadsManagementD mqthreadsMan){
		MQThreadsManagementD.getErrorThreadsSet().add(runnable);
	}
	
	/**
	 * 
	 * 清理错误线程区内的线程
	 */
	@SuppressWarnings("deprecation")
	public static synchronized void FinishErrorThread(){
		for (int i = 0; i < MQThreadsManagementD.getErrorThreadsSet().size(); i++) {
			MQThreadsManagementD.getErrorThreadsSet().get(i).stop();
		}
	}
	
}
