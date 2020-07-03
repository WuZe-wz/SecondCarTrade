package com.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bean.User;
import com.bean.UserSearchConditions;
import com.dao.UserDao;
import com.daoImpl.UserDaoImpl;
import com.utils.JdbcTools;


public class UserService {
private UserDao dao;
	
	/*��ȡdao��/��ʼ����*/
	public UserService() {
		this.dao=new UserDaoImpl();
	}
	
	
	
	//��¼login��name+pwd��
		public boolean login(String name, String pwd) throws Exception {
			boolean flag=false;
			Connection connection = null;
			User user = new User(); 
			String sql="select * from user where name='"+name+"' and pwd='"+pwd+"'";
			try {
				
				/*��ȡ����*/
				connection=JdbcTools.getconnection();
				
				System.out.println("��ʼ����resultset");
			
				//����ѯ�Ľ������һ��ResultSet����
			ResultSet rs =dao.selectSqlLogin(connection,sql);
			
			System.out.println("��ѯ����ѷ�װ��resultset��");
			
			//����ResultSet
			while(rs.next()) {
				
					if(rs.getString("name").equals(name) && rs.getString("pwd").equals(pwd)) {
						flag=true;
					}
					
				} 
				
			}catch (SQLException e) {
					e.printStackTrace();
			}finally {
				JdbcTools.releaseResource(null, connection);
			}
			
			return flag;//true����¼�ɹ�
		}
		
		
		//����name��pwd����id
		public int LoginFindId(String name, String pwd) throws Exception {
			Connection connection = null;
			User user = new User(); 
			//����дselect id ...����Ϊ����Ҫ�õ�name��pwd���쳣��Column 'name' not found��
			String sql="select * from user where name='"+name+"' and pwd='"+pwd+"'";
			try {
				
				/*��ȡ����*/
				connection=JdbcTools.getconnection();
				
				System.out.println("��ʼ����resultset");
			
				//����ѯ�Ľ������һ��ResultSet����
			ResultSet rs =dao.selectSqlLogin(connection,sql);
			System.out.println("dao.selectSqlLoginִ�����");
			System.out.println("��ѯ����ѷ�װ��resultset��");
			
			//����ResultSet
			while(rs.next()) {
				System.out.println("����while(rs.next())");
					if(rs.getString("name").equals(name) && rs.getString("pwd").equals(pwd)) {
						System.out.println("ƥ�䵽�û�");
						return rs.getInt("id");
					}
					
				} 
				
			}catch (SQLException e) {
					e.printStackTrace();
			}finally {
				JdbcTools.releaseResource(null, connection);
			}		
			return -1;//true����¼�ɹ�
		}
		
	
	/*ģ����ѯ*/
	public java.util.List<User> SearchUsersByConditions(UserSearchConditions conditions){
		Connection connection = null;
		java.util.List<User> users=new java.util.ArrayList<User>();
		try {
			/*��ȡ����*/
			connection=JdbcTools.getconnection();
			/*ͨ��dao��ͨ�ò�ѯ������ظ���Ϊuser��List����*/
			users=dao.SearchUsersByConditions(connection,conditions);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return users;
	}
	
	
	/*�޸� ֮ ʵ�ʵĶ����ݿ����*/
	public void UpdateUserServlet(User user){
		Connection connection =null;
		try {
			System.out.println("����Service��UpdateUserServlet()");
			connection=JdbcTools.getconnection();
			dao.updateUser(connection, user);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		
	}
	

	
	
	/*�޸� ֮ ����id��ѯuser����*/
	public User getUser(int id){
		User user=new User();//�����䲻����try���棬��������
		Connection connection =null;
		try {
			connection=JdbcTools.getconnection();
			user=dao.searchUserById(connection, id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return user;
	}
	
	
	
	/*��������ӵ����ݿ�*/
	public boolean RegisterUserServlet(User user) throws Exception {
		boolean result=false;//��ΪIdCard����Ψһ��Լ���������Զ�����������ʱ���ܳ�����resultָʾ
		Connection connection =null;
		try {
			connection=JdbcTools.getconnection();
			dao.addUser(connection,user);
			result=true;//����ӳɹ�ʱ��true
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return result;
	}
	
	
	
	//ɾ��
	public void DeleteUserServlet(int id) throws Exception {
		Connection connection =null;
		try {
			connection=JdbcTools.getconnection();
			dao.deleteUser(connection, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
	}
	
	public java.util.List<User> getAllUsers(){
		Connection connection = null;
		java.util.List<User> users=new java.util.ArrayList<User>();
		try {
			/*��ȡ����*/
			connection=JdbcTools.getconnection();
			/*ͨ��dao��ͨ�ò�ѯ������ظ���Ϊusers��List����*/
			users =dao.fetchAllUsers(connection);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return users;
	}
	
	
	
	
	
	
	
	/*�����û�����--------���±���Ϣ����limit������Ϊ1*/
	//����id
	public boolean LimitUser(int id) {
		Connection connection =null;
		try {
			System.out.println("����Service��LimitUser");
			connection=JdbcTools.getconnection();
			dao.updateLimit(connection, id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return false;
	}
	
	
	//����id�жϸ��û��Ƿ�����(�ڵ�¼��ʱ���ж�)
	//
	public boolean isLimit(int id) throws Exception {
		Connection connection = null;
		User user = new User(); 
		
		//ע�⣺sql����д"select limit ... Ӧ��д* ���������ݿ��ѯ�쳣��
		String sql = "select * from user where id='"+id+"'";
		try {
			
			/*��ȡ����*/
			connection=JdbcTools.getconnection();
			
			System.out.println("��ʼ����resultset");
		
			//����ѯ�Ľ������һ��ResultSet����
		ResultSet rs =dao.selectSqlLogin(connection,sql);
		
		System.out.println("��ѯ����ѷ�װ��resultset��");
		
		//����ResultSet
		while(rs.next()) {
			//rs.getInt("id")�ǵü����ţ���Ȼid��int�ͣ�ҲҪ�ӣ�
				if(rs.getInt("id")==id) {
					System.out.println("��λ�����û���isLimit()��");
					if((rs.getInt("limit")==0)) {//==0�����û�
						//System.out.println("rs.getInt(sql)="+rs.getInt(sql));
						return false;//isLimit=false
					}
				}
			} 
			
		}catch (SQLException e) {
				e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		
		return true;//true�������û�
	}
	
	
	///
	
	
	
	//*�û���ɫ�ж�--------�ж��Ƿ�Ϊ����Ա(����trueΪ����Ա)*/
	//����id
	public boolean isAdmin(int id) throws Exception {
		Connection connection = null;
		User user = new User(); 

		//ע�⣺sql����д"select limit ... Ӧ��д* ���������ݿ��ѯ�쳣��
		String sql = "select * from user where id='"+id+"'";
		try {
			
			/*��ȡ����*/
			connection=JdbcTools.getconnection();
			
			System.out.println("��ʼ����resultset");
		
			//����ѯ�Ľ������һ��ResultSet����
		ResultSet rs =dao.selectSqlLogin(connection,sql);
		
		System.out.println("��ѯ����ѷ�װ��resultset��");
		
		//����ResultSet
		while(rs.next()) {
			//rs.getInt("id")�ǵü����ţ���Ȼid��int�ͣ�ҲҪ�ӣ�
				if(rs.getInt("id")==id) {
					System.out.println("��λ�����û���isAdmin()��");
					if((rs.getInt("isAdmin")==1)) {//==1Ϊ����Ա
						return true;
					}
				}
			} 
			
		}catch (SQLException e) {
				e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		
		return false;//falseΪ��ͨ�û�
	}
	
	
	
	
	
}
