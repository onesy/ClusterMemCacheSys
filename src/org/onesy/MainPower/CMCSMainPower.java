package org.onesy.MainPower;

import org.onesy.MQ.MQThreadsManagementD;
import org.onesy.PaxosAl.ClusterInfoMap;
import org.onesy.tools.CMCS_ConstantsTable;

public class CMCSMainPower {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 初始化参数，并且从配置文件中读取数据
		ClusterInfoMap clusterInfoMap = ClusterInfoMap.getInstanceC();
		
		MQThreadsManagementD MQThreadsManagementDobj = MQThreadsManagementD.getInstance();
		
		Thread MQThreadsManThread = new Thread(MQThreadsManagementDobj);
		
		MQThreadsManThread.start();
		
		if (CMCS_ConstantsTable.DEBUG){
			//某些缓冲区填充者线程
		}

	}

}
