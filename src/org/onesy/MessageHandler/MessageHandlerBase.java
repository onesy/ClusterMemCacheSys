package org.onesy.MessageHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.onesy.tools.SolidConfigure;

public abstract class MessageHandlerBase {
	/**
	 * 注册所有MessageHandler对象，初始化时候一次加载完成，存入hashtable 使用的时候就可以不必反射去实例化，直接取就是了。
	 */
	public static volatile ConcurrentHashMap<String, MessageHandlerBase> HandlerRegister = new ConcurrentHashMap<String, MessageHandlerBase>();

	/**
	 * :: = \r\r\n\n paxos message =
	 * PAXOS_BABY::host::port::password::increamentNo
	 * ::Category::DB::backup0::backup1::backup2
	 * ::backup3::backup4::backup5::backup6::backup7::backup8::content content =
	 * {.....}
	 * 
	 */
	private String host;

	private int port;

	private String passwd;

	private Long increamentNo;

	private String status;

	private String Category;

	private int DB;

	private MessageHandlerBase handler;

	private String Content;

	public MessageHandlerBase(String Message) {

		this.OrderAnalasy(Message);
	}

	public void OrderAnalasy(String msg) {
		String content = "";
		String[] params = msg.split(SolidConfigure.PaxosOrderSplitor);
		// 处理结尾的content中的\r\r\n\n
		for (int i = 16; i < params.length; i++) {
			content += params[i] + SolidConfigure.PaxosOrderSplitor;
		}
		content = content.substring(0, content.length() - 4);
		this.setHost(params[1]);
		this.setPort(Integer.parseInt(params[2]));
		this.setPasswd(params[3]);
		this.setIncreamentNo(Long.parseLong(params[4]));
		this.setCategory(params[5]);
		this.setDB(Integer.parseInt(params[6]));
		this.setContent(content);
		// 预备位置不做处理今后如有需要需要进行调整
	}

	public static String[] BakUpFiller(String[] contents) {
		int contenlength;
		String[] filler;
		if (contents == null) {
			contenlength = 0;
		} else {
			contenlength = contents.length;
		}
		filler = new String[9 - contenlength];
		for (int i = 0; i < filler.length; i++) {
			filler[i] = SolidConfigure.BakUpFiller;
		}
		return ArrayUtils.addAll(contents, filler);
	}

	public static String OrderStr(String host, int port, String passwd,
			long increamentNo, String Category, int DB, String Content,
			String[] bakups) {
		if (bakups == null) {
			bakups = BakUpFiller(bakups);
		}
		String order = SolidConfigure.OrderHead
				+ SolidConfigure.PaxosOrderSplitor + host
				+ SolidConfigure.PaxosOrderSplitor + port
				+ SolidConfigure.PaxosOrderSplitor + passwd
				+ SolidConfigure.PaxosOrderSplitor + increamentNo
				+ SolidConfigure.PaxosOrderSplitor + Category
				+ SolidConfigure.PaxosOrderSplitor + DB
				+ SolidConfigure.PaxosOrderSplitor + bakups[0]
				+ SolidConfigure.PaxosOrderSplitor + bakups[1]
				+ SolidConfigure.PaxosOrderSplitor + bakups[2]
				+ SolidConfigure.PaxosOrderSplitor + bakups[3]
				+ SolidConfigure.PaxosOrderSplitor + bakups[4]
				+ SolidConfigure.PaxosOrderSplitor + bakups[5]
				+ SolidConfigure.PaxosOrderSplitor + bakups[6]
				+ SolidConfigure.PaxosOrderSplitor + bakups[7]
				+ SolidConfigure.PaxosOrderSplitor + bakups[8]
				+ SolidConfigure.PaxosOrderSplitor + "{" + Content + "}";
		return order;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void InitMessageHandlers() {
		// 初始化子类,并注册
		for (int i = 0; i < SolidConfigure.HandlerKeys.length; i++) {
			String clazzPath = SolidConfigure.OdrPkgPrefix
					+ SolidConfigure.HandlerKeys[i];
			try {
				Class clazz = Class.forName(clazzPath);
				MessageHandlerBase messageHandlerBase = (MessageHandlerBase) clazz
						.getConstructor(String.class).newInstance(//String host, int port, String passwd, long increamentNo, String Category, int DB, String Content, String[] bakups
								OrderStr(SolidConfigure.INIT, 0,
										SolidConfigure.INIT, 0,
										SolidConfigure.HandlerKeys[i], 0, SolidConfigure.INIT, null));
				if (messageHandlerBase != null) {
					MessageHandlerBase.HandlerRegister.put(
							SolidConfigure.HandlerKeys[i], messageHandlerBase);
				} else {
					// 错误代码尚未统一，别高兴的太早
					System.err.println("Handler初始化错误！程序退出");
					System.exit(-2);
				}
				// 这个异常抛得也太high了嘛。。。。。。。
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * TODO 未测试,其工作原理还需要修改
	 * 
	 * @param msg
	 * @return
	 * @deprecated 废弃,切勿使用,需要取得HandlerRegister中的MeesageHandlerBase子类对象请使用替代方法
	 *             {@link getHandler()}
	 */
	public synchronized MessageHandlerBase getInstanceFromRegi(String msg) {
		// 通过调用子类的OrderAnalasy()方法获得一个全新的对象
		// 未完成，通过root对象去访问其他的对象
		MessageHandlerBase messageHandlerBase = MessageHandlerBase.HandlerRegister
				.get(msg.split(SolidConfigure.PaxosOrderSplitor)[5]);
		messageHandlerBase.OrderAnalasy(msg);
		String key = messageHandlerBase.Category;
		MessageHandlerBase targetObj = MessageHandlerBase.HandlerRegister
				.get(key);
		targetObj.OrderAnalasy(msg);
		return targetObj;
	}

	/**
	 * @description 通过该方法取得一个HandlerRegister中的MessageHandlerBase对象
	 * @Thread_UnSafe 取得的对象可能其他线程也已经取到，所以对该单元的操作需要锁定取得的对象
	 * @param msg
	 * @return
	 */
	public static MessageHandlerBase getHandler(String msg) {
		// 取得当前的对应的Category
		return MessageHandlerBase.HandlerRegister
				.get(msg.split(SolidConfigure.PaxosOrderSplitor)[SolidConfigure.CategoryPosition]);

	}

	public abstract void Process(String content);

	public abstract void ProcessContent();

	public abstract void push();

	public abstract void pull();

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public MessageHandlerBase getHandler() {
		return handler;
	}

	public void setHandler(MessageHandlerBase handler) {
		this.handler = handler;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getIncreamentNo() {
		return increamentNo;
	}

	public void setIncreamentNo(Long increamentNo) {
		this.increamentNo = increamentNo;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String Category) {
		this.Category = Category;
	}

	public int getDB() {
		return DB;
	}

	public void setDB(int dB) {
		DB = dB;
	}

}
