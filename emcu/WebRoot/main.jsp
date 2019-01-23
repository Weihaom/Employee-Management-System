<%@ page language="java"  pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath }" />

<html>
<head>
	<title>主页面</title>
</head>

<body>
	<table border="1" align="center" width="95%">
		<tr>
			<td height="90" colspan="2" align="center">
    			 欢迎来到员工管理系统
			</td>
		</tr>
		<tr>
			<td width="18%" nowrap="nowrap" valign="top">
		<!--  	<a href="${path }/S1010.jsp" target="VIEW">角色管理</a><br>
				<a href="${path }/s1020.html" target="VIEW">用户管理</a><br>
				<a href="${path }/S1030.jsp" target="VIEW">菜单管理</a><br>
				<a href="${path }/s1040.html" target="VIEW">角色授权</a><br>         -->
				${userMenu }
			</td>
			<td >
				<iframe width="1600" height="800" name="VIEW"></iframe>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="20" align="center">
				员工管理平台(C)2019
			</td>
		</tr>
		
	</table>
</body>
</html>