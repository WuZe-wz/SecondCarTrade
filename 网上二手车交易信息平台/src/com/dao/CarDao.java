package com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.bean.Car;
import com.bean.CarSearchConditions;
import com.bean.User;
import com.bean.UserSearchConditions;

/*�ӿ�*/
public interface CarDao {
	public void addCar(Connection connection, Car car) 
			throws SQLException;
	public void deleteCar(Connection connection, int carid) 
			throws SQLException;
	public void updateCar(Connection connection, Car car) 
			throws SQLException;
	
	//��������
	public void updateCarComment(Connection connection,String carcomment,int carid) 
			throws SQLException;
	
	public Car searchCarByCarId(Connection connection, int carid) //ͨ��carid��ѯ����������ʾ����������Ϣ
			throws SQLException;

	//����userid�ҵ��ó���������������
	public List<Car> fetchAllCarsByUserId(Connection connection,int userid) 
			throws SQLException;
		
		
	public List<Car> fetchAllCars(Connection connection) 
			throws SQLException;
	
	public List<Car> fetchAllReleaseCars(Connection connection) 
			throws SQLException;
	
	
	
	public List<Car> SearchCarsByConditions(Connection connection,CarSearchConditions conditions) 
			throws SQLException;

	
	//���·�����Ϣ
	public void updateRelease(Connection connection,int carid) 
			throws SQLException;
	
	
	//��ֹ�ó������Թ���
	public void updateCanMessage(Connection connection,int carid) 
			throws SQLException;
}
