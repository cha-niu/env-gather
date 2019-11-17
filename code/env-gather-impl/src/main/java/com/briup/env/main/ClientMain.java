package com.briup.env.main;

import java.util.Collection;

import com.briup.env.Configuration;
import com.briup.env.ConfigurationImpl;
import com.briup.env.bean.Environment;
import com.briup.env.client.Client;
import com.briup.env.client.ClientImpl;
import com.briup.env.client.Gather;
import com.briup.env.client.GatherImpl;

@SuppressWarnings("all")
public class ClientMain {
	
	public static void main(String[] args) {
		
		Configuration config = new ConfigurationImpl();
		
		
		try {
			Gather gather = config.getGather();
			Client client = config.getClient();
			
			Collection<Environment> c = gather.gather();
			client.send(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
