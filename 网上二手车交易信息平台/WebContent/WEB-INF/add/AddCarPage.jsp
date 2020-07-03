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
<h2>${requestScope.message}</h2><br>
<!--  
<h4>车辆添加界面 -- 添加二手车（添加到个人仓库，未发布）</h4>
-->

<br>
<!-- 这是添加界面，不是更新（修改）界面，所以不用做数据的回显（value = ...） -->

<!--
 日期默认设置为当前日期，不可手动添加，但需要提交到servlet处理
----(不需要手动提交，到时候Car car=new Car(null,...)写null就行)-----
 -->

<!-- 车主添加汽车时，也可进行留言（对车辆信息进行补充和备注） -->

<!-- submit提交 触发，转到AddCarServlet，然后再转到success.jsp -->
<!-- 将id传过去AddCarServlet ，利用” ?userid=${requestScope.user.id} “
在AddCarServlet获取的时候的参数就是这里问号后面的参数 userid -->
	<form action="${pageContext.request.contextPath}/AddCarServlet.car?userid=${requestScope.user.id}" method="post">
		输入汽车品牌：&emsp;&emsp;&nbsp;<input class="setwh" name="brand" type="text"><br><br>
		输入汽车型号：&emsp;&emsp;&nbsp;<input class="setwh" name="model" type="text"><br><br>
		输入车辆价格(万)：&nbsp;&nbsp;<input class="setwh" name="price" type="text"><br><br>
		输入车辆颜色：&emsp;&emsp;&nbsp;<input class="setwh" name="color" type="text"><br><br>
		备注（留言）：&emsp;<textarea class="setwh" name="carcomment" row="5" cols="30"></textarea><br><br><br>
		&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<input  type="reset" value="重置">
		&emsp;&emsp;&emsp;<input  type="submit" value="添加"><br>
		
	</form>

</div>
</body>
</html>