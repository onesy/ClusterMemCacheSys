package org.onesy.PaxosAl;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.onesy.tools.CMCS_ConstantsTable;
import org.onesy.tools.CMCS_FileUtil;

public class ClusterInfoMap {

	private static ClusterInfoMap clusterInfoMap;

	private File localFolder;

	private File dir;

	private File nodesFolder;

	/**
	 * SYSTEM CONFIGURE LIST ------------------------------ logPath
	 * receiverCount senderCount
	 * 
	 * ------------------------------
	 */
	private File SysConfigure;

	private File nodefile;

	private File localfile;

	/**
	 * Key = node_x.properties
	 */
	public static ConcurrentHashMap<String, PaxosNode> PaxosNodes = new ConcurrentHashMap<String, PaxosNode>();

	public static ArrayList<String> KeySet = new ArrayList<String>();

	public static ConcurrentHashMap<String, String> LocalNodeInfo = new ConcurrentHashMap<String, String>();

	public static Properties properties;

	public synchronized static ClusterInfoMap getInstanceC() {
		if (null == ClusterInfoMap.clusterInfoMap) {
			ClusterInfoMap.clusterInfoMap = new ClusterInfoMap();
		}
		return ClusterInfoMap.clusterInfoMap;
	}

	private ClusterInfoMap() {

		this.PathAssembly();

		if (CheckFiles()) {
			System.err.println("配置文件不完全，程序已经自动创建，请补充配置文件，本次程序即将退出");
			System.exit(-1);
		}

		/**
		 * 初始化全部节点和配置文件
		 */
		InitialNodes(SysConfigure, localfile, nodefile);

	}

	private void PathAssembly() {

		// 从用户目录下的指定文件进行初始化
		// nodespath = ~/.cmcs/NodesConfig/nodesConfigure.properties
		// localpath = ~/.cmcs/LocalConfig/localConfigure.properties
		// sysconfigpath = ~/.cmcs/SysConfigure.properties

		SysConfigure = new File(System.getProperty("user.home")
				+ File.separator + ".cmcs" + File.separator
				+ "SysConfigure.properties");
		dir = new File(System.getProperty("user.home") + File.separator
				+ ".cmcs" + File.separator);
		localFolder = new File(System.getProperty("user.home") + File.separator
				+ ".cmcs" + File.separator + "LocalConfig");
		nodesFolder = new File(System.getProperty("user.home") + File.separator
				+ ".cmcs" + File.separator + "NodesConfig");
		nodefile = new File(nodesFolder.getAbsolutePath() + File.separator
				+ "nodesConfigure.properties");
		localfile = new File(localFolder.getAbsolutePath() + File.separator
				+ "localConfigure.properties");
	}

	private boolean CheckFiles() {

		boolean exitFlag = false;

		exitFlag |= CMCS_FileUtil.FileCheckCreate(dir, true);
		exitFlag |= CMCS_FileUtil.FileCheckCreate(SysConfigure, false);
		exitFlag |= CMCS_FileUtil.FileCheckCreate(localFolder, true);
		exitFlag |= CMCS_FileUtil.FileCheckCreate(nodesFolder, true);
		exitFlag |= CMCS_FileUtil.FileCheckCreate(localfile, false);
		exitFlag |= CMCS_FileUtil.FileCheckCreate(nodefile, false);
		return exitFlag;
	}

	/**
	 * 初始化全部节点和配置文件
	 */
	private void InitialNodes(File SysConfigure, File localfile, File nodefile) {
		Properties propertiesLocal = null;
		Properties propertiesNodes = null;
		try {
			ClusterInfoMap.properties = CMCS_FileUtil
					.getAsProperties(SysConfigure);
			propertiesLocal = CMCS_FileUtil.getAsProperties(localfile);
			propertiesNodes = CMCS_FileUtil.getAsProperties(nodefile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("配置文件错误，文件" + localfile.getAbsolutePath()
					+ "未找到");
			System.exit(-1);
		}
		// 初始化本地节点
		this.CreaterEncapsulation("LOCALNODE", "LOCAL", propertiesLocal);
		// 初始化其他节点
		for (int i = 1;; i++) {
			String path = propertiesNodes.getProperty(ClusterInfoMap.properties
					.getProperty("prefix") + "_" + i);
			if (null == path) {
				if (CMCS_ConstantsTable.DEBUG)
					System.err.println("第 " + i + " 个节点文件未能找到，程序认为已经到达配置节点的末端");
				break;
			}
			Properties tmpProper = null;
			tmpProper = CMCS_FileUtil.getAsProperties(new File(nodesFolder
					+ File.separator + path));
			if (null == tmpProper && CMCS_ConstantsTable.DEBUG) {
				System.err.println("文件"
						+ new File(nodesFolder + File.separator + path)
								.getAbsolutePath() + "未能找到");
				System.err.println("该文件为第" + i + "个文件");
				break;
			}
			System.err.println(tmpProper.getProperty("host") + ":"
					+ tmpProper.getProperty("port") + ":"
					+ tmpProper.getProperty("db"));
			// 初始化节点
			this.CreaterEncapsulation("NODES",
					ClusterInfoMap.properties.getProperty("prefix") + "_" + i,
					tmpProper);
		}
	}

	private void CreaterEncapsulation(String Kind, String Key,
			Properties properties) {

		this.NodeCreater(Kind, Key, properties.getProperty("host"),
				Integer.parseInt(properties.getProperty("port")),
				Integer.parseInt(properties.getProperty("db")),
				properties.getProperty("passwd"),
				properties.getProperty("Channel"),
				properties.getProperty("name"));

	}

	private void NodeCreater(String kind, String Key, String host, int port,
			int DB, String passwd, String Channel, String name) {
		if ((host == null || 0 == port || Channel == null)
				&& CMCS_ConstantsTable.DEBUG) {
			System.err.println("节点信息不完全，放弃生成该节点");
			return;
		}
		if (DB == 0) {
			DB = 1;
		}

		if (kind.equalsIgnoreCase("LOCALNODE")) {
			if (CMCS_ConstantsTable.DEBUG)
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
		ClusterInfoMap.PaxosNodes.put(Key, paxosNode);
		ClusterInfoMap.KeySet.add(Key);
	}
}
