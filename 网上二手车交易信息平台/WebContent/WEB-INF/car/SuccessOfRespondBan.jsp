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
<h2>${requestScope.message}</h2><br>
<!-- 跳转到servlet去转发到主页 -->
<a href="${pageContext.request.contextPath}/ShowAllLocalCarSelfServlet.car?userid=${requestScope.userid}">返回上一层</a>
</body>
</html>