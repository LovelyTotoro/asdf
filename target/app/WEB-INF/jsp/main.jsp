<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<meta charset="UTF-8">
<title>Insert title here</title>
  <body>
    <table border="1" style="width: 80%">
		<tr>
			<th>用户名</th>
			<th>密码</th>
			<th>Email</th>
			<th>信息</th>
		</tr>
		<c:forEach items="${userList}" var="users">
			<tr>
				<td>${users.getDevName()}</td>
				<td>${users.getDevPassword()}</td>
				<td>${users.getDevEmail()}</td>
				<td>${users.getDevInfo()}</td>
			</tr>
		</c:forEach>
	</table>
  </body>
</html>
