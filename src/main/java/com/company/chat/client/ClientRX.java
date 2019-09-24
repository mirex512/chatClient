package com.company.chat.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.Socket;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.chat.client.entity.ClientCommandResponse;
import com.company.chat.client.entity.ClientDataType;
import com.company.chat.client.entity.ClientMessageResponse;
import com.google.gson.Gson;

import static com.company.chat.client.util.IDefaults.defaultCharset;
import static com.company.chat.client.util.IDefaults.FORMATTER;

public class ClientRX implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final int BUFFER_SIZE = 16 * 1024; 
	private static final Gson gson = new Gson();
	
	private final Socket socket;
	private final IClientChat client;
	
	public ClientRX(IClientChat client) {
		super();		
		this.client = client;
		socket = client.getSocket();		
	}

	@Override
	public void run() {
		String threadName = this.getClass().getSimpleName();
		Thread.currentThread().setName(threadName);
	
		DataInputStream in = null;
		try {
			  in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		while(!Thread.currentThread().isInterrupted()) {
			try {	
				
				byte[] buffer = new byte[BUFFER_SIZE];
				int length = in.read(buffer, 0, buffer.length);
				
				if(length == -1) {
					log.info("Server close channale");
					synchronized (client) {
						client.notify();
					}
					break;
				}
				
				if(length < 2)
					continue;
				
				byte[] rawData = new byte[length - 1];
				System.arraycopy(buffer, 1, rawData, 0, length - 1);
				
				String json = new String(rawData, defaultCharset);
				
				ClientDataType type = null;
				try {
					type = ClientDataType.values()[buffer[0]];
				}catch (ArrayIndexOutOfBoundsException e) {
					log.warn("Unknown package type: {}; package: {}", buffer[0], json);
					continue;
				}
				
				switch (type) {
					case message:
						handleMessage(json);
						break;						
					case command:
						handleCommand(json);
						break;
					default:
						log.warn("Unknown package type: {}; package: {}", buffer[0], json);
						break;
				}
				
				if(Objects.isNull(json) || json.isBlank()) {
					continue;
				}
										
			} catch (IOException e) {
				if(!Thread.currentThread().isInterrupted()) {
					log.error("Error communication with server", e);
				} else {
					log.error("Response processing error", e);
				}
			}
		}
	}
	
	private void handleMessage(String json) {
		ClientMessageResponse response = gson.fromJson(json, ClientMessageResponse.class);
		String message = generateMessageResponse(response);		
		System.out.println(message);
	}
	
	private void handleCommand(String json) {
		ClientCommandResponse response = gson.fromJson(json, ClientCommandResponse.class);
		String time = getTime(response.getTimeStamp());
		System.out.println(time + ": " + response.getMessage());
	}
	
	private static String generateMessageResponse(ClientMessageResponse response) {		
		StringBuilder sb = new StringBuilder();
		sb.append(getTime(response.getTimeStamp()))
		  .append(" ")
		  .append(response.getName())
		  .append(": ")
		  .append(response.getMessage());
		return sb.toString();
	}

	private static String getTime(long timeStamp) {
		ZonedDateTime zdt =  ZonedDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault());
		return zdt.format(FORMATTER);
	}
}
