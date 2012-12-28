package org.onesy.MQ;

import java.util.LinkedList;

import org.onesy.OrderBeans.MQRecErrOccuBean;
import org.onesy.OrderBeans.OutPutOrder;
import org.onesy.PaxosAl.ClusterInfoMap;

/**
 * 
 * 消息队列线程们的管理线程，以守护线程的方式保证每个线程均可以运行。如果线程崩溃，就会创建新的线程，代替之前的线程进行工作，并且定期对失败的消息
 * 进行处理
 * 
 * @author onesy
 * 
 */
public class MQThreadsManagementD implements Runnable {

	private static MQThreadsManagementD self = null;
	// 发送线程链表
	private static LinkedList<Runnable> sendThreads = new LinkedList<Runnable>();
	// 接受线程链表
	private static LinkedList<Runnable> recThreads = new LinkedList<Runnable>();
	// 线程异常的输出对象转储链表
	private static LinkedList<OutPutOrder> ErrorOccuSendMsgDumps = new LinkedList<OutPutOrder>();
	// 线程异常的输入对象转储链表
	private static LinkedList<MQRecErrOccuBean> MQRecErrOccuDumps = new LinkedList<MQRecErrOccuBean>();

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// 创建进程，并初始化守护进程的
		int receiversCount = Integer.parseInt(ClusterInfoMap.properties
				.getProperty("receiverCount"));

		int sendersCount = Integer.parseInt(ClusterInfoMap.properties
				.getProperty("senderCount"));

		this.CrtRecverAddToList(receiversCount);

		this.CrtSnderAddToList(sendersCount);

		for (;;) {
			// 守护进程检查线程们是否正常运行
		}

	}

	/**
	 * 
	 * 创造消息接收线程对象并存储入接受线程列表
	 * @warn 这个方法所调用的创建单个线程的方法会启用(start)线程
	 * @param receiversCount
	 */
	private void CrtRecverAddToList(int receiversCount) {
		for (int i = 0; i < receiversCount; i++) {
			Thread th = this.LocalRecThread();
			//TODO 加上这一行，异常线程转储的逻辑需要修改
			th.setName("receiver_" + i);
			MQThreadsManagementD.getRecThreads().add(i, th);
		}
	}

	/**
	 * 创造消息发送线程对象并存储入发送线程列表
	 * @warn 这个方法所调用的创建单个线程的方法会启用(start)线程
	 * @param sendersCount
	 */
	private void CrtSnderAddToList(int sendersCount) {
		for (int i = 0; i < sendersCount; i++) {
			Thread th = this.LocalSendThread();
			th.setName("sender_" + i);
			//TODO 加上这一行，异常线程转储的逻辑需要修改
			MQThreadsManagementD.getSendThreads().add(i, th);
		}
	}

	/**
	 * 单例，以便随时可以改变守护状态机的状态
	 * 
	 * @return
	 */
	public synchronized static MQThreadsManagementD getInstance() {
		if (null == MQThreadsManagementD.self) {
			MQThreadsManagementD.self = new MQThreadsManagementD();
		}
		return MQThreadsManagementD.self;
	}

	private MQThreadsManagementD() {

	}

	/**
	 * 
	 * 初始化一个本地的监听消息-守护线程
	 * @warn 这个方法会启用调用的线程
	 * @return
	 */
	private Thread LocalRecThread() {
		// 初始化消息队列接受线程
		OrderInMQRecD orderInMQRec = OrderInMQRecD.getInstance(
				ClusterInfoMap.LocalNodeInfo.get("HOST"),
				Integer.parseInt(ClusterInfoMap.LocalNodeInfo.get("PORT")),
				ClusterInfoMap.LocalNodeInfo.get("CHANNEL"));
		Thread OrderInMQRecth = new Thread(orderInMQRec);
		OrderInMQRecth.start();
		return OrderInMQRecth;
	}

	/**
	 * 初始化一个本地的发送消息-守护线程
	 * @warn 这个方法会启用调用的线程
	 * @return
	 */
	private Thread LocalSendThread() {
		// 初始化消息队列发送线程
		OrderInMQSendD orderInMQSend = new OrderInMQSendD();
		Thread OrderInMQSendthread = new Thread(orderInMQSend);
		OrderInMQSendthread.start();

		return OrderInMQSendthread;
	}

	public static LinkedList<Runnable> getSendThreads() {
		return sendThreads;
	}

	public static void setSendThreads(LinkedList<Runnable> sendThreads) {
		MQThreadsManagementD.sendThreads = sendThreads;
	}

	public static LinkedList<Runnable> getRecThreads() {
		return recThreads;
	}

	public static void setRecThreads(LinkedList<Runnable> recThreads) {
		MQThreadsManagementD.recThreads = recThreads;
	}

	public static LinkedList<OutPutOrder> getErrorOccuSendMsgDumps() {
		return ErrorOccuSendMsgDumps;
	}

	public static void setErrorOccuSendMsgDumps(
			LinkedList<OutPutOrder> errorOccuSendMsgDumps) {
		ErrorOccuSendMsgDumps = errorOccuSendMsgDumps;
	}

	public static LinkedList<MQRecErrOccuBean> getMQRecErrOccuDumps() {
		return MQRecErrOccuDumps;
	}

	public static void setMQRecErrOccuDumps(
			LinkedList<MQRecErrOccuBean> mQRecErrOccuDumps) {
		MQRecErrOccuDumps = mQRecErrOccuDumps;
	}

}
