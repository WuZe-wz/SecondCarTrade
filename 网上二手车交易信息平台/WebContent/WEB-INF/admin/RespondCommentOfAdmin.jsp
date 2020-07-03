<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.setwh{
		border: 1px solid #99ccff; 
        padding: 7px 0px;
        border-radius: 3px;
	}
</style>


</head>
<body background="image/image.jpg">
<div style="text-align:center;vertical-align:middel;">
<h2>${requestScope.message}</h2>
<!-- sumit提交 触发 显示首页 ，在汽车首页更新留言（留言是汽车table的一个属性（相当于更新表信息）） -->
<!-- 完成留言，回到主页（显示自己所有车信息）---要先去一个servlet调用 方法去更新数据库的留言属性 -->
<form action ="${pageContext.request.contextPath}/AddResondOfAdminComment.car?carid=${requestScope.car.carid }&userid=${requestScope.car.userid}" method="post">
		

		
		<!-- 备份 --> 
		<input type="hidden" class="setwh" name="userid" value="${requestScope.car.userid }"/>
		<input type="hidden" class="setwh" name="carid" value="${requestScope.car.carid }"/>
	
		<!-- 只读显示 -->
		userid:&emsp;<input type="text" class="setwh" disabled="disabled" name="userid" value="${requestScope.car.userid }"/><br><br>
		carid:&emsp;&nbsp;<input type="text" class="setwh" disabled="disabled" name="carid" value="${requestScope.car.carid }"/><br><br>
		 
		<!-- 回显之前的留言信息，这样就可以在原留言上面增加，而不会覆盖（这是一种比较巧的做法吧） -->
		<!-- 刚开始写成textarea的形式，value的回显无法显示，改成input就可以了  并设置 class='setwh'可以调整长宽 -->
		
		<!--在后面的${requestScope.car.carcomment}显示原来carcomment的内容 ，不是在value-->
		留言：<textarea name="carcomment"  style="width:300px;height:100px;border: 1px solid #99ccff;" >${requestScope.car.carcomment}***** ${requestScope.adminrespond }:</textarea><br><br>
		
		
		<input type="reset" value="清空">&emsp;&emsp;<input type="submit" value="提交"/>
	</form>

</div>
</body>
</html>