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
<!--  
<h4>${requestScope.message}</h4>
-->
<div style="text-align:center;vertical-align:middel;">
<h2>车辆修改信息界面</h2>
<br>
	<form action="${pageContext.request.contextPath}/UpdateCarSelfServlet.car?userid=${requestScope.car.userid}&carid=${requestScope.car.carid}" method="post">
	
	<!-- 备份 
	<input type="hidden" name="userid" value="${requestScope.car.userid }"/>
	<input type="hidden" name="carid" value="${requestScope.car.carid }"/>
	-->
	<!--  
		<input type="text" disabled="disabled" name="userid" value="${requestScope.car.userid }"/>
		<input type="text" disabled="disabled" name="carid" value="${requestScope.car.carid }"/>
	-->
		输入汽车品牌：&emsp;&emsp;<input class="setwh" name="brand" type="text" value="${requestScope.car.brand}"><br><br>
		输入汽车型号：&emsp;&emsp;<input class="setwh" name="model" type="text" value="${requestScope.car.model}"><br><br>
		输入车辆价格(万)：&nbsp;&nbsp;<input class="setwh" name="price" type="text" value="${requestScope.car.price}"><br><br>
		输入车辆颜色：&emsp;&emsp;<input class="setwh" name="color" type="text" value="${requestScope.car.color}"><br><br>
		<!--留言这样写不会回显  
		备注（留言）：<textarea name="carcomment" row="5" cols="30" value="${requestScope.car.carcomment}"></textarea><br><br>
		-->
		备注（留言）：&emsp;<textarea class="setwh" name="carcomment" style="width:300px;height:100px;" >${requestScope.car.carcomment}</textarea><br><br>
		<br>
		&emsp;&emsp;&emsp;&emsp;<input type="submit" value="Submit"/>

	</form>
</div>
</body>
</html>