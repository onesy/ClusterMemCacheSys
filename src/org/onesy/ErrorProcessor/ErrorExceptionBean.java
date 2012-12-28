package org.onesy.ErrorProcessor;

import java.util.UUID;

public final class ErrorExceptionBean {
	
	private UUID uuid ;
	
	private Exception exception ;
	
	public ErrorExceptionBean(UUID uuid, Exception exception){
		this.setException(exception);
		this.setUuid(uuid);
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}
