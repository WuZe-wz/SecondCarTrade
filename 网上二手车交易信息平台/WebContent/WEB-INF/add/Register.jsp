<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style>
	.setwh{
		border: 1px solid #99ccff; 
        padding: 7px 0px;
        border-radius: 3px;
	}
</style>

<title>Register Page</title>
</head>
<body background="image/image.jpg">
<div style="text-align:center;vertical-align:middel;">
<h2>注册界面</h2>
	<form action="${pageContext.request.contextPath}/RegisterUserServlet.do" method="post">
		输入用户名：&emsp;<input class="setwh" name="name" type="text"><br><br>
		输入密码：&emsp;&nbsp;<input class="setwh" name="pwd" type="password"><br><br>
		选择性别：&emsp;<input type="radio" name="sex" value="男" checked>男
				&emsp;<input type="radio" name="sex" value="女">女<br><br>
		<br>
		<!--  
		备注：<br>
		<textarea name="info" row="5" cols="30" ></textarea><br>
		-->
		
		&emsp;&emsp;<input type="reset" value="重置">&emsp;&emsp;<input type="submit" value="注册">
		
	</form>
</div>
</body>
</html>