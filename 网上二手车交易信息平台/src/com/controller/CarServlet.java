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
 * 在这里用@WebServlet配置后就不用在web.xml配置了
 */

@WebServlet("*.car")/*使用通配符，然后在doGet()方法中判断使用哪个传过来的超链接*/
public class CarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CarService serviceOfCar;
	private UserService serviceOfUser;
	
    public CarServlet() {
        super();
        this.serviceOfCar=new CarService();//通用化
        this.serviceOfUser=new UserService();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath=request.getServletPath();
		System.out.println(servletPath);
		
		/*从第二个字符开始（因为开头有“ / ”）;结尾不包含“end”位的值*/
		String methodName=servletPath.substring(1, servletPath.length()-4);//-4
		System.out.println(methodName);
		
		/*反射机制*/
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
	 * Car的相关servlet操作
	 * 
	 * 操作成功返回SuccessOfCar.jsp,再跳转到首页
	 */
	
	
	//二手车辆管理首页
	public void CarsManageHomePageServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Car> cars=new ArrayList<Car>();
		int userid=Integer.parseInt(request.getParameter("userid"));//这里是userid，不是id
		System.out.println("进入CarsManageHomePageServlet");
		System.out.println("CarsManageHomePageServlet()---userid="+userid);
		User user = serviceOfUser.getUser(userid);//根据jsp传过来的用户id，定位到用户
		cars=serviceOfCar.getCarsListOfUserid(userid);//根据userid获取到
		String message="-------二手车管理首页---------";
		request.setAttribute("user", user);//放在request，到CarsManageHomePage.jsp可以取出来用
		request.setAttribute("cars", cars);
		request.setAttribute("userid", userid);//单独传userid过去，因为CarsManageHomePageServlet不仅连接user，又连接对car的一系列操作
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/car/CarsManageHomePage.jsp").forward(request, response);
		
	}
	
	//添加车辆
	//获取添加界面的信息
	public void AddCarServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*实现*/
		int userid=Integer.parseInt(request.getParameter("userid"));//userid:jsp页面作为?参数传过来的
		String brand=request.getParameter("brand");
		String model=request.getParameter("model");
		String price=request.getParameter("price");
		String color=request.getParameter("color");
		String carcomment=request.getParameter("carcomment");
		
		
		//默认设置的属性，这些直接写null(注意，id这些整型属性在Car类要定义成Integer，不能是int)
		Car car = new Car(userid,null,brand,model,price,color,null,null,null,carcomment,null);
		boolean result=serviceOfCar.AddCarServlet(car);
		
		/*转发*/
		if(result) {//成功添加
			String message="添加车辆成功";		
			/*将消息message转到一个jsp页面success.jsp，在页面打印一个“成功”消息*/
			request.setAttribute("car",car);//记得setAttribute过去成功页面，不然那边获取不到
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/car/SuccessOfCar.jsp").forward(request, response);
		}
		else {	//添加失败
			String message="添加车辆失败";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/car/FailOfCar.jsp").forward(request, response);
		
		}
		
	}
	
	//添加车辆 触发界面（编辑界面）
	public void AddCarPageServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*实现*/
		int userid=Integer.parseInt(request.getParameter("userid"));//通过 " ?userid " 传过来了
		System.out.println("AddCarServlet()---userid="+userid);
	
		User user = serviceOfUser.getUser(userid);//根据jsp传过来的用户id，定位到用户
		
		
		
		
		/*转发*/
		String message="添加车辆页面";		
		request.setAttribute("user", user);//user放在这个request里
		/*将消息message转到一个jsp页面success.jsp，在页面打印一个“成功”消息*/
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/add/AddCarPage.jsp").forward(request, response);

	}
	
	
	//更新车辆信息（实现）
	public void UpdateCarSelfServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
		/*实现*/
		//userid和carid是通过这句传过来的 " <form action="... .car?userid=${requestScope.car.userid}&carid=${requestScope.car.carid}" ...> "
		int userid=Integer.parseInt(request.getParameter("userid"));
		int carid=Integer.parseInt(request.getParameter("carid"));
		System.out.println("carid="+carid);
		System.out.println("userid="+userid);
		
		String brand=request.getParameter("brand");
		String model=request.getParameter("model");
		String price=request.getParameter("price");
		String color=request.getParameter("color");
		String carcomment=request.getParameter("carcomment");
		
		
		//报错，注意这里的carid的位置不能写null，因为写null，就不知道更新哪一辆车了
		Car car = new Car(userid,carid,brand,model,price,color,null,null,null,carcomment,null);
		
		
		
		
		serviceOfCar.UpdateCarSelfServlet(car);//同名方法
		System.out.println("车辆信息修改成功");
		
		System.out.println("ShowAllLocalCarSelfServlet------userid="+userid);
		
		//List<Car> cars=new ArrayList<Car>();
		//cars=serviceOfCar.getCarsListOfUserid(userid);//根据userid获取到当前车主所有车辆
		
		//根据carid获取到要更新那样车的car信息
		//car=serviceOfCar.getCarOfCarid(carid);
		
		System.out.println("根据carid="+carid+" 更新该车信息成功");
		
		/*转发*/
		String message="车辆信息修改成功";
		request.setAttribute("message", message);
		
		//ShowAllLocalCarSelf.jsp显示需要，所以这里要再用userid获取一次该车主所有信息（放到request域中）
		List<Car> cars=new ArrayList<Car>();
		cars=serviceOfCar.getCarsListOfUserid(userid);
		request.setAttribute("cars", cars);//本地仓库那边要根据这里穿过去的userid判断是谁的仓库，然后显示用户自己的本地仓库
		request.setAttribute("userid", userid);//!!
		request.getRequestDispatcher("/WEB-INF/user/ShowAllLocalCarSelf.jsp").forward(request, response);
	}
	
	
	
	
	//修改车辆信息（编辑界面）
	public void EditCarServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*实现*/
		System.out.println("进入EditCarServlet");
		//定位到用户
		int userid=Integer.parseInt(request.getParameter("userid"));
		User user=serviceOfUser.getUser(userid);//这里不用new User，因为service.getUser()方法中有了。
		System.out.println("userid="+userid);
		//定位到车辆
		int carid=Integer.parseInt(request.getParameter("carid"));
		Car car = serviceOfCar.getCarOfCarid(carid);
		System.out.println("carid="+carid);

		if(car.getIsrelease()==0) {//未发布，可进行修改
			/*转发*/
			String message="进入修改界面";		
			/*将消息message转到一个jsp页面success.jsp，在页面打印一个“成功”消息*/
			
			request.setAttribute("message", message);
			request.setAttribute("car", car);//to UpdateCarSelf.jsp
			request.getRequestDispatcher("/WEB-INF/update/UpdateCarSelf.jsp").forward(request, response);
		}
		else {
			System.out.println("该车辆已发布，不可修改");
			String message="该车辆已发布，不可修改";
			request.setAttribute("message", message);
			
			/*//这里直接转到这个servlet比较好，如果转到FailOfCar的话，还要在request域放参数，才能正常跳转到首页，
			 * 还不如这里直接转过去，但是这样缺少交互
			 * 所以还是转到jsp -> servlet -> 首页 
			 * 
				ShowAllLocalCarSelfServlet(request,response);
			*/		
			
			request.setAttribute("car", car);
			request.getRequestDispatcher("/WEB-INF/update/UpdateCarFail.jsp").forward(request, response);
			
		}
	}
	
	
	
	
	
	//显示我当前所有的车辆（本地仓库）
	public void ShowAllLocalCarSelfServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*实现*/
		System.out.println("进入ShowAllLocalCarSelfServlet，下面获取userid");
		int userid=Integer.parseInt(request.getParameter("userid"));
		System.out.println("ShowAllLocalCarSelfServlet------userid="+userid);
		List<Car> cars=new ArrayList<Car>();
		cars=serviceOfCar.getCarsListOfUserid(userid);//根据userid获取到当前车主所有车辆
		System.out.println("ShowAllLocalCarSelfServlet------开始转发");
	
		/*转发*/
		String message="显示我当前所有的车辆 界面";		
		/*将消息message转到一个jsp页面success.jsp，在页面打印一个“成功”消息*/
		request.setAttribute("message", message);
		request.setAttribute("cars", cars);
		request.setAttribute("userid", userid);//!!
		request.getRequestDispatcher("/WEB-INF/user/ShowAllLocalCarSelf.jsp").forward(request, response);
	
	}
	
	
	//显示所有用户二手车
	public void ShowAllCarsServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*实现*/	
		int respondid;
		int userid=Integer.parseInt(request.getParameter("userid"));//!!
		List<Car> cars=new ArrayList<Car>();
		//cars=serviceOfCar.getAllCarsList();//获取到所有车主所有车辆(这个会把未发布的汽车也获取到)
		cars=serviceOfCar.getAllReleaseCarsList();//获取已发布车辆
		
		respondid=userid;
		
		/*转发*/
		String message="所有用户的二手车信息页面";		
		/*将消息message转到一个jsp页面success.jsp，在页面打印一个“成功”消息*/
		request.setAttribute("message", message);
		request.setAttribute("cars", cars);
		request.setAttribute("userid", userid);//!!
		request.setAttribute("respondid", respondid);
		request.getRequestDispatcher("/WEB-INF/car/ShowAllCars.jsp").forward(request, response);
	
	}
	
	
	//留言之后显示所有用户二手车
public void ShowAllCarsServletAfterRespond(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*实现*/	
		int respondid;
		int userid=Integer.parseInt(request.getParameter("userid"));//!!
		List<Car> cars=new ArrayList<Car>();
		cars=serviceOfCar.getAllCarsList();//获取到所有车主所有车辆
		
		respondid=Integer.parseInt(request.getParameter("respondid"));;
		
		/*转发*/
		String message="所有用户的二手车信息页面";		
		/*将消息message转到一个jsp页面success.jsp，在页面打印一个“成功”消息*/
		request.setAttribute("message", message);
		request.setAttribute("cars", cars);
		request.setAttribute("userid", userid);//!!
		request.setAttribute("respondid", respondid);
		request.getRequestDispatcher("/WEB-INF/car/ShowAllCars.jsp").forward(request, response);
	
	}
	
	
	
	
	//发布该车辆
	//发布成功，到ShowAllLocalCarSelf可以看到"发布状态"的属性更新
	//对已发布的汽车点击"发布"按钮，会跳到失败界面并发message，然后可点击跳回显示主页
	public void ReleaseCarServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*实现*/
		int carid=Integer.parseInt(request.getParameter("carid"));	
		
		int isrelease=Integer.parseInt(request.getParameter("isrelease"));
		
		int userid=Integer.parseInt(request.getParameter("userid"));
		
		
		if(isrelease==0) {//未发布
			serviceOfCar.ReleaseCarServlet(carid);
			System.out.println("车辆发布成功");
			
			/*转发*/
			//String message="message:车辆发布成功";		
			/*将消息message转到一个jsp页面success.jsp，在页面打印一个“成功”消息*/
			//request.setAttribute("message", message);
			request.setAttribute("userid", userid);
			
			ShowAllLocalCarSelfServlet(request,response);//再次显示（最新）页面(需要传userid过去)
		}
		else {
			request.setAttribute("userid", userid);
			String message="车辆已发布，请勿重复发布";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/car/ReleaseCarFail.jsp").forward(request, response);
			
		}
	}
	
	
	
	//删除该车辆
	//根据carid删除车辆信息
	public void DeleteCarServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*实现*/
		int carid=Integer.parseInt(request.getParameter("carid"));
		//int userid=Integer.parseInt(request.getParameter("userid"));
		serviceOfCar.DeleteCarServlet(carid);//调用方法实现删除
		ShowAllLocalCarSelfServlet(request,response);//再次显示（最新）页面
		
	}
	
	
	//对留言进行回复（个人用户）
	public void RespondCommentServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*实现*/
		int carid=Integer.parseInt(request.getParameter("carid"));
		int userid=Integer.parseInt(request.getParameter("userid"));
		String carcomment=request.getParameter("carcomment");	
		int canmessage=Integer.parseInt(request.getParameter("canmessage"));
		
		//留言权限设置
		if(canmessage==0) {//可留言:0
			/*转发*/
			String message="留言板";		
			/*将消息message转到一个jsp页面success.jsp，在页面打印一个“成功”消息*/
			request.setAttribute("message", message);
			//request.setAttribute("carid", carid);

			
			if(request.getParameter("respondid")!=null) {
				int respondid=Integer.parseInt(request.getParameter("respondid"));
				request.setAttribute("respondid", respondid);//对“是哪个用户”留言进行标注
			}
			
			//User user=serviceOfUser.getUser(userid);//获取到当前是哪个登录用户
			
			//这里必须setAttribute(car)，不然无法做留言的回显
			//需要把userid传过去，不然在留言板的“只读”显示那里没办法显示userid出来
			Car car = new Car(userid,carid,null,null,null,null,null,null,canmessage,carcomment,null);
			request.setAttribute("car", car);
			
			request.getRequestDispatcher("/WEB-INF/car/RespondComment.jsp").forward(request, response);
		}
		else {//canmessage为1则不可留言
			String message="该车辆已被车主禁止留言";
			request.setAttribute("message", message);
			Car car = new Car(userid,carid,null,null,null,null,null,null,canmessage,carcomment,null);
			request.setAttribute("car", car);
			request.getRequestDispatcher("/WEB-INF/car/RespondFail.jsp").forward(request, response);
		}
	}
	
	
	//把留言更新到数据库中（个人用户）
	public void AddResondComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*实现*/
		int carid=Integer.parseInt(request.getParameter("carid"));
		int userid=Integer.parseInt(request.getParameter("userid"));
		String carcomment=request.getParameter("carcomment");
		

		int respondid=Integer.parseInt(request.getParameter("respondid"));
		request.setAttribute("respondid", respondid);//对“是哪个用户”留言进行标注
	
		
		/*
		//不需要更新的属性写null即可
		Car car = new Car(null,carid,null,null,null,null,null,null,null,carcomment);
		serviceOfCar.UpdateCarSelfServlet(car);//同名方法
		*/
		User user=serviceOfUser.getUser(userid);
		//直接传入carid和carcomment作为参数也行，但是这样需要再写一个update方法
		serviceOfCar.UpdateCarComment(carcomment,carid);
		
		
		System.out.println("留言更新成功");
		
		/*
		//request.setAttribute("userid", userid);不需要这句
		//需要传userid过去，但这里是调用另一个servlet，而不是jsp，所以不用再传userid了，因为AddResondComment的上一个过来的jsp已经把userid传过来这个.car的servlet了
		//但注意，这里不应该转到“显示个人主页”，应该转到显示所有车辆
		//ShowAllLocalCarSelfServlet(request,response);//再次显示（最新）页面
		*/
		
		String message="留言成功";
		request.setAttribute("message", message);
		request.setAttribute("userid", userid);
		request.setAttribute("user", user);
		
		//转发到留言成功界面
		request.getRequestDispatcher("/WEB-INF/car/SuccessOfRespond.jsp").forward(request, response);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//对留言进行回复（管理员用户）
		public void RespondCommentOfAdminServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
			/*实现*/
			int carid=Integer.parseInt(request.getParameter("carid"));
			int userid=Integer.parseInt(request.getParameter("userid"));
			String carcomment=request.getParameter("carcomment");	
			int canmessage=Integer.parseInt(request.getParameter("canmessage"));
			
			//留言权限设置
			if(canmessage==0) {//可留言:0
				/*转发*/
				String message="留言板";		
				/*将消息message转到一个jsp页面success.jsp，在页面打印一个“成功”消息*/
				request.setAttribute("message", message);
				//request.setAttribute("carid", carid);
				
				//User user=serviceOfUser.getUser(userid);//获取到当前是哪个登录用户
				
				//这里必须setAttribute(car)，不然无法做留言的回显
				//需要把userid传过去，不然在留言板的“只读”显示那里没办法显示userid出来
				Car car = new Car(userid,carid,null,null,null,null,null,null,canmessage,carcomment,null);
				request.setAttribute("car", car);
				String adminrespond="admin";
				request.setAttribute("adminrespond", adminrespond);
				request.getRequestDispatcher("/WEB-INF/admin/RespondCommentOfAdmin.jsp").forward(request, response);
			}
			else {//canmessage为1则不可留言
				String message="该车辆已被车主禁止留言";
				request.setAttribute("message", message);
				Car car = new Car(userid,carid,null,null,null,null,null,null,canmessage,carcomment,null);
				request.setAttribute("car", car);
				request.getRequestDispatcher("/WEB-INF/admin/RespondFailOfAdmin.jsp").forward(request, response);
			}
		}
		
		
		//把留言更新到数据库中（管理员用户）
		public void AddResondOfAdminComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
			/*实现*/
			int carid=Integer.parseInt(request.getParameter("carid"));
			int userid=Integer.parseInt(request.getParameter("userid"));
			String carcomment=request.getParameter("carcomment");
			
			/*
			//不需要更新的属性写null即可
			Car car = new Car(null,carid,null,null,null,null,null,null,null,carcomment);
			serviceOfCar.UpdateCarSelfServlet(car);//同名方法
			*/
			User user=serviceOfUser.getUser(userid);
			//直接传入carid和carcomment作为参数也行，但是这样需要再写一个update方法
			serviceOfCar.UpdateCarComment(carcomment,carid);
			
			
			System.out.println("留言更新成功");
			
			/*
			//request.setAttribute("userid", userid);不需要这句
			//需要传userid过去，但这里是调用另一个servlet，而不是jsp，所以不用再传userid了，因为AddResondComment的上一个过来的jsp已经把userid传过来这个.car的servlet了
			//但注意，这里不应该转到“显示个人主页”，应该转到显示所有车辆
			//ShowAllLocalCarSelfServlet(request,response);//再次显示（最新）页面
			*/
			
			String message="留言成功";
			request.setAttribute("message", message);
			request.setAttribute("userid", userid);
			request.setAttribute("user", user);
			//转发到留言成功界面
			request.getRequestDispatcher("/WEB-INF/admin/SuccessOfAdminRespond.jsp").forward(request, response);
			
		}
		
		
		
		
	
	
	
	
	
	
	
	///
	//设置禁止留言(车主界面)
	//“禁止留言”成功或失败都最后返回到个人仓库界面
		public void RespondBan(HttpServletRequest request, HttpServletResponse response) throws Exception {
			/*实现*/
			int userid=Integer.parseInt(request.getParameter("userid"));
			int carid=Integer.parseInt(request.getParameter("carid"));
			int canmessage=Integer.parseInt(request.getParameter("canmessage"));
			
			//留言权限设置
			if(canmessage==0) {//可留言:0（还未禁止该车留言功能）
				/*转发*/
				serviceOfCar.RespondBan(carid);
				String message="成功禁止该车留言功能";
				System.out.println("成功禁止该车留言功能");
				/*将消息message转到一个jsp页面success.jsp，在页面打印一个“成功”消息*/
				request.setAttribute("message", message);
				//request.setAttribute("carid", carid);
				
				
				request.setAttribute("userid", userid);
				request.getRequestDispatcher("/WEB-INF/car/SuccessOfRespondBan.jsp").forward(request, response);
				
			}
			else {//canmessage为1则不可留言
				String message="该车辆留言功能已被禁止，请勿重复操作";
				System.out.println("该车辆留言功能已被禁止，请勿重复操作");
				request.setAttribute("message", message);
				request.setAttribute("userid", userid);
				request.getRequestDispatcher("/WEB-INF/car/RespondBanFail.jsp").forward(request, response);
			}
		}
	

}
