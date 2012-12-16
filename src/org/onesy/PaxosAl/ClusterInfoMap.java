package org.onesy.PaxosAl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;

public class ClusterInfoMap {

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

	private ClusterInfoMap() throws Exception {
		// 从用户目录下的指定文件进行初始化
		// path = ~/.cmcs/NodesConfigure
		// TODO
		File dir = new File(System.getProperty("user.home") + File.pathSeparator + ".cmcs");
		if (! dir.exists()) {
			dir.mkdirs();
			throw new Exception("没有父目录，已经自动创建，需要用户自行创建配置文件");
		}
		NodesConfiger("NodesConfigure", "NODES");
		NodesConfiger("localConfigure", "LOCAL");
	}

	private void NodesConfiger(String filename, String Node) {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(System.getProperty("user.home")
							+ File.separator + ".cmcs" + File.separator
							+ filename)));
			for (String line = br.readLine(); line != null; br.readLine()) {
				if (line.startsWith("##")) {
					this.NodeCreater(Node, host, port, DB, passwd, Channel,
							name);
					host = null;
					port = 0;
					DB = 0;
					passwd = null;
					Channel = null;
					name = null;
					continue;
				}
				if (line.startsWith("#")) {
					continue;
				}
				String[] nameValue = line.split(":=");
				if (nameValue.length >= 2) {
					this.AttributeAdder(nameValue[0], nameValue[1]);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void NodeCreater(String kind, String host, int port, int DB,
			String passwd, String Channel, String name) {
		if (host.endsWith(null) || 0 == port || Channel.endsWith(null)) {
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
