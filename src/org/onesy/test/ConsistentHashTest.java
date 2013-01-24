package org.onesy.test;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.onesy.Redundancy_Balance.CircleHashSpace;
import org.onesy.Redundancy_Balance.NodeInfo;
import org.onesy.Redundancy_Balance.OrganizationNodesTable;
import org.onesy.Redundancy_Balance.RedundancyBalanceModule;

public class ConsistentHashTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * test case start
		 */

		// System.err.println(new BigInteger("1").compareTo(new
		// BigInteger("2")));
		// System.exit(0);

		long times = System.currentTimeMillis();
		String[] magics = new String[] { "123", "322", "1203", "565", "456",
				"1000", "4565" };

		String[] magic2s = new String[] { "776", "676", "453", "908", "111" };

		int userMagic = 7000;
		int userMagic2 = 7001;
		int userMagic3 = 7002;
		/**
		 * 二维
		 */
		ArrayList<ArrayList<NodeInfo>> values = new ArrayList<ArrayList<NodeInfo>>();

		ArrayList<NodeInfo> No1 = new ArrayList<NodeInfo>();
		No1.add(new NodeInfo(0, "123", 2, "127.0.0.1", 1023, "pubDior", "subDior", 0, 0));
		No1.add(new NodeInfo(1, "123", 2, "127.0.0.1", 1023, "pubDior", "subDior", 0, 1));

		ArrayList<NodeInfo> No2 = new ArrayList<NodeInfo>();
		No2.add(new NodeInfo(0, "111", 2, "127.0.0.1", 1023, "pubChannel", "subChannel", 0, 0));
		No2.add(new NodeInfo(1, "111", 2, "127.0.0.1", 1023, "pubchannel", "subChannel", 0, 1));

		ArrayList<NodeInfo> No3 = new ArrayList<NodeInfo>();
		No3.add(new NodeInfo(0, "322", 2, "127.0.0.1", 1023, "pubOly", "subOly", 0, 0));
		No3.add(new NodeInfo(1, "322", 2, "127.0.0.1", 1023, "pubOly", "subOly", 0, 1));

		values.add(No1);
		values.add(No2);
		values.add(No3);
		/**
		 * magics here 初始化Node table
		 */
		String[] magics4src = new String[] { "123", "111", "322" };

		OrganizationNodesTable organizationNodesTable = OrganizationNodesTable
				.getInstance(magics4src, values);

		ConcurrentHashMap<String, ArrayList<NodeInfo>> hashtable = OrganizationNodesTable
				.getMagicToNodeInfo_Table();

		/**
		 * magics here 初始化环状hash空间
		 * 
		 */

		CircleHashSpace.setMagicArray(magics4src);

		CircleHashSpace circleHashSpace = CircleHashSpace.getInstance();
		
		RequestInfo AliQI = new RequestInfo("ali", 19, "aliMagic");
		
		RequestInfo LaiQI = new RequestInfo("lai", 20, "laiMagic");
		
		RequestInfo TuQI = new RequestInfo("Tu", 21, "tuMagic");
		
		ArrayList<NodeInfo> targetArrayList = RedundancyBalanceModule.GetOrganization(AliQI);
		
		for (NodeInfo nodeInfo : targetArrayList) {
			System.err.println(nodeInfo.getMagic());
		}

		long timee = System.currentTimeMillis();
		
		System.err.println(timee - times);
		
		long nanos = System.nanoTime();
		
		long nanoe = System.nanoTime();
		/**
		 * 流程说明：
		 * 
		 * @1 .在环状hash空间中查找出magic_key
		 * @2. 利用magic_key在OrganizationodesTable对象中查找
		 */
		/**
		 * for (String value : CircleHashSpace.getMagicArray()) {
		 * System.err.println(value); }
		 */
		
		
		
		/**
		 * test case end
		 */
		/*
		 * for(int i = 0 ;i < magics.length; i ++){
		 * CircleHashSpace.add(magics[i]);
		 * 
		 * ArrayList<String> magicArr = new ArrayList<String>();
		 * 
		 * for (int i = 0; i < magics.length; i++) { magicArr.add(magics[i]); }
		 */
		// CircleHashSpace.setMagicArray(magics);

		// CircleHashSpace.setMagicArray(magic2s);

		// CircleHashSpace.del("123");

		/*
		 * for (int i = 0; i < CircleHashSpace.getMagicArray().size(); i++) {
		 * System.err.println(CircleHashSpace.getMagicArray().get(i)); }
		 */

		// int userMagic = 200;
		/*
		 * System.err.println("I got it! It is " +
		 * CircleHashSpace.Search4Magic(userMagic));
		 */
		/*
		 * CircleHashSpace.modify("123", "001");
		 * 
		 * for(int i = 0; i < CircleHashSpace.getMagicArray().size(); i ++){
		 * System.err.println(CircleHashSpace.getMagicArray().get(i)); }
		 */
	}

}
