package org.onesy.ThreadBuffer;

import java.util.concurrent.ConcurrentLinkedQueue;

public class OrderBufferLevel_1 {
	
	public static ConcurrentLinkedQueue<String> DeleteQueue = new ConcurrentLinkedQueue<String>();
	
	public static ConcurrentLinkedQueue<String> DesignateLeaderQueue = new ConcurrentLinkedQueue<String>();
	
	public static ConcurrentLinkedQueue<String> DesignateWatcherQueue = new ConcurrentLinkedQueue<String>();
	
	

}
