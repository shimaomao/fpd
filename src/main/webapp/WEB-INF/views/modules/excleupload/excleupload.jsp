<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.ProcDefKey" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>上传EXCEL</title>
</head>
<body>
	  <form action="${ctx}/excelUpload/ExcelUpload/upload" enctype="multipart/form-data" method="post">    
                        上传excel：<input type="file" name="file"><br/>
         <input type="submit" value="提交">
     </form>
     <!--打印错误信息  -->
<div id = "div">
<!-- 问题  {姓名:{"姓名不为空",....},姓名:{"姓名不为空",....},...  } list{(吴用:{问题1,问题2}),}-->
<c:if test="${not empty list1}">

 	<c:forEach var="listZ1" items="${list1}"> 
 		${listZ1} <br><br>
	 	<%-- <c:forEach var="map1" items="${listZ1} "> 
	 	
		${map1.key} ： ${map1.value} <br> 
		</c:forEach>  --%>
	
	</c:forEach> 
</c:if>
<c:if test="${not empty list2}">

 	<c:forEach var="listZ2" items="${list2}"> 
	 
		${listZ2} <br><br>
		<%-- <c:forEach var="map2" items="${listZ2} "> 
	 	
		${map2.key} ： ${map2.value} <br> 
		</c:forEach>  --%>
	</c:forEach> 
</c:if>

<%-- <c:if test="${not empty map1}">

 	<c:forEach var="item1" items="${map1}"> 
	${item1.key} ： ${item1.value} <br> 
	</c:forEach> 
</c:if> --%>
<%-- <c:if test="${not empty map2}">
 	<c:forEach var="item2" items="${map2}"> 
	${item2.key} ： ${item2.value} <br> 
	</c:forEach> 
</c:if> --%>
</div>
</body>
</html>