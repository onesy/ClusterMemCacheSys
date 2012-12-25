package org.onesy.PaxosAl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;

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
		File dir = new File(System.getProperty("user.home") + File.separator
				+ ".cmcs" + File.separator);
		File nodefile = new File(System.getProperty("user.home")
				+ File.separator + ".cmcs" + File.separator + "NodesConfigure");
		File localfile = new File(System.getProperty("user.home")
				+ File.separator + ".cmcs" + File.separator + "localConfigure");
		File SysConfigure = new File(System.getProperty("user.home") + File.separator + "SysConfigure.properties");
		// 检查文件夹是否存在
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 检查节点文件是否存在
		if (!nodefile.exists()) {
			try {
				nodefile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("节点信息文件不存在，且创建失败");
				e.printStackTrace();
				System.exit(-1);
			}
		}
		// 检查本地文件是否存在
		if (!localfile.exists()) {
			try {
				localfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("本地节点信息不存在，且创建失败");
				e.printStackTrace();
				System.exit(-1);
			}
		}
		//检查属性配置文件是否存在
		if (!SysConfigure.exists()) {
			try {
				SysConfigure.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("系统配置文件不存在。且创建失败");
				e.printStackTrace();
				System.exit(-1);
			}
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
