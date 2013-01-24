package org.onesy.Redundancy_Balance;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.onesy.tools.CMCS_Collection;

public class OrganizationNodesTable {

	private static OrganizationNodesTable OrganizationNodesTableI = null;
	/**
	 * key就是magic，magic就是一个hash code
	 */
	private static ConcurrentHashMap<String, ArrayList<NodeInfo>> MagicToNodeInfo_Table = null;

	private OrganizationNodesTable() {
		OrganizationNodesTable
				.setMagicToNodeInfo_Table(new ConcurrentHashMap<String, ArrayList<NodeInfo>>());
	}

	public synchronized static OrganizationNodesTable getInstance() {
		if (OrganizationNodesTableI == null)
			OrganizationNodesTableI = new OrganizationNodesTable();
		return OrganizationNodesTableI;
	}

	/**
	 * 
	 * @param magics
	 * @param values
	 * @return
	 */
	public synchronized static <T> OrganizationNodesTable getInstance(
			T[] magics, ArrayList<ArrayList<NodeInfo>> values) {
		OrganizationNodesTable.getInstance();
		for (int i = 0; i < magics.length; i++) {
			OrganizationNodesTable.getMagicToNodeInfo_Table().put(
					magics[i].toString(), values.get(i));
			for (ArrayList<NodeInfo> value : values) {
				if (magics[i].toString().equals(value.get(0).getMagic())) {
					OrganizationNodesTable.MagicToNodeInfo_Table.put(
							new BigInteger(CMCS_Collection
									.calculateMD5(magics[i])).abs().toString(),
							value);
					break;
				}
			}
		}
		return OrganizationNodesTable.getInstance();
	}

	public static ConcurrentHashMap<String, ArrayList<NodeInfo>> getMagicToNodeInfo_Table() {
		if (MagicToNodeInfo_Table == null)
			MagicToNodeInfo_Table = new ConcurrentHashMap<String, ArrayList<NodeInfo>>();
		return MagicToNodeInfo_Table;
	}

	protected static void setMagicToNodeInfo_Table(
			ConcurrentHashMap<String, ArrayList<NodeInfo>> magicToNodeInfo_Table) {
		OrganizationNodesTable.MagicToNodeInfo_Table = magicToNodeInfo_Table;
	}

	/**
	 * 如果需要更改一个键对应的数组，只需要调用这个方法使用相同的键进行覆盖即可
	 * 
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	protected static <T, E> void addKV(T key, ArrayList<E> value) {
		OrganizationNodesTable.getInstance();
		MagicToNodeInfo_Table.put(key.toString(), (ArrayList<NodeInfo>) value);
	}

	protected static <T> boolean delKey(T key) {
		try {
			if (null == MagicToNodeInfo_Table.remove(CMCS_Collection.calculateMD5(key))) {
				return false;
			} else {
				return true;
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
			return false;
		}
	}
}
