<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.ProcDefKey" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业客户管理</title>
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
			 var url = "${ctx}/company/tCompany/form";
				location.href = url;
		}
		function toDelete(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
			  	alertx("请选择一个客户！");
			  	return;
			  }
		    var url = "${ctx}/company/tCompany/delete?id="+array[0];
		    return confirmx('确认要删除吗？',url);
		}
		
		function toUpdate(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
				   alertx("请选择一个客户!");
			  	return;
			  }
			  var url = "${ctx}/company/tCompany/form?id="+array[0];
				location.href = url;
		}
		
		//加入黑名单
		function joinBlack(){
			var array = getCheckValue("cid");

			if(array.length==0){
				alertx("请选择一个客户!");
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
					var url = "${ctx}/act/task/form?procDefId="+data.procDefId+"&businessTable=t_company&businessId="+array[0];
				  	location.href = url;
				}
			);
		}
		
		
		

		function customerTreeselectCallBack(v, h, f){
			var ids = $('#customerId').val();
			if(ids){
				var url = "${ctx}/company/tCompany/addP?customerType=1&ids="+ids;
				location.href = url;
			}
		}
		
	
		function removeCompanyProductLink(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
			  	alertx("请选择一个客户！");
			  	return;
			  }
			  
			  $.ajax({
		         	type: "POST",
		         	url: "${ctx}/company/tCompany/checkCustomerContract",
		         	data: {id:array[0],type:1},
		         	dataType: "text",
		         	success: function(data){
		         	    if(data=='Y'){//可删除
		         	    	  var url = "${ctx}/company/tCompany/removeP?customerType=1&id="+array[0];
		         			    return confirmx('确认要移除吗？',url);
		         	    }else{//客户有业务，不能删除
		         	   	    alert("该客户有业务记录，不能移除！");
		         	    }
		         	}
		       });
		}
		
		
		
		//查看征信信息
		function viewCreditChecking(){
			var array = getCheckValue("cid");

		    if(array.length==0){
		    	alertx("请选择一个客户!");
		    	return;
		    }
			var name = $("#cId"+array[0]).attr("data-name");
			top.$.jBox.open("iframe:${ctx}/company/tCompany/view?id="+array[0],"查看客户征信("+name+")",$(top.document).width()-200,$(top.document).height()-150,{
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
		
		
		
		function togetherLoan(){
			var array = getCheckValue("cid");
			  if(array.length==0){ 
			  	alert("请选择一个客户！");
			  	return;
			  }
			//新增
			 showjBox("共同借款", "${ctx}/customertogether/tCustomerTogether/?mainCompanyid="+array[0]);
		}
   </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/company/tCompany/list">企业客户列表</a></li>
		<c:if test="${goal_customer=='goal_customer'}">
		<shiro:hasPermission name="company:tCompany:edit"><li><a href="${ctx}/company/tCompany/form">企业客户添加</a></li></shiro:hasPermission>
		</c:if>
	</ul>
	<div style="display: none;">
		<sys:treeselect id="customer" name="" value="" labelName="customerName" labelValue="" extId="2" checked="true"
			title="目标客户" url="/company/tCompany/treeDataP" cssClass="" allowClear="true" notAllowSelectParent="true"/>
	</div>
	<form:form id="searchForm" modelAttribute="tCompany" action="${ctx}/company/tCompany/listP" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>企业名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>法定代表：</label>
				<form:input path="surety" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-bordered">
		<tr><td>
			<c:if test="${goal_customer=='customer'}">
				<input class="btn btn-primary" type="submit" onclick="$('#customerButton').click();" value="添加" title="从目标客户里添加"/>
   				<input class="btn btn-primary" type="submit" onclick="removeCompanyProductLink();" value="移除"/>
   				<input class="btn btn-primary" type="submit" onclick="togetherLoan();" value="共同借款"/>
   				<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="joinBlack();" value="加入黑名单"/> -->
			</c:if>
			
			<c:if test="${goal_customer=='goal_customer'}">
   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="toAdd();" value="增加"/>
   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="toUpdate();" value="修改"/>
   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="toDelete();" value="删除"/>
			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="joinBlack();" value="加入黑名单"/> -->
			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="viewCreditChecking();" value="查看征信信息"/>
			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="addapath();" value="查看明细"/>-->
			</c:if>
		</td></tr>
    </table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
		<thead>
			<tr>
			    <th>选择</th>
				<th>企业名称</th>
				<th>证件号码</th>
				<th>企业性质</th>
				<th>注册资金</th>
				<th>法定代表人</th>
				<th>法人手机号码</th>
				<th>备注信息</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCompany">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
			    <td>
					 <input type="radio" name="cid" id="cId${tCompany.id}" value="${tCompany.id}" data-name="${tCompany.name}"/>
				</td>
				<td><a href="${ctx}/company/tCompany/form?id=${tCompany.id}">
					${tCompany.name}
				</a></td>
				<td>
					${tCompany.cardNum}
				</td>
				<td>
					${fns:getDictLabel(tCompany.properties, 'nature_of_unit', '')}
				</td>
				<td>
					${tCompany.captial}
				</td>
				<td>
					${tCompany.surety}
				</td>
				<td>
					${tCompany.suretyMobile}
				</td>
				<td>
					${tCompany.remarks}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>