<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/productConfig/css/common.css"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="ptag">
				<a href="#">业务中心&gt;</a>
				<a href="#">统计分析&gt;</a>
				<a href="#">业务统计分析-${fns:getUser().company.name}</a>
			</div>
		</div>
	</div>
	<div class="box-down" style="margin-top: 20px;">
		<div class="box"><sys:message content="${message}"/></div>
		<div class="box">
			<form:form id="searchForm" modelAttribute="tLoanContract" action="${ctx}/dataStatistics/loanBalanceStatistics/statisticAnalysis" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<ul class="ul-form">
					<li><label>贷款金额：</label>
						<form:input path="loanAmount" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>贷款期限：</label>
						<form:input path="loanPeriod" htmlEscape="false" maxlength="11" class="input-medium"/>
						<form:radiobuttons path="periodType" items="${fns:getDictList('period_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
					</li>
					<li><label>利率(年)：</label>
						<form:input path="loanRate" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>业务状态：</label>
		                 <form:select path="status" class="input-xlarge " style="width:188px;">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('loan_contract_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>			
					</li>
					<br><br>
			     	<li><label>阶段搜索：</label>
						<input name="starttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							value="<fmt:formatDate value="${tLoanContract.starttime}" pattern="yyyy-MM"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
							-
						 <input name="endtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							value="<fmt:formatDate value="${tLoanContract.endtime}" pattern="yyyy-MM"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>（以放款日期为准）
					</li>
			
			<li class="btns">&nbsp;&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
			</form:form>
		<!-- 图表  -->
		<div class="box"><div id="main" class="main" style='height:420px;margin-bottom:1px;padding-bottom:0;border-bottom-width:0'></div></div>
		<!-- 表格  -->
		<br><br>
		<font size="4px;">${now_time}详细业务信息(无时间搜索时默认当前月)</font>
		
		    <table id="contentTable" class="table table-center table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>合同编号</th>
				<th>客户姓名</th>
				<th>产品名称</th>
				<th>贷款金额（元）</th>
				<th>贷款期限</th>
				<th>贷款利率(%)</th>
				<th>申请日期</th>
				<th>放款日期</th>
				<th>贷款方式</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tLoanContract">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>
					${tLoanContract.contractNumber}
				</td>
				<td>
					${tLoanContract.customerName}
				</td>
				<td>
					${tLoanContract.productname}
				</td>
				<td>
					<fmt:formatNumber value="${tLoanContract.loanAmount}" pattern="#,#00.00#" />
				</td>
				<td>
					${tLoanContract.loanPeriod}
					（${fns:getDictLabels(tLoanContract.periodType, 'period_type', '')}）
				</td>
				<td>
					${tLoanContract.loanRate}(${tLoanContract.loanRateType})
<%-- 					（${fns:getDictLabels(tLoanContract.loanRateType, 'period_type', '')}） --%>
				</td>
				<td>
					<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd"/>
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
		</div>
	</div>
	<script src="${ctxStatic}/echarts/2.2.7/build/dist/echarts.js"></script>
    <script type="text/javascript">
 // 路径配置
    require.config({
        paths: {
            echarts: '${ctxStatic}/echarts/2.2.7/build/dist'
        }
    });
    // 使用
    require(
        [
            'echarts',
            'echarts/chart/line',
            'echarts/chart/bar',
            'echarts/chart/scatter',
            'echarts/chart/k'
        ],
        
        function (ec) {
        	var list = ${listJson};
        	
        	var axisData = [];
        	var sumAmounts = [];
        	var counts = [];
        	var maxmonths = [];
        	var minmonths = [];
        	
        	for(var i=0;i<list.length;i++){
        		axisData.push("${year}年"+(i+1)+"月");
        		sumAmounts.push(list[i].sumAmount);
        		maxmonths.push(list[i].maxAmount);
        		minmonths.push(list[i].minAmount);
        		counts.push(list[i].count);
        	}
        	
        	var series1 = [];
       		series1.push({
	            name:'当月累计发生金额',
	            type:'bar',
	            data: sumAmounts
	        });
       		
       		series1.push({
	            name:'当月业务金额最高',
	            type:'bar',
	            data: maxmonths
	        });
       		series1.push({
	            name:'当月业务金额最低',
	            type:'bar',
	            data: minmonths
	        });
       		series1.push({
	            name:'当月发生业务笔数',
	            type:'line',
	            yAxisIndex: 1,
	            data: counts
	        });
        	
       		var legendData = ['当月累计发生金额','当月发生业务笔数','当月业务金额最高','当月业务金额最低'];
        	
    	                option = {
    	                    title : {
    	                        text: ''
    	                    },
    	                    tooltip : {
    	                        trigger: 'axis',
    	                        showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
    	                    },
    	                    legend: {
    	                        data: legendData
    	                    },
    	                    toolbox: {
    	                        show : true,
    	                        feature : {
    	                            mark : {show: true},
    	                            dataZoom : {show: true},
    	                            magicType : {show: true, type: ['line', 'bar']},
    	                            restore : {show: true},
    	                            saveAsImage : {show: true}
    	                        }
    	                    },
    	                    grid: {
    	                        x: 80,
    	                        y: 40,
    	                        x2:25,
    	                        y2:25
    	                    },
    	                    xAxis : [
    	                        {
    	                            type : 'category',
    	                            boundaryGap : true,
    	                            data : axisData
    	                        }
    	                    ],
    	                    yAxis : [
    	                        {
    	                            type : 'value',
    	                            name : '金额（元）',
    	                            scale:true,
    	                            boundaryGap: [0.05, 0.05],
    	                            min : 0,
    	                            axisLabel: {
    	                                formatter: function (v) {
    	                                	if(v>=100000000){
	    	                                    return Math.round(v/100000000) + ' 亿'
    	                                	}else if(v>=10000){
	    	                                    return Math.round(v/10000) + ' 万'
    	                                	}else{
    	                                		return v
    	                                	}
    	                                }
    	                            },
    	                            splitArea : {show : true}
    	                        },{
    	                        	type : 'value',
    	                            name : '数量'
    	                        }
    	                    ],
    	                    series : series1
    	                };
    	                myChart = ec.init(document.getElementById('main'));
    	                myChart.setOption(option);

    	                setTimeout(function (){
    	                    window.onresize = function () {
    	                        myChart.resize();
    	                    }
    	                },200);
        }
	);
    </script>
	
	
	
</body>
</html>