package org.onesy.PaxosAl;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.onesy.tools.FileUtil;

public class ClusterInfoMap {

	private static ClusterInfoMap clusterInfoMap;

	/**
	 * tmp vars
	 */
	private String host = null;
	/**
	 * tmp vars
	 */
	private int port = 0;
	/**
	 * tmp vars
	 */
	private int DB = 0;
	/**
	 * tmp vars
	 */
	private String passwd = null;
	/**
	 * tmp vars
	 */
	private String Channel = null;
	/**
	 * tmp vars
	 */
	private String name = null;

	/**
	 * Key = host:port:DB:channel
	 */
	public static ConcurrentHashMap<String, PaxosNode> PaxosNodes;

	public static ConcurrentHashMap<String, String> LocalNodeInfo;

	public static Properties properties;

	public synchronized static ClusterInfoMap getInstanceC() {
		if (null == ClusterInfoMap.clusterInfoMap) {
			ClusterInfoMap.clusterInfoMap = new ClusterInfoMap();
		}
		return ClusterInfoMap.clusterInfoMap;
	}

	private ClusterInfoMap() {
		// 从用户目录下的指定文件进行初始化
		// path = ~/.cmcs/NodesConfigure
		// TODO

		boolean exitFlg = false;

		File SysConfigure = new File(System.getProperty("user.home")
				+ File.separator + ".cmcs" + File.separator
				+ "SysConfigure.properties");
		File dir = new File(System.getProperty("user.home") + File.separator
				+ ".cmcs" + File.separator);
		File localFolder = new File(System.getProperty("user.home")
				+ File.separator + ".cmcs" + File.separator + "LocalConfig");
		File nodesFolder = new File(System.getProperty("user.home")
				+ File.separator + ".cmcs" + File.separator + "NodesConfig");
		File nodefile = new File(nodesFolder.getAbsolutePath() + File.separator
				+ "nodesConfigure.properties");
		File localfile = new File(localFolder.getAbsolutePath()
				+ File.separator + "localConfigure.properties");

		exitFlg |= FileUtil.FileCheckCreate(dir, true);
		exitFlg |= FileUtil.FileCheckCreate(SysConfigure, false);
		exitFlg |= FileUtil.FileCheckCreate(localFolder, true);
		exitFlg |= FileUtil.FileCheckCreate(nodesFolder, true);
		exitFlg |= FileUtil.FileCheckCreate(localfile, false);
		exitFlg |= FileUtil.FileCheckCreate(nodefile, false);
		if (exitFlg) {
			System.err.println("配置文件不完全，程序已经自动创建，请补充配置文件，本次程序即将退出");
		}
		ClusterInfoMap.properties = FileUtil.getAsProperties(SysConfigure);
		Properties propertiesNodes = FileUtil.getAsProperties(nodefile);
		for (int i = 0; i < 2;) {
			String value = propertiesNodes
					.getProperty(ClusterInfoMap.properties
							.getProperty("prefix") + "_" + i + ".properties");
			if (null == value) {
				break;
			}
			Properties tmpproper = FileUtil.getAsProperties(new File(
					nodesFolder + File.separator + value));
			asdasd

		}
	}

	private void NodeCreater(String kind, String host, int port, int DB,
			String passwd, String Channel, String name) {
		if (host.endsWith(null) || 0 == port || Channel.endsWith(null)) {
			System.err.println("节点信息不完全，放弃生成该节点");
			return;
		}
		if (DB == 0) {
			DB = 1;
		}
		String key = host + ":" + port + ":" + DB + ":" + Channel;
		if (kind.equalsIgnoreCase("LOCALNODE")) {
			ClusterInfoMap.LocalNodeInfo.put("HOST", host);
			ClusterInfoMap.LocalNodeInfo.put("PORT", port + "");
			ClusterInfoMap.LocalNodeInfo.put("DB", DB + "");
			ClusterInfoMap.LocalNodeInfo.put("PASSWD", passwd);
			ClusterInfoMap.LocalNodeInfo.put("CHANNEL", Channel);
			ClusterInfoMap.LocalNodeInfo.put("NAME", name);
			return;
		}
		PaxosNode paxosNode = new PaxosNode(host, port, 500, passwd, name,
				Channel);
		ClusterInfoMap.PaxosNodes.put(key, paxosNode);
	}

	private void AttributeAdder(String name, String value) {
		if (name.equalsIgnoreCase("HOST")) {
			this.host = new String(value);
		} else if (name.equalsIgnoreCase("PORT")) {
			this.port = new Integer(Integer.parseInt(value));
		} else if (name.equalsIgnoreCase("DB")) {
			this.DB = new Integer(Integer.parseInt(value));
		} else if (name.equalsIgnoreCase("NAME")) {
			this.name = new String(value);
		} else if (name.equalsIgnoreCase("PASSWD")) {
			this.passwd = new String(value);
		} else if (name.equalsIgnoreCase("CHANNEL")) {
			this.Channel = new String(value);
		}
	}

}
