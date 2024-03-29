package com.company.chat.client.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.chat.client.entity.ClientConfig;

public abstract class ClientInit {

	private final static Logger log = LoggerFactory.getLogger(ClientInit.class);

	
	public static ClientConfig getConfig()  {
		ClientConfig config = new ClientConfig();
		Properties prop = new Properties(); 
		try (InputStream input = new FileInputStream("confClient/config.properties")) {
			if(Objects.isNull(input)) {
				log.error("unable to find config.properties!!!; APP TERMINATED!!!");
				System.exit(-1);
			}				
			prop.load(input);
			String host = getStringValue(prop, "server.host");
			config.setServerHost(host);
			int port = getIntValue(prop, "server.port");
			config.setServerPort(port);
			
			
		}catch (NoSuchElementException | NumberFormatException e) {
			log.error("Parameter initialization error: {}; APP TERMINATED!!!", e.getMessage());
			System.exit(-1);
		}catch (IOException e) {
			log.error("Initialization failed!!!; APP TERMINATED!!!", e);
			System.exit(-1);
		}
		
		return config;
	}
	
	private static String getStringValue(Properties prop, String name) {
		String param = Optional.ofNullable(prop.get(name))
				               .map(Object::toString)
				               .filter(Predicate.not(String::isBlank))
				               .orElseThrow(() -> new NoSuchElementException(name));
 
		log.info("{}: {}", name, param);
		return param;
	}
	
	private static int getIntValue(Properties prop, String name) {
		String str = getStringValue(prop, name); 
		try {
			int param = Integer.parseInt(str);
			return param;
		}catch (NumberFormatException e) {
			log.error("Parameter initialization error: {}; APP TERMINATED!!!", name);
			System.exit(-1);
		}
		return 0;
	}
}
