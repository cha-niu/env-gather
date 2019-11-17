package com.briup.env.main;

import com.briup.env.Configuration;
import com.briup.env.ConfigurationImpl;
import com.briup.env.server.Server;

public class ServerMain {
	
	public static void main(String[] args) {
		Configuration config = new ConfigurationImpl();
		try {
			Server server = config.getServer();
			server.reciver();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
