<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://org.wangxg/jsp/extl" prefix="e" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="path" value="${pageContext.request.contextPath }"/>
<html>
<head>
   <title>Insert title here</title>
   <style type="text/css">
    td{
       height:28px;
    }
    select {
		height: 22px;
		width: 174px;
	}
   </style> 
   <script type="text/javascript">
		function onEdit(obj) {
			var a = document.getElementById('myform');
			a.action="${path }/s1023.html?ssa101="+obj;
			a.submit();
		}
		function onSelect(obj) 
		{
			var count = 0;
			obj?count++:count--;
			document.getElementById("next2").disabled = (count==0);	
		}
		function onDelete(obj) 
		{
			var a = document.getElementById('myform');
			a.action = "${path }/s1025.html?idlist="+obj;
			a.submit();
		}
	</script>
</head>
<body>
<e:message/>
<br>
<br>
<form id="myform" action="" method="post">
 <!-- 查询条件 -->
 <table border="1" width="95%" align="center">
   <caption>
      	 用户管理
     <hr width="160">
   </caption>
   <tr>
     <td colspan="4">查询条件</td>
   </tr>
   <tr>
     <td>用户姓名</td>
     <td>
     	<e:text name="qssa102" autofocus="true"/>
     </td>
     <td>登录名称</td>
     <td>
       <e:text name="qssa103"/>
     </td>
   </tr>
   <tr>
     <td>用户角色</td>
     <td>
     	<e:select value="ocssa201" name="qssa201" header="true"/>
     </td>
     <td>用户状态</td>
     <td width="360px">
     	<e:select value="ocssa105" name="qssa105" header="true"/>
     </td>
   </tr>
 </table>

 <!-- 数据迭代 -->
 <table border="1" width="95%" align="center">
   <tr>
     <td></td>
     <td>序号</td>
     <td>用户姓名</td>
     <td>用户登录名</td>
     <td>用户状态</td>
     <td>用户角色</td>
     <td></td>
   </tr>
   <c:choose>
     <c:when test="${rows!=null }">
        <c:forEach items="${rows }" var="ins" varStatus="vs">
		   <tr>
		     <td>
		     	<input type="checkbox" name="idlist" value="${ins.ssa101 }" onclick="onSelect(this.checked)">
		     </td>
		     <td>${vs.count }</td>
		     <td>${ins.ssa102 }</td>
		     <td><a href="#" onclick="onEdit(${ins.ssa101})">${ins.ssa103 }</a></td>
		     <td>${ins.cnssa105 }</td>
		     <td>${ins.rolelist }</td>
		     <td>
		     	<a href="#" onclick="onDelete(${ins.ssa101 })">[删除]</a>
		     </td>
		   </tr>
        </c:forEach>
        <c:forEach begin="${fn:length(rows)+1 }" step="1" end="10">
		   <tr>
		     <td></td>
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
		     <td></td>
		     <td></td>
		   </tr>
       </c:forEach>
     </c:otherwise>
   </c:choose>
 </table>
 <!-- 功能按钮 -->
 <table border="1" width="95%" align="center">
   <tr>
     <td align="center">
       <input type="submit" name="next" id="next0" value="查询" formaction="${path }/s1022.html">
       <input type="submit" name="next" id="next1" value="添加" 
       formaction="${path }/s1020.html?path=S1021">
       <input type="submit" name="next" id="next2" value="删除" 
       disabled="disabled" formaction="${path }/s1025.html" >
     </td>
   </tr>
 </table>
</form> 
</body>
</html>




