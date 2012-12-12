package org.onesy.ThreadBuffer;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.onesy.OrderBeans.OutPutOrder;

public class  OrderBufferLevel_3{
	
	public static ConcurrentLinkedQueue<OutPutOrder> OutBufferQueue = new ConcurrentLinkedQueue<OutPutOrder>();
	
}