package org.onesy.PaxosAl;

import java.util.ArrayList;

import org.onesy.BeansInterface.NodeBean;
import org.onesy.Nodes.Node;

import redis.clients.jedis.Jedis;

public class Paxos {
	
	public static Jedis getRedisConnection(NodeBean pb){
		return new Jedis(pb.NodeHost(), pb.NodePort());
	}
	
	public static ArrayList<Node> getBrotherNode(String votSerilizeNo){
		return null;
	}

}
