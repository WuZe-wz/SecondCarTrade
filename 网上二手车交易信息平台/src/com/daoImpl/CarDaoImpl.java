package com.daoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.bean.Car;
import com.bean.CarSearchConditions;
import com.bean.User;
import com.dao.CarDao;

/*继承父类，这里实现的是Car表，类型是Car
 * 
 * 继承DaoImpl,可以直接使用里面的方法
 * */
public class CarDaoImpl extends DaoImpl<Car> implements CarDao {

	@Override
	public void addCar(Connection connection, Car car) throws SQLException {
		
		String sql = "INSERT INTO car (userid,brand,model,price,color,carcomment) VALUES (?,?,?,?,?,?)";//属性carid是递增，所以这里不用写(默认的属性也不用写)
	
		Object[] objs = {car.getUserid(),car.getBrand(),car.getModel(),car.getPrice(),car.getColor(),car.getCarcomment() };//用对象去类中获取数据
		//queryRunner.update(connection, sql, objs);//调用QueryRunner类中的方法
		update(connection,sql,objs);//更新数据库

	}

	@Override
	public void deleteCar(Connection connection, int carid) throws SQLException {
		String sql = "DELETE FROM car WHERE carid=?";
		update(connection, sql, carid);
	}

	@Override
	public void updateCar(Connection connection, Car car) throws SQLException {
		System.out.println("进入CarDaoImpl的updateCar");
		String sql = "UPDATE car set brand=?,model=?,price=?,color=?,carcomment=? WHERE carid=?";
		Object[] objs = {car.getBrand(),car.getModel(),car.getPrice(),car.getColor(),car.getCarcomment(),car.getCarid()};
		update(connection, sql, objs);
		
	}
	
	//更新留言
	public void updateCarComment(Connection connection,String carcomment,int carid) 
			throws SQLException{
		System.out.println("进入CarDaoImpl的updateCarComment");
		String sql = "UPDATE car set carcomment=? WHERE carid=?";
		update(connection, sql, carcomment,carid);
	}

	
	///更新发布信息
	public void updateRelease(Connection connection,int carid) 
			throws SQLException{
		String sql = "UPDATE car set isrelease=1 WHERE carid=?";
		update(connection, sql,carid);
	}
	
	
	@Override
	public Car searchCarByCarId(Connection connection, int carid) throws SQLException {
		String sql = "SELECT * FROM car WHERE carid=?";
		return fetch(connection, sql, carid);
	}
	

	
//获取所有用户所有车辆
	@Override
	public List<Car> fetchAllCars(Connection connection) throws SQLException {
		String sql = "SELECT * FROM car";
		return fetchList(connection, sql);
	}
	
	
	//获取已发布车辆
	@Override
	public List<Car> fetchAllReleaseCars(Connection connection) throws SQLException {
		String sql = "SELECT * FROM car where isrelease=1";
		return fetchList(connection, sql);
	}
	

	
	//根据userid找到该车主名下所有汽车
	public List<Car> fetchAllCarsByUserId(Connection connection,int userid) throws SQLException {
		String sql = "SELECT userid,carid,brand,model,price,color,datePosted,isrelease,CanMessage,carcomment FROM car where userid=?";
		System.out.println("在fetchAllCarsByUserId即将调用fetchList执行sql");

		return fetchList(connection, sql,userid);
	}
	
	
	@Override
	public List<Car> SearchCarsByConditions(Connection connection, CarSearchConditions conditions)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//禁止该车辆留言功能
	public void updateCanMessage(Connection connection,int carid) 
			throws SQLException{
		String sql = "UPDATE car set canmessage=1 WHERE carid=?";
		update(connection, sql,carid);
	}

}
