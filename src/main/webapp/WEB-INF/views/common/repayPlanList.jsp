<%@page import="com.wanfin.fpd.common.utils.StringUtils"%>
<%@page import="org.json.JSONArray"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include-builder/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
    	<thead>
			<tr>
				<th>期数</th>
				<th>计划到账日</th>
				<th>计划收入本金</th>
				<th>计划收入利息</th>
				<th>计息开始日期</th>
				<th>计息结束日期</th>
				
			</tr>
		</thead>
    	<tbody>
    	<%
    		JSONArray list = (JSONArray) request.getAttribute("list");
	    	if(list != null){
				for(int i=0;i<list.length();i++){
		%>
				<tr>
					<td>第<%=list.getJSONObject(i).get("num") %>期
						<input type="hidden" name="repayPlanList[<%=i%>].interest" value="<%=list.getJSONObject(i).get("interest") %>" />
						<input type="hidden" name="repayPlanList[<%=i%>].principal" value="<%=list.getJSONObject(i).get("principal") %>" />
						<input type="hidden" name="repayPlanList[<%=i%>].num" value="<%=list.getJSONObject(i).get("num") %>" />
						<input type="hidden" name="repayPlanList[<%=i%>].accountDate" value="<%=list.getJSONObject(i).get("accountDate") %>" />
						<input type="hidden" name="repayPlanList[<%=i%>].startDate" value="<%=list.getJSONObject(i).get("startDate") %>" />
						<input type="hidden" name="repayPlanList[<%=i%>].endDate" value="<%=list.getJSONObject(i).get("endDate") %>" />
					</td>
					<td><%=list.getJSONObject(i).get("accountDate") %></td>
					<td><%=list.getJSONObject(i).get("principal") %></td>
					<td><%=list.getJSONObject(i).get("interest") %></td>
					<td><%=list.getJSONObject(i).get("startDate") %></td>
					<td><%=list.getJSONObject(i).get("endDate") %></td>
					
				</tr>
		<%			
				}
			}
    	%>
    		<%-- <c:forEach var="item" items="${list }" varStatus="idx">
			<tr>
				<td>第${item.num}期
					<input type="hidden" name="repayPlanList[${idx.index}].interest" value="${item.interest}" />
					<input type="hidden" name="repayPlanList[${idx.index}].principal" value="${item.principal}" />
					<input type="hidden" name="repayPlanList[${idx.index}].num" value="${item.num}" />
					<input type="hidden" name="repayPlanList[${idx.index}].accountDate" value="${item.accountDate}" />
					<input type="hidden" name="repayPlanList[${idx.index}].startDate" value="${item.startDate}" />
					<input type="hidden" name="repayPlanList[${idx.index}].endDate" value="${item.endDate}" />
				</td>
				<td>${item.accountDate}</td>
				<td>${item.principal}</td>
				<td>${item.interest}</td>
				<td>${item.startDate}</td>
				<td>${item.endDate}</td>
			</tr>
			</c:forEach> --%>
    	</tbody>
    </table>
</html>