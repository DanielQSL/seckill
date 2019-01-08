package com.qsl.seckill.util;

import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {
	
//	private static Properties props;
//
//	static {
//		try {
//			InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("application.properties");
//			props = new Properties();
//			props.load(in);
//			in.close();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static Connection getConn() throws Exception{
//		String url = props.getProperty("spring.datasource.url");
//		String username = props.getProperty("spring.datasource.username");
//		String password = props.getProperty("spring.datasource.password");
//		String driver = props.getProperty("spring.datasource.driver-class-name");
//		Class.forName(driver);
//		return DriverManager.getConnection(url,username, password);
//	}

//	@Value("${spring.datasource.url}")
//	private String url;
//
//	@Value("${spring.datasource.username}")
//	private String username;
//
//	@Value("${spring.datasource.password}")
//	private String password;
//
//	@Value("${spring.datasource.driver-class-name}")
//	public String driver;
//
//	public static Connection getConn() throws Exception{
//		Class.forName(driver);
//		return DriverManager.getConnection(url,username, password);
//	}

	public static Connection getConn() throws Exception {
		String url = "jdbc:mysql://47.97.189.167:3306/seckill?characterEncoding=utf-8&useSSL=false";
		String username = "root";
		String password = "123456";
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
		return DriverManager.getConnection(url, username, password);
	}
}
