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
                      菜单${empty param.ssa401?'添加':'修改' }
      <hr width="160">
    </caption>
    <tr>
      <td>菜单编号</td>
      <td>
        <e:text name="ssa402" autofocus="true" defval="${requestScope.ins.ssa402 }" required="true"/>
      </td>
    </tr>
    <tr>
      <td>菜单名称</td>
      <td>
        <e:text name="ssa403"  defval="${requestScope.ins.ssa403 }" required="true"/>
      </td>
    </tr>
    <tr>
      <td>打开窗口</td>
      <td>
        <e:text name="ssa404" style="width:400px"  defval="${requestScope.ins.ssa404 }" required="true"/>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
         <input type="submit" name="next" value="${empty param.ssa401?'添加':'修改' }" 
               formaction="${path }/s103${empty param.ssa401?'0':'3' }.html">
         <input type="submit" name="next" value="返回" formnovalidate="formnovalidate" formaction="${path }/s1031.html" >
      </td>
    </tr>
  </table>
  <input type="hidden" name="ssa401" value="${param.ssa401 }">
  <e:hidden name="qssa402"/>
  <e:hidden name="qssa403"/>
  <e:hidden name="qssa404"/>
</form>
</body>
</html>