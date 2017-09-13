<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品业务配置</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/modules/productConfig/front/include/head2.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".personid").click(function(){
				var typeId = $(this).attr("data-typeid");
				if(typeId){
					top.$.jBox.tip("数据不完善!");
					//openWin('${ctx}/wind/creditchecking/tCreditChecking/view?type=person&typeSub=pers&id=${tCreditChecking.id}&typeId='+typeId, '个人信息');
				}
			});
			$(".corpid").click(function(){
				var typeId = $(this).attr("data-typeid");
				if(typeId){
					top.$.jBox.tip("数据不完善!");
					//openWin('${ctx}/wind/creditchecking/tCreditChecking/view?type=corporation&typeSub=corp&id=${tCreditChecking.id}&typeId='+typeId, '公司信息');
				}
			});
		});
		function openWin(url, title){
			top.$.jBox.open("iframe:"+url, 
	    			title, $(top.document).width()-200,$(top.document).height()-150,{
	    		buttons:{"返回":"return", "刷新":"refresh", "关闭":true}, 
	    		bottomText:"",
	    		submit:function(v, h, f){
	    			var ifrWin = h.find("iframe")[0].contentWindow;
	    			if(v=="refresh"){
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
	</script>
	<style type="text/css">
		.remark{width: 150px; }
		.bf50{ width: 49%; display: inline-block; border-left: 3px solid #ccc; margin: 2px 0px;  overflow: hidden; background-color: #eee;}
		.bf50 .title{margin: 5px;} .bf50 .val{margin: 5px;}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/wind/creditchecking/tCreditChecking/view?id=${tCreditChecking.id}">
		<c:if test="${tCreditChecking.type eq 'person'}">个人征信信息(${tCreditChecking.employee.name} )</c:if><c:if test="${tCreditChecking.type eq 'corporation'}">机构征信信息(${tCreditChecking.company.name} )</c:if></a></li>
	</ul><br/>
	<sys:message content="${message}"></sys:message>
	<table class="table table-bordered table-condensed">
		<thead><tr>
			<th width="10%">信息类别</th>
			<th width="90%">信息详情</th>
		</tr></thead>
		<tbody>
			<c:forEach var="tcchecking" items="${ccheckingAdapters }">
				<tr>
					<td>
						<c:if test="${tcchecking.type eq 'person'}">
							${fns:getDictLabel(tcchecking.typeSub , 'c_person', '')}
						</c:if>
						<c:if test="${tcchecking.type eq 'corporation'}">
							${fns:getDictLabel(tcchecking.typeSub , 'c_corporation', '')}
						</c:if>
					</td>
					<td>
						<c:forEach items="${tcchecking.ccdata}" var="cda">
							<table class="table table-bordered table-striped">
								<tbody>
								<c:forEach items="${cda.cmaps}" var="cmap">
									<c:if test="${(cmap.key eq 'personid')}">
										<tr>
											<td class="remark">
												个人信息
											</td>
											<td><a href="javascript:void(0);" class="personid" data-typeid="${cmap.val}">查看详情</a></td>
										</tr>
									</c:if>
									<c:if test="${(cmap.key eq 'corpid')}">
										<tr>
											<td class="remark">
												公司信息
											</td>
											<td><a href="javascript:void(0);" class="corpid" data-typeid="${cmap.val}">查看详情</a></td>
										</tr>
									</c:if>
									<c:if test="${cmap.key eq 'persionBase'}">
										<tr>
											<td class="remark">
												${cmap.remark}
											</td>
											<td>
												<c:forEach items="${cmap.val.cmaps}" var="cmapd">
													<div class="bf50"><span class="title">${cmapd.remark}</span>:<span class="val">${cmapd.val}</span></div>
												</c:forEach>
											</td>
										</tr>
									</c:if>
<%-- 									<c:if test="${cmap.key eq 'persionBase'}">
										<tr>
											<td class="remark">
												${cmap.remark}
											</td>
											<td>
												<c:forEach items="${cmap.val.cmaps}" var="cmapd">
													<div class="bf50"><span class="title">${cmapd.remark}</span>:<span class="val">${cmapd.val}</span></div>
												</c:forEach>
											</td>
										</tr>
									</c:if> --%>
									<c:if test="${(cmap.key ne 'id') && (cmap.key ne 'personid') && (cmap.key ne 'corpid') && (cmap.key ne 'persionBase')}">
										<tr>
											<td class="remark">
												${cmap.remark}
											</td>
											<td>
												${cmap.val}
											</td>
										</tr>
									</c:if>
								 </c:forEach>
								</tbody>
							</table>
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>