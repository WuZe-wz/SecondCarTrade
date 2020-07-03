<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<!-- （这是管理员界面，修改后会转到ShowAllUserServlet） -->
<h4>信息修改界面</h4>
<br>
<!-- 注意：requestScope.user.id 是在editUser 放在request中的，是小写user -->
<!-- value="${requestScope.user.id }"  是做数据的回显 -->
	<form action ="${pageContext.request.contextPath}/UpdateUserServlet.do" method="post">
		
		
		
		<!-- 备份 --> 
		<input class="setwh" type="hidden" name="id" value="${requestScope.user.id }"/>
		
	
		<!-- 只读显示 -->
		id:&emsp;&emsp;&emsp;&emsp;<input class="setwh" type="text" disabled="disabled" name="id" value="${requestScope.user.id }"/><br>
		
		 <br>
		
		Name:&emsp;&emsp;&nbsp;<input class="setwh" type="text" name="name" value="${requestScope.user.name }"/><br><br>
		Password:&emsp;<input class="setwh" type="text" name="pwd" value="${requestScope.user.pwd }"/><br><br>
		Sex:&emsp;&emsp;&emsp;<input class="setwh" type="text" name="sex" value="${requestScope.user.sex }"/><br><br>
		&emsp;&emsp;<input type="submit" value="Submit"/>
	</form>
	<br><br>
	<!-- 管理员，才能显示所有用户信息（个人用户不行，所以个人用户需要再写一个UpdateUserInfoSelfServlet和UpdateUserInfoSelf.jsp） -->
<a href="${pageContext.request.contextPath}/ShowAllUserServlet.do">显示所有信息（主界面）</a>
</div>
</body>
</html>