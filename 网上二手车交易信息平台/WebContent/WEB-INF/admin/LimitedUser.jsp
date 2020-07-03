<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body background="image/image.jpg"s>
<div style="text-align:center;vertical-align:middel;">
<h2>${requestScope.MESSAGE}</h2>
<h4>登录成功,但您的会员资格受限，无法访问汽车信息</h4>
<a href="${pageContext.request.contextPath}/LoginUserFailServlet.do">回到登录界面</a>
</div>
</body>
</html>