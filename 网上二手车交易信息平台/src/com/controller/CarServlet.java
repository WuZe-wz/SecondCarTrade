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
import com.service.CarService;
import com.service.UserService;

/**
 * Servlet implementation class CarServlet
 */
/*
 * ��������@WebServlet���ú�Ͳ�����web.xml������
 */

@WebServlet("*.car")/*ʹ��ͨ�����Ȼ����doGet()�������ж�ʹ���ĸ��������ĳ�����*/
public class CarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CarService serviceOfCar;
	private UserService serviceOfUser;
	
    public CarServlet() {
        super();
        this.serviceOfCar=new CarService();//ͨ�û�
        this.serviceOfUser=new UserService();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath=request.getServletPath();
		System.out.println(servletPath);
		
		/*�ӵڶ����ַ���ʼ����Ϊ��ͷ�С� / ����;��β��������end��λ��ֵ*/
		String methodName=servletPath.substring(1, servletPath.length()-4);//-4
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
	 * Car�����servlet����
	 * 
	 * �����ɹ�����SuccessOfCar.jsp,����ת����ҳ
	 */
	
	
	//���ֳ���������ҳ
	public void CarsManageHomePageServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Car> cars=new ArrayList<Car>();
		int userid=Integer.parseInt(request.getParameter("userid"));//������userid������id
		System.out.println("����CarsManageHomePageServlet");
		System.out.println("CarsManageHomePageServlet()---userid="+userid);
		User user = serviceOfUser.getUser(userid);//����jsp���������û�id����λ���û�
		cars=serviceOfCar.getCarsListOfUserid(userid);//����userid��ȡ��
		String message="-------���ֳ�������ҳ---------";
		request.setAttribute("user", user);//����request����CarsManageHomePage.jsp����ȡ������
		request.setAttribute("cars", cars);
		request.setAttribute("userid", userid);//������userid��ȥ����ΪCarsManageHomePageServlet��������user�������Ӷ�car��һϵ�в���
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/car/CarsManageHomePage.jsp").forward(request, response);
		
	}
	
	//��ӳ���
	//��ȡ��ӽ������Ϣ
	public void AddCarServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*ʵ��*/
		int userid=Integer.parseInt(request.getParameter("userid"));//userid:jspҳ����Ϊ?������������
		String brand=request.getParameter("brand");
		String model=request.getParameter("model");
		String price=request.getParameter("price");
		String color=request.getParameter("color");
		String carcomment=request.getParameter("carcomment");
		
		
		//Ĭ�����õ����ԣ���Щֱ��дnull(ע�⣬id��Щ����������Car��Ҫ�����Integer��������int)
		Car car = new Car(userid,null,brand,model,price,color,null,null,null,carcomment,null);
		boolean result=serviceOfCar.AddCarServlet(car);
		
		/*ת��*/
		if(result) {//�ɹ����
			String message="��ӳ����ɹ�";		
			/*����Ϣmessageת��һ��jspҳ��success.jsp����ҳ���ӡһ�����ɹ�����Ϣ*/
			request.setAttribute("car",car);//�ǵ�setAttribute��ȥ�ɹ�ҳ�棬��Ȼ�Ǳ߻�ȡ����
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/car/SuccessOfCar.jsp").forward(request, response);
		}
		else {	//���ʧ��
			String message="��ӳ���ʧ��";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/car/FailOfCar.jsp").forward(request, response);
		
		}
		
	}
	
	//��ӳ��� �������棨�༭���棩
	public void AddCarPageServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*ʵ��*/
		int userid=Integer.parseInt(request.getParameter("userid"));//ͨ�� " ?userid " ��������
		System.out.println("AddCarServlet()---userid="+userid);
	
		User user = serviceOfUser.getUser(userid);//����jsp���������û�id����λ���û�
		
		
		
		
		/*ת��*/
		String message="��ӳ���ҳ��";		
		request.setAttribute("user", user);//user�������request��
		/*����Ϣmessageת��һ��jspҳ��success.jsp����ҳ���ӡһ�����ɹ�����Ϣ*/
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/add/AddCarPage.jsp").forward(request, response);

	}
	
	
	//���³�����Ϣ��ʵ�֣�
	public void UpdateCarSelfServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
		/*ʵ��*/
		//userid��carid��ͨ����䴫������ " <form action="... .car?userid=${requestScope.car.userid}&carid=${requestScope.car.carid}" ...> "
		int userid=Integer.parseInt(request.getParameter("userid"));
		int carid=Integer.parseInt(request.getParameter("carid"));
		System.out.println("carid="+carid);
		System.out.println("userid="+userid);
		
		String brand=request.getParameter("brand");
		String model=request.getParameter("model");
		String price=request.getParameter("price");
		String color=request.getParameter("color");
		String carcomment=request.getParameter("carcomment");
		
		
		//����ע�������carid��λ�ò���дnull����Ϊдnull���Ͳ�֪��������һ������
		Car car = new Car(userid,carid,brand,model,price,color,null,null,null,carcomment,null);
		
		
		
		
		serviceOfCar.UpdateCarSelfServlet(car);//ͬ������
		System.out.println("������Ϣ�޸ĳɹ�");
		
		System.out.println("ShowAllLocalCarSelfServlet------userid="+userid);
		
		//List<Car> cars=new ArrayList<Car>();
		//cars=serviceOfCar.getCarsListOfUserid(userid);//����userid��ȡ����ǰ�������г���
		
		//����carid��ȡ��Ҫ������������car��Ϣ
		//car=serviceOfCar.getCarOfCarid(carid);
		
		System.out.println("����carid="+carid+" ���¸ó���Ϣ�ɹ�");
		
		/*ת��*/
		String message="������Ϣ�޸ĳɹ�";
		request.setAttribute("message", message);
		
		//ShowAllLocalCarSelf.jsp��ʾ��Ҫ����������Ҫ����userid��ȡһ�θó���������Ϣ���ŵ�request���У�
		List<Car> cars=new ArrayList<Car>();
		cars=serviceOfCar.getCarsListOfUserid(userid);
		request.setAttribute("cars", cars);//���زֿ��Ǳ�Ҫ�������ﴩ��ȥ��userid�ж���˭�Ĳֿ⣬Ȼ����ʾ�û��Լ��ı��زֿ�
		request.setAttribute("userid", userid);//!!
		request.getRequestDispatcher("/WEB-INF/user/ShowAllLocalCarSelf.jsp").forward(request, response);
	}
	
	
	
	
	//�޸ĳ�����Ϣ���༭���棩
	public void EditCarServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*ʵ��*/
		System.out.println("����EditCarServlet");
		//��λ���û�
		int userid=Integer.parseInt(request.getParameter("userid"));
		User user=serviceOfUser.getUser(userid);//���ﲻ��new User����Ϊservice.getUser()���������ˡ�
		System.out.println("userid="+userid);
		//��λ������
		int carid=Integer.parseInt(request.getParameter("carid"));
		Car car = serviceOfCar.getCarOfCarid(carid);
		System.out.println("carid="+carid);

		if(car.getIsrelease()==0) {//δ�������ɽ����޸�
			/*ת��*/
			String message="�����޸Ľ���";		
			/*����Ϣmessageת��һ��jspҳ��success.jsp����ҳ���ӡһ�����ɹ�����Ϣ*/
			
			request.setAttribute("message", message);
			request.setAttribute("car", car);//to UpdateCarSelf.jsp
			request.getRequestDispatcher("/WEB-INF/update/UpdateCarSelf.jsp").forward(request, response);
		}
		else {
			System.out.println("�ó����ѷ����������޸�");
			String message="�ó����ѷ����������޸�";
			request.setAttribute("message", message);
			
			/*//����ֱ��ת�����servlet�ȽϺã����ת��FailOfCar�Ļ�����Ҫ��request��Ų���������������ת����ҳ��
			 * ����������ֱ��ת��ȥ����������ȱ�ٽ���
			 * ���Ի���ת��jsp -> servlet -> ��ҳ 
			 * 
				ShowAllLocalCarSelfServlet(request,response);
			*/		
			
			request.setAttribute("car", car);
			request.getRequestDispatcher("/WEB-INF/update/UpdateCarFail.jsp").forward(request, response);
			
		}
	}
	
	
	
	
	
	//��ʾ�ҵ�ǰ���еĳ��������زֿ⣩
	public void ShowAllLocalCarSelfServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*ʵ��*/
		System.out.println("����ShowAllLocalCarSelfServlet�������ȡuserid");
		int userid=Integer.parseInt(request.getParameter("userid"));
		System.out.println("ShowAllLocalCarSelfServlet------userid="+userid);
		List<Car> cars=new ArrayList<Car>();
		cars=serviceOfCar.getCarsListOfUserid(userid);//����userid��ȡ����ǰ�������г���
		System.out.println("ShowAllLocalCarSelfServlet------��ʼת��");
	
		/*ת��*/
		String message="��ʾ�ҵ�ǰ���еĳ��� ����";		
		/*����Ϣmessageת��һ��jspҳ��success.jsp����ҳ���ӡһ�����ɹ�����Ϣ*/
		request.setAttribute("message", message);
		request.setAttribute("cars", cars);
		request.setAttribute("userid", userid);//!!
		request.getRequestDispatcher("/WEB-INF/user/ShowAllLocalCarSelf.jsp").forward(request, response);
	
	}
	
	
	//��ʾ�����û����ֳ�
	public void ShowAllCarsServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*ʵ��*/	
		int respondid;
		int userid=Integer.parseInt(request.getParameter("userid"));//!!
		List<Car> cars=new ArrayList<Car>();
		//cars=serviceOfCar.getAllCarsList();//��ȡ�����г������г���(������δ����������Ҳ��ȡ��)
		cars=serviceOfCar.getAllReleaseCarsList();//��ȡ�ѷ�������
		
		respondid=userid;
		
		/*ת��*/
		String message="�����û��Ķ��ֳ���Ϣҳ��";		
		/*����Ϣmessageת��һ��jspҳ��success.jsp����ҳ���ӡһ�����ɹ�����Ϣ*/
		request.setAttribute("message", message);
		request.setAttribute("cars", cars);
		request.setAttribute("userid", userid);//!!
		request.setAttribute("respondid", respondid);
		request.getRequestDispatcher("/WEB-INF/car/ShowAllCars.jsp").forward(request, response);
	
	}
	
	
	//����֮����ʾ�����û����ֳ�
public void ShowAllCarsServletAfterRespond(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*ʵ��*/	
		int respondid;
		int userid=Integer.parseInt(request.getParameter("userid"));//!!
		List<Car> cars=new ArrayList<Car>();
		cars=serviceOfCar.getAllCarsList();//��ȡ�����г������г���
		
		respondid=Integer.parseInt(request.getParameter("respondid"));;
		
		/*ת��*/
		String message="�����û��Ķ��ֳ���Ϣҳ��";		
		/*����Ϣmessageת��һ��jspҳ��success.jsp����ҳ���ӡһ�����ɹ�����Ϣ*/
		request.setAttribute("message", message);
		request.setAttribute("cars", cars);
		request.setAttribute("userid", userid);//!!
		request.setAttribute("respondid", respondid);
		request.getRequestDispatcher("/WEB-INF/car/ShowAllCars.jsp").forward(request, response);
	
	}
	
	
	
	
	//�����ó���
	//�����ɹ�����ShowAllLocalCarSelf���Կ���"����״̬"�����Ը���
	//���ѷ������������"����"��ť��������ʧ�ܽ��沢��message��Ȼ��ɵ��������ʾ��ҳ
	public void ReleaseCarServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*ʵ��*/
		int carid=Integer.parseInt(request.getParameter("carid"));	
		
		int isrelease=Integer.parseInt(request.getParameter("isrelease"));
		
		int userid=Integer.parseInt(request.getParameter("userid"));
		
		
		if(isrelease==0) {//δ����
			serviceOfCar.ReleaseCarServlet(carid);
			System.out.println("���������ɹ�");
			
			/*ת��*/
			//String message="message:���������ɹ�";		
			/*����Ϣmessageת��һ��jspҳ��success.jsp����ҳ���ӡһ�����ɹ�����Ϣ*/
			//request.setAttribute("message", message);
			request.setAttribute("userid", userid);
			
			ShowAllLocalCarSelfServlet(request,response);//�ٴ���ʾ�����£�ҳ��(��Ҫ��userid��ȥ)
		}
		else {
			request.setAttribute("userid", userid);
			String message="�����ѷ����������ظ�����";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/car/ReleaseCarFail.jsp").forward(request, response);
			
		}
	}
	
	
	
	//ɾ���ó���
	//����caridɾ��������Ϣ
	public void DeleteCarServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*ʵ��*/
		int carid=Integer.parseInt(request.getParameter("carid"));
		//int userid=Integer.parseInt(request.getParameter("userid"));
		serviceOfCar.DeleteCarServlet(carid);//���÷���ʵ��ɾ��
		ShowAllLocalCarSelfServlet(request,response);//�ٴ���ʾ�����£�ҳ��
		
	}
	
	
	//�����Խ��лظ��������û���
	public void RespondCommentServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*ʵ��*/
		int carid=Integer.parseInt(request.getParameter("carid"));
		int userid=Integer.parseInt(request.getParameter("userid"));
		String carcomment=request.getParameter("carcomment");	
		int canmessage=Integer.parseInt(request.getParameter("canmessage"));
		
		//����Ȩ������
		if(canmessage==0) {//������:0
			/*ת��*/
			String message="���԰�";		
			/*����Ϣmessageת��һ��jspҳ��success.jsp����ҳ���ӡһ�����ɹ�����Ϣ*/
			request.setAttribute("message", message);
			//request.setAttribute("carid", carid);

			
			if(request.getParameter("respondid")!=null) {
				int respondid=Integer.parseInt(request.getParameter("respondid"));
				request.setAttribute("respondid", respondid);//�ԡ����ĸ��û������Խ��б�ע
			}
			
			//User user=serviceOfUser.getUser(userid);//��ȡ����ǰ���ĸ���¼�û�
			
			//�������setAttribute(car)����Ȼ�޷������ԵĻ���
			//��Ҫ��userid����ȥ����Ȼ�����԰�ġ�ֻ������ʾ����û�취��ʾuserid����
			Car car = new Car(userid,carid,null,null,null,null,null,null,canmessage,carcomment,null);
			request.setAttribute("car", car);
			
			request.getRequestDispatcher("/WEB-INF/car/RespondComment.jsp").forward(request, response);
		}
		else {//canmessageΪ1�򲻿�����
			String message="�ó����ѱ�������ֹ����";
			request.setAttribute("message", message);
			Car car = new Car(userid,carid,null,null,null,null,null,null,canmessage,carcomment,null);
			request.setAttribute("car", car);
			request.getRequestDispatcher("/WEB-INF/car/RespondFail.jsp").forward(request, response);
		}
	}
	
	
	//�����Ը��µ����ݿ��У������û���
	public void AddResondComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*ʵ��*/
		int carid=Integer.parseInt(request.getParameter("carid"));
		int userid=Integer.parseInt(request.getParameter("userid"));
		String carcomment=request.getParameter("carcomment");
		

		int respondid=Integer.parseInt(request.getParameter("respondid"));
		request.setAttribute("respondid", respondid);//�ԡ����ĸ��û������Խ��б�ע
	
		
		/*
		//����Ҫ���µ�����дnull����
		Car car = new Car(null,carid,null,null,null,null,null,null,null,carcomment);
		serviceOfCar.UpdateCarSelfServlet(car);//ͬ������
		*/
		User user=serviceOfUser.getUser(userid);
		//ֱ�Ӵ���carid��carcomment��Ϊ����Ҳ�У�����������Ҫ��дһ��update����
		serviceOfCar.UpdateCarComment(carcomment,carid);
		
		
		System.out.println("���Ը��³ɹ�");
		
		/*
		//request.setAttribute("userid", userid);����Ҫ���
		//��Ҫ��userid��ȥ���������ǵ�����һ��servlet��������jsp�����Բ����ٴ�userid�ˣ���ΪAddResondComment����һ��������jsp�Ѿ���userid���������.car��servlet��
		//��ע�⣬���ﲻӦ��ת������ʾ������ҳ����Ӧ��ת����ʾ���г���
		//ShowAllLocalCarSelfServlet(request,response);//�ٴ���ʾ�����£�ҳ��
		*/
		
		String message="���Գɹ�";
		request.setAttribute("message", message);
		request.setAttribute("userid", userid);
		request.setAttribute("user", user);
		
		//ת�������Գɹ�����
		request.getRequestDispatcher("/WEB-INF/car/SuccessOfRespond.jsp").forward(request, response);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//�����Խ��лظ�������Ա�û���
		public void RespondCommentOfAdminServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
			/*ʵ��*/
			int carid=Integer.parseInt(request.getParameter("carid"));
			int userid=Integer.parseInt(request.getParameter("userid"));
			String carcomment=request.getParameter("carcomment");	
			int canmessage=Integer.parseInt(request.getParameter("canmessage"));
			
			//����Ȩ������
			if(canmessage==0) {//������:0
				/*ת��*/
				String message="���԰�";		
				/*����Ϣmessageת��һ��jspҳ��success.jsp����ҳ���ӡһ�����ɹ�����Ϣ*/
				request.setAttribute("message", message);
				//request.setAttribute("carid", carid);
				
				//User user=serviceOfUser.getUser(userid);//��ȡ����ǰ���ĸ���¼�û�
				
				//�������setAttribute(car)����Ȼ�޷������ԵĻ���
				//��Ҫ��userid����ȥ����Ȼ�����԰�ġ�ֻ������ʾ����û�취��ʾuserid����
				Car car = new Car(userid,carid,null,null,null,null,null,null,canmessage,carcomment,null);
				request.setAttribute("car", car);
				String adminrespond="admin";
				request.setAttribute("adminrespond", adminrespond);
				request.getRequestDispatcher("/WEB-INF/admin/RespondCommentOfAdmin.jsp").forward(request, response);
			}
			else {//canmessageΪ1�򲻿�����
				String message="�ó����ѱ�������ֹ����";
				request.setAttribute("message", message);
				Car car = new Car(userid,carid,null,null,null,null,null,null,canmessage,carcomment,null);
				request.setAttribute("car", car);
				request.getRequestDispatcher("/WEB-INF/admin/RespondFailOfAdmin.jsp").forward(request, response);
			}
		}
		
		
		//�����Ը��µ����ݿ��У�����Ա�û���
		public void AddResondOfAdminComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
			/*ʵ��*/
			int carid=Integer.parseInt(request.getParameter("carid"));
			int userid=Integer.parseInt(request.getParameter("userid"));
			String carcomment=request.getParameter("carcomment");
			
			/*
			//����Ҫ���µ�����дnull����
			Car car = new Car(null,carid,null,null,null,null,null,null,null,carcomment);
			serviceOfCar.UpdateCarSelfServlet(car);//ͬ������
			*/
			User user=serviceOfUser.getUser(userid);
			//ֱ�Ӵ���carid��carcomment��Ϊ����Ҳ�У�����������Ҫ��дһ��update����
			serviceOfCar.UpdateCarComment(carcomment,carid);
			
			
			System.out.println("���Ը��³ɹ�");
			
			/*
			//request.setAttribute("userid", userid);����Ҫ���
			//��Ҫ��userid��ȥ���������ǵ�����һ��servlet��������jsp�����Բ����ٴ�userid�ˣ���ΪAddResondComment����һ��������jsp�Ѿ���userid���������.car��servlet��
			//��ע�⣬���ﲻӦ��ת������ʾ������ҳ����Ӧ��ת����ʾ���г���
			//ShowAllLocalCarSelfServlet(request,response);//�ٴ���ʾ�����£�ҳ��
			*/
			
			String message="���Գɹ�";
			request.setAttribute("message", message);
			request.setAttribute("userid", userid);
			request.setAttribute("user", user);
			//ת�������Գɹ�����
			request.getRequestDispatcher("/WEB-INF/admin/SuccessOfAdminRespond.jsp").forward(request, response);
			
		}
		
		
		
		
	
	
	
	
	
	
	
	///
	//���ý�ֹ����(��������)
	//����ֹ���ԡ��ɹ���ʧ�ܶ���󷵻ص����˲ֿ����
		public void RespondBan(HttpServletRequest request, HttpServletResponse response) throws Exception {
			/*ʵ��*/
			int userid=Integer.parseInt(request.getParameter("userid"));
			int carid=Integer.parseInt(request.getParameter("carid"));
			int canmessage=Integer.parseInt(request.getParameter("canmessage"));
			
			//����Ȩ������
			if(canmessage==0) {//������:0����δ��ֹ�ó����Թ��ܣ�
				/*ת��*/
				serviceOfCar.RespondBan(carid);
				String message="�ɹ���ֹ�ó����Թ���";
				System.out.println("�ɹ���ֹ�ó����Թ���");
				/*����Ϣmessageת��һ��jspҳ��success.jsp����ҳ���ӡһ�����ɹ�����Ϣ*/
				request.setAttribute("message", message);
				//request.setAttribute("carid", carid);
				
				
				request.setAttribute("userid", userid);
				request.getRequestDispatcher("/WEB-INF/car/SuccessOfRespondBan.jsp").forward(request, response);
				
			}
			else {//canmessageΪ1�򲻿�����
				String message="�ó������Թ����ѱ���ֹ�������ظ�����";
				System.out.println("�ó������Թ����ѱ���ֹ�������ظ�����");
				request.setAttribute("message", message);
				request.setAttribute("userid", userid);
				request.getRequestDispatcher("/WEB-INF/car/RespondBanFail.jsp").forward(request, response);
			}
		}
	

}
