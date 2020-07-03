package com.bean;

import java.sql.Date;

public class CarSearchConditions {
	String brand;//����Ʒ��
	String model;//�����ͺ�
	String price;//��������
	String color;//������ɫ
	
	
	public CarSearchConditions() {
		super();
	}


	public CarSearchConditions(String brand, String model, String price, String color) {
		super();
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.color = color;
	}



	public String getBrand() {
		if(brand == null || brand.trim().equals(""))	//������û�����붫��ʱ
			return "%%";	//ƥ�����ݿ�������е�nameֵ
		else
			return "%"+brand+"%";	//�����붫��ʱ��ģ����ѯ
	}



	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getModel() {
		if(model == null || model.trim().equals(""))	//������û�����붫��ʱ
			return "%%";	//ƥ�����ݿ�������е�nameֵ
		else
			return "%"+model+"%";	//�����붫��ʱ��ģ����ѯ
	}



	public void setModel(String model) {
		this.model = model;
	}

	
	public String getPrice() {
		if(price == null || price.trim().equals(""))	//������û�����붫��ʱ
			return "%%";	//ƥ�����ݿ�������е�nameֵ
		else
			return "%"+price+"%";	//�����붫��ʱ��ģ����ѯ
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getColor() {
		if(color == null || color.trim().equals(""))	//������û�����붫��ʱ
			return "%%";	//ƥ�����ݿ�������е�nameֵ
		else
			return "%"+color+"%";	//�����붫��ʱ��ģ����ѯ
	}


	public void setColor(String color) {
		this.color = color;
	}

	
	
}
