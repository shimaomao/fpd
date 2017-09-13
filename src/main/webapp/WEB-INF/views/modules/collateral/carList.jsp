<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>质押车辆信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/collateral/car/">质押车辆信息列表</a></li>
		<shiro:hasPermission name="collateral:car:edit"><li><a href="${ctx}/collateral/car/form">质押车辆信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="car" action="${ctx}/collateral/car/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>事故次数：</label>
				<form:input path="accident" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>车况：</label>
				<form:input path="carKuang" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>新车价格(元) ：</label>
				<form:input path="carMoney" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>车型编号：</label>
				<form:input path="carNum" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>车型：</label>
				<form:input path="carType" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>出厂日期：</label>
				<input name="beginMadeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${car.beginMadeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endMadeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${car.endMadeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>模型估价(元) ：</label>
				<form:input path="moMoney" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>使用年限：</label>
				<form:input path="useDate" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>事故次数</th>
				<th>车况</th>
				<th>新车价格(元) </th>
				<th>车型编号</th>
				<th>车型</th>
				<th>发动机型号</th>
				<th>模型估价(元) </th>
				<th>使用年限</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="collateral:car:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="car">
			<tr>
				<td><a href="${ctx}/collateral/car/form?id=${car.id}">
					${car.accident}
				</a></td>
				<td>
					${car.carKuang}
				</td>
				<td>
					${car.carMoney}
				</td>
				<td>
					${car.carNum}
				</td>
				<td>
					${car.carType}
				</td>
				<td>
					${car.engine}
				</td>
				<td>
					${car.moMoney}
				</td>
				<td>
					${car.useDate}
				</td>
				<td>
					<fmt:formatDate value="${car.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${car.remarks}
				</td>
				<shiro:hasPermission name="collateral:car:edit"><td>
    				<a href="${ctx}/collateral/car/form?id=${car.id}">修改</a>
					<a href="${ctx}/collateral/car/delete?id=${car.id}" onclick="return confirmx('确认要删除该质押车辆信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>