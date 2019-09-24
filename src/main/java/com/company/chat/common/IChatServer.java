package com.company.chat.common;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IChatServer {

	public Set<ClientInfo> getAllClientInfo();
	public Optional<ClientInfo> getClientInfoById(long id);
	
	public Map<String, ICommand> getCommandsMap(); 
	public void buildCommandsMap();
	
	public IMessageStore getMessageStore();
}
