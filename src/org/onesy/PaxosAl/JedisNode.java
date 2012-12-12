package org.onesy.PaxosAl;

import redis.clients.jedis.JedisShardInfo;

public class JedisNode {

	private JedisShardInfo jedisShardInfo;

	/**
	 * 100 Available 101 is not Available
	 */
	private int isAvailable;

	public JedisNode(String host, int port, String passwd) {
		this.setJedisShardInfo(new JedisShardInfo(host, port));
		if (! "default".equals(passwd)) {
			this.getJedisShardInfo().setPassword(passwd);
		}
	}

	public JedisNode(String host, int port) {
		this.setJedisShardInfo(new JedisShardInfo(host, port));
	}

	public JedisNode(String host, int port, int timeout, String passwd) {
		this.setJedisShardInfo(new JedisShardInfo(host, port, timeout));
		if (! "default".equals(passwd)) {
			this.getJedisShardInfo().setPassword(passwd);
		}
	}

	public void addPasswd(String passwd) {
		this.getJedisShardInfo().setPassword(passwd);
	}

	public void addTimeout(int timeout) {
		this.getJedisShardInfo().setTimeout(timeout);
	}

	public int getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}

	public JedisShardInfo getJedisShardInfo() {
		return jedisShardInfo;
	}

	public void setJedisShardInfo(JedisShardInfo jedisShardInfo) {
		this.jedisShardInfo = jedisShardInfo;
	}

}
