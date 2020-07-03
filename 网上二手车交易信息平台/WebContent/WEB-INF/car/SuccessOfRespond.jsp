<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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


<!-- 管理员/个人用户 选择，因为两者的留言功能用的是同一个servlet，但是"返回上一层"的界面两个角色是不一样的-->
<!-- 
<c:out value="${requestScope.user.isAdmin }"></c:out>


<c:choose>
<c:when test="${requestScope.user.isAdmin==0}">
	<a href="${pageContext.request.contextPath}/ShowAllCarsServlet.car?userid=${requestScope.userid}">显示平台所有车辆信息界面</a>
</c:when>

<c:when test="${requestScope.user.isAdmin==1}">
	<a href="${pageContext.request.contextPath}/ShowAllCarForAdminServlet.do">显示平台所有车辆信息界面</a>
</c:when> 

</c:choose>
 -->
 
 <!-- 个人用户留言 -->
<a href="${pageContext.request.contextPath}/ShowAllCarsServletAfterRespond.car?userid=${requestScope.userid}&respondid=${requestScope.respondid}">显示平台所有车辆信息界面</a>
 
 
</div>
</body>
</html>