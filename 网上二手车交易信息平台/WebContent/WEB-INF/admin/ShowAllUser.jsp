<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="com.bean.User"%>
<%@page import="com.bean.UserSearchConditions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style type="text/css">
	.setwh{
		border: 1px solid #99ccff; 
        padding: 7px 0px;
        border-radius: 3px;
	}
</style>

<title>Insert title here</title>
</head>
<body background="image/image.jpg">
<div style="text-align:center;vertical-align:middel;">
<h2>所有个人用户信息</h2><br>

<!-- (模糊)查询用户信息（只显示名字和姓名（管理员权限）） -->
<h5>模糊查询用户信息</h5>
	<form action ="${pageContext.request.contextPath}/SearchUserServlet.do" method="post">
		Name:&emsp;<input class="setwh" type="text" name="name" value="${ param.name }"/><br><br>
		Sex:&emsp;&emsp;<input class="setwh" type="text" name="sex" value="${ param.sex }"/><br><br>
		<input type="submit" value="Search"/>
	</form>
	
	
<br>
<table border="1" cellspacing="0" cellpadding="10" align="center" border="100" width="500">
	<tr>
	<th>name</th>
	<th>sex</th>
	<th>limit</th>
	<th>删除</th>
	<th>修改</th>
	<th>取消会员资格</th>
	</tr>
<!-- 下面的users是ShowAllUserServlet中 "request.setAttribute("users",users);//把获取到的users放到request里" 设置的 -->
	<!-- 这里(ShowAllUserServlet转发的jsp页面中)可以直接获取 -->
	<c:if test="${!empty requestScope.users }">
		<c:forEach items="${requestScope.users }" var="peruser">
		
		<tr>
		<td>${peruser.name }</td>
		<td>${peruser.sex }</td>
		<td>${peruser.limit }</td>
		<td>
			<a href ="${pageContext.request.contextPath}/DeleteUserServlet.do?id=${ peruser.id }">Delete User</a>	
		</td>
		
		<td><!-- 记得将id传过去servlet，不然servlet那边不知道id，无法处理业务 -->
		<a href ="${pageContext.request.contextPath}/editUser.do?id=${ peruser.id }">Update User</a>
		</td>
		
		<!-- 记得将id传过去servlet，不然servlet那边不知道id，无法处理业务 ,,,limit也要传过去，因为那边需要获取-->
		<td><!-- 受限用户管理（只允许登录，无法进行其他操作（无法浏览汽车信息），即取消会员资格） -->
		<a href="${pageContext.request.contextPath}/LimitUser.do?id=${ peruser.id }&limit=${peruser.limit}">Limit this user</a><br>
		</td>
		
		</tr>
		</c:forEach>
	</c:if>
	</table>
	<br>
	<!-- 返回上一层（主页） -->
	<a href="${pageContext.request.contextPath}/returnAdminHomePage.do">返回管理员主页</a><br>
</div>
</body>
</html>