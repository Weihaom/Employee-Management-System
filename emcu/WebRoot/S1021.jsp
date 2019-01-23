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
      <td>用户姓名</td>
      <td>
      	<e:text name="ssa102" required="true" autofocus="true" defval="${requestScope.ins.ssa102 }"/>
      </td>
    </tr>
    <tr>
      <td>用户角色</td>
      <td>
      	<e:groupbox name="roleList" value="ocssa201" defval="${requestScope.ins.rolelist }"/>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
         <input type="submit" name="next" value="${empty param.ssa101?'添加':'修改' }" 
               formaction="${path }/s102${empty param.ssa101?'1':'4'}.html">
               
         <input type="submit" name="next" value="返回" formnovalidate="formnovalidate" 
         formaction="${path }/s1022.html">
      </td>
    </tr>
  </table>
  <input type="text" name="ssa101" value="${param.ssa101 }">
</form>
</body>
</html>