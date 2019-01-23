<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://org.wangxg/jsp/extl" prefix="e" %>
<c:set var="path" value="${pageContext.request.contextPath }" />

<html>
<head>
	<title>��ɫ����</title>
	<style type="text/css">
		td {
			height: 28px;
		}
	</style>
	<script type="text/javascript">
		var count = 0;
		function onSelect(obj) 
		{
			obj?count++:count--;
			document.getElementById("next2").disabled=(count==0);
			
			var n3 = document.getElementById("next3");
			if(n3!=null)
			{
			n3.disabled=(count==0);
			}
		}
		
		function onEdit(obj) 
		{
			var f = document.getElementById("myform");
			f.action="${path}/s1013.html?ssa201="+obj;
			f.submit();
		}
		
		function onDelete(obj) 
		{
			var f = document.getElementById("myform");
			f.action="${path}/s1012.html?ssa203=3&next=u&idlist="+obj;
			f.submit();
		}
	</script>
	
</head>


<body>
<e:message/>
<br>
<br>
<form id="myform" action="${path }/s1011.html" method="post">
	<!-- ��ѯ���� -->
	<table border="1" width="95%" align="center">
	<caption>
		��ɫ����
		<hr width="160">
	</caption>
	<tr>
		<td colspan="4">��ѯ����</td>
	</tr>
	<tr>
		<td width="15%">��ɫ����</td>
		<td width="25%">
			<e:text name="qssa202" autofocus="true"/>
		</td>
		<td width="15%">��ɫ״̬</td>
		<td width="45%">
			<e:select name="qssa203" value="��Ч:1,ͣ��:2" header="true"/>
		</td>
	</tr>
	</table>
	
	<!-- ���ݵ��� -->
	<table border="1" width="95%" align="center">
		<tr>
			<td></td>
			<td>���</td>
			<td>��ɫ����</td>
			<td>��ɫ״̬</td>
			<td>��ɫ����</td>
			<td></td>
		</tr>
		<c:choose>
			<c:when test="${requestScope.rows!=null }">
				<c:forEach items="${rows }" var="ins" varStatus="vs" >
				<tr>
					<td>
						<input type="checkbox" name="idlist" value="${ins.ssa201 }"
						onclick="onSelect(this.checked)">
					</td>
					<td>${vs.count }</td>
					<td>
						<a href="#" onclick="onEdit('${ins.ssa201}')">${ins.ssa202 }</a>
					</td>
					<td>${ins.cnssa203 }</td>
					<td>${ins.ssa204 }</td>
					<td>
						<a href="#" onclick="onDelete('${ins.ssa201}')">[ɾ��]</a>
					</td>
				</tr>	
				</c:forEach>
				<c:forEach begin="${fn:length(requestCope.rows)+1 }" step="1" end="10">
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach begin="1" step="1" end="10">
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
	
	<!-- ���ܰ�ť -->
	<table border="1" width="95%" align="center">
		<tr>
			<td align="center">
				<input type="submit" name="next" id="next0" value="��ѯ">
				<input type="submit" name="next" id="next1" value="���" 
				formaction="${path }/S1011.jsp">
				<c:if test="${requestScope.rows!=null }">
					<input type="submit" name="next" id="next2" value="ɾ��"
					disabled="disabled" formaction="${path }/s1012.html?ssa203=3">
					<c:if test="${param.qssa203=='2' }">
						<input type="submit" name="next" id="next3" value="����"
						disabled="disabled" formaction="${path }/s1012.html?ssa203=1">
					</c:if>
					<c:if test="${param.qssa203=='1' }">
						<input type="submit" name="next" id="next3" value="ͣ��"
						disabled="disabled" formaction="${path }/s1012.html?ssa203=2">
					</c:if>
				</c:if>
			</td>
		</tr>
	</table>
	
</form>
</body>
</html>