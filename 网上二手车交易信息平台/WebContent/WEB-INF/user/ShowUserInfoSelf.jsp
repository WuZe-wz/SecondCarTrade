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
	<h5>${requestScope.message}</h5>
	<br><br><br>
	<!-- 显示当前用户的个人信息（个人用户） -->
	<table border="1" cellspacing="0" cellpadding="20" align="center" border="100" width="400">
	<caption>我的信息</caption>
	<!-- 显示表头 -->
		<tr>
		<th>id</th>
		<th>name</th>
		<th>sex</th>
		<th>updateInfo</th>
		</tr>
		
		<!-- 密码不显示 -->
		<tr>
			<td>${requestScope.user.id }</td>
			<td>${requestScope.user.name }</td>
			<td>${requestScope.user.sex }</td>
		
		<!-- 修改信息 -->
			<td><!-- 记得将id传过去servlet，不然servlet那边不知道id，无法处理业务 -->
			<a href ="${pageContext.request.contextPath}/editUserSelfInfo.do?id=${ requestScope.user.id }">Update Info</a>
			</td>
			
		
		</tr>
				
	</table>
	<br>
	<a href ="${pageContext.request.contextPath}/USerHomePageServlet.do?id=${ requestScope.user.id }">返回上一层</a>
</div>
</body>
</html>