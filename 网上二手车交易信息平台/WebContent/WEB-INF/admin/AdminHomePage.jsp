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
<h2>管理员主页</h2><br><br>
<!--  <h5>${requestScope.message}</h5> -->
<a href="${pageContext.request.contextPath}/ShowAllUserServlet.do">显示所有用户信息</a>
<br><br><br>

<a href="${pageContext.request.contextPath}/ShowAllCarForAdminServlet.do">显示所有二手车信息</a>
<br><br><br>
<!-- 退出登录，回到登录页面 -->
<a href="returnLoginPage.do">退出登录</a>
</div>
</body>
</html>