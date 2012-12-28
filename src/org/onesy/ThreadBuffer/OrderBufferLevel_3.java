package org.onesy.ThreadBuffer;

import java.util.concurrent.LinkedBlockingQueue;

import org.onesy.OrderBeans.OutPutOrder;

public class  OrderBufferLevel_3{
	
	public static LinkedBlockingQueue<OutPutOrder> OutBufferQueue = new LinkedBlockingQueue<OutPutOrder>(500);
	
}