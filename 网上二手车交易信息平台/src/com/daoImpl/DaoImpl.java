package com.daoImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.dao.Dao;

public class DaoImpl<T> implements Dao<T> {
	private QueryRunner queryRunner;//因为QueryRunner是线程安全的，所以可以写在所有方法的外面
	private Class<T> type;//成员变量
	
	/*利用“反射”机制，获取到 类的类型
	 * 下面这个函数本质是站在 DAOImpl 类的一个子类的对象 的角度上考虑
	 * */
	@SuppressWarnings("rawtypes")
	private Class getSuperClassGenricType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}
	
	/*
	 * 构造器
	 * 1.创建QueryRunner对象
	 * 2.获取类的类型type
	 * */
	@SuppressWarnings("unchecked")
	public DaoImpl() {
		queryRunner = new QueryRunner();
		type = getSuperClassGenricType(getClass(), 0);
	}
	
	
	/*查询（获取）一行数据
	 * 注意：是向函数外抛出异常，而不是在函数内部处理
	 * */
	@Override
	public T fetch(Connection connection, String sql, Object... objects) throws SQLException {
		
		/*BeanHandler需要传入一个“类.class”参数，但是这里并不知道这个类是什么类型（/什么类）
		 * 这里利用到“反射”机制，实现了用于寻找类型type的方法
		 * */
		BeanHandler<T> rsh = new BeanHandler<T>(type);//返回一行数据，用到BeanHandler
		
		return queryRunner.query(connection, sql, rsh, objects);//直接返回这个对象
	}

	@Override
	public List<T> fetchList(Connection connection, String sql, Object... objects) throws SQLException {
		
		/*同上*/
		BeanListHandler<T> rsh = new BeanListHandler<T>(type);//返回一行数据，用到BeanHandler
		
		return queryRunner.query(connection, sql, rsh, objects);//直接返回这个对象
	}

	@Override
	public void update(Connection connection, String sql, Object... objects) throws SQLException {
		queryRunner.update(connection, sql, objects);	
	}

	@Override
	public <E> E fetchScaler(Connection connection, String sql, Object... objects) throws SQLException {
		ScalarHandler <E> rsh =new ScalarHandler<E>();//泛型
		return (E) queryRunner.query(connection, sql, rsh, objects);//强制转换为(E)
	}

}
