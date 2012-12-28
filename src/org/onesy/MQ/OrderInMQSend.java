package org.onesy.MQ;

import org.onesy.OrderBeans.OutPutOrder;
import org.onesy.ThreadBuffer.OrderBufferLevel_3;

import redis.clients.jedis.Jedis;

public class OrderInMQSend implements Runnable {

	
	OutPutOrder order = null;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (;;) {
			try {
				order = OrderBufferLevel_3.OutBufferQueue.poll();
				if (null != order) {
					Jedis jedis = new Jedis(order.targetHost, order.port);// <-
					jedis.publish(order.Channel, order.Content);// <-
					jedis.quit();
				}
			} catch (Exception e) {
				System.err.println(Thread.currentThread().getName() + " has detected a Exception and must Quit now");
				// TODO
				//发生异常的单元转储，即转储当前处理的OutPutOrder
				
				//异常线程转储存，转储本线程本身到异常线程队列，并将本线程从正常队列中消除
				
				//
				break;
			}
		}
	}

}
