package com.briup.env.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import com.briup.env.support.PropertiesAware;

public class BackupImpl implements Backup,PropertiesAware{
	private String filePath;
	
	@Override
	public Object load(String fileName, boolean del) throws Exception {
		File file = new File(filePath+fileName);
		if(!file.exists()) {
//			System.out.println("备份文件不存在："+fileName);
			return null;
		}
		
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream in = new ObjectInputStream(fis);
		
		Object obj = in.readObject();
		
		if(del) {
			file.delete();
		}
		
		if(fis!=null)fis.close();
		if(in!=null)in.close();
		
		return obj;
	}

	@Override
	public void store(String fileName, Object obj, boolean append) throws Exception {
		
		FileOutputStream fos = new FileOutputStream(filePath+fileName,append);
		ObjectOutputStream out = new ObjectOutputStream(fos);
		
		out.writeObject(obj);
		out.flush();
		
		if(fos!=null)fos.close();
		if(out!=null)out.close();
		
	}

	@Override
	public void init(Properties p) throws Exception {
		filePath = p.getProperty("file-path");
	}

}
