<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抵押独栋别墅管理</title>
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
		<li class="active"><a href="${ctx}/collateral/singleVilla/">抵押独栋别墅列表</a></li>
		<shiro:hasPermission name="collateral:singleVilla:edit"><li><a href="${ctx}/collateral/singleVilla/form">抵押独栋别墅添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="singleVilla" action="${ctx}/collateral/singleVilla/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>地址：</label>
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>面积(㎡)：</label>
				<form:input path="area" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>按揭余额(元)：</label>
				<form:input path="balance" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>产权性质：</label>
				<form:input path="chanQuality" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>建成年份：</label>
				<input name="beginJyear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${singleVilla.beginJyear}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endJyear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${singleVilla.endJyear}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>共有人：</label>
				<form:input path="man" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>模型估价(元)：</label>
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
				<th>地址</th>
				<th>面积(㎡)</th>
				<th>按揭余额(元)</th>
				<th>产权性质</th>
				<th>登记情况</th>
				<th>建成年份</th>
				<th>共有人</th>
				<th>模型估价(元)</th>
				<th>权证编号</th>
				<th>剩余年限</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="collateral:singleVilla:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="singleVilla">
			<tr>
				<td><a href="${ctx}/collateral/singleVilla/form?id=${singleVilla.id}">
					${singleVilla.address}
				</a></td>
				<td>
					${singleVilla.area}
				</td>
				<td>
					${singleVilla.balance}
				</td>
				<td>
					${singleVilla.chanQuality}
				</td>
				<td>
					${singleVilla.dengji}
				</td>
				<td>
					<fmt:formatDate value="${singleVilla.jyear}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${singleVilla.man}
				</td>
				<td>
					${singleVilla.moMoney}
				</td>
				<td>
					${singleVilla.quanNum}
				</td>
				<td>
					${singleVilla.yearLimit}
				</td>
				<td>
					<fmt:formatDate value="${singleVilla.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${singleVilla.remarks}
				</td>
				<shiro:hasPermission name="collateral:singleVilla:edit"><td>
    				<a href="${ctx}/collateral/singleVilla/form?id=${singleVilla.id}">修改</a>
					<a href="${ctx}/collateral/singleVilla/delete?id=${singleVilla.id}" onclick="return confirmx('确认要删除该抵押独栋别墅吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>