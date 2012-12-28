package org.onesy.test;

import org.onesy.OrderBeans.OutPutOrder;
import org.onesy.PaxosAl.ClusterInfoMap;
import org.onesy.PaxosAl.PaxosNode;
import org.onesy.ThreadBuffer.OrderBufferLevel_3;

public class OutBufferQueueFiller implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub

		PaxosNode tmpPaxos = this.getNodeInfo();
		int count = 0;
		for (;;) {
			try {
				OrderBufferLevel_3.OutBufferQueue.put(new OutPutOrder(tmpPaxos
						.getHost(), tmpPaxos.getPort(), tmpPaxos.getPasswd(),
						tmpPaxos.getChannel(), "hello world!" + ++count));
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public PaxosNode getNodeInfo() {
		return ClusterInfoMap.PaxosNodes.get(ClusterInfoMap.KeySet.get(0));
	}

}
