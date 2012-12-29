package org.onesy.MQ;

import java.util.Iterator;
import java.util.LinkedList;

import org.onesy.OrderBeans.MQRecErrOccuBean;
import org.onesy.OrderBeans.OutPutOrder;
import org.onesy.PaxosAl.ClusterInfoMap;
import org.onesy.tools.CMCS_ConstantsTable;
import org.onesy.tools.CMCS_ThreadTools;

/**
 * 
 * 消息队列线程们的管理线程，以守护线程的方式保证每个线程均可以运行。如果线程崩溃，就会创建新的线程，代替之前的线程进行工作，并且定期对失败的消息 进行处理
 * 
 * @author onesy
 * 
 */
public class MQThreadsManagementD implements Runnable {

	private static MQThreadsManagementD self = null;
	// 发送线程链表
	private volatile static LinkedList<Thread> sendThreads = new LinkedList<Thread>();
	// 接受线程链表
	private volatile static LinkedList<Thread> recThreads = new LinkedList<Thread>();
	// 线程异常的输出对象转储链表
	private volatile static LinkedList<OutPutOrder> ErrorOccuSendMsgDumps = new LinkedList<OutPutOrder>();
	// 线程异常的输入对象转储链表
	private volatile static LinkedList<MQRecErrOccuBean> MQRecErrOccuDumps = new LinkedList<MQRecErrOccuBean>();
	// 异常线程集
	private volatile static LinkedList<Thread> ErrorThreadsSet = new LinkedList<Thread>();

	@SuppressWarnings("unused")
	@Override
	public void run() {
		// TODO Auto-generated method stub

		// 创建进程，并初始化守护进程的
		int receiversCount = Integer.parseInt(ClusterInfoMap.properties
				.getProperty("receiverCount"));

		int sendersCount = Integer.parseInt(ClusterInfoMap.properties
				.getProperty("senderCount"));

		if (CMCS_ConstantsTable.DEBUG)
			System.out.println("receiverCount:" + receiversCount
					+ "  senderCount:" + sendersCount);
		this.CrtRecverAddToList(receiversCount);

		this.CrtSnderAddToList(sendersCount);

		// daemon!
		for (;;) {

			// 结束异常线程列表中的线程
			CMCS_ThreadTools.FinishErrorThread();
			// 守护进程检查线程们是否正常运行
			CheckThreads();
			// CheckThreads("receiverCount", "receiver");
			// CheckThreads("senderCount", "sender");
			// 检查 线程异常的输入对象转储链表
			// TODO
			// 处理线程异常导致的异常输入对象（专用线程处理,流程较为复杂）
			// TODO
			// 检查 线程异常的输出对象转储链表
			// TODO
			// 处理线程异常导致的异常输出对象（专用线程处理，流程较为复杂）
			// TODO

			// 发出错误，记得更改这里，记得接收线程和发送线程的异常处理都需要加入新的线程处理

			if (CMCS_ConstantsTable.MQ_MANAGERD_DEBUG && CMCS_ConstantsTable.DEBUG) {
				System.exit(-2);
			}

		}

	}

	private void CheckThreads() {

		if (CMCS_ConstantsTable.CHECKTHREAD_DEBUG)
			System.err.println("CheckThreads");
		int Iter;
		synchronized (recThreads) {

			if (Integer.parseInt(ClusterInfoMap.properties
					.getProperty("receiverCount")) != recThreads.size()
					&& (Iter = Integer.parseInt(ClusterInfoMap.properties
							.getProperty("receiverCount")) - recThreads.size()) > 0) {
				if (CMCS_ConstantsTable.DEBUG)
					System.out.println("已经有：" + Iter + "个receicer线程死亡");
				for (int i = 0; i < Iter; i++) {
					Thread th = LocalRecThread();
					recThreads.add(th);
					th.start();
				}
			}
		}
		synchronized (sendThreads) {

			if (Integer.parseInt(ClusterInfoMap.properties
					.getProperty("senderCount")) != sendThreads.size()
					&& (Iter = Integer.parseInt(ClusterInfoMap.properties
							.getProperty("senderCount")) - sendThreads.size()) > 0) {
				if (CMCS_ConstantsTable.DEBUG)
					System.out.println("已经有：" + Iter + "个sender线程死亡");
				for (int i = 0; i < Iter; i++) {
					Thread th = LocalSendThread();
					sendThreads.add(th);
					th.start();
				}
			}
		}
	}

	/**
	 * 
	 * 创造消息接收线程对象并存储入接受线程列表
	 * 
	 * @warn 这个方法所调用的创建单个线程的方法会启用(start)线程
	 * @param receiversCount
	 */
	private synchronized void CrtRecverAddToList(int receiversCount) {
		for (int i = 0; i < receiversCount; i++) {
			Thread th = this.LocalRecThread();
			// TODO 加上这一行，异常线程转储的逻辑需要修改
			if (CMCS_ConstantsTable.DEBUG) {
				System.out.println("已经成功创建接受者线程");
			}
			MQThreadsManagementD.getRecThreads().add(i, th);

		}
		Iterator<Thread> iterator = MQThreadsManagementD.getRecThreads()
				.iterator();
		while (iterator.hasNext()) {
			iterator.next().start();
		}
	}

	/**
	 * 创造消息发送线程对象并存储入发送线程列表
	 * 
	 * @warn 这个方法会启用所创建线程的start()方法
	 * @param sendersCount
	 */
	private synchronized void CrtSnderAddToList(int sendersCount) {
		for (int i = 0; i < sendersCount; i++) {
			Thread th = this.LocalSendThread();
			// TODO 加上这一行，异常线程转储的逻辑需要修改
			if (CMCS_ConstantsTable.DEBUG) {
				System.out.println("已经成功创建发送者线程");
			}
			MQThreadsManagementD.getSendThreads().add(i, th);
		}
		Iterator<Thread> iterator = MQThreadsManagementD.getSendThreads()
				.iterator();
		while (iterator.hasNext()) {
			iterator.next().start();
		}
	}

	/**
	 * 单例，以便随时可以改变守护状态机的状态
	 * 
	 * @return
	 */
	public static MQThreadsManagementD getInstance() {
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
	 * 
	 * @return
	 */
	private Thread LocalRecThread() {
		// 初始化消息队列接受线程
		OrderInMQRecD orderInMQRec = OrderInMQRecD.getInstance(
				ClusterInfoMap.LocalNodeInfo.get("HOST"),
				Integer.parseInt(ClusterInfoMap.LocalNodeInfo.get("PORT")),
				ClusterInfoMap.LocalNodeInfo.get("CHANNEL"));
		Thread OrderInMQRecth = new Thread(orderInMQRec);
		// OrderInMQRecth.start();//这一行代码会导致初始化错误！
		return OrderInMQRecth;
	}

	/**
	 * 初始化一个本地的发送消息-守护线程
	 * 
	 * @return
	 */
	private Thread LocalSendThread() {
		// 初始化消息队列发送线程
		OrderInMQSendD orderInMQSend = new OrderInMQSendD();
		Thread OrderInMQSendthread = new Thread(orderInMQSend);
		// OrderInMQSendthread.start();//这一行代码会导致初始化错误！

		return OrderInMQSendthread;
	}

	public static LinkedList<Thread> getSendThreads() {
		return sendThreads;
	}

	public static void setSendThreads(LinkedList<Thread> sendThreads) {
		MQThreadsManagementD.sendThreads = sendThreads;
	}

	public static LinkedList<Thread> getRecThreads() {
		return recThreads;
	}

	public static void setRecThreads(LinkedList<Thread> recThreads) {
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

	public static LinkedList<Thread> getErrorThreadsSet() {
		return ErrorThreadsSet;
	}

	public static void setErrorThreadsSet(LinkedList<Thread> errorThreadsSet) {
		ErrorThreadsSet = errorThreadsSet;
	}

}
