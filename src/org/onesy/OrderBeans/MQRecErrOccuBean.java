package org.onesy.OrderBeans;

public class MQRecErrOccuBean {
	
	private String Channel ;
	
	private String Msg ;
	
	public MQRecErrOccuBean(String Channel, String Msg){
		this.setChannel(Channel);
		this.setMsg(Msg);
	}

	public String getChannel() {
		return Channel;
	}

	public void setChannel(String channel) {
		Channel = channel;
	}

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}
	
	

}
