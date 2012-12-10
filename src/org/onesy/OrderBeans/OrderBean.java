package org.onesy.OrderBeans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OrderBean implements Serializable {
	
	private String srcVotSerilizer;

	private String kind;

	private String content;
	
	public OrderBean(String scrVotSerializer,String kind,String content){
		this.setSrcVotSerilizer(scrVotSerializer);
		this.setKind(kind);
		this.setContent(content);
	}

	public String getSrcVotSerilizer() {
		return srcVotSerilizer;
	}

	public void setSrcVotSerilizer(String srcVotSerilizer) {
		this.srcVotSerilizer = srcVotSerilizer;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
