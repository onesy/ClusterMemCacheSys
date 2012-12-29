package org.onesy.MQ;

import java.util.UUID;

import org.onesy.ErrorProcessor.ErrorExceptionBean;
import org.onesy.ErrorProcessor.ErrorRememberer;
import org.onesy.OrderBeans.MQRecErrOccuBean;
import org.onesy.ThreadBuffer.OrderBufferLevel_1;
import org.onesy.tools.CMCS_ConstantsTable;
import org.onesy.tools.CMCS_ThreadTools;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class OrderInMQRecD extends JedisPubSub implements Runnable {

	private static OrderInMQRecD orderInMQRec;

	private Jedis jedis;

	private String channel;
	// test
	long count = 0;

	private OrderInMQRecD(String host, int port, String channel) {
		this.setJedis(new Jedis(host, port));
		this.channel = channel;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(CMCS_ConstantsTable.DEBUG)
			System.out.println("a OrderInMQRecD thread run!");
		this.getJedis().subscribe(OrderInMQRecD.orderInMQRec, this.channel);
	}

	/**
	 * @author way
	 * @param host
	 * @param port
	 * @return TODO
	 */
	public static synchronized OrderInMQRecD getInstance(String host, int port,
			String channel) {
		if (null == OrderInMQRecD.orderInMQRec) {
			OrderInMQRecD.orderInMQRec = new OrderInMQRecD(host, port, channel);
			return OrderInMQRecD.orderInMQRec;
		} else {
			return OrderInMQRecD.orderInMQRec;
		}
	}

	@Override
	public void onMessage(String channel, String message) {
		// TODO Auto-generated method stub
		try {
			if (CMCS_ConstantsTable.DEBUG)
				System.out.println("count:" + ++count + " Channel:" + channel
						+ " message:" + message);
		} catch (Exception e) {
			// TODO: handle exception
			// 转储一个异常对象class = MQRecErrOccuBean
			UUID uuid = UUID.randomUUID();
			if (CMCS_ConstantsTable.DEBUG)
				System.out.println("消息接受或者处理过程中遭遇到异常错误ID=" + uuid.toString()
						+ ",错误已经转储请到控制台查看。");
			// 转储错误对象
			ErrorExceptionBean errorExceptionBean = new ErrorExceptionBean(
					uuid, e);
			ErrorRememberer.getErrExceptionsList().add(errorExceptionBean);
			// 转储当前处理的消息
			MQThreadsManagementD.getMQRecErrOccuDumps().add(
					new MQRecErrOccuBean(channel, message));
			// 取消订阅让线程得以退出
			this.unsubscribe(channel);
			// 将本线程从正常线程转储链表中删除
			MQThreadsManagementD.getRecThreads().remove(Thread.currentThread());
			// 将本线程排入异常线程链表
			CMCS_ThreadTools.PutIntoErrorList(Thread.currentThread(),
					MQThreadsManagementD.getInstance());
			if (CMCS_ConstantsTable.DEBUG)
				System.out.println("错误消息、当前处理消息、异常线程对象已经被转储。线程应正常退出");
		}
		//
		/*
		 * author = host::port::passwd version = long status =
		 * proposed/accepted/against category = operation : set get del
		 * management : VotLeader VotReceipt DesignateLeader VotWatcher
		 * DesignateWatcher LeaderASK LeaderAS WatcherASK WatcherAS VersionREQ
		 * VersionRESPConfirm ShutdownREQ content = different type message =
		 * author::version::status::category::content
		 * 节点需要记录所有proposed的message一段时间，以等待status 转变为 accepted 最终 message 形如
		 * 127.0.0.1::6379::passwd::123456789098765321::proposed::VotLeader::{
		 * 内部结构不干涉但要附上version}
		 */

		/**
		 * while the normal process can run this should be useing String hashKey
		 * = message.split("::")[5];
		 * 
		 * OrderBufferLevel_1.getInstance().getQueue(hashKey).add(message);
		 */

	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		// TODO Auto-generated method stub
		System.out.println(pattern + ":" + channel + ":" + message);

	}

	@Override
	public void onPSubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub

	}

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

}
