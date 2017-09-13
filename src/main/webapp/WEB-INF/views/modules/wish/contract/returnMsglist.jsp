<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>扣款明细</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${isClose == 1}">
				showTip("${message}");
				top.$.jBox.close(true);
			</c:if>
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			//附件${ctx}/files/tContractFiles/showfilelist/{业务id}
			// businesstype 附件类型、oper= edit|其他   是否可编辑
			var url = "${ctx}/files/tContractFiles/showfilelist/${fileId}.html?height=350&businesstype=wish&oper=edit";
		    $("#${nid}filelist").load(url);
		});
	</script>
</head>
<body>
	<form:form id="inputForm" action="${ctx}/push/p2p/repayment" method="post" class="form-horizontal">
	
	    <input id="ids" type="hidden" name="ids" value="${ids}"/>
	    <input id="taskId" type="hidden" name="taskId" value="${fileId}"/>
	   
		<table id="contentTable"
			class="table table-center table-striped table-bordered table-condensed">
			<thead>

				<th>借款业务id</th>
				<th>当次还款本金</th>
			</thead>
			<tbody>
				<tr>
					<td>合计：</td>
					<td>${sum}</td>

				</tr>
				<c:forEach items="${list}" var="returnedMsg">
					<tr>
						<td>${returnedMsg.loanContractId}</td>
						<td>${returnedMsg.money}</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
		<label class="control-label">相关附件${nid}：</label>
	       <div class="control-group" style="height: 500px;">
			     <div id="${nid}filelist" style="height: 550px;"></div>
		   </div>
		    
		  <div class="form-actions" style="display: none;">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交"/>
		</div>
	</form:form>
</body>
</html>