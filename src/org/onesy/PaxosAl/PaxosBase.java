package org.onesy.PaxosAl;

import java.util.ArrayList;

import org.onesy.BeansInterface.NodeBean;
import org.onesy.Nodes.Node;

import redis.clients.jedis.Jedis;
/**
 * 这个类是PaxosModel的基类用于提供一些基础的方法，这个类包含Paxos状态机 
 * @author leentingone
 *
 */
public class PaxosBase {
	
	public static PaxosStateMachine paxosStateMachine = null;
	
	public PaxosBase(){
		//TODO 获得状态机
	}
	
	public static Jedis getRedisConnection(NodeBean pb){
		return new Jedis(pb.NodeHost(), pb.NodePort());
	}
	
	public static ArrayList<Node> getBrotherNode(String votSerilizeNo){
		return null;
	}

}
