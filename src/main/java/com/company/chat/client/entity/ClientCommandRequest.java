package com.company.chat.client.entity;

import java.io.Serializable;

public class ClientCommandRequest implements Serializable {
		
	private static final long serialVersionUID = -4387694086688630375L;
	
	private final ClientDataType dataType = ClientDataType.command;	
	private String command;
	private String param;
	private int commandId;
	
	public ClientCommandRequest(String command, String param, int commandId) {
		super();
		this.command = command;
		this.param = param;
		this.commandId = commandId;
	}

	public ClientDataType getDataType() {
		return dataType;
	}

	public String getCommand() {
		return command;
	}

	public String getParam() {
		return param;
	}

	public int getCommandId() {
		return commandId;
	}
	
}
