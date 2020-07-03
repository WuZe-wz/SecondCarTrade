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
	
	/*��ȡdao��/��ʼ����*/
	public CarService() {
		this.cardao=new CarDaoImpl();
		this.userdao=new UserDaoImpl();
	}
	
	
	/*������������ӵ����ݿ�*/
	public  boolean AddCarServlet(Car car) throws Exception {
		boolean result=false;//��ΪIdCard����Ψһ��Լ���������Զ�����������ʱ���ܳ�����resultָʾ
		Connection connection =null;
		try {
			connection=JdbcTools.getconnection();
			
			cardao.addCar(connection,car);//ʵ��
			
			result=true;//����ӳɹ�ʱ��true
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return result;
	}
	
	/*�޸� ֮ ����carid��λ������*/
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
		return car;//����car����
	}
	
	
	/*get ����userid��λ���ó��������г���*/
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
	
	
	
	//��ȡ�����г������г���
	public List<Car> getAllCarsList(){
		Connection connection =null;
		List<Car> cars=new ArrayList<Car>();
		try {
			System.out.println("���� getAllCarsList");
			connection=JdbcTools.getconnection();
			cars=cardao.fetchAllCars(connection);
		} catch (Exception e) {
			//System.out.println("��ȡ���г������г�������ȡʧ��");
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return cars;//����car����
	}
	
	
	
	
	//��ȡ�ѷ�������
	public List<Car> getAllReleaseCarsList(){
		Connection connection =null;
		List<Car> cars=new ArrayList<Car>();
		try {
			System.out.println("���� getAllCarsList");
			connection=JdbcTools.getconnection();
			cars=cardao.fetchAllReleaseCars(connection);
		} catch (Exception e) {
			//System.out.println("��ȡ���г������г�������ȡʧ��");
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		return cars;//����car����
	}
	
	
	
	
	/*�޸� ֮ ʵ�ʵĶ����ݿ����*/
	public void UpdateCarSelfServlet(Car car){
		Connection connection =null;
		try {
			System.out.println("����Service��UpdateCarSelfServlet()");
			connection=JdbcTools.getconnection();
			cardao.updateCar(connection, car);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		
	}
	
	
	
	//ɾ�����û���ĳһ�����ֳ�������carid��
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
	
	
	//���� ���� �����ݿ�
	public void UpdateCarComment(String carcomment,int carid){
		Connection connection =null;
		try {
			System.out.println("����Service��UpdateCarComment()");
			connection=JdbcTools.getconnection();
			cardao.updateCarComment(connection,carcomment,carid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		
	}
	
	
	//��������
	public void ReleaseCarServlet(int carid){
		Connection connection =null;
		try {
			System.out.println("����Service��UpdateCarComment()");
			connection=JdbcTools.getconnection();
			cardao.updateRelease(connection,carid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTools.releaseResource(null, connection);
		}
		
	}
	
	//��ֹ�ó������Թ��ܣ�����carid��
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
