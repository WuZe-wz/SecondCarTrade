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
	
	/*获取dao（/初始化）*/
	public UserService() {
		this.dao=new UserDaoImpl();
	}
	
	
	
	//登录login（name+pwd）
		public boolean login(String name, String pwd) throws Exception {
			boolean flag=false;
			Connection connection = null;
			User user = new User(); 
			String sql="select * from user where name='"+name+"' and pwd='"+pwd+"'";
			try {
				
				/*获取连接*/
				connection=JdbcTools.getconnection();
				
				System.out.println("开始调用resultset");
			
				//将查询的结果放在一个ResultSet里面
			ResultSet rs =dao.selectSqlLogin(connection,sql);
			
			System.out.println("查询结果已封装到resultset中");
			
			//遍历ResultSet
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
			
			return flag;//true即登录成功
		}
		
		
		//根据name和pwd查找id
		public int LoginFindId(String name, String pwd) throws Exception {
			Connection connection = null;
			User user = new User(); 
			//不能写select id ...，因为下面要用到name和pwd（异常：Column 'name' not found）
			String sql="select * from user where name='"+name+"' and pwd='"+pwd+"'";
			try {
				
				/*获取连接*/
				connection=JdbcTools.getconnection();
				
				System.out.println("开始调用resultset");
			
				//将查询的结果放在一个ResultSet里面
			ResultSet rs =dao.selectSqlLogin(connection,sql);
			System.out.println("dao.selectSqlLogin执行完毕");
			System.out.println("查询结果已封装到resultset中");
			
			//遍历ResultSet
			while(rs.next()) {
				System.out.println("进入while(rs.next())");
					if(rs.getString("name").equals(name) && rs.getString("pwd").equals(pwd)) {
						System.out.println("匹配到用户");
						return rs.getInt("id");
					}
					
				} 
				
			}catch (SQLException e) {
					e.printStackTrace();
			}finally {
				JdbcTools.releaseResource(null, connection);
			}		
			return -1;//true即登录成功
		}
		
	
	/*模糊查询*/
	public java.util.List<User> SearchUsersByConditions(UserSearchConditions conditions){
		Connection connection = null;
		java.util.List<User> users=new java.util.ArrayList<User>();
		try {
			/*获取连接*/
			connection=JdbcTools.getconnection();
			/*通过dao将通用查询结果返回给名为user的List保存*/
			users=dao.SearchUsersByConditions(connection,conditions);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return users;
	}
	
	
	/*修改 之 实际的对数据库更新*/
	public void UpdateUserServlet(User user){
		Connection connection =null;
		try {
			System.out.println("进入Service的UpdateUserServlet()");
			connection=JdbcTools.getconnection();
			dao.updateUser(connection, user);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		
	}
	

	
	
	/*修改 之 根据id查询user对象*/
	public User getUser(int id){
		User user=new User();//这个语句不能在try里面，报错？？？
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
	
	
	
	/*将数据添加到数据库*/
	public boolean RegisterUserServlet(User user) throws Exception {
		boolean result=false;//因为IdCard具有唯一性约束，所以自定义输入数据时可能出错，用result指示
		Connection connection =null;
		try {
			connection=JdbcTools.getconnection();
			dao.addUser(connection,user);
			result=true;//当添加成功时，true
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return result;
	}
	
	
	
	//删除
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
			/*获取连接*/
			connection=JdbcTools.getconnection();
			/*通过dao将通用查询结果返回给名为users的List保存*/
			users =dao.fetchAllUsers(connection);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return users;
	}
	
	
	
	
	
	
	
	/*受限用户管理--------更新表信息，将limit属性置为1*/
	//传入id
	public boolean LimitUser(int id) {
		Connection connection =null;
		try {
			System.out.println("进入Service的LimitUser");
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
	
	
	//根据id判断该用户是否受限(在登录的时候判断)
	//
	public boolean isLimit(int id) throws Exception {
		Connection connection = null;
		User user = new User(); 
		
		//注意：sql不能写"select limit ... 应该写* （否则：数据库查询异常）
		String sql = "select * from user where id='"+id+"'";
		try {
			
			/*获取连接*/
			connection=JdbcTools.getconnection();
			
			System.out.println("开始调用resultset");
		
			//将查询的结果放在一个ResultSet里面
		ResultSet rs =dao.selectSqlLogin(connection,sql);
		
		System.out.println("查询结果已封装到resultset中");
		
		//遍历ResultSet
		while(rs.next()) {
			//rs.getInt("id")记得加引号（虽然id是int型，也要加）
				if(rs.getInt("id")==id) {
					System.out.println("定位到该用户（isLimit()）");
					if((rs.getInt("limit")==0)) {//==0正常用户
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
		
		return true;//true即受限用户
	}
	
	
	///
	
	
	
	//*用户角色判断--------判断是否为管理员(返回true为管理员)*/
	//传入id
	public boolean isAdmin(int id) throws Exception {
		Connection connection = null;
		User user = new User(); 

		//注意：sql不能写"select limit ... 应该写* （否则：数据库查询异常）
		String sql = "select * from user where id='"+id+"'";
		try {
			
			/*获取连接*/
			connection=JdbcTools.getconnection();
			
			System.out.println("开始调用resultset");
		
			//将查询的结果放在一个ResultSet里面
		ResultSet rs =dao.selectSqlLogin(connection,sql);
		
		System.out.println("查询结果已封装到resultset中");
		
		//遍历ResultSet
		while(rs.next()) {
			//rs.getInt("id")记得加引号（虽然id是int型，也要加）
				if(rs.getInt("id")==id) {
					System.out.println("定位到该用户（isAdmin()）");
					if((rs.getInt("isAdmin")==1)) {//==1为管理员
						return true;
					}
				}
			} 
			
		}catch (SQLException e) {
				e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		
		return false;//false为普通用户
	}
	
	
	
	
	
}
