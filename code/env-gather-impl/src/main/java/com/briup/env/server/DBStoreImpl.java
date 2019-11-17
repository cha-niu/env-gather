package com.briup.env.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Properties;

import com.briup.env.Configuration;
import com.briup.env.bean.Environment;
import com.briup.env.support.ConfigurationAware;
import com.briup.env.support.PropertiesAware;
import com.briup.env.util.Log;

public class DBStoreImpl implements DBStore,PropertiesAware,ConfigurationAware{
	private String driverClass;
	private String url;
	private String user;
	private String password;
	private int batchSize;
	
	private Log logger;
	
	@Override
	public void saveDB(Collection<Environment> c) throws Exception {
		Class.forName(driverClass);
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		conn = DriverManager.getConnection(url, user, password);
		
		conn.setAutoCommit(false);
		
		int dayOfPrefixData = -1;
		
		int count = 0;
		
		for(Environment env : c) {
			count++;
			
			Timestamp date = env.getGather_date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(date.getTime());
			int day = calendar.get(Calendar.DATE);
			logger.debug("入口模块当前处理的数据中的日期为："+day);
			
			if(day != dayOfPrefixData) {
				if(dayOfPrefixData!=-1) {
					ps.executeBatch();
					if(ps != null)ps.close();
				}
				count = 0;
				String tableName = "e_detail_"+day;
				String sql = "insert into "+tableName+"(name,srcId,desId,devId,sersorAddress,count,cmd,status,data,gather_date) values(?,?,?,?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
			}
			
			ps.setString(1, env.getName());
			ps.setString(2, env.getSrcId());
			ps.setString(3, env.getDesId());
			ps.setString(4, env.getDevId());
			ps.setString(5, env.getSersorAddress());
			ps.setInt(6, env.getCount());
			ps.setString(7, env.getCmd());
			ps.setInt(8, env.getStatus());
			ps.setFloat(9, env.getData());
			ps.setTimestamp(10, env.getGather_date());
			ps.addBatch();
			
			if(count==batchSize) {
				ps.executeBatch();
				count = 0;
			}
			
			dayOfPrefixData = day;
			
		}
		
		ps.executeBatch();
		
		conn.commit();
		
		
		if(ps!=null)ps.close();
		if(conn!=null)conn.close();
		
		
	}

	@Override
	public void init(Properties p) throws Exception {
		driverClass = p.getProperty("driver");
		url = p.getProperty("url");
		user = p.getProperty("username");
		password = p.getProperty("password");
		batchSize = Integer.parseInt(p.getProperty("batch-size"));
	}

	@Override
	public void setConfiguration(Configuration configuration) throws Exception {
		logger = configuration.getLogger();
	}

}
