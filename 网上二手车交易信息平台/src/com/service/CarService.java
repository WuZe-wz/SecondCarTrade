package com.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bean.Car;
import com.dao.CarDao;
import com.dao.UserDao;
import com.daoImpl.CarDaoImpl;
import com.daoImpl.UserDaoImpl;
import com.utils.JdbcTools;

public class CarService {

	private UserDao userdao;
	private CarDao cardao;
	
	/*获取dao（/初始化）*/
	public CarService() {
		this.cardao=new CarDaoImpl();
		this.userdao=new UserDaoImpl();
	}
	
	
	/*将车辆数据添加到数据库*/
	public  boolean AddCarServlet(Car car) throws Exception {
		boolean result=false;//因为IdCard具有唯一性约束，所以自定义输入数据时可能出错，用result指示
		Connection connection =null;
		try {
			connection=JdbcTools.getconnection();
			
			cardao.addCar(connection,car);//实现
			
			result=true;//当添加成功时，true
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return result;
	}
	
	/*修改 之 根据carid定位到车辆*/
	public Car getCarOfCarid(int carid){
		Car car=new Car();
		Connection connection =null;
		try {
			connection=JdbcTools.getconnection();
			car=cardao.searchCarByCarId(connection, carid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return car;//返回car对象
	}
	
	
	/*get 根据userid定位到该车主的所有车辆*/
	public List<Car>getCarsListOfUserid(int userid){
		Connection connection =null;
		List<Car> cars=new ArrayList<Car>();
		try {
			connection=JdbcTools.getconnection();
			cars=cardao.fetchAllCarsByUserId(connection, userid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return cars;
	}
	
	
	
	//获取到所有车主所有车辆
	public List<Car> getAllCarsList(){
		Connection connection =null;
		List<Car> cars=new ArrayList<Car>();
		try {
			System.out.println("进入 getAllCarsList");
			connection=JdbcTools.getconnection();
			cars=cardao.fetchAllCars(connection);
		} catch (Exception e) {
			//System.out.println("获取所有车主所有车辆，获取失败");
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return cars;//返回car对象
	}
	
	
	
	
	//获取已发布车辆
	public List<Car> getAllReleaseCarsList(){
		Connection connection =null;
		List<Car> cars=new ArrayList<Car>();
		try {
			System.out.println("进入 getAllCarsList");
			connection=JdbcTools.getconnection();
			cars=cardao.fetchAllReleaseCars(connection);
		} catch (Exception e) {
			//System.out.println("获取所有车主所有车辆，获取失败");
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return cars;//返回car对象
	}
	
	
	
	
	/*修改 之 实际的对数据库更新*/
	public void UpdateCarSelfServlet(Car car){
		Connection connection =null;
		try {
			System.out.println("进入Service的UpdateCarSelfServlet()");
			connection=JdbcTools.getconnection();
			cardao.updateCar(connection, car);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		
	}
	
	
	
	//删除该用户的某一辆二手车（根据carid）
	public void DeleteCarServlet(int carid) throws Exception {
		Connection connection =null;
		try {
			connection=JdbcTools.getconnection();
			cardao.deleteCar(connection, carid);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
	}
	
	
	//更新 留言 到数据库
	public void UpdateCarComment(String carcomment,int carid){
		Connection connection =null;
		try {
			System.out.println("进入Service的UpdateCarComment()");
			connection=JdbcTools.getconnection();
			cardao.updateCarComment(connection,carcomment,carid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		
	}
	
	
	//发布车辆
	public void ReleaseCarServlet(int carid){
		Connection connection =null;
		try {
			System.out.println("进入Service的UpdateCarComment()");
			connection=JdbcTools.getconnection();
			cardao.updateRelease(connection,carid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		
	}
	
	//禁止该车辆留言功能（根据carid）
	public void RespondBan(int carid){
		Car car=new Car();
		Connection connection =null;
		try {
			connection=JdbcTools.getconnection();
			cardao.updateCanMessage(connection,carid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
	}
	
	
	
	
}
