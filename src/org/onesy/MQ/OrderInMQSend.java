package org.onesy.MQ;

import org.onesy.OrderBeans.OutPutOrder;
import org.onesy.ThreadBuffer.OrderBufferLevel_3;

import redis.clients.jedis.Jedis;

public class OrderInMQSend implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(;;){
			OutPutOrder order = OrderBufferLevel_3.OutBufferQueue.poll();
			Jedis jedis = new Jedis(order.targetHost, order.port);
			jedis.publish(order.Channel, order.Content);
		}
	}
	
}
