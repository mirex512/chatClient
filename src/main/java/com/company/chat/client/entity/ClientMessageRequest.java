package com.company.chat.client.entity;

import java.io.Serializable;

public class ClientMessageRequest implements Serializable {

	private static final long serialVersionUID = 568794497027064158L;
	
	private ClientDataType dataType = ClientDataType.message;	
	private String text;
	
	public ClientMessageRequest(String message) {
		text = message;
	}
	
	public ClientDataType getDataType() {
		return dataType;
	}
	public String getText() {
		return text;
	}
		
}
