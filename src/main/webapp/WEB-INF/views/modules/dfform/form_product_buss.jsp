<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>产品业务功能配置</title>
		<%@ include file="/WEB-INF/views/include/meta.jsp"%>
		<link rel="stylesheet" href="${ctxStatic}/css/style.css" type="text/css" media="all" />
		<script type="text/javascript" charset="utf-8" src="${ctxStatic}/jquery/jquery-1.8.3.min.js"></script>
		<script type="text/javascript">
           function toSubmit(){
        	   
        	   var b_id = document.getElementsByName('productbiz_id');//业务功能id
        	   var a_id = document.getElementsByName('actid');//审批流程id
        	   var productbiz_name = document.getElementsByName('productbiz_name');//业务名称
        	   
               var bid = "";
	       		for(var i =0;i<b_id.length;i++){
	       			bid = bid + b_id[i].value+",";
				}
	       		$("#bizId").val(bid);
       		
	       		
	       		var aid = "";
       			for(var i =0;i<a_id.length;i++){
	       			aid = aid + a_id[i].value+",";
				}
	       		$("#act_Id").val(aid);
	       		
	       		var bname = "";
       			for(var i =0;i<productbiz_name.length;i++){
	       			bname = bname + productbiz_name[i].value+",";
				}
	       		$("#bizName").val(bname);	
	       		
	       		
	       		
	       		if (window.confirm("请确认是否保存？")) {
	       			document.forms[0].submit();
	       			$.ligerDialog.waitting('正在保存中,请稍候...'); 
	       			openWinInfo('', 340 , 140) ;
	       	    }	
	       		 
           }
         

		</script>
		
		
	</head>

	<body>
		<form id="inputForm" action="${ctx }/dfform/form/savedepLoy" method="post">
			<table width="30%" border="0" align="center" cellpadding="0"
				class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">
						产品业务流程配置
					</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" align="center"	cellspacing="0" style="margin-top: 0px;width: 60%">
				<tr style="height: 30px;">
					<td class="td_table_1">
						<span style="font-size: 15px;">产品名称：</span>
					</td>
					<td class="td_table_2" colspan="3" style="font-size: 15px;">
						${form_displayName}
						<input type="hidden" value="${form_id}" name="form_id">
						<input type="hidden" id="bizId"  name="bizId">
						<input type="hidden" id="bizName"  name="bizName">
						<input type="hidden" id="act_Id"  name="act_Id">
					</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px;width: 60%">
				<tr style="height: 30px;">
					<td class="td_table_1" align="center">
						<span style="font-size: 15px;">业务功能</span>
					</td>
					<td class="td_table_1" align="center">
						<span style="font-size: 15px;">表单模板配置</span>
					</td>
					<td class="td_table_1" align="center">
						<span style="font-size: 15px;">审批流程配置</span>
					</td>
					<td class="td_table_1">
						<span style="font-size: 15px;">风控配置</span>
					</td>
				</tr>
				<c:forEach items="${productbizList}" var="productbiz">
					<tr>
						<td class="td_table_1">
							<input type="hidden" value="${productbiz[0]}" name="productbiz_id">
							<input type="hidden" value="${productbiz[1]}" name="productbiz_name">
							${productbiz[1]}
						</td>
						<td class="td_table_1" >
<!-- 						    <select> -->
<%-- 						        <c:forEach items="${productbizList}" var="biz_select"> --%>
<%-- 						        <option value="${biz_select[0]}">${biz_select[1]}</option> --%>
<%-- 						        </c:forEach> --%>
<!-- 						    </select> -->
						</td>
						<td class="td_table_1">
							 <select name="actid">
							    <option value="--">-请选择业务流程-</option>
						        <c:forEach items="${actList}" var="act">
						         <option value="${act[0]}">${act[3]}</option>
						        </c:forEach>
						    </select>
						</td>
						<td class="td_table_1">
						
						</td>
					</tr>
				</c:forEach>
			</table>
			<table align="center" border="0" cellpadding="0"
				cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<input type="button" class="button_70px" name="reback" value="保存" onclick="toSubmit();">
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
