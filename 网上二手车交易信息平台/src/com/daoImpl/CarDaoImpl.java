package com.daoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.bean.Car;
import com.bean.CarSearchConditions;
import com.bean.User;
import com.dao.CarDao;

/*�̳и��࣬����ʵ�ֵ���Car��������Car
 * 
 * �̳�DaoImpl,����ֱ��ʹ������ķ���
 * */
public class CarDaoImpl extends DaoImpl<Car> implements CarDao {

	@Override
	public void addCar(Connection connection, Car car) throws SQLException {
		
		String sql = "INSERT INTO car (userid,brand,model,price,color,carcomment) VALUES (?,?,?,?,?,?)";//����carid�ǵ������������ﲻ��д(Ĭ�ϵ�����Ҳ����д)
	
		Object[] objs = {car.getUserid(),car.getBrand(),car.getModel(),car.getPrice(),car.getColor(),car.getCarcomment() };//�ö���ȥ���л�ȡ����
		//queryRunner.update(connection, sql, objs);//����QueryRunner���еķ���
		update(connection,sql,objs);//�������ݿ�

	}

	@Override
	public void deleteCar(Connection connection, int carid) throws SQLException {
		String sql = "DELETE FROM car WHERE carid=?";
		update(connection, sql, carid);
	}

	@Override
	public void updateCar(Connection connection, Car car) throws SQLException {
		System.out.println("����CarDaoImpl��updateCar");
		String sql = "UPDATE car set brand=?,model=?,price=?,color=?,carcomment=? WHERE carid=?";
		Object[] objs = {car.getBrand(),car.getModel(),car.getPrice(),car.getColor(),car.getCarcomment(),car.getCarid()};
		update(connection, sql, objs);
		
	}
	
	//��������
	public void updateCarComment(Connection connection,String carcomment,int carid) 
			throws SQLException{
		System.out.println("����CarDaoImpl��updateCarComment");
		String sql = "UPDATE car set carcomment=? WHERE carid=?";
		update(connection, sql, carcomment,carid);
	}

	
	///���·�����Ϣ
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
	

	
//��ȡ�����û����г���
	@Override
	public List<Car> fetchAllCars(Connection connection) throws SQLException {
		String sql = "SELECT * FROM car";
		return fetchList(connection, sql);
	}
	
	
	//��ȡ�ѷ�������
	@Override
	public List<Car> fetchAllReleaseCars(Connection connection) throws SQLException {
		String sql = "SELECT * FROM car where isrelease=1";
		return fetchList(connection, sql);
	}
	

	
	//����userid�ҵ��ó���������������
	public List<Car> fetchAllCarsByUserId(Connection connection,int userid) throws SQLException {
		String sql = "SELECT userid,carid,brand,model,price,color,datePosted,isrelease,CanMessage,carcomment FROM car where userid=?";
		System.out.println("��fetchAllCarsByUserId��������fetchListִ��sql");

		return fetchList(connection, sql,userid);
	}
	
	
	@Override
	public List<Car> SearchCarsByConditions(Connection connection, CarSearchConditions conditions)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//��ֹ�ó������Թ���
	public void updateCanMessage(Connection connection,int carid) 
			throws SQLException{
		String sql = "UPDATE car set canmessage=1 WHERE carid=?";
		update(connection, sql,carid);
	}

}
