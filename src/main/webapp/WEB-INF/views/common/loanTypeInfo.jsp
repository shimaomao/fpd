<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%--
	author : zzm 
	不同贷款方式对应的信息
	通用块
	
	注意：如果业务数据是新增，那么需要给业务id赋值一个临时newid（如：'new_'+UUID），然后在保存业务数据后更换掉临时id，与附近上传相类似
		 参考TLoopLoanService.java——》save()方法
 --%>
<c:set var="loanTypesStr" value="${param.loanTypesStr}" /> <!--贷款方式-->
<c:set var="nid" value="${param.nid}" />
<c:set var="height" value="${empty param.height ? 80 : param.height }" /> <!-- 列表的高度，默认80-->
<c:set var="businessTable" value="${param.businessTable}" /> <!-- 必传参数，关联的业务的表名称 -->
<c:set var="businessId" value="${param.businessId }" />  <!-- 必传参数，关联的业务id（合同ID） -->
<c:set var="oper" value="${empty param.oper ? 'edit' : param.oper }" /> <!-- 是否可编辑，view|其它  = 不可编|可编 -->
<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
<link href="${ctxStatic}/jqGrid/4.6/css/css_cerulean/ui.jqgrid.css" rel="stylesheet"/>
<link href="${ctxStatic}/jqGrid/4.6/css/css_cerulean/jquery.ui.theme.css" rel="stylesheet"/>
<script src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.js" type="text/javascript"></script>
<script src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.extend.js" type="text/javascript"></script>
	<script type="text/javascript">
	require(['app/loanType'], function(lt){
		lt.loanTypeChange({
			loanTypesStr:"${loanTypesStr}",
			height: "${height}",
			businessTable: "${businessTable}",
			businessId: "${businessId}",
			oper: "${oper}",
			nid: "${nid}"
		});
		
	});
	</script>
	<p>
	<div>
		<div id="${nid}guaranteelist"></div>
	</div>
	<p>
	<div>
		<div id="${nid}mortgagelist" ></div>
	</div>
	<p>
	<div>
		<div id="${nid}pledgelist"></div>
	</div>
	<p>
		
