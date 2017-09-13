<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>表单管理</title>
		<%@ include file="/WEB-INF/views/include/meta.jsp"%>
		<link rel="stylesheet" href="${ctxStatic}/css/style.css" type="text/css" media="all" />
		<script type="text/javascript" charset="utf-8" src="${ctxStatic}/jquery/jquery-1.8.3.min.js"></script>
	</head>

	<body>
		<form id="inputForm" action="" method="post">
			<table width="100%" border="0" align="center" cellpadding="0"
				class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr style="height: 50px;">
					<td class="td_table_top" align="center">
						产品查看
					</td>
				</tr>
			</table>
			<table class="table_all" align="left" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px;width: 50%;">
				${form1}
				<tr>
					<td class="td_table_1">
						<span>更新时间：</span>
					</td>
					<td class="td_table_2" colspan="3">
						&nbsp;${form.UPDATETIME}
					</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0"	cellspacing="0" style="margin-top: 0px;width: 50%;">
				<tr>
		              <td class='td_table_2' style='width: 40%;'>业务功能</td>
		              <td class='td_table_2' style='width: 40%;'>流程配置</td>
		        </tr>
		        <c:if test="${peizhilist!=null}">
		             <c:forEach items="${peizhilist}" var="peizhi">
	        	    <tr>
			              <td class='td_table_2' style='width: 40%;'>${peizhi[3]}</td>
			              <td class='td_table_2' style='width: 40%;'>${peizhi[2]}</td>
	                </tr>
	        	</c:forEach>
		        </c:if>
	        	
			</table>
			<table align="center" border="0" cellpadding="0"
				cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<input type="button" class="button_70px" name="reback" value="返回"
							onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
