package org.onesy.MessageHandler;
/**
 * 
 * @author way
 * @category Confirm message
 * @content version:status:confirm 
 */
public class Confirm extends MessageHandlerBase {
	
	/**
	 * version:返回传输到本机的议题的版本号
	 * status:对proposal的响应 agree:200 not agree:400 其他为异常
	 * confirm:confirm的
	 * @param Message
	 */

	public Confirm(String Message) {
		super(Message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ProcessContent() {
		// TODO Auto-generated method stub
		
		/**
		 * Paxos process
		 * 
		 */

	}

	@Override
	public void push() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pull() {
		// TODO Auto-generated method stub

	}

}
