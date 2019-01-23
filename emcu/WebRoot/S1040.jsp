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
     select{
       height:22px;
       width:162px;
     }
   </style>
   <script type="text/javascript">
     var count=0;
     function onSelect(obj)
     {
    	 obj?count++:count--;
    	 document.getElementById("next2").disabled=(count==0);
    	 
    	 var n3=document.getElementById("next3");
    	 if(n3!=null)
    	 {
    		 n3.disabled=(count==0);	 
    	 }
     }
     
     function onDelete(obj)
     {
    	 var f=document.getElementById("myform");
    	 f.action="${path}/s1012.html?ssa203=3&next=u&idlist="+obj;
    	 f.submit();
     }
     function onEdit(obj)
     {
    	 var f=document.getElementById("myform");
    	 f.action="${path}/s1013.html?ssa201="+obj;
    	 f.submit();
     }
     
     function onUnSelect()
     {
    	 var vidlist=document.getElementsByName("idlist");
    	 for(var i=0;i<vidlist.length;i++)
    	 {
    		 vidlist[i].checked=false;
    	 }
     }
     </script>
   
</head>
<body>
<e:message/>
<br>
<br>
<form id="myform" action="${path }/s1041.html" method="post">
 <!-- 查询条件 -->
 <table border="1" width="95%" align="center">
   <caption>
                 角色授权
     <hr width="160">
   </caption>
   <tr>
     <td colspan="6">查询条件</td>
   </tr>
   <tr>
     <td>系统角色</td>
     <td>
       <e:select value="ocssa201" name="qssa201"/>
     </td>
     <td>菜单名称</td>
     <td>
       <e:text name="qssa403" autofocus="true"/>
     </td>
     <td>菜单编号</td>
     <td>
       <e:text name="qssa402"/>
     </td>
   </tr>
 </table>
 <!-- 数据迭代 -->
 <table border="1" width="95%" align="center">
   <tr>
     <td></td>
     <td>序号</td>
     <td>菜单编号</td>
     <td>菜单名称</td>
     <td>打开窗口</td>
     <td>菜单状态</td>
     <td></td>
   </tr>
   <c:choose>
     <c:when test="${rows!=null }">
        <c:forEach items="${rows }" var="ins" varStatus="vs">
		   <tr>
		     <td>
		       <e:checkbox name="idlist" value="${ins.ssa401 }" valArray="menuList" />
		     </td>
		     <td>${vs.count }</td>
		     <td>${ins.ssa402 }</td>
		     <td>
		       <a href="#" onclick="onEdit('${ins.ssa401}')">${ins.ssa403 }</a>
		     </td>
		     <td>${ins.ssa404 }</td>
		     <td>${ins.cnssa405 }</td>
		     <td></td>
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
		   </tr>
       </c:forEach>
     </c:otherwise>
   </c:choose>
 </table>

 <!-- 功能按钮 -->
 <table border="1" width="95%" align="center">
   <tr>
     <td align="center">
       <input type="submit" name="next" id="next0" value="查询" onclick="return onUnSelect()">
       <input type="submit" name="next" id="next1" value="授权" formaction="${path }/s1042.html">
     </td>
   </tr>
 </table>
</form> 
</body>
</html>




