package com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.bean.Car;
import com.bean.CarSearchConditions;
import com.bean.User;
import com.bean.UserSearchConditions;

/*接口*/
public interface CarDao {
	public void addCar(Connection connection, Car car) 
			throws SQLException;
	public void deleteCar(Connection connection, int carid) 
			throws SQLException;
	public void updateCar(Connection connection, Car car) 
			throws SQLException;
	
	//更新留言
	public void updateCarComment(Connection connection,String carcomment,int carid) 
			throws SQLException;
	
	public Car searchCarByCarId(Connection connection, int carid) //通过carid查询到车辆并显示车辆所有信息
			throws SQLException;

	//根据userid找到该车主名下所有汽车
	public List<Car> fetchAllCarsByUserId(Connection connection,int userid) 
			throws SQLException;
		
		
	public List<Car> fetchAllCars(Connection connection) 
			throws SQLException;
	
	public List<Car> fetchAllReleaseCars(Connection connection) 
			throws SQLException;
	
	
	
	public List<Car> SearchCarsByConditions(Connection connection,CarSearchConditions conditions) 
			throws SQLException;

	
	//更新发布信息
	public void updateRelease(Connection connection,int carid) 
			throws SQLException;
	
	
	//禁止该车辆留言功能
	public void updateCanMessage(Connection connection,int carid) 
			throws SQLException;
}
