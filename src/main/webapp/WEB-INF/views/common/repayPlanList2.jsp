<%@page import="org.json.JSONArray"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include-builder/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <table class="table table-striped table-bordered table-condensed table-center">
    	<thead>
			<tr>
				<td rowspan="2">期数</td>
				<td rowspan="2">催收次数</td>
				<td rowspan="2">计息开始日期</td>
				<td rowspan="2">计息结束日期</td>
				<td rowspan="2">计划到账日</td>
				<td colspan="2">本金</td>
				<td colspan="2">利息</td>
				<td rowspan="2">状态</td>
			</tr>
			<tr align="center" class="tit_center_bg">
				<td>应还</td>
				<td>已还</td>
				<td>应还</td>
				<td>已还</td>
			</tr>
		</thead>
    	<tbody>
    		<c:forEach var="repayPlan" items="${list }" varStatus="idx">
			<tr>
				<td>第${repayPlan.num}期</td>
				<td>${repayPlan.csNum}</td>
				<td>${repayPlan.startDate}</td>
				<td>${repayPlan.endDate}</td>
				<td>${repayPlan.accountDate}</td>
				<td>${repayPlan.principal}</td>
				<td><c:if test="${empty repayPlan.principalReal}">0</c:if>${repayPlan.principalReal}</td>
				<td>${repayPlan.interest}</td>
				<td><c:if test="${empty repayPlan.interestReal}">0</c:if>${repayPlan.interestReal}</td>
				<td>${fns:getDictLabel(repayPlan.status, 'repay_status', '')}
				</td>
			</tr>
			</c:forEach>
    	</tbody>
    </table>
</html>