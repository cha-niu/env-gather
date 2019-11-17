package com.briup.env.util;

import org.apache.log4j.Logger;

public class LogImpl implements Log{
	
	private Logger logger = Logger.getRootLogger();
	
	@Override
	public void debug(String message) {
		logger.debug(message);
	}

	@Override
	public void info(String message) {
		logger.info(message);
	}

	@Override
	public void warn(String message) {
		logger.warn(message);
	}

	@Override
	public void error(String message) {
		logger.error(message);
	}

	@Override
	public void fatal(String message) {
		logger.fatal(message);
	}

}
