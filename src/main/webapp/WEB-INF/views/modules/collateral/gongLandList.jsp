<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抵押-工业用地信息管理</title>
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
		<li class="active"><a href="${ctx}/collateral/gongLand/">抵押-工业用地信息列表</a></li>
		<shiro:hasPermission name="collateral:gongLand:edit"><li><a href="${ctx}/collateral/gongLand/form">抵押-工业用地信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gongLand" action="${ctx}/collateral/gongLand/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>地址：</label>
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>面积(㎡)：</label>
				<form:input path="area" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>购买年份：</label>
				<form:input path="buyYear" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>市场交易价格(元)：</label>
				<form:input path="jiaoMoney" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>权证编号：</label>
				<form:input path="quanNum" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>剩余年限：</label>
				<form:input path="yuYear" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>出租价格1(元)：</label>
				<form:input path="zuMoney1" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>出租价格2(元)：</label>
				<form:input path="zuMoney2" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>地址</th>
				<th>面积(㎡)</th>
				<th>土地性质</th>
				<th>土地抵押贷款余额(元)</th>
				<th>产权人</th>
				<th>销售模型估价(元)</th>
				<th>剩余年限</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="collateral:gongLand:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gongLand">
			<tr>
				<td><a href="${ctx}/collateral/gongLand/form?id=${gongLand.id}">
					${gongLand.address}
				</a></td>
				<td>
					${gongLand.area}
				</td>
				<td>
					${gongLand.chanQuality}
				</td>
				<td>
					${gongLand.daiBalance}
				</td>
				<td>
					${gongLand.man}
				</td>
				<td>
					${gongLand.xiaoMoney}
				</td>
				<td>
					${gongLand.yuYear}
				</td>
				<td>
					<fmt:formatDate value="${gongLand.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${gongLand.remarks}
				</td>
				<shiro:hasPermission name="collateral:gongLand:edit"><td>
    				<a href="${ctx}/collateral/gongLand/form?id=${gongLand.id}">修改</a>
					<a href="${ctx}/collateral/gongLand/delete?id=${gongLand.id}" onclick="return confirmx('确认要删除该抵押-工业用地信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>