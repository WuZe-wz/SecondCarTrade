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
<!--  <h4>${requestScope.message}</h4>-->

<br>
<table border="1" cellspacing="0" cellpadding="10" align="center" border="100" width="500">
<caption>平台所有二手车信息</caption>
	<tr>
	<th>userid</th>
	<th>carid</th>
	<th>brand</th>
	<th>model</th>
	<th>price</th>
	<th>datePosted</th>
	<th>canMessage</th>
	<th>carcomment</th>
	<th>respond</th>
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
			
			
	
	
			<!-- 带四个参数：用户id（定位用户），汽车id（定位到哪辆汽车），留言板 ，是否可留言-->
			<td>
			<a href="RespondCommentServlet.car?userid=${percar.userid}&carid=${percar.carid}&carcomment=${percar.carcomment}&canmessage=${percar.canmessage }&respondid=${requestScope.respondid}">留言回复</a>
			</td>
			
			</tr>
		</c:forEach>
	</c:if>
</table>
<br><br>
<!-- 返回主界面，车辆管理界面 -->
<a href="${pageContext.request.contextPath}/CarsManageHomePageServlet.car?userid=${requestScope.respondid }">返回主界面</a>
</div>
</body>
</html>