package org.onesy.Nodes;

import redis.clients.jedis.Jedis;

public class NodeInfo {
	
	private String pubChannel;
	
	private String subChannel;
	
	private Integer VotSerilizer;
	
	private String NodeName;
	
	private String host;
	
	private int port;
	
	private Jedis jedis ;
	
	public NodeInfo(String VotSerilizer){
		//VotSerilizer host:port:pubChannel:recChannel:NodeName:VotSerilizer
		String[] votParam = VotSerilizer.split(":");
		this.setPubChannel(votParam[2]);
		this.setSubChannel(votParam[3]);
		this.setVotSerilizer(Integer.parseInt(votParam[5]));
		this.setHost(votParam[0]);
		this.setNodeName(votParam[4]);
		this.setPort(Integer.parseInt(votParam[1]));
		this.setJedis(new Jedis(this.getHost(), this.getPort()));
	}

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public Integer getVotSerilizer() {
		return VotSerilizer;
	}

	public void setVotSerilizer(Integer votSerilizer) {
		VotSerilizer = votSerilizer;
	}

	public String getNodeName() {
		return NodeName;
	}

	public void setNodeName(String nodeName) {
		NodeName = nodeName;
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

	public String getPubChannel() {
		return pubChannel;
	}

	public void setPubChannel(String pubChannel) {
		this.pubChannel = pubChannel;
	}

	public String getSubChannel() {
		return subChannel;
	}

	public void setSubChannel(String subChannel) {
		this.subChannel = subChannel;
	}

}
