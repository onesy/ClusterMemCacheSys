package org.onesy.PaxosAl;

public class PaxosNode {

	private JedisNode jedisNode;

	/**
	 * 100 Available
	 * 101 is not Available
	 */
	private int isAvailable;

	private String host;

	private int port;

	private String passwd;

	private String name;
	
	private String channel;
	
	/**
	 * @param host
	 * @param port
	 * @param passwd
	 * @param name
	 * @param channel
	 * @throws IllegalArgumentException
	 * @description host和port为必须，如果没有设置或者设置的数值非法，会抛出异常，其他参数传null，默认为"default".
	 * 				PaxosNode对象会根据收到的参数进行实例化jedisNode对象，并引用改对象
	 */
	public PaxosNode(String host, int port, String passwd, String name, String channel) throws IllegalArgumentException {
		if (null == host || port < 0) {
			throw new IllegalArgumentException();
		}
		this.setHost(host);
		this.setPort(port);
		this.setPasswd(passwd);
		this.setName(name);
		this.setChannel(channel);
	}

	public JedisNode getJedisNode() {
		return jedisNode;
	}

	private void setJedisNode(JedisNode jedisNode) {
		this.jedisNode = jedisNode;
	}

	public int getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if ( null == name) {
			this.name = "default";
			return;
		}
		this.name = name;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		if(null == channel){
			this.channel = "default";
			return;
		}
		this.channel = channel;
	}

}
