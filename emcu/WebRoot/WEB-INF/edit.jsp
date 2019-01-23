<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://org.wangxg/jsp/extl" prefix="e" %>
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
  <table border="1" width="55%" align="center">
    <caption>
                      角色${empty param.ssa101?'添加':'修改' }
      <hr width="160">
    </caption>
    <tr>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td colspan="2" align="center">
         <input type="submit" name="next" value="${empty param.ssa101?'添加':'修改' }" 
               formaction="${path }/${empty param.ssa101?'添加':'修改' }">
               
         <input type="submit" name="next" value="返回" formnovalidate="formnovalidate" formaction="${path }">
      </td>
    </tr>
  </table>
  <input type="hidden" name="ssa101" value="${param.ssa101 }">
</form>
</body>
</html>