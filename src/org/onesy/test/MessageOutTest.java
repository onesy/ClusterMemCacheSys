package org.onesy.test;

import org.onesy.MQ.OrderInMQSend;
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
		OrderInMQSend orderInMQSend = new OrderInMQSend();
		Thread OrderInMQSendthread = new Thread(orderInMQSend);
		OrderInMQSendthread.start();
		OrderInMQSend orderInMQSend2 = new OrderInMQSend();
		Thread OrderInMQSendthread2 = new Thread(orderInMQSend2);
		OrderInMQSendthread2.start();
		OrderInMQSend orderInMQSend3 = new OrderInMQSend();
		Thread OrderInMQSendthread3 = new Thread(orderInMQSend3);
		OrderInMQSendthread3.start();
	}

}
