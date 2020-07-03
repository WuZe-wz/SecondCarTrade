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
<h2>${requestScope.message}</h2>
<!--  
<h4>(car)操作执行失败</h4>
-->
<!-- 跳转到servlet去转发到主页 -->
<a href="${pageContext.request.contextPath}/CarsManageHomePageServlet.car">显示我的车辆首页（主界面）</a>
</div>
</body>
</html>