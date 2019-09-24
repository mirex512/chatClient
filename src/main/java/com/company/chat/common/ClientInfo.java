package com.company.chat.common;

import java.util.Objects;

public class ClientInfo {

	private final String name;
	private final long id;
	
	public ClientInfo(String name, long id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientInfo other = (ClientInfo) obj;
		return Objects.equals(name, other.name);
	}	
	
	public static ClientInfo getEmpty() {
		return new ClientInfo("", -1);
	}
}
