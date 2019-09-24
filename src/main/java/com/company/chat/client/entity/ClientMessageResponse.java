package com.company.chat.client.entity;

import java.io.Serializable;

public class ClientMessageResponse implements Serializable {
	
	private static final long serialVersionUID = -7414461117695938022L;
	
	private String name;	
	private String message;
	private long timeStamp;
	
	
	public ClientMessageResponse(String name, String message, long timeStamp) {		
		this.name = name;		
		this.message = message;
		this.timeStamp = timeStamp;
	}	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}	
}
