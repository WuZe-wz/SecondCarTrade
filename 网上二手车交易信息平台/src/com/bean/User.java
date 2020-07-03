package com.bean;

public class User {
	private Integer id;//不要写成int，不然在RegisterUserServlet，注册的时候无法传入自增的id为null
	private String name;
	private String pwd;
	private String sex;
	private Integer limit;//受限情况
	private Integer isAdmin;//是否为管理员
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(Integer id, String name, String pwd, String sex, Integer limit,Integer isAdmin) {
		super();
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.sex = sex;
		this.limit=limit;
		this.isAdmin=isAdmin;
	}

	
	/**
	 * @return the isAdmin
	 */
	public Integer getIsAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getLimit() {
		return limit;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setLimit(int limit) {
		this.limit=limit;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}
	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
}
