<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="com.wanfin.fpd.common.config.Cons"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抵押物品信息管理</title>
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
		
		function toAdd(){
			 var url = "${ctx}/mortgage/mortgageContract/form";
				location.href = url;
		}
		function toDelete(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
			  	alertx("请选择一笔业务！");
			  	return;
			  }
		    var url = "${ctx}/mortgage/mortgageContract/delete?id="+array[0];
		    return confirmx('确认要删除吗？',url);
		}
		
		function toUpdate(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
				   alertx("请选择一条业务!");
			  	return;
			  }
			  $.ajax({
		         	type: "POST",
		         	url: "${ctx}/mortgage/mortgageContract/getMortEntityStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){//data要收押物品的数量
		         		if(data.status=='no'){
		         			alertx("没有找到关联的抵押合同，不能进行修改");
		         		}else{
		         			if(data.mcStatus==1 && data.conStatus==1){
				         		  var url = "${ctx}/mortgage/mortgageContract/form?id="+array[0];
				       			  location.href = url;
				         		}else if(data.conStatus!=1){
				         			alertx("关联业务合同不是新增状态，不能进行修改");
				         		}else if(data.mcStatus!=1){
				         			alertx("抵押物不是新增状态，不能进行修改");
				         		}
		         		   }
		         		
		         	   }
		       });
			 
		}
		//收押
		function GetMoretgage(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
				   alertx("请选择一条业务!");
			  	return;
			  }
			 $.ajax({
		         	type: "POST",
		         	url: "${ctx}/mortgage/mortgageContract/getMortEntityStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){//data要收押物品的数量
		         		if(data.mcStatus==1){
		         			 $.ajax({
		     		         	type: "POST",
		     		         	url: "${ctx}/mortgage/mortgageContract/changeTakeInStatus",
		     		         	data: {id:array[0],status:3},
		     		         	dataType: "json",
		     		         	success: function(status){//data要收押物品的数量
		     		         		alertx(status.message);
		     		         		location.reload(true);
		     		         	}
		     		          });
		         		}else{
		         			alert("不是新增状态的抵押物，不能进行收押");
		         		}
		         	}
		       });
		}
		
		//借出
		function takeOut(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
				   alertx("请选择一条业务!");
			  	return;
			  }
			 $.ajax({
		         	type: "POST",
		         	url: "${ctx}/mortgage/mortgageContract/getMortEntityStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){//状态
		         		if(data.mcStatus==3){
		         			$.post("${ctx}/product/tProduct/getBizCfgByBizCode", { "bizCode": "<%=Cons.FModel.M_BUSINESS_APPLICATION_YPJC.getKey()%>"},
		           					function(data) {
		           						if(!data){
		           							showTip("无此业务");
		           							return;
		           						}
		           						if(!data.procDefId){
		           							showTip("此业务没有配置流程");
		           							return;
		           						}
		           						var url = "${ctx}/act/task/form?procDefId="+data.procDefId+"&businessTable=t_loan_contract&businessId="+array[0];
		        	         			location.href = url;
		           					}
		           				); 
		         		}else{
		         			alert("不是已收押状态的抵押物，不能进行借出");
		         		}
		         	}
		       });
		}
		//归还
		function takeIn(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
				   alertx("请选择一条业务!");
			  	return;
			  }
			 $.ajax({
		         	type: "POST",
		         	url: "${ctx}/mortgage/mortgageContract/getMortEntityStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){//状态
		         		if(data.mcStatus==4){
		         			$.post("${ctx}/product/tProduct/getBizCfgByBizCode", { "bizCode": "<%=Cons.FModel.M_BUSINESS_APPLICATION_YPJC.getKey()%>"},
		           					function(data) {
		           						if(!data){
		           							showTip("无此业务");
		           							return;
		           						}
		           						if(!data.procDefId){
		           							showTip("此业务没有配置流程");
		           							return;
		           						}
		           						var url = "${ctx}/act/task/form?procDefId="+data.procDefId+"&businessTable=t_loan_contract&businessId="+array[0];
		        	         			location.href = url;
		           					}
		           				); 
		         		}else{
		         			alert("不是已借出状态的抵押物，不能进行归还操作");
		         		}
		         	}
		       });
		}
		//解押
		function BackMoretgage(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
				   alertx("请选择一条业务!");
			  	return;
			  }
			 $.ajax({
		         	type: "POST",
		         	url: "${ctx}/mortgage/mortgageContract/getMortEntityStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){//data要收押物品的数量
		         		if(data.mcStatus==<%=Cons.StrutsStatus.TAKE_INTO%>||data==<%=Cons.StrutsStatus.RETURN_YES%>){
		         			 $.ajax({
		     		         	type: "POST",
		     		         	url: "${ctx}/mortgage/mortgageContract/changeMortStatus",
		     		         	data: {id:array[0],status:<%=Cons.StrutsStatus.REMOVE_YES%>},
		     		         	dataType: "json",
		     		         	success: function(data){//data要收押物品的数量
		     		         		if(data.status==1){
		     		         			alert("解除成功！");
		     		         			location.reload();
		     		         		}else{
		     		         			alert(data.message);
		     		         		}
		     		         	}
		     		          });
		         		}else{
		         			alert("不是已收押和已归还状态的抵押物，不能进行解除");
		         		}
		         	}
		       });
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/mortgage/mortgageContract/">抵押物品信息列表</a></li>
		<shiro:hasPermission name="mortgage:mortgageContract:edit"><li><a href="${ctx}/mortgage/mortgageContract/form">抵押物品信息添加</a></li></shiro:hasPermission>
	</ul>

       <%-- <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		               业务中心>我的业务>保质抵押>抵押
	   </div>
	</div> --%>
	<form:form id="searchForm" modelAttribute="mortgageContract" action="${ctx}/mortgage/mortgageContract/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>抵押物：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>抵押物类型：</label>
				<form:select path="pledgeType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('collateral_mortgage')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="struts" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ypjc_struts_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>客户姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-bordered">
		<tr><td>
   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="toAdd();" value="增加"/>
   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="toUpdate();" value="修改"/>
   		    <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="toDelete();" value="删除"/>
   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="GetMoretgage();" value="收押"/>
   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="takeOut();" value="借出"/>
   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="takeIn();" value="归还"/>
   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="BackMoretgage();" value="解除"/>
		</td></tr>
    </table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
		<thead>
			<tr>
			      <th>选择</th>				
				<th>业务编号</th>
				<th>抵押物</th>
				<th>客户姓名</th>
				<th>存放地点</th>				
				<th>数量及单位</th>
				<th>评估价值(元)</th>
				<th>抵押物的状态</th>
				<th>抵押物类型</th>
				<th>抵押性质</th>
<!-- 				<th>备注</th> -->
<%-- 				<shiro:hasPermission name="mortgage:mortgageContract:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="mortgageContract">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
			    <td>
					 <input type="radio" name="cid" id="cId" value="${mortgageContract.id}"/>
				</td>				
				<td>
					${mortgageContract.contractNumber}
				</td>				
				<td>
					<shiro:hasPermission name="credit:customerCredit:view"><a href="${ctx}/mortgage/mortgageContract/view?id=${mortgageContract.id}">${mortgageContract.name}</a></shiro:hasPermission>
				</td>
				<td>
					${mortgageContract.customerName}
				</td>
				<td>
					${mortgageContract.address}
				</td>
				<td>
					${mortgageContract.unit}
				</td>
				<td>
					${mortgageContract.worth}
				</td>
				<td>
			
					${fns:getDictLabel(mortgageContract.struts, 'ypjc_struts_status', '')}
				</td>
				<td>
					${fns:getDictLabel(mortgageContract.pledgeType, 'collateral_mortgage', '')}
				</td>
				<td>
					${mortgageContract.number}
				</td>
<!-- 				<td> -->
<%-- 					${mortgageContract.remarks} --%>
<!-- 				</td> -->
<%-- 				<shiro:hasPermission name="mortgage:mortgageContract:edit"><td> --%>
<%--     				<a href="${ctx}/mortgage/mortgageContract/form?id=${mortgageContract.id}">修改</a> --%>
<%-- 					<a href="${ctx}/mortgage/mortgageContract/delete?id=${mortgageContract.id}" onclick="return confirmx('确认要删除该抵押物品信息吗？', this.href)">删除</a> --%>
<%-- 				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>