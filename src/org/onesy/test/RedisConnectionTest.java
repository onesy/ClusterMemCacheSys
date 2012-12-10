package org.onesy.test;

import org.onesy.Charactor.Proposor;
import org.onesy.Nodes.Node;
import org.onesy.Nodes.NodeInfo;

public class RedisConnectionTest {
	// -------------------------------
	// 功能完成后需要被重构到配置文件中

	// 序列号起始
	public static int VotSerilizeNo = 0;
	
	

	
	// -------------------------------
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		////VotSerilizer host:port:pubChannel:recChannel:NodeName:VotSerilizer
		//-------------------------------------
		Node.VotSeriToHost.put(0, new NodeInfo("192.168.20.23:6380:foo:bar:Proposor:0"));
		Node.VotSeriToHost.put(1, new NodeInfo("192.168.20.23:6381:foo:bar:Proposor:1"));
		Node.VotSeriToHost.put(2, new NodeInfo("192.168.20.23:6382:foo:bar:Proposor:2"));
		Node.VotSeriToHost.put(3, new NodeInfo("192.168.20.23:6383:foo:bar:Proposor:3"));
		Node.VotSeriToHost.put(4, new NodeInfo("192.168.20.23:6384:foo:bar:Proposor:4"));
		//-------------------------------------

		//host:port:pubChannel:recChannel:NodeName:VotSerilizer
		
		Node proposor = new Proposor("192.168.20.23:6380:foo:bar:Proposor:0");
//		proposor.sendMsgToAllBrotherNode("hello!");
		proposor.sendMsg(Node.VotSeriToHost.get(0), "吱吱吱");

	}

}
