package org.onesy.ThreadBuffer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrderBufferLevel_1 {
	
	private static OrderBufferLevel_1  itself ;

	private static ConcurrentLinkedQueue<String> ConfirmQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> DeleteQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> DesignateLeaderQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> DesignateLeaderRecipeQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> DesignateWatcherQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> DesignateWatcherRecipeQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> GetValueQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> LeaderASQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> LeaderASKQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> OriginOrderExecuteQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> RefuseQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> SetKeyValueQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> VersionREQQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> VersionRESPQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> VotLeaderQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> VotLeaderRecipeQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> VotWatcherQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> VotWatcherReceiptQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> WatcherASQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentLinkedQueue<String> WatcherASKQueue = new ConcurrentLinkedQueue<String>();

	private static ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> BufferLevel_1_Hash = new ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>();
	
	/**
	 * 初始化所有通道
	 */
	private OrderBufferLevel_1(){
		OrderBufferLevel_1.BufferLevel_1_Hash.put("Confirm", OrderBufferLevel_1.ConfirmQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("Delete", OrderBufferLevel_1.DeleteQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("DesignateLeader", OrderBufferLevel_1.DesignateLeaderQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("DesignateLeaderRecipe", OrderBufferLevel_1.DesignateLeaderRecipeQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("DesignateWatcher", OrderBufferLevel_1.DesignateWatcherQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("DesignateWatcherRecipe", OrderBufferLevel_1.DesignateWatcherRecipeQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("GetValue", OrderBufferLevel_1.GetValueQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("LeaderASK", OrderBufferLevel_1.LeaderASKQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("LeaderAS", OrderBufferLevel_1.LeaderASQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("OriginOrderExecute", OrderBufferLevel_1.OriginOrderExecuteQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("Refuse", OrderBufferLevel_1.RefuseQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("SetKeyValue", OrderBufferLevel_1.SetKeyValueQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("VersionREQ", OrderBufferLevel_1.VersionREQQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("VersionRESP", OrderBufferLevel_1.VersionRESPQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("VotLeader", OrderBufferLevel_1.VotLeaderQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("VotLeaderRecipe", OrderBufferLevel_1.VotLeaderRecipeQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("VotWatcher", OrderBufferLevel_1.VotWatcherQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("VotWatcherReceipt", OrderBufferLevel_1.VotWatcherReceiptQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("WatcherAS", OrderBufferLevel_1.WatcherASQueue);
		OrderBufferLevel_1.BufferLevel_1_Hash.put("WatcherASK", OrderBufferLevel_1.WatcherASKQueue);
	}
	
	public synchronized static OrderBufferLevel_1 getInstance(){
		if(null == OrderBufferLevel_1.itself){
			OrderBufferLevel_1.itself = new OrderBufferLevel_1();
		}
		return OrderBufferLevel_1.itself;
	}

}
