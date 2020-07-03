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
<a href="${pageContext.request.contextPath}/ShowAllUserServlet.do">显示所有信息（主界面）</a>
</div>
</body>
</html>