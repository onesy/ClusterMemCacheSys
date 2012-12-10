package org.onesy.OrderBeans;

import java.util.ArrayList;

import org.onesy.Nodes.NodeInfo;

public abstract class Order {
	
	private OrderBean orderBean;
	
	private ArrayList<NodeInfo> reciever;
	
	public Order(String srcVotSerilizer, String kind, String content, ArrayList<NodeInfo> reciever){
		this.setOrderBean(new OrderBean(srcVotSerilizer, kind, content));
		this.setReciever(reciever);
	}

	public ArrayList<NodeInfo> getReciever() {
		return reciever;
	}

	public void setReciever(ArrayList<NodeInfo> reciever) {
		this.reciever = reciever;
	}

	public OrderBean getOrderBean() {
		return orderBean;
	}

	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}

}
