package org.onesy.MessageHandler;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public abstract class MessageHandlerBase {
	
	public static Hashtable HandlerRegister = new Hashtable();
	
	/**
	 * author = host::port::passwd
	 * machineId = long
	 * status = proposed/accepted
	 * category = 
	 * operation : set get del
	 * management : VotLeader VotReceipt DesignateLeader VotWatcher DesignateWatcher 
	 * LeaderASK LeaderAS WatcherASK WatcherAS VersionREQ VersionRESPConfirm ShutdownREQ
	 * content = different type
	 * 节点需要记录所有proposed的message一段时间，以等待status 转变为 accepted
	 * 最终
	 * message 形如
	 * 127.0.0.1::6379::passwd::123456789098765321::proposed::VotLeader::{内部结构不干涉但要附上version}
	 */
	private String authorHost ;
	
	private int port ;
	
	private String passwd ;
	
	private Long machineId ;
	
	private String status ;
	
	private String HandlerName;
	
	private MessageHandlerBase handler;
	
	private String Content;
	
	public MessageHandlerBase(String Message){
		
		String[] messageSlice = Message.split("::");
		this.setAuthorHost(messageSlice[0]);
		this.setPort(Integer.parseInt(messageSlice[1]));
		this.setPasswd(messageSlice[2]);
		this.setMachineId(Long.parseLong(messageSlice[3]));
		this.setStatus(messageSlice[4]);
		this.setContent(messageSlice[6]);
		this.setHandlerName(messageSlice[5]);
		
	}
	
	public abstract void ProcessContent();
	
	public abstract void push();
	
	public abstract void pull();

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public MessageHandlerBase getHandler() {
		return handler;
	}

	public void setHandler(MessageHandlerBase handler) {
		this.handler = handler;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getMachineId() {
		return machineId;
	}

	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAuthorHost() {
		return authorHost;
	}

	public void setAuthorHost(String authorHost) {
		this.authorHost = authorHost;
	}

	public String getHandlerName() {
		return HandlerName;
	}

	public void setHandlerName(String handlerName) {
		HandlerName = handlerName;
	}

}
