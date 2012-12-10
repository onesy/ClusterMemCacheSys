package org.onesy.MQ;

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
	public void onMessage(String arg0, String arg1) {
		// TODO Auto-generated method stub
		System.out.println(arg0 + ":" + arg1);
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
		
	}

	@Override
	public void onPMessage(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		System.out.println(arg0 + ":" + arg1 + ":" + arg2);
		
	}

	@Override
	public void onPSubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPUnsubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnsubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}
	
}
