package com.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.bean.User;
import com.bean.UserSearchConditions;
import com.dao.UserDao;

/*�̳и��࣬����ʵ�ֵ���User��������User
 * 
 * �̳�DaoImpl,����ֱ��ʹ������ķ���
 * */
public class UserDaoImpl extends DaoImpl <User> implements UserDao {
	static ResultSet rs = null;
	static PreparedStatement ps =null;

		/*��ΪQueryRunner���̰߳�ȫ�ģ����Կ���д�����з���������*/
		QueryRunner queryRunner=new QueryRunner();//QueryRunner��
		
		
		//��¼��֤��name+pwd��
		public ResultSet selectSqlLogin(Connection connection,String sql){
			try {
				ps=connection.prepareStatement(sql);
				rs=ps.executeQuery(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("���ݿ��ѯ�쳣");
				e.printStackTrace();
			}
			return rs;//���ز�ѯ�����ݼ�
	}
		
		
		public void addUser(Connection connection, User user) throws SQLException {
			String sql = "INSERT INTO user (name,pwd, sex) VALUES ( ?, ?, ?)";//����id�ǵ������������ﲻ��д
			Object[] objs = {user.getName(), user.getPwd(), user.getSex()};//�ö���ȥ���л�ȡ����
			//queryRunner.update(connection, sql, objs);//����QueryRunner���еķ���
			update(connection,sql,objs);//�������ݿ�
		}

		public void deleteUser(Connection connection, int id) throws SQLException {
			String sql = "DELETE FROM user WHERE id=?";
			update(connection, sql, id);
		}

		public void updateUser(Connection connection, User user) throws SQLException {
			System.out.println("����UserDaoImpl��updateUser");
			String sql = "UPDATE user set name=?, pwd=?, sex=? WHERE id=?";
			Object[] objs = {user.getName(), user.getPwd(), user.getSex(), user.getId()};
			update(connection, sql, objs);
		}

		public User searchUserById(Connection connection, int id) throws SQLException {
			String sql = "SELECT id,name,pwd,sex,isAdmin FROM user WHERE id=?";
			return fetch(connection, sql, id);
		}

		public List<User> fetchAllUsers(Connection connection) throws SQLException {
			String sql = "SELECT * FROM user";
			return fetchList(connection, sql);
		}
		

		
		
		//ģ����ѯ
		public List<User> SearchUsersByConditions(Connection connection, UserSearchConditions conditions) throws SQLException {
			String sql = "SELECT name,sex FROM user " //���ﻻ��ʱҪ�Ӹ��ո񣬷�����������whereʶ���һ������
		+"WHERE name like ? AND sex like ?";
			Object[] objs= {conditions.getName(),conditions.getSex()};
			return fetchList(connection, sql,objs);
		}
	

		
		//�����û���������ԱȨ�ޣ�
		public void updateLimit(Connection connection, int id) 
				throws SQLException{
			System.out.println("����updateLimit�����洴��sql");
			
			/*ע�⣬limit��mysql�ǹؼ��֣�������Ҫ�ӷ���Ȧ����*/
			String sql = "UPDATE user set `limit`=1 WHERE id=?";
			
			System.out.println("sql������ɣ����濪ʼִ��sql");
//			ps=connection.prepareStatement(sql);
//			ps.executeQuery(sql);
			//queryRunner.update(connection, sql);
			update(connection, sql, id);
			System.out.println("limit���³ɹ�");
		}
	
		
		//��¼ʱ��ѯ���û��Ƿ�����
		public ResultSet SearchIsLimit(Connection connection, String sql) 
				throws SQLException{
			try {
			ps=connection.prepareStatement(sql);
			rs=ps.executeQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("���ݿ��ѯ�쳣");
			e.printStackTrace();
		}
			return rs;//���ز�ѯ�����ݼ�
		}
		

	
}
