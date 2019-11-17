package com.briup.env;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.briup.env.client.Client;
import com.briup.env.client.Gather;
import com.briup.env.server.DBStore;
import com.briup.env.server.Server;
import com.briup.env.support.ConfigurationAware;
import com.briup.env.support.PropertiesAware;
import com.briup.env.util.Backup;
import com.briup.env.util.Log;

public class ConfigurationImpl implements Configuration{
	private Map<String,Object> map = new HashMap<>();
	private Properties p = new Properties();
	
	public ConfigurationImpl() {
		parse("conf.xml");
		initOtherModel();
	}
	
	private void initOtherModel() {
		for(Object obj:map.values()) {
			if(obj instanceof PropertiesAware) {
				try {
					((PropertiesAware)obj).init(p);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(obj instanceof ConfigurationAware) {
				try {
					((ConfigurationAware)obj).setConfiguration(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void parse(String configFilePath) {
		SAXReader reader = new SAXReader();
		try {
			Document document = 
					reader.read(getClass().getClassLoader().getResourceAsStream(configFilePath));
			
			Element rootElement = document.getRootElement();
			List<Element> elements = rootElement.elements();
			for(Element e : elements) {
				
				String modelName = e.getName();
				String className = e.attributeValue("class");
				map.put(modelName, Class.forName(className).newInstance());
				
				List<Element> elements2 = e.elements();
				for(Element e2 : elements2) {
					String key = e2.getName();
					String value = e2.getText();
					p.setProperty(key, value);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Log getLogger() throws Exception {
		return (Log)map.get(ModelName.LOGGER);
	}

	@Override
	public Server getServer() throws Exception {
		return (Server)map.get(ModelName.SERVER);
	}

	@Override
	public Client getClient() throws Exception {
		return (Client)map.get(ModelName.CLIENT);
	}

	@Override
	public DBStore getDbStore() throws Exception {
		return (DBStore)map.get(ModelName.DBSTORE);
	}

	@Override
	public Gather getGather() throws Exception {
		return (Gather)map.get(ModelName.GATHER);
	}

	@Override
	public Backup getBackup() throws Exception {
		return (Backup)map.get(ModelName.BACKUP);
	}
	
	//这里的名字和conf.xml中的标签名对应
	private interface ModelName{
		String GATHER = "gather";
		String LOGGER = "logger";
		String BACKUP = "backup";
		String CLIENT = "client";
		String SERVER = "server";
		String DBSTORE = "dbStore";
	}

}
