<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>贷后检查管理</title>
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
		
		function showjBox(title, url){
			top.$.jBox.open(
					"iframe:"+url, title,
					$(top.document).width()-100,
					$(top.document).height()-100, 
					{
						buttons : {
							//"刷新" : "refresh",
							"关闭" : true
						},
						bottomText : "",
						submit : function(v, h, f) {
							var ifrWin = h.find("iframe")[0].contentWindow;
							if (v == "refresh") {
								ifrWin.location.reload(true);
								//ifrWin.clearAssign();
								return false;
							}
						},
						loaded : function(h) {
							$(".jbox-content", top.document).css("overflow-y",
									"hidden");
						}
					});
		}
		
		//查看明细
		function trackDetail(){
			var cid = getCheckValue("id");
			//alert("添加用途跟踪:id:"+cid);
			if(cid.length==0){
				alert("请选择一个对应的检查");
				return;
			}
			
			showjBox("查看明细", "${ctx}/postlending/usetracking/actualPurpose/detail?id="+cid);
		}
		
		//添加用途跟踪
		function toAddTrack(){
			var loanContractId = "${actualPurpose.loanContractId}";
			//alert("添加用途跟踪:loanContractId:"+loanContractId);
			if(loanContractId.length==0){
				alert("没有获取到对应的合同信息");
				return;
			}
			showjBox("添加检查记录", "${ctx}/postlending/usetracking/actualPurpose/form?loanContractId="+loanContractId);
		}
	</script>
</head>
<body>
	<shiro:hasPermission name="refund:reimburse:edit"><td>
		<a  class="btn btn-primary"  onclick="toAddTrack();">添加检查记录</a>
		<a  class="btn btn-primary"  onclick="trackDetail();">查看明细</a>
	</shiro:hasPermission>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>检查日期</th>
				<th>标题</th>
				<th>合同编号</th>
				<th>客户名称</th>
				<th>结欠金额</th>
				<th>借款金额</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="actualPurpose">
			<tr>
				<td>
					 <input type="radio" name="id" id="id" value="${actualPurpose.id}"/>
				</td>
				<td>
					<fmt:formatDate value="${actualPurpose.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${actualPurpose.title}
				</td>
				<td>
					${actualPurpose.contractNumber}
				</td>
				<td>
					${actualPurpose.customerName}
				</td>
				<td>
					${actualPurpose.lackAmount}
				</td>
				<td>
					${actualPurpose.loanAmount}
				</td>
				<td>
					${fns:getDictLabel(actualPurpose.status, 'ContractStatus', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>