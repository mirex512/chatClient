package com.company.chat.client.entity;

public class ClientConfig {

	private String serverHost;
	private int serverPort;	
	
	public ClientConfig() {}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
}
