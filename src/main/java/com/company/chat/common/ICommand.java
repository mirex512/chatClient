package com.company.chat.common;

import com.company.chat.client.entity.ClientCommandRequest;
import com.company.chat.client.entity.ClientCommandResponse;

public interface ICommand {
	
	public String getCommandName();
	public String getDescription();
	public void init(IChatServer server);
	public ClientCommandResponse execute(ClientCommandRequest clientData, long clientId);
}
