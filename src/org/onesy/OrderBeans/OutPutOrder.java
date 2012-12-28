package org.onesy.OrderBeans;

public class OutPutOrder {
	
	public String targetHost ;
	
	public int port ;
	
	public String passwd ; 
	
	public String Channel ;
	
	public String Content ;
	
	public OutPutOrder(String targetHost, int port, String passwd, String Channel, String content){
		this.targetHost = targetHost;
		this.port = port;
		this.passwd = passwd;
		this.Channel = Channel;
		this.Content = content;
	}

}
