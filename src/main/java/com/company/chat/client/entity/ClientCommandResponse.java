package com.company.chat.client.entity;

public class ClientCommandResponse extends ClientMessageResponse {

	private static final long serialVersionUID = -6389756717272111939L;

	private final boolean status;
	
	public ClientCommandResponse(boolean status, String name, String message) {
		super(name, message, System.currentTimeMillis());
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}	
}
