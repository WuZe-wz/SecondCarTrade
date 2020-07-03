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
<br>
 <!-- 管理员留言 -->
<a href="${pageContext.request.contextPath}/ShowAllCarForAdminServlet.do">显示平台所有车辆信息界面</a>
</div>
</body>
</html>