<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="com.bean.User"%>
<%@page import="com.bean.UserSearchConditions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body background="image/image.jpg">
<br><br>
<div style="text-align:center;vertical-align:middel;">

<table border="1" cellspacing="0" cellpadding="10" align="center" border="100" width="500">
	<caption>查询结果</caption>
	<tr>
	<th>name</th>
	<th>sex</th>
	</tr>

	<c:if test="${!empty requestScope.users }">
		<c:forEach items="${requestScope.users }" var="peruser">
		
		<tr>
		<td>${peruser.name }</td>
		<td>${peruser.sex }</td>
		</tr>
		
		</c:forEach>
	</c:if>
</table><br><br>
<a href="${pageContext.request.contextPath}/ShowAllUserServlet.do">返回上一层</a>
</div>
</body>
</html>