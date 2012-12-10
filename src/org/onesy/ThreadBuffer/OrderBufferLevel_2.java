package org.onesy.ThreadBuffer;

public class OrderBufferLevel_2 {
	
	private static OrderBufferLevel_2 orderBufferLevel_2 ;
	
	private OrderBufferLevel_2(){
		
	}
	
	public synchronized static OrderBufferLevel_2 getInstace(){
		if(null == OrderBufferLevel_2.orderBufferLevel_2) {
			OrderBufferLevel_2.orderBufferLevel_2 = new OrderBufferLevel_2();
		}
		return OrderBufferLevel_2.orderBufferLevel_2;
	}

}
