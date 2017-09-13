<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>质押用地管理</title>
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
		<li class="active"><a href="${ctx}/collateral/yongLand/">质押用地列表</a></li>
		<shiro:hasPermission name="collateral:yongLand:edit"><li><a href="${ctx}/collateral/yongLand/form">质押用地添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="yongLand" action="${ctx}/collateral/yongLand/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>地址：</label>
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>土地抵押贷款余额(元)：</label>
				<form:input path="daiBalance" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>产权人：</label>
				<form:input path="man" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>权证编号：</label>
				<form:input path="quanNum" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>剩余年限：</label>
				<form:input path="yuYear" htmlEscape="false" maxlength="11" class="input-medium"/>
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
				<th>模型估价</th>
				<th>权证编号</th>
				<th>销售模型估值(元)</th>
				<th>剩余年限</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="collateral:yongLand:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="yongLand">
			<tr>
				<td><a href="${ctx}/collateral/yongLand/form?id=${yongLand.id}">
					${yongLand.address}
				</a></td>
				<td>
					${yongLand.area}
				</td>
				<td>
					${yongLand.chanQuality}
				</td>
				<td>
					${yongLand.daiBalance}
				</td>
				<td>
					${yongLand.man}
				</td>
				<td>
					${yongLand.moMoney}
				</td>
				<td>
					${yongLand.quanNum}
				</td>
				<td>
					${yongLand.xiaoMoney}
				</td>
				<td>
					${yongLand.yuYear}
				</td>
				<td>
					<fmt:formatDate value="${yongLand.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${yongLand.remarks}
				</td>
				<shiro:hasPermission name="collateral:yongLand:edit"><td>
    				<a href="${ctx}/collateral/yongLand/form?id=${yongLand.id}">修改</a>
					<a href="${ctx}/collateral/yongLand/delete?id=${yongLand.id}" onclick="return confirmx('确认要删除该质押用地吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>