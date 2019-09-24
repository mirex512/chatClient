package com.company.chat.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.company.chat.client.entity.ClientCommandRequest;
import com.company.chat.client.entity.ClientMessageRequest;
import com.google.gson.Gson;

import static com.company.chat.client.util.IDefaults.defaultCharset;

public class ClientTX implements Runnable {
	private final static Gson gson = new Gson();
	private final IClientChat client;
	private final Socket socket;	
	private static int commandCounter = 0;
	
	public ClientTX(IClientChat client) {
		this.client = client;
		socket = client.getSocket();
	}

	@Override
	public void run() {
		String threadName = this.getClass().getSimpleName();
		Thread.currentThread().setName(threadName);
		
		BufferedReader br;
		if(System.getProperty("os.name").toLowerCase().startsWith("win")) 
			br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.ISO_8859_1));		
		else 
			br = new BufferedReader(new InputStreamReader(System.in, defaultCharset));
		 
		DataOutputStream out = null;
        try {
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(!socket.isOutputShutdown()) {
			try {			
				
				String text = br.readLine();
				if(Objects.isNull(text) || text.isBlank())
					continue;
				if(text.equals("exit")) {
					synchronized (client) {
						client.notify();
					}
					break;
				}
				
				byte[] msg = null;
				if(text.startsWith("/"))
					msg = generateCommand(text);
				else
					msg = generateMessage(text);
					
				
				out.write(msg);
				out.flush();
			} catch (IOException e) {			
				e.printStackTrace();
			}				
		}
	}
	
	private byte[] generateMessage(String text) {
		String json = gson.toJson(new ClientMessageRequest(text));
		byte[] b = json.getBytes();
		byte[] msg = new byte[b.length + 1];
		msg[0] = 0;
		System.arraycopy(b, 0, msg, 1, b.length);
		return msg;
	}
	
	private byte[] generateCommand(String text) {
		
		int pos = text.indexOf(" ");
		String command;
		String param = null;
		if(pos == -1){
			command = text;
		} else {
			command = text.substring(0, pos);
			param = text.substring(pos + 1);
		}
		String json = gson.toJson(new ClientCommandRequest(command, param, commandCounter++));
		byte[] b = json.getBytes();
		byte[] msg = new byte[b.length + 1];
		msg[0] = 1;
		System.arraycopy(b, 0, msg, 1, b.length);
		return msg;
	}
}
