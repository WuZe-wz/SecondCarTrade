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
<h4>操作执行失败</h4>
-->
<!-- 注意：需要转到一个servlet，然后让servlet再转发到另一个jsp显示
不能直接在这个jsp转到其他jsp -->
<a href="${pageContext.request.contextPath}/ShowAllUserServlet.do">显示所有信息（回到主界面）</a>
</div>
</body>
</html>