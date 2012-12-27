package org.onesy.MainPower;

import org.onesy.MQ.OrderInMQRec;
import org.onesy.PaxosAl.ClusterInfoMap;

public class CMCSMainPower {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 初始化参数，并且从配置文件中读取数据
		ClusterInfoMap clusterInfoMap = ClusterInfoMap.getInstanceC();
		// 初始化消息队列接受线程
		OrderInMQRec orderInMQRec = OrderInMQRec.getInstance(
				ClusterInfoMap.LocalNodeInfo.get("HOST"),
				Integer.parseInt(ClusterInfoMap.LocalNodeInfo.get("PORT")),
				ClusterInfoMap.LocalNodeInfo.get("CHANNEL"));
		Thread OrderInMQRecth = new Thread(orderInMQRec);
		orderInMQRec.run();
		// 初始化消息队列发送线程

	}

}
