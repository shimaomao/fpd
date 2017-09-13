<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>产品业务功能配置</title>
		<meta name="decorator" content="default"/>
		<script type="text/javascript" src="${ctxStatic}/util.js"></script>
		<script type="text/javascript">
        function toSubmit(){
        	    
       	     var a_id = document.getElementsByName('actid');//审批流程id
       	     var aid = "";
      			for(var i =0;i<a_id.length;i++){
       			  aid = aid + a_id[i].value+",";
		    	}
       		 $("#act_Id").val(aid);
	       		
	       	 var productbiz_name = document.getElementsByName('productbiz_name');//业务名称
	       	 var bname = "";
   			 for(var i =0;i<productbiz_name.length;i++){
       			bname = bname + productbiz_name[i].value+",";
		 	 }
       		 $("#bizName").val(bname);	  
       		 
       		 
       		 var wind_ids = getCheckValue('windCheck');
       		 $("#wind_ids").val(wind_ids);	  
       		 
       		
	       	 
       		if (window.confirm("请确认是否保存？")) {
       			document.forms[0].submit();
       	    }	
        }
		</script>
		<style type="text/css">
			.content_block {
				width:100%;
				border-bottom:0px #999999 solid;
				margin-top:0px;
				margin-bottom:10px;
				padding-bottom:10px;
			}
			.content_block .ax_ico {
				float:left;
				margin-right: 10px;
			}
			.content_block .ax_title {
			    font-family: '微软雅黑 Regular','微软雅黑';
			    font-weight: 400;
			    font-style: normal;
			    font-size: 13px;
			    color: rgb(0, 0, 0);
			    text-align: center;
			    line-height: 20px;
				height:20px;
				float:left;
			}	
		</style>
		
	</head>

	<body>
		<div class="content_block">
			<div class="ax_ico"><img src="${ctxStatic}/images/pz.png" width="20px" height="20px"/></div>
			<div class="ax_title">产品业务流程配置-(产品名称：${tProduct.name})</div>
			<div style="clear:left;"></div>
		</div>
		<form id="inputForm" action="${ctx}/product/tProduct/savedepLoy" method="post">
             <!--产品id -->
			<input type="hidden" value="${tProduct.id}" name="id">
             <!--业务功能点id -->
			<input type="hidden" value="${biz_id}" name="bizId">
             <!--业务功能点名称 -->
			<input type="hidden" id="bizName"  name="bizName">
			 <!--流程id -->
			<input type="hidden" name="act_Id" id="act_Id">
             <!--风控模型id -->
			<input type="hidden" name="wind_ids" id="wind_ids">
			
			<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:80%;">
					<thead><tr><th colspan=2 style="width:35%;">业务模块</th><th>表单配置</th><th>流程配置</th><th>风控配置</th></tr></thead>
					<tr>
						<td style="height: 100px;width: 100px;font-size: 15px;">
							贷前管理
						</td>
						<td>
							<div style="float: left;">
								<c:forEach items="${productbizList}" var="productbiz" varStatus="index">
								    <c:if test="${productbiz[8]=='1'}">
								       <div style="height: 10px;font-size: 14px;">${productbiz[1]}</div><br>
								       <input type="hidden" value="${productbiz[1]}" name="productbiz_name">
								    </c:if>
								</c:forEach>
							</div>
						</td>
						<td>
							<span style="font-size: 15px;">-</span>
						</td>
						<td  >
							  <c:forEach items="${productbizList}" var="productbiz">
								    <c:if test="${productbiz[8]=='1'}">
								         <select name="actid" style="height: 24px;font-size: 14px;width: 190px;">
											    <option value="0">-请选择业务流程-</option>
										        <c:forEach items="${actList}" var="act">
										         <option value="${act[0]}">${act[3]}</option>
										        </c:forEach>
										  </select><br>
								    </c:if>
							   </c:forEach>
						</td>
						<td rowspan="3"  style="vertical-align:top">
							<br/><br/>
							
							 <c:forEach items="${winds}" var="wind">
							    <c:if test="${wind.status==1}">
							    	<span style="font-size: 15px;"><input type="checkbox" name="windCheck" value="${wind.id}"/>${wind.name}</span><br/><br/>
							    </c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td  style="height: 100px;font-size: 15px;">
							贷中管理
						</td>
						<td>
						<div style="float: left;">
								<c:forEach items="${productbizList}" var="productbiz">
								    <c:if test="${productbiz[8]=='2'}">
								        <div style="height: 25px;font-size: 14px;">${productbiz[1]}</div>
								        <input type="hidden" value="${productbiz[1]}" name="productbiz_name">
								    </c:if>
							   </c:forEach>
							</div>
						</td>
						<td  >
							<span style="font-size: 15px;">-</span>
						</td>
						<td  >
							<c:forEach items="${productbizList}" var="productbiz">
							    <c:if test="${productbiz[8]=='2'}">
							    	<div style="height: 25px;font-size: 14px;">
								         <select name="actid" style="height: 24px;font-size: 14px;width: 190px;">
											    <option value="0">-请选择业务流程-</option>
										        <c:forEach items="${actList}" var="act">
										         <option value="${act[0]}">${act[3]}</option>
										        </c:forEach>
										  </select>
									  </div>
							    </c:if>
						     </c:forEach>
						</td>
					</tr>
					<tr>
						<td  style="height: 100px;font-size: 15px;">
							贷后管理
						</td>
						<td>
						<div style="float: left;">
								<c:forEach items="${productbizList}" var="productbiz">
								    <c:if test="${productbiz[8]=='3'}">
								        <div style="height: 25px;font-size: 14px;">${productbiz[1]}</div>
								        <input type="hidden" value="${productbiz[1]}" name="productbiz_name">
								    </c:if>
								</c:forEach>
							</div>
						</td>
						<td  >
						     <span style="font-size: 15px;">-</span>
						</td>
						<td  >
							<c:forEach items="${productbizList}" var="productbiz">
								    <c:if test="${productbiz[8]=='3'}">
								         <select name="actid" style="height: 24px;font-size: 14px;width: 190px;">
											    <option value="0">-请选择业务流程-</option>
										        <c:forEach items="${actList}" var="act">
										         <option value="${act[0]}">${act[3]}</option>
										        </c:forEach>
										  </select><br>
								    </c:if>
							 </c:forEach>
						</td>
					</tr>
			</table>
			<div class="form-actions">
				<shiro:hasPermission name="product:tProduct:edit"><input id="btnSubmit" class="btn btn-primary" onclick="toSubmit();"  value="保存"/>&nbsp;</shiro:hasPermission>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
		</form>
	</body>
</html>
