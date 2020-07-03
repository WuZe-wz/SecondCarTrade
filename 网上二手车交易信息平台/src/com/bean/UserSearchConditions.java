package com.bean;

/*条件查询*/
/*
 * 查看个人用户信息（管理员权限）
 * 
 * */
public class UserSearchConditions {
	private String name;
	private String sex;
	
	public UserSearchConditions() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserSearchConditions(String name, String sex) {
		super();
		this.name = name;
		this.sex = sex;
	}


	public String getName() {
		if(name == null || name.trim().equals(""))	//方框中没有输入东西时
			return "%%";	//匹配数据库表中所有的name值
		else
			return "%"+name+"%";	//有输入东西时，模糊查询	
	}


	public void setName(String name) {
		this.name = name;
	}



	public String getSex() {
		if(sex == null || sex.trim().equals(""))	//方框中没有输入东西时
			return "%%";	//匹配数据库表中所有的name值
		else
			return "%"+sex+"%";	//有输入东西时，模糊查询
	}

	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
	
}
