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
<div style="text-align:center;vertical-align:middel;"><br>
<h2>${requestScope.message}</h2>
<!--  
<h4>所有用户的二手车信息页面（管理员界面）</h4>
-->
<br>
<table border="1" cellspacing="0" cellpadding="5" align="center">
	<tr>
	<th>userid</th>
	<th>carid</th>
	<th>brand</th>
	<th>model</th>
	<th>price</th>
	<th>datePosted</th>
	<th>canMessage</th>
	<th>carcomment</th>
	<th>isfake</th>
	<th>留言回复</th>
	<th>屏蔽</th>
	</tr>


	<c:if test="${!empty requestScope.cars }">
		<c:forEach items="${requestScope.cars }" var="percar">
		
			<tr>
			<td>${percar.userid }</td>
			<td>${percar.carid }</td>
			<td>${percar.brand }</td>
			<td>${percar.model }</td>
			<td>${percar.price }</td>
			<td>${percar.datePosted }</td>
			<td>${percar.canmessage }</td>
			<td>${percar.carcomment }</td>
			<td>${percar.isfake }</td>
			
			
			<!-- 留言 -->
			<td>
			<a href="RespondCommentOfAdminServlet.car?userid=${percar.userid}&carid=${percar.carid}&carcomment=${percar.carcomment}&canmessage=${percar.canmessage }">留言回复</a>
			</td>
	
			<!-- 屏蔽该用户信息 -->
			<td>
			<a href="DeleteFakeCarInfoServlet.do?carid=${percar.carid}">屏蔽该车辆信息</a>
			</td>
			
			</tr>
		</c:forEach>
	</c:if>
</table>
<br>
	<!-- 返回上一层（主页） -->
	<a href="${pageContext.request.contextPath}/returnAdminHomePage.do">返回管理员主页</a><br>
		

</body>
</html>