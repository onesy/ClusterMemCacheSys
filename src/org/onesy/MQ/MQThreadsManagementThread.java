package org.onesy.MQ;

import java.util.LinkedList;

import org.onesy.PaxosAl.ClusterInfoMap;

public class MQThreadsManagementThread implements Runnable {
	
	private static LinkedList<Runnable> sendThreads = new LinkedList<Runnable>();
	
	private static LinkedList<Runnable> recThread= new LinkedList<Runnable>();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		
		for(;;){
			//守护所有的进程是否正常运行
		}

	}

	public void InitRecMQThreads() {
		this.LocalRecThread();
	}

	public Thread LocalRecThread() {
		// 初始化消息队列接受线程
		OrderInMQRec orderInMQRec = OrderInMQRec.getInstance(
				ClusterInfoMap.LocalNodeInfo.get("HOST"),
				Integer.parseInt(ClusterInfoMap.LocalNodeInfo.get("PORT")),
				ClusterInfoMap.LocalNodeInfo.get("CHANNEL"));
		Thread OrderInMQRecth = new Thread(orderInMQRec);
		OrderInMQRecth.start();
		return OrderInMQRecth;
	}

	public Thread LocalSendThread() {
		// 初始化消息队列发送线程
		OrderInMQSend orderInMQSend = new OrderInMQSend();
		Thread OrderInMQSendthread = new Thread(orderInMQSend);
		OrderInMQSendthread.start();
		
		return OrderInMQSendthread;
	}

}
