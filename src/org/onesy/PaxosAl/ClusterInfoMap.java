package org.onesy.PaxosAl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.onesy.tools.FileUtil;

public class ClusterInfoMap {

	private static ClusterInfoMap clusterInfoMap;

	/**
	 * Key = host:port:DB:channel TODO
	 */
	public static ConcurrentHashMap<String, PaxosNode> PaxosNodes = new ConcurrentHashMap<String, PaxosNode>();

	public static Vector<String> KeySet = new Vector<String>();

	public static ConcurrentHashMap<String, String> LocalNodeInfo = new ConcurrentHashMap<String, String>();

	public static Properties properties;

	public synchronized static ClusterInfoMap getInstanceC() {
		if (null == ClusterInfoMap.clusterInfoMap) {
			ClusterInfoMap.clusterInfoMap = new ClusterInfoMap();
		}
		return ClusterInfoMap.clusterInfoMap;
	}

	private ClusterInfoMap() {
		// 从用户目录下的指定文件进行初始化
		// nodespath = ~/.cmcs/NodesConfig/nodesConfigure.properties
		// localpath = ~/.cmcs/LocalConfig/localConfigure.properties
		// sysconfigpath = ~/.cmcs/SysConfigure.properties

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
			System.exit(-1);
		}
		Properties propertiesLocal = null;
		Properties propertiesNodes = null;
		try {
			ClusterInfoMap.properties = FileUtil.getAsProperties(SysConfigure);
			propertiesLocal = FileUtil.getAsProperties(localfile);
			propertiesNodes = FileUtil.getAsProperties(nodefile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("配置文件错误，文件" + localfile.getAbsolutePath()
					+ "未找到");
			System.exit(-1);
		}
		// 初始化本地节点
		this.NodeCreater("LOCALNODE", propertiesLocal.getProperty("host"),
				Integer.parseInt(propertiesLocal.getProperty("port")),
				Integer.parseInt(propertiesLocal.getProperty("db")),
				propertiesLocal.getProperty("passwd"),
				propertiesLocal.getProperty("Channel"),
				propertiesLocal.getProperty("name"));
		// 初始化其他节点
		for (int i = 1;; i++) {
			String path = propertiesNodes.getProperty(ClusterInfoMap.properties
					.getProperty("prefix") + "_" + i);
			if (null == path) {
				System.err.println("第 " + i + " 个节点文件未能找到，程序认为已经到达配置节点的末端");
				break;
			}
			Properties tmpProper = null;
			tmpProper = FileUtil.getAsProperties(new File(nodesFolder
					+ File.separator + path));
			if (null == tmpProper) {
				System.err.println("文件"
						+ new File(nodesFolder + File.separator + path)
								.getAbsolutePath() + "未能找到");
				System.err.println("该文件为第" + i + "个文件");
				break;
			}
			System.err.println(tmpProper.getProperty("host") + ":"
					+ tmpProper.getProperty("port") + ":"
					+ tmpProper.getProperty("db"));

			// TODO Auto-generated catch block

			this.NodeCreater("NODES", tmpProper.getProperty("host"),
					Integer.parseInt(tmpProper.getProperty("port")),
					Integer.parseInt(tmpProper.getProperty("db")),
					tmpProper.getProperty("passwd"),
					tmpProper.getProperty("Channel"),
					tmpProper.getProperty("name"));
		}
	}

	private void NodeCreater(String kind, String host, int port, int DB,
			String passwd, String Channel, String name) {
		if (host == null || 0 == port || Channel == null) {
			System.err.println("节点信息不完全，放弃生成该节点");
			return;
		}
		if (DB == 0) {
			DB = 1;
		}
		String key = host + ":" + port + ":" + DB + ":" + Channel;
		if (kind.equalsIgnoreCase("LOCALNODE")) {
			System.err.println("LOCALNODE:" + host + ":" + port + ":" + DB);
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
		ClusterInfoMap.KeySet.add(key);
	}
}
