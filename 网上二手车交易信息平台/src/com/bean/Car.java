package com.bean;

import java.sql.Date;

public class Car {
	Integer userid;
	Integer carid;
	String brand;//����Ʒ��
	String model;//�����ͺ�
	String price;//��������
	String color;//������ɫ
	Date datePosted;//��������
	Integer isrelease;//����״̬��Ĭ��0��δ����
	Integer canmessage;//�Ƿ�����ԣ�Ĭ��0����������(ע�⣺��Ϊ�淶���⣬д��CanMessage����дC���쳣��javax.el.PropertyNotFoundException: ����[com.bean.Car]���Ҳ�������[CanMessage]��)
	String carcomment;//���԰壬Ĭ��Ϊ��
	Integer isfake;
	
	public Car() {
		super();
		// TODO Auto-ge nerated constructor stub
	}

	public Car(Integer userid,Integer carid, String brand, String model, String price, String color, Date datePosted, Integer isrelease,
			Integer canmessage, String carcomment,Integer isfake) {
		super();
		this.userid=userid;
		this.carid = carid;
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.color = color;
		this.datePosted = datePosted;
		this.isrelease = isrelease;
		this.canmessage = canmessage;
		this.carcomment = carcomment;
		this.isfake=isfake;
	}

	
	
	public Integer getIsfake() {
		return isfake;
	}

	public void setIsfake(Integer isfake) {
		this.isfake = isfake;
	}

	/**
	 * @return the userid
	 */
	public Integer getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	
	/**
	 * @return the carId
	 */
	public int getCarid() {
		return carid;
	}



	/**
	 * @param carId the carId to set
	 */
	public void setCarid(int carid) {
		this.carid = carid;
	}



	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}



	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}



	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}



	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}



	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}



	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}



	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}



	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}



	/**
	 * @return the datePosted
	 */
	public Date getDatePosted() {
		return datePosted;
	}



	/**
	 * @param datePosted the datePosted to set
	 */
	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}



	/**
	 * @return the isrelease
	 */
	public int getIsrelease() {
		return isrelease;
	}



	/**
	 * @param isrelease the isrelease to set
	 */
	public void setIsrelease(int isrelease) {
		this.isrelease = isrelease;
	}





	/**
	 * @return the canmessage
	 */
	public Integer getCanmessage() {
		return canmessage;
	}

	/**
	 * @param canmessage the canmessage to set
	 */
	public void setCanmessage(Integer canmessage) {
		this.canmessage = canmessage;
	}

	
	
	/**
	 * @return the carcomment
	 */
	public String getCarcomment() {
		return carcomment;
	}



	/**
	 * @param carcomment the carcomment to set
	 */
	public void setCarcomment(String carcomment) {
		this.carcomment = carcomment;
	}





	


}
