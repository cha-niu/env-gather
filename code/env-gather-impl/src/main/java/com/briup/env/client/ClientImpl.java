package com.briup.env.client;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.env.bean.Environment;
import com.briup.env.support.PropertiesAware;

public class ClientImpl implements Client,PropertiesAware{
	
	private String host;
	private int port;
	
	@Override
	public void send(Collection<Environment> c) throws Exception {
		
		Socket socket = new Socket(host, port);
		
		ObjectOutputStream out = 
				new ObjectOutputStream(socket.getOutputStream());
		
		out.writeObject(c);
		out.flush();
		
		if(out!=null)out.close();
		if(socket!=null)socket.close();
		
	}

	@Override
	public void init(Properties p) throws Exception {
		host = p.getProperty("host");
		port = Integer.parseInt(p.getProperty("port"));
	}

}
