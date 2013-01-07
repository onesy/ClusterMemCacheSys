package org.onesy.MainPower;

import org.onesy.MQ.MQThreadsManagementD;
import org.onesy.MessageHandler.MessageHandlerBase;
import org.onesy.PaxosAl.ClusterInfoMap;
import org.onesy.tools.CMCS_ConstantsTable;
import org.onesy.tools.CMCS_VariableRegisterUtil;

public class CMCSMainPower {

	public static ClusterInfoMap clusterInfoMap = null;

	public static CMCS_VariableRegisterUtil UserParams = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (!CMCS_ConstantsTable.MHB_DEBUG) {
			// 初始化系统参数空间，从配置文件中读取数据
			clusterInfoMap = ClusterInfoMap.getInstance();

			// 初始化用户参数空间
			UserParams = CMCS_VariableRegisterUtil.getInstance();

			MQThreadsManagementD MQThreadsManagementDobj = MQThreadsManagementD
					.getInstance();

			Thread MQThreadsManThread = new Thread(MQThreadsManagementDobj);

			MQThreadsManThread.start();

			if (CMCS_ConstantsTable.DEBUG) {
				// 某些缓冲区填充者线程
			}
		}

		// 初始化MessageHandler的HandlerRegister
		MessageHandlerBase.InitMessageHandlers();
		//

	}

}
