package com.company.chat.client.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.chat.client.ChatClient;
import com.company.chat.client.entity.ClientConfig;

public class ClientMain {

	private final static Logger log = LoggerFactory.getLogger(ClientMain.class);
	
	public static void main(String[] args) throws InterruptedException {
		log.info("Init....");
		ClientConfig config = ClientInit.getConfig(); 
		ChatClient client = new ChatClient(config);
		client.start();
	}
}
