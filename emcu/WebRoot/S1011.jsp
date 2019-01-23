<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://org.wangxg/jsp/extl" prefix="e" %>
<c:set var="path" value="${pageContext.request.contextPath }" />

<html>
<head>
	<title>角色添加</title>
	<style type="text/css">
		td {
			height: 28px;
		}
	</style>
</head>

<body>
<e:message/>
<br>
<br>
	<form id="myform" action="" method="post">
		<table border="1" width="55%" align="center">
			<caption>
				角色${empty param.ssa201?'添加':'修改' }
				<hr width="160">
			</caption>
			<tr>
				<td>角色名称</td>
				<td>
					<e:text name="ssa202" required="true" autofocus="true"
					defval="${requestScope.ins.ssa202 }"/>
				</td>
			</tr>
			<tr>
				<td valign="top">角色描述</td>
				<td><e:textarea rows="10" cols="55" name="ssa204" 
				defval="${requestScope.ins.ssa204 }"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="next" value="${empty param.ssa201?'添加':'修改' }"
					formaction="${path }/s101${empty param.ssa201?'0':'4' }.html">
					<input type="submit" name="next" value="返回"  
					formnovalidate="formnovalidate" formaction="${path }/s1011.html">
				</td>
			</tr>
		</table>
		<input type="text" name="ssa201" value="${param.ssa201 }">
	</form>
	<e:text name="qssa202"/>
	<e:text name="qssa203"/>
</body>
</html>