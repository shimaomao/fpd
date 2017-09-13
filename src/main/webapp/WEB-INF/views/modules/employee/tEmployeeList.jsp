<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.ProcDefKey" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人客户管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${not empty message}">
			showTip("${message}");
			</c:if>
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		
		function toAdd(){
			 var url = "${ctx}/employee/tEmployee/form";
				location.href = url;
		}
		function toDelete(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
			  	alert("请选择一个客户！");
			  	return;
			  }
		    var url = "${ctx}/employee/tEmployee/delete?id="+array[0];
		    return confirmx('确认要删除吗？',url);
		}
		
		function toUpdate(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
				   alert("请选择一个客户!");
			  	return;
			  }
			  var url = "${ctx}/employee/tEmployee/form?id="+array[0];
				location.href = url;
		}
		
		//加入黑名单
		function joinBlack(){
			 var array = getCheckValue("cid");

		    if(array.length==0){
		    	alert("请选择一个客户!");
		    	return;
		    }
			$.post("${ctx}/product/tProduct/getBizCfgByBizCode", { "bizCode": "<%=FModel.M_CUSTOMER_BLACK.getKey()%>"},
					function(data) {
						if(!data){
							showTip("无此业务");
							return;
						}
						if(!data.procDefId){
							showTip("此业务没有配置流程");
							return;
						}
						var url = "${ctx}/act/task/form?procDefId="+data.procDefId+"&businessTable=t_employee&businessId="+array[0];
					  	location.href = url;
					}
				);
		}
		//------ 产品——客户  关联
		//添加客户与产品的关联
		 function customerTreeselectCallBack(v, h, f){
			var ids = $('#customerId').val();
			if(ids){
				var url = "${ctx}/company/tCompany/addP?customerType=2&ids="+ids;
				location.href = url;
			}
		}
		
		//移除客户与产品的关联
		 function removeEmployeeProductLink(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
			  	alert("请选择一个客户！");
			  	return;
			  }
			  $.ajax({
		         	type: "POST",
		         	url: "${ctx}/company/tCompany/checkCustomerContract",
		         	data: {id:array[0],type:2},
		         	dataType: "text",
		         	success: function(data){
		         	    if(data=='Y'){//可删除
		         	    	var url = "${ctx}/company/tCompany/removeP?customerType=2&id="+array[0];
		        		    return confirmx('确认要移除吗？',url);
		         	    }else{//客户有业务，不能删除
		         	   	    alert("该客户有业务记录，不能移除！");
		         	    }
		         	}
		       });
		} 
		
		//共同借款
		function togetherLoan(){
			var array = getCheckValue("cid");
			  if(array.length==0){ 
			  	alert("请选择一个客户！");
			  	return;
			  }
			//新增
			 showjBox("共同借款", "${ctx}/customertogether/tCustomerTogether/?mainEmployeeid="+array[0]);
			// showjBox("新增高管信息", "${ctx}/customertogether/tCustomerTogether/form?companyId="+$("#companyId").val());
		}
		
		
		//查看征信信息
		function viewCreditChecking(){
			var array = getCheckValue("cid");

		    if(array.length==0){
		    	alertx("请选择一个客户!");
		    	return;
		    }
			var name = $("#cId"+array[0]).attr("data-name");
			top.$.jBox.open("iframe:${ctx}/employee/tEmployee/view?id="+array[0],"查看客户征信("+name+")",$(top.document).width()-200,$(top.document).height()-100,{
	    		buttons:{"刷新":"refresh", "关闭":true}, 
	    		bottomText:"",
	    		submit:function(v, h, f){
	    			var ifrWin = h.find("iframe")[0].contentWindow;
	    			if(v=="refresh"){
	    				ifrWin.location.reload(true);
	                	//ifrWin.clearAssign();
	    				return false;
	                }else if(v=="return"){
	                	ifrWin.history.go(-1);
	                	ifrWin.location.reload();
	    				return false;
	                }
	    		}, loaded:function(h){
	    			$(".jbox-content", top.document).css("overflow-y","hidden");
	    		}
	    	});
	    }
	    
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:if test="${goal_customer=='customer'}">
			<li class="active"><a href="${ctx}/employee/tEmployee/listP">个人客户列表</a></li>
		</c:if>
		<c:if test="${goal_customer=='goal_customer'}">
			<li class="active"><a href="${ctx}/employee/tEmployee/list">个人客户列表</a></li>
			<shiro:hasPermission name="employee:tEmployee:edit"><li><a href="${ctx}/employee/tEmployee/form">个人客户添加</a></li></shiro:hasPermission>
		</c:if>
	</ul>
	
	<div style="display: none;">
		<sys:treeselect id="customer" name="" value="" labelName="customerName" labelValue="" extId="1" checked="true"
			title="目标客户" url="/company/tCompany/treeDataP" cssClass="" allowClear="true" notAllowSelectParent="true"/>
	</div>
	
	<c:if test="${goal_customer=='customer'}">
		<form:form id="searchForm" modelAttribute="tEmployee" action="${ctx}/employee/tEmployee/listP" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<ul class="ul-form">
				<li><label>姓名：</label>
					<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
				</li>
				<li><label>身份证号码：</label>
					<form:input path="cardNum" htmlEscape="false" maxlength="50" class="input-medium"/>
				</li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:if>
		<c:if test="${goal_customer=='goal_customer'}">
		<form:form id="searchForm" modelAttribute="tEmployee" action="${ctx}/employee/tEmployee/list" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<ul class="ul-form">
				<li><label>姓名：</label>
					<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
				</li>
				<li><label>身份证号码：</label>
					<form:input path="cardNum" htmlEscape="false" maxlength="50" class="input-medium"/>
				</li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:if>
	<sys:message content="${message}"/>
	<table class="table table-bordered">
		<tr><td>
			<c:if test="${goal_customer=='customer'}">
				<input class="btn btn-primary" type="submit" onclick="$('#customerButton').click();" value="添加" title="从目标客户里添加"/>
   				<input class="btn btn-primary" type="submit" onclick="removeEmployeeProductLink();" value="移除"/>
   				<input class="btn btn-primary" type="submit" onclick="togetherLoan();" value="共同借款"/>
   				<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="joinBlack();" value="加入黑名单"/> -->
			</c:if>
			<c:if test="${goal_customer=='goal_customer'}">
		    <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="toAdd();" value="增加"/>
   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="toUpdate();" value="修改"/>
   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="toDelete();" value="删除"/>
			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="joinBlack();" value="加入黑名单"/> -->
			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="viewCreditChecking();" value="查看征信信息"/>
			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="addapath();" value="查看明细"/>
			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="addapath();" value="征信信息"/> -->
			</c:if>
		</td></tr>
    </table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
		<thead>
			<tr>
			    <th>选择</th>
				<th>姓名</th>
				<th>身份证号码</th>
				<th>现住址</th>
				<th>手机号码</th>
				<th>联系电话</th>
				<th>是否本地户口</th>
				<th>个人职务</th>
				<th>更新时间</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tEmployee">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
			     <td>
					 <input type="radio" name="cid" id="cId${tEmployee.id}" value="${tEmployee.id}" data-name="${tEmployee.name}"/>
				</td>
				<td><a href="${ctx}/employee/tEmployee/form?id=${tEmployee.id}">
					${tEmployee.name}
				</a></td>
				<td>
					${tEmployee.cardNum}
				</td>
				<td>
					${tEmployee.currentLiveAddress}
				</td>
				<td>
					${tEmployee.mobile}
				</td>
				<td>
					${tEmployee.telephone}
				</td>
				<td>
					${fns:getDictLabel(tEmployee.isLocalHousehold, 'is_local_household', '')}
				</td>
				<td>
					${tEmployee.post}
				</td>
				<td>
					<fmt:formatDate value="${tEmployee.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tEmployee.remarks}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>