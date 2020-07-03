package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.bean.User;
import com.bean.UserSearchConditions;


/*�ӿ�*/
public interface UserDao {
	public ResultSet selectSqlLogin(Connection connection,String sql);//��¼��֤
	public void addUser(Connection connection, User user) 
			throws SQLException;
	public void deleteUser(Connection connection, int id) 
			throws SQLException;
	public void updateUser(Connection connection, User user) 
			throws SQLException;
	public User searchUserById(Connection connection, int id) 
			throws SQLException;
	public List<User> fetchAllUsers(Connection connection) 
			throws SQLException;
	public List<User> SearchUsersByConditions(Connection connection,UserSearchConditions conditions) 
			throws SQLException;

	public void updateLimit(Connection connection, int id) 
			throws SQLException;
	
	//��¼ʱ��ѯ���û��Ƿ�����
	public ResultSet SearchIsLimit(Connection connection, String sql) 
			throws SQLException;
}
