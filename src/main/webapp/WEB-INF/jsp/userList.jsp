<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="static/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('p>a').click(function(){
			var pageNow = $(this).attr('data');
			$('#pageNow').val(pageNow);
			$('form').submit();
		});
	});
</script>
</head>
<body>
	<form action="selectNewsByPage" method="post">
		<label>标题</label>
		<input type="text" name="title" value="${param.title}">
		<label>类别</label>
		<select name="ntid">
			<option value="-1">--请选择--</option>
			<option value="1"
				<c:if test="${param.ntid == 1}">
					selected="true"
				</c:if>
			>国内</option>
			<option value="2"
				<c:if test="${param.ntid == 2}">
					selected="true"
				</c:if>
			>国际</option>
			<option value="3"
				<c:if test="${param.ntid == 3}">
					selected="true"
				</c:if>
			>军事</option>
		</select>
		<input type="hidden" value="1" name="pageNow" id="pageNow">
		<input type="submit" value="查询">
	</form>
	<br>
	<table border="1" style="width: 80%">
		<tr>
			<th>序号</th>
			<th>标题</th>
			<th>作者</th>
			<th>类别</th>
			<th>时间</th>
		</tr>
		<c:forEach items="${pageInfo.list}" var="users">
			<tr>
				<td>${users.nid}</td>
				<td>${users.ntitle}</td>
				<td>${users.nauthor}</td>
				<td>${users.ntid}</td>
				<td>${users.ncreateDate}</td>
			</tr>
		</c:forEach>
	</table>
	<p>
		<a data="1" href="javascript:void(0);">首页</a>
		<a data="${pageInfo.pageNow-1 == 0 ? 1 : pageInfo.pageNow-1 }" href="javascript:void(0);">上一页</a>
		<span>${pageInfo.pageNow}/${pageInfo.totalePage}</span>
		<a data="${pageInfo.pageNow+1 > pageInfo.totalePage ? pageInfo.totalePage : pageInfo.pageNow+1}" href="javascript:void(0);">下一页</a>
		<a data="${pageInfo.totalePage}" href="javascript:void(0);">尾页</a>
	</p>
</body>
</html>