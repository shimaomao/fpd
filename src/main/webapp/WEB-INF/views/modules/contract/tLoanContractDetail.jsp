<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"%>
<html>
<head>
<title>业务办理</title>
<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${ctxStatic}/util.js?v=1"></script>
<script type="text/javascript" src="${ctxStatic}/pay_plan.js"></script>
<script type="text/javascript">
        require(['helper/api','app/repayPlan'], function(api,rp){
        	$(document).ready(function(){ //#3435 生产环境网速差，要阻塞一下，让上下文先加载完，不然数据加载不全
        		var data = ${fns:toJson(tLoanContract)};
 				var form = $("#target");
 				var btn = $("#submitViewForm");
 				api.form.init(form, eval(data));
 				//禁用form表单中所有的选项
 				disableForm("target",true);
 				//1	新增	
				//2	待审批	
				//3	待签订	
				//4	放款待审批	
				//5待放款(贷款)
				//var ddd = '0,1,2,3,4,5'.indexOf('${tLoanContract.status}');
				//alert('${tLoanContract.status}');
 				if ('1,2,3,4,5'.indexOf('${tLoanContract.status}') >= 0) {
 					//alert('create');
 					rp.initialize({
 						businessId : "${tLoanContract.id}",
 	 					businessType : "apply",  //apply|extend|earlyrepay...
 	 					amount : "${tLoanContract.loanAmount}",
 	 					loanRate : "${tLoanContract.loanRate}",
 	 					loanRateType : "${tLoanContract.loanRateType}",//add by srf #3121
 	 					loanPeriod : "${tLoanContract.loanPeriod}",
 	 					loanDate : '<fmt:formatDate value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd"/>',
 	 					payType : "${tLoanContract.payType}",//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
 	 					periodType : "${tLoanContract.periodType}",//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
 	 					payDay : "${tLoanContract.payDay}",//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
 	 					payOptions : "${tLoanContract.payOptions}",//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
 	 					ifRealityDay: "${tLoanContract.ifRealityDay}"
 	 				},"#showplansDiv");
 				}
 				url = "${ctx}/files/tContractFiles/showfilelist/${tLoanContract.id}.html?oper=view";
 				$("#${nid}filelist").load(url);
 				
 				showTab("${param.tab}");
    		});
        	   
         });
        
        function showTab(tab){
    		if((tab == undefined) || (tab == null) || (tab == '')){
    			tab = "bd";
    		}
    		$(tab).show();
    		$("."+tab).addClass('active').siblings().removeClass('active');
    		$("#"+tab).show().siblings('.nav-tab-con').hide();
    	}
        
      	//通过id导出PDF文件
    	function toViewContract(businessContractId){
    		//console.info("通过id导出PDF文件--》businessContractId="+businessContractId);
    		if(businessContractId && businessContractId.length > 0){
    			$.post("${ctx}/contract/businessContract/exportBusinessContract",{"id":businessContractId},
    					function(data){
    						if(data.status ===1){
    							//console.info("============================");
    							//console.info("data.businessContract.id="+data.businessContract.id);
    							//console.info("============================");
    							window.open("${ct}"+data.pdfpath, "_blank");
    						}else{
    							alertx(data.message);
    						}
    					}
    				);
    		}else{
    			alert("导出错误！");
    		}
    	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="bd"><a href="${ctx}/contract/tLoanContract/detail?id=${tLoanContract.id}&tab=bd">业务明细查看</a></li>
		<li class="cw"><a href="${ctx}/contract/tLoanContract/detail?id=${tLoanContract.id}&tab=cw">财务记录信息</a></li>
		<li class="ginfo"><a href="${ctx}/contract/tLoanContract/detail?id=${tLoanContract.id}&tab=ginfo">担保信息</a></li>
	</ul><br/>
	<div id="bd" class="nav-tab-con" style="display:none;">
		${dfFormTpl.parsehtml }
		<br><br>
		<table cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				<td>
					<h3 align="center">还款计划</h3>
					<c:if test="${fn:contains('1,2,3,4,5', tLoanContract.status)}">	
						<div id="showplansDiv">	
						</div>					
					</c:if>
					<c:if test="${!fn:contains('1,2,3,4,5', tLoanContract.status)}">	
					<table id="plansTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<td rowspan="2">期数</td>
								<td rowspan="2">催收次数</td>
								<td rowspan="2">计息开始日期</td>
								<td rowspan="2">计息结束日期</td>
								<td rowspan="2">计划到账日</td>
								<td colspan="2">本金</td>
								<td colspan="2">利息</td>
								<td rowspan="2">状态</td>
							</tr>
							<tr align="center" class="tit_center_bg">
								<td>应还</td>
								<td>已还</td>
								<td>应还</td>
								<td>已还</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${repayplanList}" var="repayPlan">
								<tr>
									<td>第${repayPlan.num}期</td>
									<td>${repayPlan.csNum}</td>
									<td>${repayPlan.startDate}</td>
									<td>${repayPlan.endDate}</td>
									<td>${repayPlan.accountDate}</td>
									<td>${repayPlan.principal}</td>
									<td><c:if test="${empty repayPlan.principalReal}">0</c:if>${repayPlan.principalReal}</td>
									<td>${repayPlan.interest}</td>
									<td><c:if test="${empty repayPlan.interestReal}">0</c:if>${repayPlan.interestReal}</td>
									<td>${fns:getDictLabel(repayPlan.status, 'repay_status', '')}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</c:if>
				</td>
			</tr>
			<tr>
				<td><br><br>
					<h3 align="center">业务申请及放款审批记录</h3>
				</td>
			</tr>
		</tbody>
				<table id="liuchenTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<td>流程名称</td>
							<td>审批意见</td>
							<td>审批人</td>
							<td>得到任务时间</td>
							<td>审批完结时间</td>
						</tr>
					</thead>
					<tbody>
						<c:if test="${!empty loanexaminelist}">
						<c:forEach items="${loanexaminelist}" var="examine" varStatus="ri">
							<tr>
								<td>${examine[0]}</td>
								<td>${examine[1]}</td>
								<td>${examine[2]}</td>
								<td>${examine[3]}</td>
		                     	<td>${examine[4]}</td>
							</tr>
						</c:forEach>
						</c:if>
					</tbody>
				</table>
		
		<!-- 引用不同贷款方式对应的表单信息 -->
		<jsp:include page="/WEB-INF/views/common/loanTypeInfo.jsp">
			<jsp:param name="loanTypeInputName" value="loanTypeList"/>
			<jsp:param name="nid" value="${nid}"/>
			<jsp:param name="height" value="80"/>
			<jsp:param name="businessTable" value="t_loan_contract"/>
			<jsp:param name="businessId" value="${tLoanContract.id}"/>
			<jsp:param name="oper" value="edit"/>
		</jsp:include>
		<!-- 生成的合同 -->
		<c:if test="${!empty businessContractList}">
			<label class="control-label"><h5>已经生成合同列表：</h5></label><br><br>
			<div class="control-group">
				<%-- <ul class="ul-form">
					<c:forEach items="${businessContractList}" var="businessContractEntity">
						<a class="btn btn-primary" onclick="toEditContract('${businessContractEntity.id}');">${businessContractEntity.contractName}</a>&nbsp;&nbsp;&nbsp;
					</c:forEach>
				</ul> --%>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>合同名称</th>
							<th>更新时间</th>
							<%-- <shiro:hasPermission name="contract:tLoanContract:edit"> --%><th>操作</th><%-- </shiro:hasPermission> --%>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${businessContractList}" var="businessContractEntity">
							<tr>
								<td>
									${businessContractEntity.contractName}
								</td>
								<td>
									<fmt:formatDate value="${businessContractEntity.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
									<a style="cursor:pointer" onclick="toViewContract('${businessContractEntity.id}');">PDF预览</a>&nbsp;
				    				<%-- <a style="cursor:pointer" onclick="toEditContract('${businessContractEntity.id}');">修改</a>&nbsp; --%>
									<c:if test="${!empty ifEdit && ifEdit == 'edit'}">
									<a href="${ctx}/contract/contractAudit/delBusinessContract?tab=files&businessContractId=${businessContractEntity.id}">删除</a>
									</c:if>
									<%-- <c:if test="${!empty ifEdit && ifEdit == 'edit'}">
									</c:if> --%>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<br>
			</div>
			<br>
		</c:if>
		<!-- 附件 -->
		<div id="${nid}filelist" ></div>
		
		<div class="form-actions"  align="center">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</div>
	<div id="cw" class="nav-tab-con" style="display:none;width: 100%;">
		<br>
	    <br>
	     <table cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				<td width="49" valign="top">
					<h3 align="center">还款记录</h3>
					<table width="99%"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr class="tit_center_bg">
								<td width="10%">序号</td>
								<td>日期</td>
								<td>金额</td>
								<!-- <td>操作</td> -->
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${repRecordList}" var="repayRecord"
								varStatus="re">
								<tr>
									<td>${re['index']+1}</td>
									<td><fmt:formatDate value="${repayRecord.repayDate}"
											pattern="yyyy-MM-dd" /></td>
									<td>${repayRecord.money}</td>
									<!-- <td align="center">&nbsp;
									</td> -->
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
				<td width="2">&nbsp;</td>
				
				<td width="49" valign="top">
					<h3 align="center">提前还款记录</h3>
					<table id="tiqianTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<td>序号</td>
								<td>申请日期</td>
								<td>提前还款金额</td>
								<td>违约金比率</td>
								<td>合同状态</td>
							</tr>
						</thead>
						<tbody>
							<c:if test="${!empty advanceList}">
							<c:forEach items="${advanceList}" var="advance" varStatus="al">
								<tr>
									<td>${al['index']+1 }</td>
									<td>
										<c:if test="${!empty advance.applyDate}">
											<fmt:formatDate value="${advance.applyDate}" pattern="yyyy-MM-dd" />
                        				</c:if>
                        			</td>
									<td>${advance.advanceamount }
									<td>${advance.floatrate }
									<td>
									    <c:if test="${advance.status eq '0'}">
											待审批
                        				</c:if>
                        				 <c:if test="${advance.status eq '1'}">
											审批不通过
                        				</c:if>
                        				 <c:if test="${advance.status eq '2'}">
											审批通过
                        				</c:if>
									</td>
								</tr>
							</c:forEach>
							</c:if>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td width="49" valign="top">
					<h3 align="center">违约金、咨询费、罚息实收款记录</h3>
					<table id="weiyueTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<td>序号</td>
								<td>日期</td>
								<td>违约金</td>
								<td>咨询服务费</td>
								<td>罚息</td>
							</tr>
						</thead>
						<tbody>
							<c:if test="${!empty realIncomeList}">
							<c:forEach items="${realIncomeList}" var="realIncome" varStatus="ri">
								<tr>
									<td>${ri['index']+1 }</td>
									<td>
										<c:if test="${!empty realIncome.payRealDate}">
											<fmt:formatDate value="${realIncome.payRealDate}" pattern="yyyy-MM-dd" />
                        				</c:if>
                        			</td>
									<td>${realIncome.punishAmount }
									<td>${realIncome.consultancyAmount }
									<td>${realIncome.interestPenalties }
									</td>
								</tr>
							</c:forEach>
							</c:if>
						</tbody>
					</table>
				</td>
				<td width="2">&nbsp;</td>
				<td width="49" valign="top">
					<h3 align="center">逾期记录</h3>
					<table width="99%"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr class="tit_center_bg">
								<td width="10%">序号</td>
								<td>还款截止日期</td>
								<td>结清日期</td>
								<td>逾期天数</td>
							</tr>
						</thead>
						<tbody>
							<c:if test="${!empty overdueList}">
								<c:forEach items="${overdueList}" var="overRepay" varStatus="ov">
									<tr>
										<td>${ov['index']+1 }</td>
										<td><c:if test="${!empty overRepay.endDate}">
											${overRepay.endDate}
                        				</c:if></td>
										<td><c:if test="${!empty overRepay.overDate}">
											${overRepay.overDate}
                        				</c:if></td>
										<td>${overRepay.yuQi }
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	
	<h3 align="center">放款记录</h3>
	<table id="fangkuanTable" class="table table-striped table-bordered table-condensed">
			<tr>
				<td>序号</td>
				<td>放款日期</td>
				<td>金额</td>
				<td>操作人</td>
			</tr>
		<c:forEach items="${lendRecord}" var="data" varStatus="status">
			<tr>
				<td>${status.count}</td>
				<td>${data[0]}</td>
				<td>${data[1]}</td>
				<td>${data[2]}</td>
			</tr>
		   </c:forEach>
		</table>
	
	<h3 align="center">退费历史记录</h3>
	<table id="tuifeiTable" class="table table-striped table-bordered table-condensed">
			<tr>
				<td>还款月数</td>
				<td>借款人</td>
				<td>业务类型</td>
				<td>申请人</td>
				<td>申请时间</td>
				<td>贷款期限</td>
				<td>超支费用</td>
				<td>收款日期</td>
				<td>退费实际时间</td>
				<td>提醒</td>
				<td>借款金额(元)</td>
				<td>提前还款金额(元)</td>
			</tr>
		<c:forEach items="${reimburseList}" var="reimburse" varStatus="index">
			<tr>
				<td>${reimburse.backMonth}</td>
				<td>${reimburse.backName}</td>
				<td>${fns:getDictLabel(reimburse.businessType, 'ReimbBusinessStatus', '')}</td>
				<td>${reimburse.customerName}</td>
				<td><fmt:formatDate value="${reimburse.insertTime}" pattern="yyyy-MM-dd"/></td>
				<td>${reimburse.loanPeriod}</td>
				<td>${reimburse.outPrice}</td>
				<td><fmt:formatDate value="${reimburse.reimburseyDate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${reimburse.returnTime}" pattern="yyyy-MM-dd"/></td>
				<td>${reimburse.isRead}</td>
				<td>${reimburse.jiePrice}</td>
				<td>${reimburse.tiPrice}</td>
			</tr>
		   </c:forEach>
		</table>
	
	
	
		<div class="form-actions" align="center">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</div>
	<div id="ginfo" class="nav-tab-con" style="display:none;width: 100%;">
		<%-- 担保信息 --%>
		<c:if test="${empty mortgageList && empty pledgeList && empty guaranteeList}">
		<h3 align="center">无相关担保信息！</h3>
		<!-- <table id="meidanbaoTable" class="table table-striped table-bordered table-condensed table-center">
			<thead>
				<tr>
					<th>无相关担保信息！</th>
				</tr>
			</thead>
		</table> -->
		</c:if>
		<!-- 抵押内容 -->
		<c:if test="${not empty mortgageList}">
		<h3 align="center">抵&nbsp;&nbsp;押&nbsp;&nbsp;信&nbsp;&nbsp;息</h3>
		<table id="diyaTable" class="table table-striped table-bordered table-condensed table-center">
			<thead>
				<!-- <tr><th colspan="8" style="font-size:22px;">抵&nbsp;&nbsp;押&nbsp;&nbsp;信&nbsp;&nbsp;息</th></tr> -->
				<tr>
					<th>关联业务编号</th>
					<th>名称</th>
					<th>存放地点</th>				
					<th>数量及单位</th>
					<th>评估价值(元)</th>
					<th>抵押物的状态</th>
					<th>抵押物类型</th>
					<th>抵押性质</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${mortgageList}" var="mortgageContract">
				<tr>
					<td>
						${mortgageContract.contractNumber}
					</td>				
					<td>
						<shiro:hasPermission name="credit:customerCredit:view"><a href="${ctx}/mortgage/mortgageContract/view?id=${mortgageContract.id}">${mortgageContract.name}</a></shiro:hasPermission>
					</td>
					<td>
						${mortgageContract.address}
					</td>
					<td>
						${mortgageContract.unit}
					</td>
					<td>
						${mortgageContract.worth}
					</td>
					<td>
						${fns:getDictLabel(mortgageContract.struts, 'ypjc_struts_status', '')}
					</td>
					<td>
						${fns:getDictLabel(mortgageContract.pledgeType, 'collateral_mortgage', '')}
					</td>
					<td>
						${mortgageContract.number}
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<br/>
		<br/>
		</c:if>
		<!-- 质押内容 -->
		<c:if test="${not empty pledgeList}">
			<h3 align="center">质&nbsp;&nbsp;押&nbsp;&nbsp;信&nbsp;&nbsp;息</h3>
			<table id="zhiyaTable" class="table table-striped table-bordered table-condensed table-center">
				<thead>
					<!-- <tr><th colspan="7" style="font-size:22px;">质&nbsp;&nbsp;押&nbsp;&nbsp;信&nbsp;&nbsp;息</th></tr> -->
					<tr> 
					    <th>关联业务编号</th>
						<th>质押名称</th>
						<th>质押物类型</th>
						<th>数量</th>
						<th>价值</th>
						<th>状态</th>
						<th>更新时间</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${pledgeList}" var="pledgeContract">
					<tr>
						<td>
							${pledgeContract.contractNumber}
						</td>
						<td><a href="${ctx}/pledge/pledgeContract/detail?id=${pledgeContract.id}">
							${pledgeContract.name}
						</a></td>
						<td>
							${fns:getDictLabel(pledgeContract.pledgeType, 'collateral_pledge', '')}
						</td>
						<td>
							${pledgeContract.unit}
						</td>
						<td>
							${pledgeContract.worth}
						</td>
						<td>
							${fns:getDictLabel(pledgeContract.struts, 'ypjc_struts_status', '')}
						</td>
						<td>
							<fmt:formatDate value="${pledgeContract.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<br/>
			<br/>
		</c:if>
		<!-- 保证内容 -->
		<c:if test="${not empty guaranteeList}">
			<h3 align="center">保&nbsp;&nbsp;证&nbsp;&nbsp;信&nbsp;&nbsp;息</h3>
			<table id="baozhenTable" class="table table-striped table-bordered table-condensed table-center">
				<thead>
					<!-- <tr><th colspan="9" style="font-size:22px;">保&nbsp;&nbsp;证&nbsp;&nbsp;内&nbsp;&nbsp;容</th></tr> -->
					<tr>
						<th>关联业务编号</th>
						<th>担保编号</th>
						<th>保证金额</th>
						<th>证件号码</th>
						<th>担保日期</th>
						<th>联系电话</th>
						<th>资产价值</th>
						<th>担保人姓名</th>
						<th>更新时间</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${guaranteeList}" var="tGuaranteeContract">
					<tr>
						<td><a href="${ctx}/guarantee/tGuaranteeContract/detail?id=${tGuaranteeContract.id}">
						     ${tGuaranteeContract.contractNumber}
						</a></td>
						<td>
							${tGuaranteeContract.guaranteeNumber}
						</td>
						<td>
							${tGuaranteeContract.amount}
						</td>
						<td>
							${tGuaranteeContract.cardNum}
						</td>
						<td>
							<fmt:formatDate value="${tGuaranteeContract.contractDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${tGuaranteeContract.telephone}
						</td>
						<td>
							${tGuaranteeContract.assetsWorth}
						</td>
						<td>
							${tGuaranteeContract.guarantorName}
						</td>
						<td>
							<fmt:formatDate value="${tGuaranteeContract.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:if>
		
		<div class="form-actions" align="center">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</div>
</body>
</html>