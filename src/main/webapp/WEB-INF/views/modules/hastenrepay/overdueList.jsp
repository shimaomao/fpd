<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<html>
<head>
	<title>催收管理</title>
	<meta name="decorator" content="default"/>
  <script type="text/javascript" src="${ctxStatic}/util.js"></script>
  <script type="text/javascript" src="${ctxStatic}/vow/contract_view.js?v=1"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
    		return false;
    	}
		 
		
		function add(){
			var array = getCheckValue("loanContractId");
			if(array.length==0){
			    showTip("请选择一条业务合同!");
			    return;
			}
			top.$.jBox.open("iframe:${ctx}/hastenrepay/hastenRepayRecord/form?contractId="+array[0], 
	    			"催收记录",800,400,{
	    		buttons:{"保存":"ok", "刷新":"refresh", "关闭":true}, 
	    		bottomText:"",
	    		submit:function(v, h, f){
	    			var ifrWin = h.find("iframe")[0].contentWindow;
	    			if(v=="ok"){
	    				ifrWin.inputForm.submit();
	                	//ifrWin.clearAssign();
	    				return false;
	                }else if(v=="refresh"){
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
		
		function detail(){
			var array = getCheckValue("loanContractId");
			if(array.length==0){
			    showTip("请选择一条业务合同!");
			    return;
			}
			top.$.jBox.open("iframe:${ctx}/hastenrepay/hastenRepayRecord/list?contractId="+array[0], 
	    			"催收记录",800,400,{
	    		buttons:{"刷新":"refresh", "关闭":true}, 
	    		bottomText:"",
	    		submit:function(v, h, f){
	    			var ifrWin = h.find("iframe")[0].contentWindow;
	    			if(v=="ok"){
	    				ifrWin.inputForm.submit();
	                	//ifrWin.clearAssign();
	    				return false;
	                }else if(v=="refresh"){
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
		
		//打印
		function print(){
			var array = getCheckValue("loanContractId");
			if(array.length==0){
			    showTip("请选择一条业务合同!");
			    return;
			}
			top.$.jBox.open("iframe:${ctx}/lettertpl/letterTpl/toView?id="+array[0]+"&ukey=<%=Cons.LetterTplType.HASTEN_REPAY_RECORD%>", 
	    			"催收函件模板",830,$(top.document).height()-240,{
	    		buttons:{"刷新":"refresh", "关闭":true}, 
	    		bottomText:"选择合同模板，然后查看合同填充数据预览。",
	    		submit:function(v, h, f){
	    			var ifrWin = h.find("iframe")[0].contentWindow;
	    			if(v=="ok"){
	    				ifrWin.inputForm.submit();
	                	//ifrWin.clearAssign();
	    				return false;
	                }else if(v=="refresh"){
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

		
		/***还款提醒***/
		function warn(){
			var array = getCheckValue("loanContractId");
		  if(array.length==0){
			  showTip("请选择一条业务合同!");
		  	return;
		  }
		  var url = "${ctx}/hastenrepay/hastenRepayRecord/repaywarn?id="+array[0];
		  location.href = url;
		}
		
		/***律师函 短信提醒***/
		function letterSms(){
			var array = getCheckValue("loanContractId");
		  if(array.length==0){
			  showTip("请选择一条业务合同!");
		  	return;
		  }
		  var url = "${ctx}/hastenrepay/hastenRepayRecord/letterSms?id="+array[0];
		  location.href = url;
		}
		
		/***仲裁 提醒***/
		function zhongCaiSms(){
			var array = getCheckValue("loanContractId");
		  if(array.length==0){
			  showTip("请选择一条业务合同!");
		  	return;
		  }
		  var url = "${ctx}/hastenrepay/hastenRepayRecord/zhongCaiSms?id="+array[0];
		  location.href = url;
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hastenrepay/hastenRepayRecord/overdueList">逾期催收</a></li>
	</ul>

	<form:form id="searchForm" modelAttribute="tLoanContract" action="${ctx}/hastenrepay/hastenRepayRecord/overdueList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申请日期：</label>
				<input name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>业务编号：</label>
				<form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>客户姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>贷款金额：</label>
				<form:input path="loanAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>贷款期限：</label>
				<form:input path="loanPeriod" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
<!-- 			<li><label>贷款方式：</label> -->
<%-- 				<form:checkboxes path="loanTypeList" items="${fns:getDictList('loan_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<!-- 			</li> -->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>

  	<shiro:hasPermission name="contract:tLoanContract:edit">
		<table class="table table-bordered">
			<tr>
			   <td>
			    <a class="btn btn-primary" onclick="add();">通话催收记录</a>
				<a class="btn btn-primary" onclick="warn();" title="短信记录在我的桌面-消息管理-短信记录">短信催收</a>
				<a class="btn btn-primary" onclick="letterSms();" title="短信记录在我的桌面-消息管理-短信记录">律师函</a>			
				<a class="btn btn-primary" onclick="zhongCaiSms();" title="短信记录在我的桌面-消息管理-短信记录">仲裁</a>
				<a class="btn btn-primary" onclick="print();">打印催收函</a>
			   </td>
			</tr>
	  </table>
	</shiro:hasPermission>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
		<thead>
			<tr>
				<th>选择</th>
				<th>业务编号</th>
				<th>客户姓名</th>
				<th>贷款金额（元）</th>
				<th>贷款期限</th>
				<th>贷款利率</th>
				<th>申请日期</th>
				<th>贷款方式</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tLoanContract">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>
					 <input type="radio" name="loanContractId" id="loanContractId" value="${tLoanContract.id}" data-ukey="${tLoanContract.ukey}" />
				</td>
				<td><a href="${ctx}/contract/tLoanContract/detail?id=${tLoanContract.id}">
					${tLoanContract.contractNumber}
				</a>
				</td>
				<td>
					${tLoanContract.customerName}
				</td>
				<td>
					<fmt:formatNumber value="${tLoanContract.loanAmount}" pattern="#,#00.00#"/>
				</td>
				<td>
					${tLoanContract.loanPeriod}
					${(tLoanContract.loanPeriod == 1) ? '年' : ((tLoanContract.loanPeriod == 3) ? '日' : '月')}
				</td>
				<td>
					${tLoanContract.loanRate}%
				</td>
				<td>
					<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabels(tLoanContract.loanType, 'loan_type', '')}
				</td>
				<td>
					${fns:getDictLabel(tLoanContract.status, 'loan_contract_status', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>