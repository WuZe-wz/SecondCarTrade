package com.bean;

/*������ѯ*/
/*
 * �鿴�����û���Ϣ������ԱȨ�ޣ�
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
		if(name == null || name.trim().equals(""))	//������û�����붫��ʱ
			return "%%";	//ƥ�����ݿ�������е�nameֵ
		else
			return "%"+name+"%";	//�����붫��ʱ��ģ����ѯ	
	}


	public void setName(String name) {
		this.name = name;
	}



	public String getSex() {
		if(sex == null || sex.trim().equals(""))	//������û�����붫��ʱ
			return "%%";	//ƥ�����ݿ�������е�nameֵ
		else
			return "%"+sex+"%";	//�����붫��ʱ��ģ����ѯ
	}

	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
	
}
