package org.onesy.Redundancy_Balance;

public class NodeInfo {

	private int serialNode = 0;

	/**
	 * 节点特征码
	 */
	private String magic = null;
	/**
	 * 选举优先级
	 */
	private int votePriority = 0;

	private static int VirtualNodeCount = 0;

	private String host = null;

	private int port = 0;

	private String pubChannel = null;

	private String subChannel = null;

	private int DB = 0;

	/**
	 * serialNo:序列号，必须递增|
	 * magic:同组识别码，同组的节点必须一致|
	 * VirtualNodeCount:同组节点总数|
	 * host:节点的host|
	 * port:节点监听的redis的端口|
	 * pubChannel:节点发布信息的端口|
	 * subChannel:节点收听信息的端口|
	 * DB:节点监听redis的db号|
	 * votePriority:节点的优先级别(同组中有效)|
	 * @param serialNo
	 * @param magic
	 * @param VirtualNodeCount
	 * @param host
	 * @param port
	 * @param pubChannel
	 * @param subChannel
	 * @param DB
	 * @param votePriority
	 */
	public NodeInfo(int serialNo, String magic, int VirtualNodeCount,
			String host, int port, String pubChannel, String subChannel,
			int DB, int votePriority) {
		this.setMagic(magic);

		this.setSerialNode(serialNo);

		if (VirtualNodeCount != 0)
			NodeInfo.setVirtualNodeCount(VirtualNodeCount);

		this.setHost(host);

		this.setPort(port);

		this.setPubChannel(pubChannel);

		this.setSubChannel(subChannel);

		this.setDB(DB);

		this.setVotePriority(votePriority);
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

	public int getDB() {
		return DB;
	}

	public void setDB(int dB) {
		DB = dB;
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

	public int getVotePriority() {
		return votePriority;
	}

	public void setVotePriority(int votePriority) {
		this.votePriority = votePriority;
	}

}
