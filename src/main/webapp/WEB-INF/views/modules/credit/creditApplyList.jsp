<%@page import="com.wanfin.fpd.common.config.Cons"%>
<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>授信申请管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		//添加客户与产品的关联
		function customerTreeselectCallBack(v, h, f){
			var customerId = $('#customerId').val();
			var customertype = ($("#customerpId").val());//1企业   2个人
			if(!customerId){
				alertx("请选择一个客户!");
				return;
			}
			//判断当前产品是否已经到期，否则不让授信
			$.post("${ctx}/product/tProduct/toCheckProductPeriod",
	         	{id:customerId},
	         	function(data){
	         	   if(data<=0){
	         		  showTip("此产品的有效期限已经过去，无法继续申请授信");
						return;
	         	   }else if(data<=30&&data>0){
	         		    alert("温馨提醒：此产品的有效期限不足一个月");
	         	   }
		         	  $.post("${ctx}/product/tProduct/getBizCfgByBizCode", { "bizCode": "<%=FModel.M_CUSTOMER_CREDIT.getKey()%>"},
		      				function(data) {
		      					if(!data){
		      						showTip("无此业务");
		      						return;
		      					}
		      					if(!data.procDefId){
		      						showTip("此业务没有配置流程");
		      						return;
		      					}		
		      					var url = "${ctx}/credit/creditApply/form?act.procDefId="+data.procDefId+"&customerId="+customerId+"&customerType="+customertype+"&tab=bd";
		      				  	location.href = url;
		      				}
		      			);
	         	}
		    );
		}
		
		/***签订合同***/
		function toSign(){
			var array = getCheckValue("cid");
			if(array.length==0){
			 showTip("请选择一笔授信申请!");
				return;
			}
		 	$.post("${ctx}/credit/creditApply/getCreditApplyStatus",
	         	{id:array[0]},
	         	function(data){
	         		if(data == "<%=Cons.CreditApplyStatus.TO_SIGN%>"){
	         			 var url = "${ctx}/credit/creditApply/toSign?id="+array[0];
	         			location.href = url;
	         		}else{
	         			showTip("不是[待签订]状态的申请，不能签订合同！");
	         		}
	         	}
	       );
		}
		
		
		//删除
		function toDelete(creditApplyId,status){
		    if(creditApplyId.length==0){
		    	alert("请选择一条授信申请!");
		    	return;
		    }
		    if(status == '<%=Cons.CreditApplyStatus.NEW%>'){
		    	var url = "${ctx}/credit/creditApply/delete?id="+creditApplyId;
	 		    return confirmx('确认要删除该授信信息吗？',url);
     		}else{
     			alert("不是新增状态的申请！不能删除！");
     		}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/credit/creditApply/">授信申请列表</a></li>
	</ul>
	<div style="display: none;">
		<sys:treeselect id="customer" isAll="false" name="" value="" labelName="customerName" labelValue="" 
			title="客户" url="/company/tCompany/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
	</div>
	<form:form id="searchForm" modelAttribute="creditApply" action="${ctx}/credit/creditApply/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申请单号：</label>
				<form:input path="applyNum" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>客户名称：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-bordered">
		<tr><td>
			<input class="btn btn-primary" type="button" onclick="$('#customerButton').click();" value="申请授信" title="选择客户进行授信"/>
			<a class="btn btn-primary" onclick="toSign();">签订合同</a>
		</td></tr>
    </table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>授信单号</th>
				<th>客户名称</th>
				<th>申请额度（元）</th>
				<th>剩余额度（元）</th>
				<th>授信截止时间</th>
				<th>创建时间</th>
				<th>备注信息</th>
				<th>状态</th>
				<shiro:hasPermission name="credit:creditApply:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="creditApply">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>
					 <input type="radio" name="cid" id="cid" value="${creditApply.id}" />
				</td>
				<td><a href="${ctx}/credit/creditApply/form?id=${creditApply.id}">
					${creditApply.applyNum}
				</a></td>
				<td>
					${creditApply.employeeName}${creditApply.companyName}
				</td>
				<td>
					<fmt:formatNumber value="${creditApply.credit}" pattern="#,#00.00#" />
				</td>
				<td>
					<fmt:formatNumber value="${creditApply.residueCredit}" pattern="#,#00.00#" />
				</td>
				<td>
					<fmt:formatDate value="${creditApply.expirationDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${creditApply.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${creditApply.remarks}
				</td>
				<td>
					${fns:getDictLabel(creditApply.status, 'credit_apply_status', '')}
				</td>
				<shiro:hasPermission name="credit:creditApply:edit"><td>
    				<%-- <a href="${ctx}/credit/creditApply/form?id=${creditApply.id}&bizCode=<%=FModel.M_CUSTOMER_CREDIT.getKey()%>">修改</a> --%>
					<a onclick="toDelete('${creditApply.id}','${creditApply.status}');" style="cursor: pointer;">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>