<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body background="image/image.jpg">
<div style="text-align:center;vertical-align:middel;">
<h2>个人用户主页</h4>
<h5>${requestScope.message}</h5>
<br>

	<table border="1" cellspacing="0" cellpadding="20" align="center" border="100" width="400">
	
	<tr><td>
	<!-- 表格数据获取 --><!-- 显示个人信息（个人用户） -->
	<a href="${pageContext.request.contextPath}/SearchUserSelfById.do?id=${requestScope.id}">显示个人信息</a>
	</td></tr>
	
	
	<!-- 传id，定位到某个用户 -->
	<tr><td>
	<a href="CarsManageHomePageServlet.car?userid=${requestScope.id}">二手车管理首页</a>
	</td></tr>
		
		
	<!-- 退出登录，回到登录页面 -->
	<tr><td>
	<a href="returnLoginPage.do">退出登录</a>
	</td></tr>
	
	</table>
</div>
</body>
</html>