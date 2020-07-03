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

/*继承父类，这里实现的是User表，类型是User
 * 
 * 继承DaoImpl,可以直接使用里面的方法
 * */
public class UserDaoImpl extends DaoImpl <User> implements UserDao {
	static ResultSet rs = null;
	static PreparedStatement ps =null;

		/*因为QueryRunner是线程安全的，所以可以写在所有方法的外面*/
		QueryRunner queryRunner=new QueryRunner();//QueryRunner类
		
		
		//登录验证（name+pwd）
		public ResultSet selectSqlLogin(Connection connection,String sql){
			try {
				ps=connection.prepareStatement(sql);
				rs=ps.executeQuery(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("数据库查询异常");
				e.printStackTrace();
			}
			return rs;//返回查询的数据集
	}
		
		
		public void addUser(Connection connection, User user) throws SQLException {
			String sql = "INSERT INTO user (name,pwd, sex) VALUES ( ?, ?, ?)";//属性id是递增，所以这里不用写
			Object[] objs = {user.getName(), user.getPwd(), user.getSex()};//用对象去类中获取数据
			//queryRunner.update(connection, sql, objs);//调用QueryRunner类中的方法
			update(connection,sql,objs);//更新数据库
		}

		public void deleteUser(Connection connection, int id) throws SQLException {
			String sql = "DELETE FROM user WHERE id=?";
			update(connection, sql, id);
		}

		public void updateUser(Connection connection, User user) throws SQLException {
			System.out.println("进入UserDaoImpl的updateUser");
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
		

		
		
		//模糊查询
		public List<User> SearchUsersByConditions(Connection connection, UserSearchConditions conditions) throws SQLException {
			String sql = "SELECT name,sex FROM user " //这里换行时要加个空格，否则会与下面的where识别成一个单词
		+"WHERE name like ? AND sex like ?";
			Object[] objs= {conditions.getName(),conditions.getSex()};
			return fetchList(connection, sql,objs);
		}
	

		
		//受限用户管理（管理员权限）
		public void updateLimit(Connection connection, int id) 
				throws SQLException{
			System.out.println("进入updateLimit，下面创建sql");
			
			/*注意，limit在mysql是关键字，在这里要加符号圈起来*/
			String sql = "UPDATE user set `limit`=1 WHERE id=?";
			
			System.out.println("sql创建完成，下面开始执行sql");
//			ps=connection.prepareStatement(sql);
//			ps.executeQuery(sql);
			//queryRunner.update(connection, sql);
			update(connection, sql, id);
			System.out.println("limit更新成功");
		}
	
		
		//登录时查询该用户是否受限
		public ResultSet SearchIsLimit(Connection connection, String sql) 
				throws SQLException{
			try {
			ps=connection.prepareStatement(sql);
			rs=ps.executeQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("数据库查询异常");
			e.printStackTrace();
		}
			return rs;//返回查询的数据集
		}
		

	
}
