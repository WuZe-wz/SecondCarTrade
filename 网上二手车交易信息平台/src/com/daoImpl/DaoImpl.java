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
	private QueryRunner queryRunner;//��ΪQueryRunner���̰߳�ȫ�ģ����Կ���д�����з���������
	private Class<T> type;//��Ա����
	
	/*���á����䡱���ƣ���ȡ�� �������
	 * �����������������վ�� DAOImpl ���һ������Ķ��� �ĽǶ��Ͽ���
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
	 * ������
	 * 1.����QueryRunner����
	 * 2.��ȡ�������type
	 * */
	@SuppressWarnings("unchecked")
	public DaoImpl() {
		queryRunner = new QueryRunner();
		type = getSuperClassGenricType(getClass(), 0);
	}
	
	
	/*��ѯ����ȡ��һ������
	 * ע�⣺���������׳��쳣���������ں����ڲ�����
	 * */
	@Override
	public T fetch(Connection connection, String sql, Object... objects) throws SQLException {
		
		/*BeanHandler��Ҫ����һ������.class���������������ﲢ��֪���������ʲô���ͣ�/ʲô�ࣩ
		 * �������õ������䡱���ƣ�ʵ��������Ѱ������type�ķ���
		 * */
		BeanHandler<T> rsh = new BeanHandler<T>(type);//����һ�����ݣ��õ�BeanHandler
		
		return queryRunner.query(connection, sql, rsh, objects);//ֱ�ӷ����������
	}

	@Override
	public List<T> fetchList(Connection connection, String sql, Object... objects) throws SQLException {
		
		/*ͬ��*/
		BeanListHandler<T> rsh = new BeanListHandler<T>(type);//����һ�����ݣ��õ�BeanHandler
		
		return queryRunner.query(connection, sql, rsh, objects);//ֱ�ӷ����������
	}

	@Override
	public void update(Connection connection, String sql, Object... objects) throws SQLException {
		queryRunner.update(connection, sql, objects);	
	}

	@Override
	public <E> E fetchScaler(Connection connection, String sql, Object... objects) throws SQLException {
		ScalarHandler <E> rsh =new ScalarHandler<E>();//����
		return (E) queryRunner.query(connection, sql, rsh, objects);//ǿ��ת��Ϊ(E)
	}

}
