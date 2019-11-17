package com.briup.env.test;

import org.apache.log4j.Logger;

@SuppressWarnings("all")
public class HelloTest {
	
	private static Logger logger = Logger.getRootLogger();
	
	public static void main(String[] args) {
		
		String className = "com.briup.env.server.ServerImpl";
		
		try {
			Class<?> c = Class.forName(className);
			Object obj = c.newInstance();
			System.out.println(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
