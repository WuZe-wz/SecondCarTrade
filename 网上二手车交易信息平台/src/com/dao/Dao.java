package com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {	//让接口具有通用性，所以这里使用泛型 <T>
	
	public T fetch(Connection connection, 
			String sql, Object... objects) throws SQLException;//向函数外抛出异常，而不是在函数内部处理

	public List<T> fetchList(Connection connection, 
			String sql, Object... objects) throws SQLException;

	public void update(Connection connection, 
			String sql, Object... objects) throws SQLException;

	public <E> E fetchScaler(Connection connection, 
			String sql, Object... objects) throws SQLException;
	
}
