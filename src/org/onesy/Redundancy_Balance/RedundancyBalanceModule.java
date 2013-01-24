package org.onesy.Redundancy_Balance;

import java.util.ArrayList;

/**
 * 
 * so called the "RB" means redundancy and balance
 * 
 * @author onesy
 * 
 */
public class RedundancyBalanceModule {

	public static <T> ArrayList<NodeInfo> GetOrganization(T magic) {

		ArrayList<NodeInfo> NodeInfoArr = OrganizationNodesTable
				.getMagicToNodeInfo_Table().get(
						CircleHashSpace.Search4Magic(magic));

		return NodeInfoArr;
	}
	
	public static boolean DelOrganization(String magic) {
		return OrganizationNodesTable.delKey(magic);
	}
	
	/**
	 * get random nodeinfo in organization
	 * @param magic
	 * @return
	 */
	public static <T> NodeInfo GetRandomNodeInfoInOrg(T magic) {
		ArrayList<NodeInfo> NodeInfoArr = GetOrganization(magic);
		int Random = GetRandomNumInArea(1, NodeInfoArr.size());
		return NodeInfoArr.get(Random - 1);
	}
	
	/**
	 * start at 1
	 * @param start
	 * @param end
	 * @return
	 */
	public static int GetRandomNumInArea(int start,int end){
		return (int)(Math.ceil(Math.random() * (end - start)) + start); 
	}

}
