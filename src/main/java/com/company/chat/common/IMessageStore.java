package com.company.chat.common;

import java.util.List;

import com.company.chat.client.entity.ClientMessageResponse;

public interface IMessageStore {

	public void addMessage(ClientMessageResponse data);
	public List<ClientMessageResponse> getLastMessages();
}
