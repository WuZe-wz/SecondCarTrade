<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 
border: 1px solid #ccc;  设置输入框边框，边缘线的颜色、大小、及实线虚线
padding: 7px 0px; 设置输入框高度，也可以用height，但是用height的话，输入框的光标会置于顶部，还要设置其他样式去固定，而且还不一定会兼容很好
border-radius: 3px; 圆角
padding-left:5px;  距离左边5个像素

 -->
<style type="text/css">
	.setwh{
		border: 1px solid #99ccff; 
        padding: 7px 0px;
        border-radius: 3px;
	}
	

</style>

<title>login page</title>
</head>
<body background="image/image.jpg">
<br>
<!-- 设置居中显示 -->
<div style="text-align:center;vertical-align:middel;">

<h1>网上二手车交易信息平台</h1>
	<form action ="${pageContext.request.contextPath}/LoginUserServlet.do" method="post" >
		姓名:<input class="setwh" type="text" name="name" value="${ param.name }"/><br><br>
		密码:<input class="setwh" type="password" name="pwd" value="${ param.pwd }"/><br><br>
		<select name="userType" >
             <option value="-1">请选择</option>
             <option value="0">会员</option>
             <option value="1">管理员</option>
		 </select>
		 <br><br>
				
		<input type="submit" value="登录"/>&emsp;&emsp; <input type="reset" value="重置"><br>
	</form>
	<br>

	<!-- 跳转到注册的jsp页面显示----------报错，不能直接在这里转到WEB-INF下的jsp，因为是安全目录（需要通过servlet） 
	<form action="/WEB-INF/view/Register.jsp">
		<input type="submit" value="New User Register">
	</form>
-->
 
 <!-- 转到WebContet(根目录就可以)
 <form action="test.jsp">
		<input type="submit" value="New User Register">
	</form>
-->

<!-- 利用Servlet ,在Servlet转发到Register.jsp-->
	<form action="RegisterUser.do" method="post">
		<input type="submit" value="新用户注册">
	</form>
</div>
<br>
</body>
</html>