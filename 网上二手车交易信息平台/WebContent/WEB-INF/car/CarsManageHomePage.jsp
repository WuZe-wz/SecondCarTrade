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
	
	<br>
	<h1>二手车管理首页</h1>
	<!-- 在这里可以通过requestScope.user.id 获取汽车发布者的用户id-->
	<br><br><br>
	
	
	
	
	
		<!-- ShowAllCarSelf.jsp里面设置发布按钮 -->
		<!-- 将id传到 AddCarPageServlet ，在那边可以通过getParameter获取-->
		<!-- （添加到个人仓库，尚未发布） -->
		<a href="AddCarPageServlet.car?userid=${requestScope.userid}">添加二手车</a>
		<br><br><br>
		
		
	
		
		<!-- 本地仓库的二手车界面有 " 发布 " 按钮，可以发布该车 -->
		<!-- 可在这个界面查看留言，并触发超链接去到 " 另一个jsp、servlet " 去回复留言  (支持多条留言？)-->
		<!-- 在这个节目还可删除指定车（未发布/已发布都可删除） -->
		<!-- 显示并操作我的仓库的二手车（未发布） -->
		<a href="ShowAllLocalCarSelfServlet.car?userid=${requestScope.userid}">我的二手车仓库</a>
		<br><br><br>
		
		
		<!-- 显示所有车主所有车辆信息（已发布车辆） -->
		<a href="ShowAllCarsServlet.car?userid=${requestScope.userid}">显示平台所有车辆信息</a>
		<br><br><br>
		
		<!-- 回到个人首页 -->
		<a href="USerHomePageServlet.do?id=${requestScope.userid}">回到个人首页</a>
		
	</form>
</div>
</body>
</html>