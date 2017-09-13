<%@ page language="java" import="com.wanfin.fpd.common.config.Cons"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<title>易联支付跨境账户系统</title>
</head>
<script type="text/javascript">	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
    	return false;
    }
</script>
<body>
<!--布局头部-->
<div id="wrap">

  <%@ include file="/WEB-INF/views/modules/wishNew/merchant/wishHead.jsp"%> 
	<!-- 主体内容 -->
	<div class="outside mainbody">
		<div class="inner clearfix">


		<div class="left-content-in fl">
				<div class="title"></div>
				<ul class="menu-list">
					<li>
						<a href="${pageContext.request.contextPath}/wish/contract/wishContract/saveWishContract">
							提前收款
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/wish/contract/wishContract/contractList">
							融资记录
						</a>
					</li>
				</ul>
			</div>
			<div class="right-content-in fl">
			<div class="search-box" style="margin-top: 0">
				<div class="search-item">
					<p class="f_input">
						申请日期
						<input type="text" name="add_time_start" style="height:34px;" class="ipt-date Wdate ipt02 w_15" onfocus="WdatePicker({lang:'zh_CN',readOnly:true,dateFmt:'yyyyMMdd'})" value="" readonly="">
						-
						<input type="text" name="add_time_end" style="height:34px;" class="ipt-date Wdate ipt02 w_15" onfocus="WdatePicker({lang:'zh_CN',readOnly:true,dateFmt:'yyyyMMdd'})" value="">
					</p>
				</div>
				<p class="search-btn">
					<input id="parentIframe" class="btn01" name="" type="submit" value="查询">
				</p>
			</div>
			<div class="table-box">
				<div class="tbbox">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb01">
						<colgroup><col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
						</colgroup><tbody><tr>
						<th>业务编号</th>
						<th>业务类型</th>
						<th>融资金额( 元 )</th>
						<th>申请时间</th>
						<th>放款时间</th>
						<th>订单状态</th>
						<th>操作</th>
					</tr>
					
					
					<c:forEach items="${page.list}" var="contract">
						<tr>
							<td>${contract.contractNumber}</td>
							<td>易借</td>
							<td>${contract.loanAmount}</td>
							<td><fmt:formatDate value="${contract.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${contract.loanDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td> 
							   <c:if test="${contract.status eq '0'}">
	                                                                                   拒绝(中止)
	                            </c:if>
	                            <c:if test="${contract.status eq '1'}">
	                                                                                   审核中
	                            </c:if>
							    <c:if test="${contract.status eq '2'}">
	                                                                                   审核中
	                            </c:if>
							    <c:if test="${contract.status eq '4'}">
	                                                                                   审核中
	                            </c:if>
	                             <c:if test="${contract.status eq '5'}">
	                                                                                        审核中
	                            </c:if>
							    <c:if test="${contract.status eq '6'}">
	                                                                                        已放款
	                            </c:if>
							    <c:if test="${contract.status eq '7'}">
	                                                                                   已结清
	                            </c:if>
	                            <td><a href="${pageContext.request.contextPath}/wish/contract/wishContract/contractDetail?contractId=${contract.id}">查看详情</a></td>
	                        </td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				</div>
					<form class="pageForm" action="/mer/transData.do" targetfor=".data">

					<input type="hidden" name="seller_id" value="-1">

					<input type="hidden" name="tradeId" value="blank">


					<!-- pagination -->
					<div class="pagination pagination-left">
						<ul class="pager">
                            ${page}
						</ul>
						<!-- <div class="results"> <span>总共0条记录</span> </div> -->
					</div>
					<!-- end pagination -->

				</form>
			</div>
		</div>
	</div>
	</div>
  <%@ include file="/WEB-INF/views/modules/wishNew/merchant/wishFoot.jsp"%> 
</div>
</body>
</html>