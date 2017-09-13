<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>理财产品附件</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//附件
			var url = "${ctx}/files/tContractFiles/showfilelist/${tFinancialProduct.id}.html?width=815&height=160&businesstype=<%=FileType.FILE_TYPE_FINANCIAL_PRODUCT%>&oper=view&nid=${nid}file";
			$("#${nid}filelist").load(url);
		});
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<td><div id="${nid}filelist" ></div></td>
		</tr>
	</table>
</body>
</html>