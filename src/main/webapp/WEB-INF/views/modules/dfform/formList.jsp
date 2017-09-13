<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>表单管理</title>
		<%@ include file="/WEB-INF/views/include/meta.jsp"%>
		<link rel="stylesheet" href="${ctxStatic}/css/style.css" type="text/css" media="all" />
		<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctxStatic}/jquery/table.js" type="text/javascript"></script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/dfform/form" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNumber}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">
					表单管理
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0"
			cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">
					<span>表单名称：</span>
				</td>
				<td class="td_table_2" colspan="3">
					<input type="text" class="input_240" name="name" value="${name }"/>
					<input type='submit' class='button_70px' style="margin-top: 5px;" value='查询'/>
				</td>
			</tr>
		</table>
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left" style="padding-left: 10px;">
					<c:if test="${empty lookup}">
					<input type='button' onclick="addNew('${ctx}/dfform/form/add')" class='button_70px' value='新建'/>
					</c:if>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align=center  class="td_list_1" nowrap>
					产品表单
				</td>
				<td align=center  class="td_list_1" nowrap>
					产品名称
				</td>
				<td align=center  class="td_list_1" nowrap>
					创建人
				</td>
				<td align=center  class="td_list_1" nowrap>
					创建时间
				</td>
				<td align=center  class="td_list_1" nowrap>
					操作
				</td>				
			</tr>
			<c:forEach items="${page.list}" var="form">
				<tr>
					<td class="td_list_2" align=center nowrap>
						${form.name}&nbsp;
					</td>
					<td class="td_list_2" align=center nowrap>
						${form.displayName}&nbsp;
					</td>
					<td class="td_list_2" align=center nowrap>
						${form.creator}&nbsp;
					</td>
					<td class="td_list_2" align=center nowrap>
						${form.createTime}&nbsp;
					</td>
					
					<td class="td_list_2"  align=center nowrap>
					<c:choose>
                        <c:when test="${empty lookup}">
						<a href="${ctx}/dfform/form/delete/${form.id }"  title="删除" onclick="return confirmDel();">删除</a>
<%-- 						<a href="${ctx}/dfform/form/edit/${form.id }"  title="编辑">编辑</a> --%>
						<a href="${ctx}/dfform/form/designer/${form.id }"  title="设计">设计</a>
						<a href="${ctx}/dfform/form/use/${form.id }"  title="录入产品">录入产品</a>
						<a href="${ctx}/dfform/form/formData/${form.id}"  title="查看">查看产品</a>
						<a href="${ctx}/dfform/form/depLoy/${form.id}"  title="业务配置">业务配置</a>
						</c:when>
                        <c:otherwise>
                        <a href="javascript:void(0)" class="btnSelect" onclick="confirmForm('${form.id}')" title="选择">选择</a>
                        </c:otherwise>
                    </c:choose>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNumber}" totalPages="${page.totalPage }" totalRecords="${page.totalRow }"/>
		</table>
	</form>
    <script>
        function confirmForm(formId) {
            window.returnValue = formId;
            window.close();
        }
    </script>
	</body>
</html>
