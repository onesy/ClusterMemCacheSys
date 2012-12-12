package org.onesy.MQ;

import org.onesy.ThreadBuffer.OrderBufferLevel_1;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class OrderInMQRec extends JedisPubSub implements Runnable {
	
	private static OrderInMQRec orderInMQRec;
	
	private Jedis jedis;
	
	private String channel;
	
	private OrderInMQRec(String host,int port,String channel){
		this.setJedis(new Jedis(host, port));
		this.channel = channel;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.getJedis().subscribe(OrderInMQRec.orderInMQRec, this.channel);
	}
	
	/**
	 * @author way
	 * @param host
	 * @param port
	 * @return
	 *  TODO
	 */
	public static synchronized OrderInMQRec getInstance(String host,int port,String channel){
		if (null == OrderInMQRec.orderInMQRec) {
			OrderInMQRec.orderInMQRec = new OrderInMQRec(host,port,channel);
			return OrderInMQRec.orderInMQRec;
		} else {
			return OrderInMQRec.orderInMQRec;
		}
	}

	@Override
	public void onMessage(String channel, String message) {
		// TODO Auto-generated method stub
		System.out.println(channel + ":" + message);
		//
		/*
		 * author = host::port::passwd
		 * version = long
		 * status = proposed/accepted
		 * category = 
		 * operation : set get del
		 * management : VotLeader VotReceipt DesignateLeader VotWatcher DesignateWatcher 
		 * LeaderASK LeaderAS WatcherASK WatcherAS VersionREQ VersionRESPConfirm ShutdownREQ
		 * content = different type
		 * message = author::version::status::category::content
		 * 节点需要记录所有proposed的message一段时间，以等待status 转变为 accepted
		 * 最终
		 * message 形如
		 * 127.0.0.1::6379::passwd::123456789098765321::proposed::VotLeader::{内部结构不干涉但要附上version}
		 */
		
		String hashKey = message.split("::")[5];
		
		OrderBufferLevel_1.getInstance().getQueue(hashKey).add(message);
		
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
