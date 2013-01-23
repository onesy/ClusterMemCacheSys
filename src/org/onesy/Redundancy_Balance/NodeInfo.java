package org.onesy.Redundancy_Balance;

public class NodeInfo {
	
	private int serialNode = 0;

	private String magic = null;
	
	private static int VirtualNodeCount = 0;
	
	private String host = null;
	
	private int port = 0;
	
	private String channel = null;
	
	private int DB = 0;
	
	
	
	public NodeInfo(int serialNo,String magic, int VirtualNodeCount, String host, int port, String channel, int DB){
		this.setMagic(magic);
		
		this.setSerialNode(serialNo);
		
		if(VirtualNodeCount != 0)
			NodeInfo.setVirtualNodeCount(VirtualNodeCount);
		
		this.setHost(host);
		
		this.setPort(port);
		
		this.setChannel(channel);
		
		this.setDB(DB);
	}

	public int getSerialNode() {
		return serialNode;
	}

	public void setSerialNode(int serialNode) {
		this.serialNode = serialNode;
	}

	public String getMagic() {
		return magic;
	}

	public void setMagic(String magic) {
		this.magic = magic;
	}

	public static int getVirtualNodeCount() {
		return VirtualNodeCount;
	}

	public static void setVirtualNodeCount(int virtualNodeCount) {
		VirtualNodeCount = virtualNodeCount;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public int getDB() {
		return DB;
	}

	public void setDB(int dB) {
		DB = dB;
	}

}
