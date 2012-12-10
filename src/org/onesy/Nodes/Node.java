package org.onesy.Nodes;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.onesy.OrderBeans.Order;

import redis.clients.jedis.Jedis;

public abstract class Node {
	
	//-------------------------------
	//功能完成后需要被重构到配置文件中
	
	//冗余数
	public static int redanduncy = 5;
	//-------------------------------
	
	protected String name;
	
	protected String host;
	
	protected int post;
	
	protected String pubChannel;
	
	protected String subChannel;
	
	//选举编号
	protected Integer VotSerilizer;
	
	protected Jedis jedis;
	
	//兄弟节点互相拥有redis，兄弟节点之间的同步操作频繁
	protected ArrayList<NodeInfo> brotherNodes = new ArrayList<NodeInfo>();
	
	//选举编号-主机key——value hash
	public static ConcurrentHashMap<Integer, NodeInfo> VotSeriToHost = new ConcurrentHashMap<Integer, NodeInfo>();
	//-----------------------------------------------------------------------------------------------
	//产生order都往这里塞
	//读出缓冲区MQ
	public static ConcurrentLinkedQueue<Order> ordersOutQueue = new ConcurrentLinkedQueue<Order>();
	//读入大盘的order都往这里塞
	//读入缓冲区MQ
	public static ConcurrentLinkedQueue<Order> ordersInQueue = new ConcurrentLinkedQueue<Order>();
	//-----------------------------------------------------------------------------------------------
	
	public Node(String VotSerilizer){
		
		//VotSerilizer host:port:pubChannel:recChannel:NodeName:VotSerilizer
		//VotSerilizer 自增
		String[] votParam = VotSerilizer.split(":");
		this.host = votParam[0];
		this.post = Integer.parseInt(votParam[1]);
		this.pubChannel = votParam[2];
		this.subChannel = votParam[3];
		this.name = votParam[4];
		this.VotSerilizer = Integer.parseInt(votParam[5]);
		
		//第一个节点是写节点
		for(int i = 0;i < 5; i ++){
			this.brotherNodes.add(Node.VotSeriToHost.get(i));
		}
		
	}
	
	public Jedis getJedis(){
		return this.jedis;
	}
	
	public ArrayList<NodeInfo> getBrotherNodes(){
		return this.brotherNodes;
	}
	
	public void sendMsgToAllBrotherNode(String msg){
		for (int i = 0; i < this.brotherNodes.size(); i++) {
			NodeInfo nodeinfo = this.getBrotherNodes().get(i);
			nodeinfo.getJedis().publish(nodeinfo.getPubChannel(), msg);
		}
	}
	public void sendMsg(NodeInfo nodeinfo,String msg){
		nodeinfo.getJedis().publish(nodeinfo.getPubChannel(), msg);
	}
	
	public abstract void SendOrder(NodeInfo nodeInfo,Order order);
	
	public abstract void SendOrderToAllBrother();
}
