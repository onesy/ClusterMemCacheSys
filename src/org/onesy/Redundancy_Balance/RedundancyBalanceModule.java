package org.onesy.Redundancy_Balance;

import java.io.File;
import java.util.ArrayList;

import org.onesy.tools.FileUtil;

/**
 * 
 * so called the "RB" means redundancy and balance
 * 
 * @author onesy
 * 
 */
public class RedundancyBalanceModule {

	private static boolean IsInit = false;

	private static String UserBaseUrl = System.getProperty("user.home");

	public static <T> ArrayList<NodeInfo> GetOrganization(T magic) {

		CheckInit();
		ArrayList<NodeInfo> NodeInfoArr = OrganizationNodesTable
				.getMagicToNodeInfo_Table().get(
						CircleHashSpace.Search4Magic(magic));

		return NodeInfoArr;
	}

	public static boolean DelOrganization(String magic) {
		CheckInit();
		return OrganizationNodesTable.delKey(magic);
	}

	/**
	 * get random nodeinfo in organization
	 * 
	 * @param magic
	 * @return
	 */
	public static <T> NodeInfo GetRandomNodeInfoInOrg(T magic) {
		CheckInit();
		ArrayList<NodeInfo> NodeInfoArr = GetOrganization(magic);
		int Random = GetRandomNumInArea(1, NodeInfoArr.size());
		return NodeInfoArr.get(Random - 1);
	}

	private static void CheckInit() {
		if (RedundancyBalanceModule.IsInit == false) {
			System.exit(-10086);
		}
	}

	/**
	 * start at 1
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int GetRandomNumInArea(int start, int end) {
		CheckInit();
		return (int) (Math.ceil(Math.random() * (end - start)) + start);
	}

	/**
	 * 属性文件格式
	 */
	public static void Initialization() {
		// 检查用户目录下有没有./cmcs/OrgCfg/文件夹
		// 读取./cmcs/OrgCfg/OrganizationCfg.properties文件
		// 取出所有的值和键
		// 键作为magic，值为序列号，作为后缀，例如abc=5，就可以在./cmcs/OrgCfg/下找到abc_0....abc_4的文件

		File OrgCfg = new File(UserBaseUrl + File.pathSeparator
				+ ".cmcs/OrgCfg");
		if (!FileUtil.FileCheckCreate(OrgCfg, true)) {
			System.err.println("文件夹：" + OrgCfg.getPath() + ",不存在");
		}
		File OrgCfgPro = new File(UserBaseUrl + File.pathSeparator + ".cmcs"
				+ File.separator + "OrgCfg" + File.separator
				+ "OrganizationCfg.properties");
		if(! FileUtil.FileCheckCreate(OrgCfgPro, false)){
			System.err.println("文件：" + OrgCfgPro.getPath() + ",不存在");
		}

	}

}
