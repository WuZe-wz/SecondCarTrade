<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="com.bean.User"%>
<%@page import="com.bean.Car"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body background="image/image.jpg">
<div style="text-align:center;vertical-align:middel;">
<!-- 如果该用户车库没有任何车辆，则无法获取到下面的数据，在这里做一个判断 -->
<c:if test="${empty requestScope.cars }">
	<c:out value="该用户车库没有车辆"><br></c:out>
</c:if>
<br><br>
<c:if test="${!empty requestScope.cars }">
	<table border="1" cellspacing="0" cellpadding="2" align="center" border="70" width="100%">
	<caption>我的二手车仓库</caption>
		<tr>
		<th>userid</th>
		<th>carid</th>
		<th>brand</th>
		<th>model</th>
		<th>price</th>
		<th>color</th>
		<th>datePosted</th>
		<th>isrelease</th>
		<th>canMessage</th>
		<th>carcomment</th>
		<th>update</th>
		<th>release</th>
		<th>delete</th>
		<th>respond</th>
		<th>Ban</th>
		</tr>
		
<!-- 带两个参数：用户id（定位用户），汽车id（定位到哪辆汽车）-->
<!--  		若已发布，点击修改按钮，显示错误，未发布则正常修改 -->


	
		<c:forEach items="${requestScope.cars }" var="percar">
		
			<tr>
			<td>${percar.userid }</td>
			<td>${percar.carid }</td>
			<td>${percar.brand }</td>
			<td>${percar.model }</td>
			<td>${percar.price }</td>
			<td>${percar.color }</td>
			<td>${percar.datePosted }</td>
			<td>${percar.isrelease }</td>
			<td>${percar.canmessage }</td>
			<td>${percar.carcomment }</td>
			
			<!-- 使用requestScope.user.id 在servlet取不到这边转过去的数值
				写成percar.userid就可以了（因为ShowAllLocalCarSelfServlet只传了cars到request过来，没有传users）
				-->
			<td>
			<a href="EditCarServlet.car?userid=${percar.userid}&carid=${percar.carid}">修改信息</a>
			</td>
			
			
			
			<!-- 带三个参数：用户id（定位用户），汽车id（定位到哪辆汽车） ，发布状态----- 传回ReleaseCarServlet -->
			<td>
			<a href="ReleaseCarServlet.car?userid=${percar.userid}&carid=${percar.carid}&userid=${percar.userid}&isrelease=${percar.isrelease}">发布该车辆</a>
			</td>
			
			
			
			
			<!-- 带两个参数：用户id（定位用户），汽车id（定位到哪辆汽车） -->
			<td><a href="DeleteCarServlet.car?userid=${percar.userid}&carid=${percar.carid}">删除该车辆</a>
			</td>
	
	
	
			<!-- 带三个参数：用户id（定位用户），汽车id（定位到哪辆汽车），留言板 -->
			<!-- 还要再带一个参数：获取是否有留言权限 -->
			<td>
			<a href="RespondCommentServlet.car?userid=${percar.userid}&carid=${percar.carid}&canmessage=${percar.canmessage}&canmessage=${percar.canmessage}">留言回复</a>
			</td>
			
			<td>
			<a href="RespondBan.car?userid=${percar.userid}&carid=${percar.carid}&canmessage=${percar.canmessage}">禁止留言</a>
			</td>
			
			
			</tr>
		</c:forEach>

	</table>
<br>
</c:if>
<br><br>
<!-- 返回上一层（个人车辆管理首页） CarsManageHomePage.jsp (只需传userid即可)(这里用EL表达式的数组访问形式+点访问)-->
&emsp;&emsp;<a href="CarsManageHomePageServlet.car?userid=${requestScope.userid}">返回上一层</a>
</div>
<br>		
</body>
</html>