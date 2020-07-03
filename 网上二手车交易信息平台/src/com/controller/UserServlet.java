package com.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.Car;
import com.bean.User;
import com.bean.UserSearchConditions;
import com.dao.UserDao;
import com.daoImpl.UserDaoImpl;
import com.mysql.cj.Query;
import com.service.CarService;
import com.service.UserService;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("*.do")/*ʹ��ͨ�����Ȼ����doGet()�������ж�ʹ���ĸ��������ĳ�����*/
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService service;
	private CarService serviceOfCar;

    public UserServlet() {
        super();
        this.service=new UserService();//ͨ�û�
        this.serviceOfCar=new CarService();//���������˹���Ա�鿴���г�����servlet��������������ûд����һֱ����
    }

    //��doget����������д����
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath=request.getServletPath();
		System.out.println(servletPath);
		
		/*�ӵڶ����ַ���ʼ����Ϊ��ͷ�С� / ����;��β��������end��λ��ֵ*/
		String methodName=servletPath.substring(1, servletPath.length()-3);
		System.out.println(methodName);
		
		/*�������*/
		try {
			Method method=getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	
	
	/*
	 * User�����servlet����
	 * ע�⣺������Ҫ��jspҳ���      "/���� "   �� "����" Ҫ��Ӧ����Сд��
	 */
	
	
	//��¼
	public void LoginUserServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//�õ�jspҳ�洫�����Ĳ���
				//����Ϊ��ͬ��request���У����Կ�����request��ȡ�����ݣ�
				String name=request.getParameter("name");
				String pwd=request.getParameter("pwd");
				int userType=Integer.parseInt(request.getParameter("userType"));// ��Ա0 /����Ա1
				
				
				System.out.println("userType="+userType);
				/*����id����ֱ��request.getParameter��ȡ����Ϊ���û���¼��ʱ��û����id������jspҳ��û�д�"id"��Ϣ�����������ȡ������*/
				int id=service.LoginFindId(name, pwd);//����name��pwd����id
				UserDao ud =new UserDaoImpl();//�����ӿڵ�ʵ���࣬ud������Բ���UserDaoImpl������
				System.out.println("id="+id);
				System.out.println("����LoginUserServlet����δ����service.login����");
				
				//service.LimitUser(id)ִ�гɹ�������true������û���Ϊ����
				//service.isLimit(id)����true������û���Ϊ����
				//userType==0  ��Ա
				if(service.login(name, pwd) && (!service.isLimit(id)) && (userType==0) && !service.isAdmin(id)) {//�����¼ͨ��,���Ҳ�����
					
					System.out.println("����Ա����¼�ɹ�");
					String message="���˻�Ա ��¼�ɹ�";
					
					//ע�������û���¼�ɹ���������id����request���У���jspҳ����Ի�ȡ��Ȼ��ͨ��jsp�����ٰѸ�id��Ϣת������servlet��(���������SearchUserSelfById() )
					request.setAttribute("id", id);//����id��request����ÿ�������û���¼��ֱ���ʾ�����˵���Ϣ��ͨ�����idȥ��ȡ��
					request.setAttribute("message", message);
					//ת���������û���ҳUserHomePage.jsp
					request.getRequestDispatcher("/WEB-INF/user/UserHomePage.jsp").forward(request, response);//ת�����ɹ�ҳ��
				}
				//userType==1  ����Ա
				else if(service.login(name, pwd) && (!service.isLimit(id)) && (userType==1) && service.isAdmin(id)) {//�����¼ͨ��,���Ҳ�����
					
					System.out.println("������Ա����¼�ɹ�");
					String message="����Ա��¼�ɹ�";
					request.setAttribute("message", message);
					request.getRequestDispatcher("/WEB-INF/admin/AdminHomePage.jsp").forward(request, response);//ת�����ɹ�ҳ��
				}
				//�����û��������û���
				else if(service.login(name, pwd) && service.isLimit(id) && (userType==0) && !service.isAdmin(id)){//��¼�ɹ���������
					System.out.println("��¼�ɹ�,�����Ļ�Ա�ʸ����ޣ��޷�����������Ϣ");
					String message="��¼�ɹ�,�����Ļ�Ա�ʸ����ޣ��޷�����������Ϣ";
					request.setAttribute("ERRMESSAGE", message);
					request.getRequestDispatcher("/WEB-INF/admin/LimitedUser.jsp").forward(request, response);//ת�����ɹ�ҳ��
				
				}
				//����
				else {//��¼��ͨ�����ض���ʧ�ܽ���(respnse ��Ӧ)
					System.out.println("��¼ʧ��,��ȷ���˺������Լ�����Ƿ���ȷ");
					String message="��¼ʧ��,��ȷ���˺������Լ�����Ƿ���ȷ";
					request.setAttribute("message", message);
					//response.sendRedirect("/WEB-INF/view/Fail.jsp"); �ض�����һ��request����
					request.getRequestDispatcher("/WEB-INF/view/LoginFail.jsp").forward(request, response);//ת����ʧ��ҳ��
				}
	}
	
	/*��¼ʧ�ܻص���¼����(�����servlet����)*/
	//login�ڸ�Ŀ¼
	public void LoginUserFailServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
		
	
	//�˳���¼���ص���¼ҳ��
	public void returnLoginPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
	
	//����Ա���ص�����Ա�û���ҳ��
	public void returnAdminHomePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String message="����Ա��ҳ";
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/admin/AdminHomePage.jsp").forward(request, response);
	}
	
	
	/*����ԱȨ�ޣ������Ҫ��ʾ����ģ�--------��ʱ��������һ�����������ڸ����û�ͨ��ģ����ѯ��ĳ��������������ʾ���ֳ���Ϣ������Ȩ�ޣ�*/
	/*ģ����ѯ�����û���name��sex��*/
	public void SearchUserServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name=request.getParameter("name");//getParameter�������Ҳ�Ǹ�jspҳ���б�ܺ��������һ�£���Сд��
		String sex=request.getParameter("sex");
		
		UserSearchConditions conditions=new UserSearchConditions(name, sex);
		List<User> users = service.SearchUsersByConditions(conditions);//UserService������
				
		request.setAttribute("users", users);
		request.getRequestDispatcher("/WEB-INF/admin/ShowSearchUser.jsp").forward(request, response);
		
	}
	
	
	//����Ա����
	/*�����û���¼�󣬸�������Ψһid�Ŷ�λ���������ݿ�������У�Ȼ��ֻ��ʾ���Լ�����Ϣ�������Ͳ���й¶������Ϣ��*/
	/*���£��޸ģ�--�����ݿ������ʵ�ʣ�*/
	public void UpdateUserServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("�������UpdateUserServlet");
		
		/*��ȡ�� Update.jsp ҳ���������Ϣ���û��޸ĺ����Ϣ��*/
		int id=Integer.parseInt(request.getParameter("id"));//��ȡ����id
		String name=request.getParameter("name");
		String pwd=request.getParameter("pwd");
		String sex=request.getParameter("sex");
		
		/*
		//limit��isAdmin��jsp��û�д�ֵ������������������ȡ����ֵ�ģ����Իᱨ������ʧ�ܣ�
		int limit=Integer.parseInt(request.getParameter("limit"));
		int isAdmin=Integer.parseInt(request.getParameter("isAdmin"));
		*/
		
		System.out.println("��UpdateUserServlet�ѻ�ȡ������");
		User user=new User(id,name,pwd,sex,null,null);//User������и����ι��췽��
		service.UpdateUserServlet(user);//Userservice��ͬ������
		System.out.println("��Ϣ�޸ĳɹ�");
		String message="��Ϣ�޸ĳɹ�";	
		/*����Ϣmessageת��һ��jspҳ��success.jsp����ҳ���ӡһ�����ɹ�����Ϣ*/
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/view/Success.jsp").forward(request, response);
		
	}
	
	//����Ա����
	/*����������˵��ͨ��id��֪���ĸ��û���Ȼ����ת�����޸Ľ��棬���û����Ը����Լ�����Ϣ*/
	/*���£��޸ģ�--����*/
	public void editUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id=Integer.parseInt(request.getParameter("id"));
		User user=service.getUser(id);//���ﲻ��new User����Ϊservice.getUser()���������ˡ�
		
		/*ת���ύ��request,����Ӧjspҳ����ʾ*/
		request.setAttribute("user", user);	/*��ȡ������user���󱣴���request������*/
		request.getRequestDispatcher("/WEB-INF/update/Update.jsp").forward(request, response);//ת����jspҳ����ʾ
	}
	
	
	/*ע��(addMember)*/
	/*�������������ӵ����ݿ�,�����ǿ��Ʋ��֣�ʵ�ֲ�����Service*/
	//ע��Ĭ��ע������û�������Ա�û�����ע�ᣩ����Ϊһ����վ������ϵͳ������Ա��Ը����û���˵��������Թ̶������������Ȳ�����ע�����Ա��
	public void RegisterUserServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name=request.getParameter("name");
		String pwd=request.getParameter("pwd");
		String sex=request.getParameter("sex");
		
		/*
		//limit��isAdmin��jsp��û�д�ֵ�������������޷�ȡֵ�ģ����Թ��캯������ֱ��дnull
		int limit=Integer.parseInt(request.getParameter("limit"));
		int isAdmin=Integer.parseInt(request.getParameter("isAdmin"));
		*/
		
		User user=new User(null,name,pwd,sex,null,null);//idΪinteger����д��int��
		boolean result = service.RegisterUserServlet(user);// true / false
		if(result) {	//�ɹ����
			String message="ע��ɹ�";
			
			/*����Ϣmessageת��һ��jspҳ��success.jsp����ҳ���ӡһ�����ɹ�����Ϣ*/
			request.setAttribute("message", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		else {	//��������п��������ݿ�����п������Լ����id��
			//��������ٴμ���ҳ�����û���������
			String message="ע��ʧ��";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/add/Register.jsp").forward(request, response);
		}
		
	}
	
	/*�������ֻ��˵�����Add member��ť������һ���������memberinput.jsp��Ȼ���������������һ��������addMember.do��
	 ��submit�������������ת����Ӧ�ķ���addMember()ʵ�ֽ�������ӵ����ݿ�Ĺ���*/
	public void RegisterUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("/WEB-INF/add/Register.jsp").forward(request, response);
	}
	
	
	/*����ԱȨ�ޣ����ճ��ڲ��õ��û��ʺţ�*/
	//����idɾ���û�
	public void DeleteUserServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id=Integer.parseInt(request.getParameter("id"));//����ġ�id�����������ʺţ�����ġ�id���ַ�����ͬ
		service.DeleteUserServlet(id);//���÷���ʵ��ɾ��
		ShowAllUserServlet(request,response);//�ٴ���ʾ�����£�ҳ��
	}
	

	
	public void ShowAllUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		java.util.List<User> users=service.getAllUsers();//��ȡUserService�в�ѯ���
		request.setAttribute("users",users);//�ѻ�ȡ����users�ŵ�request��	
		System.out.println("����ShowAllUserServlet");
		/*ת����jspҳ����ʾ*/
		request.getRequestDispatcher("/WEB-INF/admin/ShowAllUser.jsp").forward(request, response);
	}
	
	
	/*�����ƵĻ�Ա���޷����������Ϣ��ֻ�ܵ�¼��*/
	public void LimitUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));//����ġ�id�����������ʺţ�����ġ�id���ַ�����ͬ
		System.out.println("��servlet��ȡ��Ҫ���Ƶ��û�id="+id);
		int limit=Integer.parseInt(request.getParameter("limit"));
		
		if(limit==0) {//limit=0,�����û����Ϳ��Խ�������
			service.LimitUser(id);//�����û�idȥ�������Ʋ���
			
			String message="�ѽ����û�����Ϊ ���޻�Ա ";
			System.out.println("�ѽ����û�����Ϊ ���޻�Ա ");
			/*����Ϣmessageת��һ��jspҳ��success.jsp����ҳ���ӡһ�����ɹ�����Ϣ*/
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/Success.jsp").forward(request, response);
		}
		else {//limit=1,�Ѿ��������û��������ظ�����
			String message="���û��Ѿ��� ���޻�Ա�������ظ����� ";
			System.out.println("���û��Ѿ��� ���޻�Ա�������ظ����� ");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/Fail.jsp").forward(request, response);
			
		}
	}
	
	
	
	/*���ݸ���id��ѯ������Ϣ�������û���*/
	public void SearchUserSelfById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));//��jspҳ�洫��������getParameter
		System.out.println("SearchUserSelfById()----- id="+id);
		
		//id,name,sex��Ҫ�����servlet����ȥShowUserInfoSelf.jsp���Ǳ߲�����ʾ(ע�⣺Ҫ�����ﴫ��)
		//��������û��name��sex���ݣ�����Ҫ��idһ���������ӱ��servlet������һ��jsp��Ȼ���ٴ��Ǹ�jsp������
		//���������servlet������ݸոջ�ȡ����idȥ ��ȡname��sexֵ��
		request.setAttribute("id", id);//��id����request���У�Ȼ����ShowUserInfoSelf���Ի�ȡ������ʾ��
		User user=service.getUser(id);//���ﲻ��new User����Ϊservice.getUser()���������ˡ�
		
		/*ת���ύ��request,����Ӧjspҳ����ʾ*/
		request.setAttribute("user", user);	/*��ȡ������user���󱣴���request������*/
		
		String message="������Ϣ��ѯ�ɹ�";
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/user/ShowUserInfoSelf.jsp").forward(request, response);
		
	}
	
	
	
	
	
	//�����û���Ϣ�޸ĳɹ���ص�����
	/*���ĸ�����Ϣ�������û���---------����Ա�û��Ǳ߼ȿ��Ը���ͨ�û�����Ϣ�������룬Ҳ���Ը��Լ��ģ�����û��Ҫ��дһ��������servlet*/
	public void UpdateUserInfoSelfServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		/*��ȡ�� Update.jsp ҳ���������Ϣ���û��޸ĺ����Ϣ��*/
		System.out.println("���� UpdateUserInfoSelfServlet ");
		int id=Integer.parseInt(request.getParameter("id"));//��jspҳ�洫��������getParameter
		System.out.println("UpdateUserInfoSelfServlet()----- id="+id);
		String name=request.getParameter("name");
		String pwd=request.getParameter("pwd");
		String sex=request.getParameter("sex");
		System.out.println("name="+name+"  pwd="+pwd+"  sex="+sex);
	
		/*�����������ȡlimit �� isAdmin ���ᱨ��
		//limit��isAdmin��jsp��û�д�ֵ����������ֻ��Ϊ�����㹹�캯��
		int limit=Integer.parseInt(request.getParameter("limit"));
		int isAdmin=Integer.parseInt(request.getParameter("isAdmin"));
		 */
		System.out.println("��UpdateUserServlet�ѻ�ȡ������");
		User user=new User(id,name,pwd,sex,0,0);//User������и����ι��췽��(��Ϊ�����ڸ�����Ϣ�޸ĵķ��������ֱ�Ӱ�limit��isAdminĬ��0)
		service.UpdateUserServlet(user);//Userservice��ͬ������
		System.out.println("update successfully");
		String message="������Ϣ�޸ĳɹ�";
		request.setAttribute("message", message);
		
		//�޸ĳɹ��ص���ʾ������Ϣ�Ľ���
		request.setAttribute("user",user);//����ShowUserInfoSelf.jsp����Ȼ�Ǳ��޷���ʾ���º����Ϣ
		request.getRequestDispatcher("/WEB-INF/user/ShowUserInfoSelf.jsp").forward(request, response);
		
	}
	
	//�����û��޸���Ϣ��������(��ת���޸ĵĽ���)
	public void editUserSelfInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id=Integer.parseInt(request.getParameter("id"));//��jspҳ���ȡ"----- /editUserSelfInfo.do?id=${ requestScope.user.id }  -----  "
		User user=service.getUser(id);//���ﲻ��new User����Ϊservice.getUser()���������ˡ�
		
		/*ת���ύ��request,����Ӧjspҳ����ʾ*/
		request.setAttribute("user", user);	/*��ȡ������user���󱣴���request������*/
		request.getRequestDispatcher("/WEB-INF/update/UpdateUserInfoSelf.jsp").forward(request, response);//ת����jspҳ����ʾ
	}


	//�ص�USerHomePage
	public void USerHomePageServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id=Integer.parseInt(request.getParameter("id"));//��jspҳ���ȡ"----- /editUserSelfInfo.do?id=${ requestScope.user.id }  -----  "
		String message="������ҳ";
		
		request.setAttribute("id", id);//����id��request����ÿ�������û���¼��ֱ���ʾ�����˵���Ϣ��ͨ�����idȥ��ȡ��
		request.setAttribute("message", message);
		//ת���������û���ҳUserHomePage.jsp
		request.getRequestDispatcher("/WEB-INF/user/UserHomePage.jsp").forward(request, response);//ת�����ɹ�ҳ��
	
	}
	
	
	

	
	
	//������Ա����ʾ���г�����Ϣ������ͨ�û����ˡ����Ρ����ܣ��� ������+���Ρ� ��
	//����Ա���Կ������г�����Ϣ������δ������
	public void ShowAllCarForAdminServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("����ShowAllCarForAdminServlet()");
		List<Car> cars=new ArrayList<Car>();
		System.out.println("������� getAllCarsList()");
		cars=serviceOfCar.getAllCarsList();//��ȡ�����г������г���
		System.out.println("ShowAllCarForAdminServlet()-----��ȡ��cars�б�");
		String message="�����û��Ķ��ֳ���Ϣҳ��";		//(����Աҳ��)
		
		request.setAttribute("message", message);
		request.setAttribute("cars", cars);
		request.getRequestDispatcher("/WEB-INF/admin/ShowAllCarForAdmin.jsp").forward(request, response);
	
	}
	
	
	
	
	
	//����Ա�������Ϣ���������ֱ��ɾ����
	public void DeleteFakeCarInfoServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int carid=Integer.parseInt(request.getParameter("carid"));
		serviceOfCar.DeleteCarServlet(carid);//���÷���ʵ��ɾ��
		ShowAllCarForAdminServlet(request,response);//�ٴ���ʾ�����£�ҳ��
	}
	
	
	
	
}
