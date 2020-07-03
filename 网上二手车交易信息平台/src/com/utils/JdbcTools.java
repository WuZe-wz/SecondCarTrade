package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcTools {
	
	/*使用数据库连接池获取连接*/
	/*ComboPooledDataSource是DataSource的子接口
	 * 所以这里写成private static DataSource cpds = null;也没问题
	 * */
	private static ComboPooledDataSource cpds = null;//static静态 
	
	/*数据库连接池，-------当前系统没有使用数据库连接池，而是普通的数据库连接*/
	public static Connection getConnectionFromPool() throws Exception {
		if(cpds==null)//如果此时没有数据库连接池，才创建
			cpds = new ComboPooledDataSource("com.dgut.javaweb");
		return cpds.getConnection();//用数据库连接池去获取连接，返回
	}
	
	
	
	//建立连接
	public static Connection getconnection() throws Exception {
		String driverClass="com.mysql.cj.jdbc.Driver";//mysql的实现类//注册1
		String jdbcUrl="jdbc:mysql://localhost:3306/SecondCarTrade?serverTimezone=Hongkong";//问号前面的“jdbc”就是数据库的名称
		String user="root";
		String password="wuzedb100";
		Class.forName(driverClass);//注册2
		Connection connection=DriverManager.getConnection(jdbcUrl, user, password);
		return connection;
		//return getConnectionFromPool();//20200610-MVC-从数据库连接池获取连接(速度快)
	}
	
	//释放资源-两个参数statement、connection
	public static void releaseResource(Statement statement,Connection connection) {
		try {
			if(statement!=null)
				statement.close();
		}catch(Exception e2) {
			e2.printStackTrace();
		}
		
		try {
			if(connection!=null)
				connection.close();
		}catch(Exception e3) {
			e3.printStackTrace();
		}
	}
	//释放资源-三个参数rs、statement、connection(注意顺序)
		public static void releaseResource(ResultSet rs,Statement statement,Connection connection) {
			
			try {
				if(rs!=null)
					rs.close();
			}catch(Exception e1) {
				e1.printStackTrace();
			}
			
			try {
				if(statement!=null)
					statement.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
			
			try {
				if(connection!=null)
					connection.close();
			}catch(Exception e3) {
				e3.printStackTrace();
			}
		}
}
