<%@ page language="java"  pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath }" />

<html>
<head>
	<title>��ҳ��</title>
</head>

<body>
	<table border="1" align="center" width="95%">
		<tr>
			<td height="90" colspan="2" align="center">
    			 ��ӭ����Ա������ϵͳ
			</td>
		</tr>
		<tr>
			<td width="18%" nowrap="nowrap" valign="top">
		<!--  	<a href="${path }/S1010.jsp" target="VIEW">��ɫ����</a><br>
				<a href="${path }/s1020.html" target="VIEW">�û�����</a><br>
				<a href="${path }/S1030.jsp" target="VIEW">�˵�����</a><br>
				<a href="${path }/s1040.html" target="VIEW">��ɫ��Ȩ</a><br>         -->
				${userMenu }
			</td>
			<td >
				<iframe width="1600" height="800" name="VIEW"></iframe>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="20" align="center">
				Ա������ƽ̨(C)2019
			</td>
		</tr>
		
	</table>
</body>
</html>