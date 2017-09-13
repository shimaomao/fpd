<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="java.util.*" %>
<table class="table table-striped table-bordered table-condensed">
	<!-- <tr><th>执行环节</th><th>执行人</th><th>开始时间</th><th>结束时间</th><th>提交意见</th><th>任务历时</th></tr> -->
	<tr>
	<%
	 List list = (List)request.getAttribute("lineList");  
	  for(int i=0;i<list.size();i++){
		    if(list.get(i).equals("同意")){
		    	%>
			     <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="<%=list.get(i) %>" onclick="$('#pass').val('1');$('#flag').val('yes');"/>&nbsp;</td>
			  <%
		    }else if(list.get(i).equals("驳回")){
		    	%>
			     <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="<%=list.get(i) %>" onclick="$('#pass').val('0');$('#flag').val('no');"/>&nbsp;</td>
			  <%
		    }
		  
	  }
	
	%>
	 <td><input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/></td>
	</tr>
</table>