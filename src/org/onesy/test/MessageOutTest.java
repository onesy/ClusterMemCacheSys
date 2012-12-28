package org.onesy.test;

import org.onesy.MQ.OrderInMQSendD;
import org.onesy.PaxosAl.ClusterInfoMap;

public class MessageOutTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ClusterInfoMap clusterInfoMap = ClusterInfoMap.getInstanceC();

		OutBufferQueueFiller outBufferQueueFiller = new OutBufferQueueFiller();
		Thread obqtThread = new Thread(outBufferQueueFiller);
		obqtThread.start();
		OrderInMQSendD orderInMQSend = new OrderInMQSendD();
		Thread OrderInMQSendthread = new Thread(orderInMQSend);
		OrderInMQSendthread.start();
		OrderInMQSendD orderInMQSend2 = new OrderInMQSendD();
		Thread OrderInMQSendthread2 = new Thread(orderInMQSend2);
		OrderInMQSendthread2.start();
		OrderInMQSendD orderInMQSend3 = new OrderInMQSendD();
		Thread OrderInMQSendthread3 = new Thread(orderInMQSend3);
		OrderInMQSendthread3.start();
	}

}
