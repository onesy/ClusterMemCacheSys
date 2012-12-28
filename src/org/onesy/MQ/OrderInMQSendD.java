package org.onesy.MQ;

import java.util.UUID;

import org.onesy.ErrorProcessor.ErrorExceptionBean;
import org.onesy.ErrorProcessor.ErrorRememberer;
import org.onesy.OrderBeans.OutPutOrder;
import org.onesy.ThreadBuffer.OrderBufferLevel_3;

import redis.clients.jedis.Jedis;

public class OrderInMQSendD implements Runnable {

	
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
				System.err.println(Thread.currentThread().getName() + " 已经检测到一个错误，现在该线程必须立即退出！");
				// TODO
				// 发生异常的单元转储，即转储当前处理的OutPutOrder
				MQThreadsManagementD.getErrorOccuSendMsgDumps().add(order);
				// 将本线程从正常队列中消除
				MQThreadsManagementD.getSendThreads().remove(Thread.currentThread());
				// 转储错误信息
				ErrorExceptionBean rBean = new ErrorExceptionBean(UUID.randomUUID(), e);
				ErrorRememberer.getErrExceptionsList().add(rBean);
				// 退出 
				return ;
			}
		}
	}

}
