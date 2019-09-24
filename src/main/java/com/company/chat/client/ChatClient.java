package com.company.chat.client;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.chat.client.entity.ClientConfig;

public class ChatClient implements IClientChat {
	
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final ClientConfig config;
	private Socket socket;
	
	public ChatClient(ClientConfig config) {
		super();
		this.config = config;
	}

	public void start() {		
		try {
			socket = new Socket(config.getServerHost(), config.getServerPort());
			
			Thread thread  = new Thread(new ClientRX(this));
			thread.setDaemon(true);
			thread.start();
			
			thread = new Thread(new ClientTX(this));
			thread.setDaemon(true);
			thread.start();
			
			log.info("Init....OK");			
			log.info("Start");
			
			synchronized (this) {				
				this.wait();
			}
			
			socket.close();	
		}catch (InterruptedException e) {
			log.error("Internal error!!!", e);		
		}catch (IOException e) {
			log.error("error connecting to server {}:{}", config.getServerHost(), config.getServerPort(), e);
			log.error("Application terminated!!!");
			System.exit(-1);
		}			
		
		
		
		log.info("Stop");
	}

	@Override
	public Socket getSocket() {		
		return socket;
	}
}
