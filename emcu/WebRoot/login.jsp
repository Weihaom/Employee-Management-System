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
<form id="myform" action="${path }/loginServlet.xhtml" method="post">
  <table border="1" width="20%" align="center">
    <caption>
                 �û���¼        
      <hr width="160">
    </caption>
    <tr>
      <td width="40px" text-align="center">��¼��</td>
      <td width="60px">
         <input type="text" name="loginName">
      </td>
    </tr>
    <tr>
      <td width="40px" align="center">����</td>
      <td width="60px">
        <input type="password" name="pwd">
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
         <input type="submit" name="next" value="��¼"> 
      </td>
    </tr>
  </table>
</form>
</body>
</html>