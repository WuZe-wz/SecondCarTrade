package com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {	//�ýӿھ���ͨ���ԣ���������ʹ�÷��� <T>
	
	public T fetch(Connection connection, 
			String sql, Object... objects) throws SQLException;//�������׳��쳣���������ں����ڲ�����

	public List<T> fetchList(Connection connection, 
			String sql, Object... objects) throws SQLException;

	public void update(Connection connection, 
			String sql, Object... objects) throws SQLException;

	public <E> E fetchScaler(Connection connection, 
			String sql, Object... objects) throws SQLException;
	
}
