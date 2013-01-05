package org.onesy.MessageHandler;


import org.onesy.tools.SolidConfigure;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public abstract class MessageHandlerBase {
	/**
	 * 注册所有MessageHandler对象，初始化时候一次加载完成，存入hashtable
	 * 使用的时候就可以不必反射去实例化，直接取就是了。
	 */
	public static Hashtable HandlerRegister = new Hashtable();
	
	/**
	 * :: = \r\r\n\n paxos message =
	 * PAXOS_BABY::host::port::password::increamentNo
	 * ::Category::DB::backup0::backup1::backup2
	 * ::backup3::backup4::backup5::backup6::backup7::backup8::content content =
	 * {.....}
	 * 
	 */
	private String host ;
	
	private int port ;
	
	private String passwd ;
	
	private Long increamentNo ;
	
	private String status ;
	
	private String Category;
	
	private int DB;
	
	private MessageHandlerBase handler;
	
	private String Content;
	
	public MessageHandlerBase(String Message){
		
		this.OrderAnalasy(Message);
		
	}
	public void OrderAnalasy(String msg) {
		String content = "";
		String[] params = msg.split(SolidConfigure.PaxosOrderSplitor);
		// 处理结尾的content中的\r\r\n\n
		for (int i = 16; i < params.length; i++) {
			content += params[i] + SolidConfigure.PaxosOrderSplitor;
		}
		content = content.substring(0, content.length() - 4);
		this.setHost(params[1]);
		this.setPort(Integer.parseInt(params[2]));
		this.setPasswd(params[3]);
		this.setIncreamentNo(Long.parseLong(params[4]));
		this.setCategory(params[5]);
		this.setDB(Integer.parseInt(params[6]));
		this.setContent(content);
		//预备位置不做处理今后如有需要需要进行调整
	}
	
	public void InitMessageHandlers(){
		初始化子类
	}
	
	public void getInstanceFromRegi(String msg){
		通过调用子类的OrderAnalasy()方法获得一个全新的对象
	}
	
	public abstract void Process(String content);
	
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

	public Long getIncreamentNo() {
		return increamentNo;
	}

	public void setIncreamentNo(Long increamentNo) {
		this.increamentNo = increamentNo;
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String Category) {
		this.Category = Category;
	}
	public int getDB() {
		return DB;
	}
	public void setDB(int dB) {
		DB = dB;
	}

}
