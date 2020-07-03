package com.bean;

import java.sql.Date;

public class CarSearchConditions {
	String brand;//汽车品牌
	String model;//汽车型号
	String price;//汽车报价
	String color;//汽车颜色
	
	
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
		if(brand == null || brand.trim().equals(""))	//方框中没有输入东西时
			return "%%";	//匹配数据库表中所有的name值
		else
			return "%"+brand+"%";	//有输入东西时，模糊查询
	}



	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getModel() {
		if(model == null || model.trim().equals(""))	//方框中没有输入东西时
			return "%%";	//匹配数据库表中所有的name值
		else
			return "%"+model+"%";	//有输入东西时，模糊查询
	}



	public void setModel(String model) {
		this.model = model;
	}

	
	public String getPrice() {
		if(price == null || price.trim().equals(""))	//方框中没有输入东西时
			return "%%";	//匹配数据库表中所有的name值
		else
			return "%"+price+"%";	//有输入东西时，模糊查询
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getColor() {
		if(color == null || color.trim().equals(""))	//方框中没有输入东西时
			return "%%";	//匹配数据库表中所有的name值
		else
			return "%"+color+"%";	//有输入东西时，模糊查询
	}


	public void setColor(String color) {
		this.color = color;
	}

	
	
}
