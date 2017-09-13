<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>担保信息管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/util.js"></script>
    <script type="text/javascript" src="${ctxStatic}/vow/contract_view.js?v=3"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function toAdd(){
			 var url = "${ctx}/guarantee/tGuaranteeContract/form";
				location.href = url;
		}
		function toDelete(){
			 var array = getCheckValue("gid");
			  if(array.length==0){
			  	alertx("请选择一个客户！");
			  	return;
			  }
		    var url = "${ctx}/guarantee/tGuaranteeContract/delete?id="+array[0];
		    return confirmx('确认要删除吗？',url);
		}
		
		function toUpdate(){
			 var array = getCheckValue("gid");
			
			  if(array.length==0){
				alertx("请选择一条业务!");
			  	return;
			  }else{
				  $.ajax({
				     	type: "POST",
				     	url: "${ctx}/guarantee/tGuaranteeContract/getGuaranteeContractStatus",
				     	data: {id:array[0]},
				     	dataType: "json",
				     	success: function(data){
				     		if(data.status=='no'){
				     			alertx("关联保证业务合同不存在，不允许修改!");
				     		}else{
				     			if(data.conStatus==1){
					     			var url = "${ctx}/guarantee/tGuaranteeContract/form?id="+array[0];
					 				location.href = url;
					     			return;
					     		}else{
					     			alertx("关联保证业务合同不是新增状态，不允许修改!");
					     		}
				     		}
				     		
				     	}
				  });
			  }
			   
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/guarantee/tGuaranteeContract/">担保信息列表</a></li>
		<shiro:hasPermission name="guarantee:tGuaranteeContract:edit"><li><a href="${ctx}/guarantee/tGuaranteeContract/form">担保信息添加</a></li></shiro:hasPermission>
	</ul>
    <%-- <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		               业务中心>我的业务>保质抵押>保证
	   </div>
	</div> --%>
    
	<form:form id="searchForm" modelAttribute="tGuaranteeContract" action="${ctx}/guarantee/tGuaranteeContract/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>证件号码：</label>
				<form:input path="cardNum" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>担保人名称：</label>
				<form:input path="guarantorName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>客户姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<shiro:hasPermission name="contract:tLoanContract:edit">
		<table class="table table-bordered">
			<tr><td>
			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="toAdd();" value="增加"/>
   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="toUpdate();" value="修改"/>
   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="toDelete();" value="删除"/>
   			<a class="btn btn-primary"  onclick="viewLoanContract();">查看合同</a>
			</td></tr>
	    </table>
	</shiro:hasPermission>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
		<thead>
			<tr>
				<th>选择</th>
				<th>业务编号</th>
				<th>客户姓名</th>
				<th>担保编号</th>
				<th>保证金额</th>
				<th>证件号码</th>
				<th>担保日期</th>
				<th>联系电话</th>
				<th>资产价值</th>
				<th>担保人姓名</th>
				<th>更新时间</th>
<!-- 				<th>备注</th> -->
<%-- 				<shiro:hasPermission name="guarantee:tGuaranteeContract:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tGuaranteeContract">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>
					 <input type="radio" name="gid" id=gid value="${tGuaranteeContract.id}" data-ukey="${tGuaranteeContract.ukey}" data-other="${tGuaranteeContract.contractNumber}"/>
				</td>
				<td><a href="${ctx}/guarantee/tGuaranteeContract/view?id=${tGuaranteeContract.id}">
				     ${tGuaranteeContract.contractNumber}
				</a></td>
				<td>
					${tGuaranteeContract.customerName}
				</td>
				<td>
					${tGuaranteeContract.guaranteeNumber}
				</td>
				<td>
					${tGuaranteeContract.amount}
				</td>
				<td>
					${tGuaranteeContract.cardNum}
				</td>
				<td>
					<fmt:formatDate value="${tGuaranteeContract.contractDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tGuaranteeContract.telephone}
				</td>
				<td>
					${tGuaranteeContract.assetsWorth}
				</td>
				<td>
					${tGuaranteeContract.guarantorName}
				</td>
				<td>
					<fmt:formatDate value="${tGuaranteeContract.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
<!-- 				<td> -->
<%-- 					${tGuaranteeContract.remarks} --%>
<!-- 				</td> -->
<%-- 				<shiro:hasPermission name="guarantee:tGuaranteeContract:edit"><td> --%>
<%--     				<a href="${ctx}/guarantee/tGuaranteeContract/form?id=${tGuaranteeContract.id}">修改</a> --%>
<%-- 					<a href="${ctx}/guarantee/tGuaranteeContract/delete?id=${tGuaranteeContract.id}" onclick="return confirmx('确认要删除该担保信息吗？', this.href)">删除</a> --%>
<%-- 				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>