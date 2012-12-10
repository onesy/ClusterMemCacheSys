package org.onesy.Charactor;

import org.onesy.BeansInterface.NodeBean;
import org.onesy.BeansInterface.PaxosBean;
import org.onesy.Nodes.Node;
import org.onesy.Nodes.NodeInfo;
import org.onesy.OrderBeans.Order;

public class Learner extends Node implements PaxosBean,NodeBean {
	
	public Learner(String VotSerilize){
		super(VotSerilize);
	}

	@Override
	public String NodeName() {
		return super.name;
	}

	@Override
	public String NodeHost() {
		return super.host;
	}

	@Override
	public int NodePort() {
		return super.post;
	}

	@Override
	public void SendOrder(NodeInfo nodeInfo, Order order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SendOrderToAllBrother() {
		// TODO Auto-generated method stub
		
	}
}
