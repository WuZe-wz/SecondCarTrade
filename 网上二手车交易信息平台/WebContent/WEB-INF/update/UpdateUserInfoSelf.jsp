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
	<h2>个人用户修改信息界面</h2>
	<br>
	<!-- value="${requestScope.user.id }"  是做数据的回显 -->
		<form action ="${pageContext.request.contextPath}/UpdateUserInfoSelfServlet.do" method="post">
			
			<input type="hidden" class="setwh" name="id" value="${requestScope.user.id }"/><!-- 备份 --> 
			id:&emsp;&emsp;&emsp;&emsp;<input type="text" class="setwh" disabled="disabled" name="id" value="${requestScope.user.id }"/><br><br><!-- 只读显示 -->
		
			Name:&emsp;&emsp;&nbsp;<input type="text" class="setwh" name="name" value="${requestScope.user.name }"/><br><br>
			Password:&emsp;<input type="text" class="setwh" name="pwd" value="${requestScope.user.pwd }"/><br><br>
			Sex:&emsp;&emsp;&emsp;&nbsp;<input type="text" class="setwh" name="sex" value="${requestScope.user.sex }"/><br><br>
			&emsp;&emsp;&emsp;&emsp;<input type="submit"  class="setwh" value="Submit"/>
		</form>
</div>
</body>
</html>