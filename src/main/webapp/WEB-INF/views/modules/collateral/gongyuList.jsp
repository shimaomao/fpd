<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抵押公寓信息管理</title>
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
		<li class="active"><a href="${ctx}/collateral/gongyu/">抵押公寓信息列表</a></li>
		<shiro:hasPermission name="collateral:gongyu:edit"><li><a href="${ctx}/collateral/gongyu/form">抵押公寓信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gongyu" action="${ctx}/collateral/gongyu/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>房地产地址：</label>
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>面积(㎡)：</label>
				<form:input path="area" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>登记情况：</label>
				<form:input path="dengji" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>建成年份：</label>
				<input name="beginJyear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${gongyu.beginJyear}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endJyear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${gongyu.endJyear}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>模型估价：</label>
				<form:input path="moMoney" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>权证编号：</label>
				<form:input path="quanNum" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>剩余年限：</label>
				<form:input path="yearLimit" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>房地产地址</th>
				<th>面积(㎡)</th>
				<th>登记情况</th>
				<th>建成年份</th>
				<th>模型估价(元)</th>
				<th>权证编号</th>
				<th>剩余年限</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="collateral:gongyu:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gongyu">
			<tr>
				<td><a href="${ctx}/collateral/gongyu/form?id=${gongyu.id}">
					${gongyu.address}
				</a></td>
				<td>
					${gongyu.area}
				</td>
				<td>
					${gongyu.dengji}
				</td>
				<td>
					<fmt:formatDate value="${gongyu.jyear}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${gongyu.moMoney}
				</td>
				<td>
					${gongyu.quanNum}
				</td>
				<td>
					${gongyu.yearLimit}
				</td>
				<td>
					<fmt:formatDate value="${gongyu.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${gongyu.remarks}
				</td>
				<shiro:hasPermission name="collateral:gongyu:edit"><td>
    				<a href="${ctx}/collateral/gongyu/form?id=${gongyu.id}">修改</a>
					<a href="${ctx}/collateral/gongyu/delete?id=${gongyu.id}" onclick="return confirmx('确认要删除该抵押公寓信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>