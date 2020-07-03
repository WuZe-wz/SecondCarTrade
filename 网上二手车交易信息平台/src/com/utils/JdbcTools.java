package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcTools {
	
	/*ʹ�����ݿ����ӳػ�ȡ����*/
	/*ComboPooledDataSource��DataSource���ӽӿ�
	 * ��������д��private static DataSource cpds = null;Ҳû����
	 * */
	private static ComboPooledDataSource cpds = null;//static��̬ 
	
	/*���ݿ����ӳأ�-------��ǰϵͳû��ʹ�����ݿ����ӳأ�������ͨ�����ݿ�����*/
	public static Connection getConnectionFromPool() throws Exception {
		if(cpds==null)//�����ʱû�����ݿ����ӳأ��Ŵ���
			cpds = new ComboPooledDataSource("com.dgut.javaweb");
		return cpds.getConnection();//�����ݿ����ӳ�ȥ��ȡ���ӣ�����
	}
	
	
	
	//��������
	public static Connection getconnection() throws Exception {
		String driverClass="com.mysql.cj.jdbc.Driver";//mysql��ʵ����//ע��1
		String jdbcUrl="jdbc:mysql://localhost:3306/SecondCarTrade?serverTimezone=Hongkong";//�ʺ�ǰ��ġ�jdbc���������ݿ������
		String user="root";
		String password="wuzedb100";
		Class.forName(driverClass);//ע��2
		Connection connection=DriverManager.getConnection(jdbcUrl, user, password);
		return connection;
		//return getConnectionFromPool();//20200610-MVC-�����ݿ����ӳػ�ȡ����(�ٶȿ�)
	}
	
	//�ͷ���Դ-��������statement��connection
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
	//�ͷ���Դ-��������rs��statement��connection(ע��˳��)
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
