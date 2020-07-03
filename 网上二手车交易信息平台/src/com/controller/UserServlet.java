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
@WebServlet("*.do")/*使用通配符，然后在doGet()方法中判断使用哪个传过来的超链接*/
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService service;
	private CarService serviceOfCar;

    public UserServlet() {
        super();
        this.service=new UserService();//通用化
        this.serviceOfCar=new CarService();//后来增加了管理员查看所有车辆的servlet，但这里这句语句没写，就一直报错
    }

    //在doget（）方法中写代码
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath=request.getServletPath();
		System.out.println(servletPath);
		
		/*从第二个字符开始（因为开头有“ / ”）;结尾不包含“end”位的值*/
		String methodName=servletPath.substring(1, servletPath.length()-3);
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
	 * User的相关servlet操作
	 * 注意：方法名要与jsp页面的      "/名字 "   的 "名字" 要对应（大小写）
	 */
	
	
	//登录
	public void LoginUserServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//得到jsp页面传过来的参数
				//（因为在同个request域中，所以可以用request获取其数据）
				String name=request.getParameter("name");
				String pwd=request.getParameter("pwd");
				int userType=Integer.parseInt(request.getParameter("userType"));// 会员0 /管理员1
				
				
				System.out.println("userType="+userType);
				/*这里id不能直接request.getParameter获取，因为在用户登录的时候没有填id，所以jsp页面没有传"id"信息过来，这里获取不到的*/
				int id=service.LoginFindId(name, pwd);//根据name和pwd查找id
				UserDao ud =new UserDaoImpl();//创建接口的实现类，ud对象可以操作UserDaoImpl类内容
				System.out.println("id="+id);
				System.out.println("进入LoginUserServlet，还未调用service.login方法");
				
				//service.LimitUser(id)执行成功，返回true，则该用户变为受限
				//service.isLimit(id)返回true，则该用户变为受限
				//userType==0  会员
				if(service.login(name, pwd) && (!service.isLimit(id)) && (userType==0) && !service.isAdmin(id)) {//如果登录通过,并且不受限
					
					System.out.println("（会员）登录成功");
					String message="个人会员 登录成功";
					
					//注：个人用户登录成功，将他的id放在request域中，在jsp页面可以获取，然后通过jsp可以再把该id信息转到其他servlet中(比如下面的SearchUserSelfById() )
					request.setAttribute("id", id);//设置id到request域，在每个个人用户登录后分别显示他个人的信息（通过这个id去获取）
					request.setAttribute("message", message);
					//转发到个人用户主页UserHomePage.jsp
					request.getRequestDispatcher("/WEB-INF/user/UserHomePage.jsp").forward(request, response);//转发到成功页面
				}
				//userType==1  管理员
				else if(service.login(name, pwd) && (!service.isLimit(id)) && (userType==1) && service.isAdmin(id)) {//如果登录通过,并且不受限
					
					System.out.println("（管理员）登录成功");
					String message="管理员登录成功";
					request.setAttribute("message", message);
					request.getRequestDispatcher("/WEB-INF/admin/AdminHomePage.jsp").forward(request, response);//转发到成功页面
				}
				//个人用户（受限用户）
				else if(service.login(name, pwd) && service.isLimit(id) && (userType==0) && !service.isAdmin(id)){//登录成功，但受限
					System.out.println("登录成功,但您的会员资格受限，无法访问汽车信息");
					String message="登录成功,但您的会员资格受限，无法访问汽车信息";
					request.setAttribute("ERRMESSAGE", message);
					request.getRequestDispatcher("/WEB-INF/admin/LimitedUser.jsp").forward(request, response);//转发到成功页面
				
				}
				//共用
				else {//登录不通过，重定向到失败界面(respnse 响应)
					System.out.println("登录失败,请确认账号密码以及身份是否正确");
					String message="登录失败,请确认账号密码以及身份是否正确";
					request.setAttribute("message", message);
					//response.sendRedirect("/WEB-INF/view/Fail.jsp"); 重定向不在一个request里面
					request.getRequestDispatcher("/WEB-INF/view/LoginFail.jsp").forward(request, response);//转发到失败页面
				}
	}
	
	/*登录失败回到登录界面(在这个servlet控制)*/
	//login在根目录
	public void LoginUserFailServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
		
	
	//退出登录，回到登录页面
	public void returnLoginPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
	
	//管理员（回到管理员用户首页）
	public void returnAdminHomePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String message="管理员主页";
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/admin/AdminHomePage.jsp").forward(request, response);
	}
	
	
	/*管理员权限（这个是要显示密码的）--------有时间再增加一个方法，用于个人用户通过模糊查询到某个车主，可以显示二手车信息（个人权限）*/
	/*模糊查询个人用户（name、sex）*/
	public void SearchUserServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name=request.getParameter("name");//getParameter这个参数也是跟jsp页面的斜杠后面的名字一致（大小写）
		String sex=request.getParameter("sex");
		
		UserSearchConditions conditions=new UserSearchConditions(name, sex);
		List<User> users = service.SearchUsersByConditions(conditions);//UserService内配置
				
		request.setAttribute("users", users);
		request.getRequestDispatcher("/WEB-INF/admin/ShowSearchUser.jsp").forward(request, response);
		
	}
	
	
	//管理员界面
	/*个人用户登录后，根据他的唯一id号定位到他在数据库的数据行，然后只显示他自己的信息出来（就不会泄露他人信息）*/
	/*更新（修改）--对数据库操作（实际）*/
	public void UpdateUserServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("下面进入UpdateUserServlet");
		
		/*获取从 Update.jsp 页面输入的信息（用户修改后的信息）*/
		int id=Integer.parseInt(request.getParameter("id"));//获取他的id
		String name=request.getParameter("name");
		String pwd=request.getParameter("pwd");
		String sex=request.getParameter("sex");
		
		/*
		//limit和isAdmin在jsp并没有传值过来，所以在这里是取不到值的，所以会报错（更新失败）
		int limit=Integer.parseInt(request.getParameter("limit"));
		int isAdmin=Integer.parseInt(request.getParameter("isAdmin"));
		*/
		
		System.out.println("在UpdateUserServlet已获取到数据");
		User user=new User(id,name,pwd,sex,null,null);//User类必须有个带参构造方法
		service.UpdateUserServlet(user);//Userservice的同名方法
		System.out.println("信息修改成功");
		String message="信息修改成功";	
		/*将消息message转到一个jsp页面success.jsp，在页面打印一个“成功”消息*/
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/view/Success.jsp").forward(request, response);
		
	}
	
	//管理员界面
	/*就是上面所说，通过id得知是哪个用户，然后跳转到该修改界面，该用户可以更新自己的信息*/
	/*更新（修改）--界面*/
	public void editUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id=Integer.parseInt(request.getParameter("id"));
		User user=service.getUser(id);//这里不用new User，因为service.getUser()方法中有了。
		
		/*转发提交到request,到相应jsp页面显示*/
		request.setAttribute("user", user);	/*将取出来的user对象保存在request域里面*/
		request.getRequestDispatcher("/WEB-INF/update/Update.jsp").forward(request, response);//转发到jsp页面显示
	}
	
	
	/*注册(addMember)*/
	/*将输入的数据添加到数据库,这里是控制部分，实现部分在Service*/
	//注册默认注册个人用户（管理员用户不可注册）（因为一个网站，或者系统，管理员相对个人用户来说，人数相对固定，所以这里先不考虑注册管理员）
	public void RegisterUserServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name=request.getParameter("name");
		String pwd=request.getParameter("pwd");
		String sex=request.getParameter("sex");
		
		/*
		//limit和isAdmin在jsp并没有传值过来，这里是无法取值的，所以构造函数那里直接写null
		int limit=Integer.parseInt(request.getParameter("limit"));
		int isAdmin=Integer.parseInt(request.getParameter("isAdmin"));
		*/
		
		User user=new User(null,name,pwd,sex,null,null);//id为integer（不写成int）
		boolean result = service.RegisterUserServlet(user);// true / false
		if(result) {	//成功添加
			String message="注册成功";
			
			/*将消息message转到一个jsp页面success.jsp，在页面打印一个“成功”消息*/
			request.setAttribute("message", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		else {	//输入错误（有可能是数据库出错，有可能是自己输错id）
			//输入错误，再次加载页面让用户重新输入
			String message="注册失败";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/add/Register.jsp").forward(request, response);
		}
		
	}
	
	/*这个方法只是说：点击Add member按钮后跳到一个输入界面memberinput.jsp，然后在输入界面再有一个超链接addMember.do，
	 用submit触发，点击后跳转到相应的方法addMember()实现将数据添加到数据库的功能*/
	public void RegisterUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("/WEB-INF/add/Register.jsp").forward(request, response);
	}
	
	
	/*管理员权限（回收长期不用的用户帐号）*/
	//根据id删除用户
	public void DeleteUserServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id=Integer.parseInt(request.getParameter("id"));//这里的“id”跟超链接问号？后面的“id”字符串相同
		service.DeleteUserServlet(id);//调用方法实现删除
		ShowAllUserServlet(request,response);//再次显示（最新）页面
	}
	

	
	public void ShowAllUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		java.util.List<User> users=service.getAllUsers();//获取UserService中查询结果
		request.setAttribute("users",users);//把获取到的users放到request里	
		System.out.println("进入ShowAllUserServlet");
		/*转交给jsp页面显示*/
		request.getRequestDispatcher("/WEB-INF/admin/ShowAllUser.jsp").forward(request, response);
	}
	
	
	/*受限制的会员（无法浏览汽车信息，只能登录）*/
	public void LimitUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));//这里的“id”跟超链接问号？后面的“id”字符串相同
		System.out.println("在servlet获取到要限制的用户id="+id);
		int limit=Integer.parseInt(request.getParameter("limit"));
		
		if(limit==0) {//limit=0,正常用户，就可以进行限制
			service.LimitUser(id);//根据用户id去管理限制操作
			
			String message="已将该用户设置为 受限会员 ";
			System.out.println("已将该用户设置为 受限会员 ");
			/*将消息message转到一个jsp页面success.jsp，在页面打印一个“成功”消息*/
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/Success.jsp").forward(request, response);
		}
		else {//limit=1,已经是受限用户，不可重复操作
			String message="该用户已经是 受限会员，不可重复操作 ";
			System.out.println("该用户已经是 受限会员，不可重复操作 ");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/Fail.jsp").forward(request, response);
			
		}
	}
	
	
	
	/*根据个人id查询个人信息（个人用户）*/
	public void SearchUserSelfById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));//从jsp页面传过来，用getParameter
		System.out.println("SearchUserSelfById()----- id="+id);
		
		//id,name,sex都要从这个servlet传过去ShowUserInfoSelf.jsp，那边才能显示(注意：要从这里传！)
		//但是这里没有name，sex数据，所以要和id一个方法，从别的servlet传到另一个jsp，然后再从那个jsp传过来
		//或者在这个servlet里面根据刚刚获取到的id去 获取name，sex值！
		request.setAttribute("id", id);//把id放在request域中，然后在ShowUserInfoSelf可以获取到，显示用
		User user=service.getUser(id);//这里不用new User，因为service.getUser()方法中有了。
		
		/*转发提交到request,到相应jsp页面显示*/
		request.setAttribute("user", user);	/*将取出来的user对象保存在request域里面*/
		
		String message="个人信息查询成功";
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/user/ShowUserInfoSelf.jsp").forward(request, response);
		
	}
	
	
	
	
	
	//个人用户信息修改成功后回到这里
	/*更改个人信息（个人用户）---------管理员用户那边既可以改普通用户的信息包括密码，也可以改自己的，所以没必要再写一个独立的servlet*/
	public void UpdateUserInfoSelfServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		/*获取从 Update.jsp 页面输入的信息（用户修改后的信息）*/
		System.out.println("进入 UpdateUserInfoSelfServlet ");
		int id=Integer.parseInt(request.getParameter("id"));//从jsp页面传过来，用getParameter
		System.out.println("UpdateUserInfoSelfServlet()----- id="+id);
		String name=request.getParameter("name");
		String pwd=request.getParameter("pwd");
		String sex=request.getParameter("sex");
		System.out.println("name="+name+"  pwd="+pwd+"  sex="+sex);
	
		/*不能在这里获取limit 和 isAdmin ，会报错
		//limit和isAdmin在jsp并没有传值过来，这里只是为了满足构造函数
		int limit=Integer.parseInt(request.getParameter("limit"));
		int isAdmin=Integer.parseInt(request.getParameter("isAdmin"));
		 */
		System.out.println("在UpdateUserServlet已获取到数据");
		User user=new User(id,name,pwd,sex,0,0);//User类必须有个带参构造方法(因为这是在个人信息修改的方法里，所以直接把limit和isAdmin默认0)
		service.UpdateUserServlet(user);//Userservice的同名方法
		System.out.println("update successfully");
		String message="个人信息修改成功";
		request.setAttribute("message", message);
		
		//修改成功回到显示个人信息的界面
		request.setAttribute("user",user);//传回ShowUserInfoSelf.jsp，不然那边无法显示更新后的信息
		request.getRequestDispatcher("/WEB-INF/user/ShowUserInfoSelf.jsp").forward(request, response);
		
	}
	
	//个人用户修改信息触发界面(跳转到修改的界面)
	public void editUserSelfInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id=Integer.parseInt(request.getParameter("id"));//从jsp页面获取"----- /editUserSelfInfo.do?id=${ requestScope.user.id }  -----  "
		User user=service.getUser(id);//这里不用new User，因为service.getUser()方法中有了。
		
		/*转发提交到request,到相应jsp页面显示*/
		request.setAttribute("user", user);	/*将取出来的user对象保存在request域里面*/
		request.getRequestDispatcher("/WEB-INF/update/UpdateUserInfoSelf.jsp").forward(request, response);//转发到jsp页面显示
	}


	//回到USerHomePage
	public void USerHomePageServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id=Integer.parseInt(request.getParameter("id"));//从jsp页面获取"----- /editUserSelfInfo.do?id=${ requestScope.user.id }  -----  "
		String message="个人首页";
		
		request.setAttribute("id", id);//设置id到request域，在每个个人用户登录后分别显示他个人的信息（通过这个id去获取）
		request.setAttribute("message", message);
		//转发到个人用户主页UserHomePage.jsp
		request.getRequestDispatcher("/WEB-INF/user/UserHomePage.jsp").forward(request, response);//转发到成功页面
	
	}
	
	
	

	
	
	//（管理员）显示所有车辆信息（比普通用户多了“屏蔽”功能，即 “留言+屏蔽” ）
	//管理员可以看到所有车辆信息，包括未发布的
	public void ShowAllCarForAdminServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("进入ShowAllCarForAdminServlet()");
		List<Car> cars=new ArrayList<Car>();
		System.out.println("下面调用 getAllCarsList()");
		cars=serviceOfCar.getAllCarsList();//获取到所有车主所有车辆
		System.out.println("ShowAllCarForAdminServlet()-----获取到cars列表");
		String message="所有用户的二手车信息页面";		//(管理员页面)
		
		request.setAttribute("message", message);
		request.setAttribute("cars", cars);
		request.getRequestDispatcher("/WEB-INF/admin/ShowAllCarForAdmin.jsp").forward(request, response);
	
	}
	
	
	
	
	
	//管理员对虚假信息进行清除（直接删除）
	public void DeleteFakeCarInfoServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int carid=Integer.parseInt(request.getParameter("carid"));
		serviceOfCar.DeleteCarServlet(carid);//调用方法实现删除
		ShowAllCarForAdminServlet(request,response);//再次显示（最新）页面
	}
	
	
	
	
}
