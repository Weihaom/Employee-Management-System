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
   </style>
</head>
<body>
<e:message/>
<br>
<br>
<form id="myform" action="" method="post">
 <!-- 查询条件 -->
 <table border="1" width="95%" align="center">
   <caption>
       xx管理
     <hr width="160">
   </caption>
   <tr>
     <td colspan="4">查询条件</td>
   </tr>
   <tr>
     <td></td>
     <td></td>
     <td></td>
     <td></td>
   </tr>
   <tr>
     <td></td>
     <td></td>
     <td></td>
     <td></td>
   </tr>
 </table>

 <!-- 数据迭代 -->
 <table border="1" width="95%" align="center">
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
   <c:choose>
     <c:when test="${rows!=null }">
        <c:forEach items="${rows }" var="ins" varStatus="vs">
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
        <c:forEach begin="${fn:length(rows)+1 }" step="1" end="10">
		   <tr>
		     <td></td>
		     <td>序号</td>
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
       <input type="submit" name="next" id="next0" value="查询">
       <input type="submit" name="next" id="next1" value="添加" formaction="${path }">
       <input type="submit" name="next" id="next2" value="删除" disabled="disabled" formaction="${path }" >
     </td>
   </tr>
 </table>
</form> 
</body>
</html>




