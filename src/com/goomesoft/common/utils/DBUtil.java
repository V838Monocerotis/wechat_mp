package com.goomesoft.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * DB工具类
 * @author LX
 *
 */
public class DBUtil {
	
	private static String driver = null;
	private static String url = null;
	private static String username = null;
	private static String password = null;
	
	static {
		Properties prop = new Properties();
		InputStream in = DBUtil.class.getResourceAsStream("/jdbc.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver = prop.getProperty("jdbc.driver").trim();
		url = prop.getProperty("jdbc.url").trim();
		username = prop.getProperty("jdbc.username").trim();
		password = prop.getProperty("jdbc.password").trim();
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection(Connection conn) {
		try{
			if(conn != null) {
				conn.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
