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
				<a href="#">我的桌面&gt;</a>
				<a href="#">统计分析&gt;</a>
				<a href="#">产品统计分析</a>
			</div>
		</div>
	</div>
	<div class="box-down" style="margin-top: 20px;">
		<div class="box"><sys:message content="${message}"/></div>
		<!-- 图表  -->
		<div class="box"><div id="main" class="main" style='height:230px;margin-bottom:1px;padding-bottom:0;border-bottom-width:0'></div></div>
		<div class="box"><div id="main2" class="main" style='height:145px;margin-bottom:1px;padding:1px 10px;border-width:0 1px;'></div></div>
		<div class="box"><div id="main3" class="main" style='height:105px;padding-top:1px;border-top-width:0'></div></div>
		<!-- 表格  -->
		<div class="box">
			<form:form id="searchForm" modelAttribute="tProduct" action="${ctx}/product/tProduct/list" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			</form:form>
		    <table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
				<thead>
					<tr>
						<th rowspan="2">产品名称</th>
						<th rowspan="2">累计发生金额</th>
						<th rowspan="2">已结清金额</th>
						<th rowspan="2">未结清金额</th>
						<th rowspan="2">逾期金额</th>
						<th rowspan="2">坏账金额</th>
						<th colspan="6">单笔业务</th>
					</tr>
					<tr>
						<th>最高金额</th>
						<th>最低金额</th>
						<th>平均金额</th>
						<th>最高利息</th>
						<th>最低利息</th>
						<th>平均利息</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="sa" varStatus="idx">
					<tr>
						<td>
							${sa.name}
						</td>
						<td>
							<fmt:formatNumber value="${sa.sumAmount}" pattern="#,#00.00#"/>
						</td>
						<td>
							<fmt:formatNumber value="${sa.clearedAmount}" pattern="#,#00.00#"/>
						</td>
						<td>
							<fmt:formatNumber value="${sa.unclearedAmount}" pattern="#,#00.00#"/>
						</td>
						<td>
							<fmt:formatNumber value="${sa.overdueAmount}" pattern="#,#00.00#"/>
						</td>
						<td>
							<fmt:formatNumber value="${sa.badAmount}" pattern="#,#00.00#"/>
						</td>
						<td>
							<fmt:formatNumber value="${sa.maxAmount}" pattern="#,#00.00#"/>
						</td>
						<td>
							<fmt:formatNumber value="${sa.minAmount}" pattern="#,#00.00#"/>
						</td>
						<td>
							<fmt:formatNumber value="${sa.avgAmount}" pattern="#,#00.00#"/>
						</td>
						<td>
							<fmt:formatNumber value="${sa.maxInterest}" pattern="#,#00.00#"/>
						</td>
						<td>
							<fmt:formatNumber value="${sa.minInterest}" pattern="#,#00.00#"/>
						</td>
						<td>
							<fmt:formatNumber value="${sa.avgInterest}" pattern="#,#00.00#"/>
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
        	var clearedAmounts = [];
        	var unclearedAmounts = [];
        	var overdueAmounts = [];
        	var badAmounts = [];
        	var counts = [];
        	
        	var maxAmounts = [];
        	var minAmounts = [];
        	var avgAmounts = [];
        	var maxInterests = [];
        	var minInterests = [];
        	var avgInterests = [];
        	var blanks = [];
        	for(var i=0;i<list.length;i++){
        		axisData.push(list[i].name);
        		sumAmounts.push(list[i].sumAmount);
        		clearedAmounts.push(list[i].clearedAmount);
        		unclearedAmounts.push(list[i].unclearedAmount);
        		overdueAmounts.push(list[i].overdueAmount);
        		badAmounts.push(list[i].badAmount);
        		counts.push(list[i].count);
        		
        		maxAmounts.push(list[i].maxAmount);
        		minAmounts.push(list[i].minAmount);
        		avgAmounts.push(list[i].avgAmount);
        		maxInterests.push(list[i].maxInterest);
        		minInterests.push(list[i].minInterest);
        		avgInterests.push(list[i].avgInterest);
        		blanks.push(0);
        	}
        	/* 
        	for(var i=0;i<10;i++){
        		axisData.push(list[0].name+i);
        		sumAmounts.push(list[0].sumAmount + list[0].sumAmount*Math.pow(-1,i)*i/10);
        		clearedAmounts.push(list[0].clearedAmount + list[0].clearedAmount*Math.pow(-1,i)*i/10);
        		unclearedAmounts.push(list[0].unclearedAmount + list[0].unclearedAmount*Math.pow(-1,i)*i/10);
        		overdueAmounts.push(list[0].overdueAmount + list[0].overdueAmount*Math.pow(-1,i)*i/10);
        		badAmounts.push(1000 + 1000*Math.pow(-1,i)*i/10);
        		counts.push(list[0].count*2 + Math.pow(-1,i)*i);
        		
        		maxAmounts.push(list[0].maxAmount + list[0].maxAmount*Math.pow(-1,i)*i/10);
        		minAmounts.push(10000+10000*Math.pow(-1,i)*i/10);
        		avgAmounts.push(list[0].avgAmount + list[0].avgAmount*Math.pow(-1,i)*i/10);
        		maxInterests.push(list[0].maxInterest + list[0].maxInterest*Math.pow(-1,i)*i/10);
        		minInterests.push(list[0].minInterest + list[0].minInterest*Math.pow(-1,i)*i/10);
        		avgInterests.push(list[0].avgInterest + list[0].avgInterest*Math.pow(-1,i)*i/10);
        		blanks.push(0);
        	} */
        	
        	var series1 = [];
       		series1.push({
	            name:'累计发生金额',
	            type:'bar',
	            data: sumAmounts
	        });
       		series1.push({
	            name:'已结清金额',
	            type:'bar',
	            stack: 'detail',
	            data: clearedAmounts
	        });
       		series1.push({
	            name:'未结清金额',
	            type:'bar',
	            stack: 'detail',
	            data: unclearedAmounts
	        });
       		series1.push({
	            name:'坏账金额',
	            type:'bar',
	            stack: 'detail',
	            data: badAmounts
	        });
       		series1.push({
	            name:'逾期金额',
	            type:'bar',
	            data: overdueAmounts
	        });
       		series1.push({
	            name:'发生业务笔数',
	            type:'line',
	            yAxisIndex: 1,
	            data: counts
	        });
        	
       		var legendData = ['累计发生金额','已结清金额','未结清金额','坏账金额','逾期金额','发生业务笔数'];
        	
    	                option = {
    	                    title : {
    	                        text: '${fns:getUser().company.name} - 产品统计分析'
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
    	                            /* axisTick: {onGap:false},
    	                            splitLine: {show:false}, */
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
	    	                                    return Math.round(v/10000000)/10 + ' 亿'
    	                                	}else if(v>=10000){
	    	                                    return Math.round(v/1000)/10 + ' 万'
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

    	                var series2 = [];
    	           		series2.push({
    	    	            name:'平均单笔金额',
    	    	            type:'bar',
    	    	            stack: '1',
    	    	            barWidth : 10,
    	    	            data: avgAmounts
    	    	        });
    	           		series2.push({
    	    	            name:'单笔最高金额',
    	    	            type:'line',
    	    	            data: maxAmounts
    	    	        });
    	           		series2.push({
    	    	            name:'单笔最低金额',
    	    	            type:'line',
    	    	            data: minAmounts
    	    	        });
    	           		series2.push({
    	    	            name:'平均利息',
    	    	            type:'bar',
    	    	            stack: '1',
    	    	            barWidth : 0,
    	    	            data: blanks
    	    	        });
    	           		series2.push({
    	    	            name:'最高利息',
    	    	            type:'line',
    	    	            data: blanks
    	    	        });
    	           		series2.push({
    	    	            name:'最低利息',
    	    	            type:'line',
    	    	            data: blanks
    	    	        });
    	           		
    	                option2 = {
    	                    tooltip : {
    	                        trigger: 'axis',
    	                        showDelay: 0,             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
    	                        formatter: function (params) {
    	                            var res = '单笔业务贷款';
    	                            res += '<br/>' + params[2].seriesName + " : " +params[2].value;
    	                            res += '<br/>' + params[3].seriesName + " : " +params[3].value;
    	                            res += '<br/>' + params[1].seriesName + " : " +params[1].value;
    	                            return res;
    	                        }
    	                    },
    	                    legend: {
    	                    	y : 'bottom',
    	                        data: ['平均单笔金额','单笔最高金额','单笔最低金额','平均利息','最高利息','最低利息']
    	                    },
    	                    toolbox: {
    	                        y : -30,
    	                        show : true,
    	                        feature : {
    	                            mark : {show: true},
    	                            dataZoom : {show: true},
    	                            dataView : {show: true, readOnly: false},
    	                            magicType : {show: true, type: ['line', 'bar']},
    	                            restore : {show: true},
    	                            saveAsImage : {show: true}
    	                        }
    	                    },
    	                    grid: {
    	                        x: 80,
    	                        y: 5,
    	                        x2:25,
    	                        y2:40
    	                    },
    	                    xAxis : [
    	                        {
    	                            type : 'category',
    	                            position:'top',
    	                            boundaryGap : true,
    	                            axisLabel:{show:false},
    	                            axisTick: {onGap:false},
    	                            splitLine: {show:false},
    	                            data : axisData
    	                        }
    	                    ],
    	                    yAxis : [
    	                        {
    	                            type : 'value',
    	                            scale:true,
    	                            splitNumber:3,
    	                            boundaryGap: [0.05, 0.05],
    	                            min : 0,
    	                            axisLabel: {
    	                                formatter: function (v) {
    	                                	if(v>=100000000){
	    	                                    return Math.round(v/10000000)/10 + ' 亿'
    	                                	}else if(v>=10000){
	    	                                    return Math.round(v/1000)/10 + ' 万'
    	                                	}else{
    	                                		return v
    	                                	}
    	                                }
    	                            },
    	                            splitArea : {show : true}
    	                        }
    	                    ],
    	                    series : series2
    	                };
    	                myChart2 = ec.init(document.getElementById('main2'));
    	                myChart2.setOption(option2);

    	                var series3 = [];
    	                series3.push({
    	    	            name:'平均利息',
    	    	            type:'bar',
    	    	            stack: '1',
    	    	            barWidth : 10,
    	    	            data: avgInterests
    	    	        });
    	           		series3.push({
    	    	            name:'最高利息',
    	    	            type:'line',
    	    	            data: maxInterests
    	    	        });
    	           		series3.push({
    	    	            name:'最低利息',
    	    	            type:'line',
    	    	            data: minInterests
    	    	        });
    	           		series3.push({
    	    	            name:'平均单笔金额',
    	    	            type:'bar',
    	    	            stack: '1',
    	    	            barWidth : 0,
    	    	            data: blanks
    	    	        });
    	           		series3.push({
    	    	            name:'单笔最高金额',
    	    	            type:'line',
    	    	            data: blanks
    	    	        });
    	           		series3.push({
    	    	            name:'单笔最低金额',
    	    	            type:'line',
    	    	            data: blanks
    	    	        });
    	           		
    	                option3 = {
    	                	    tooltip : {
    	                	        trigger: 'axis',
    	                	        showDelay: 0,             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
    	                	        formatter: function (params) {
        	                            var res = '单笔业务利息';
        	                            res += '<br/>' + params[2].seriesName + " : " +params[2].value;
        	                            res += '<br/>' + params[3].seriesName + " : " +params[3].value;
        	                            res += '<br/>' + params[1].seriesName + " : " +params[1].value;
        	                            return res;
        	                        }
    	                	    },
    	                	    legend: {
    	                	        y : -30,
    	                	        data:['平均单笔金额','单笔最高金额','单笔最低金额','平均利息','最高利息','最低利息']
    	                	    },
    	                	    toolbox: {
    	                	        y : -30,
    	                	        show : true,
    	                	        feature : {
    	                	            mark : {show: true},
    	                	            dataZoom : {show: true},
    	                	            dataView : {show: true, readOnly: false},
    	                	            magicType : {show: true, type: ['line', 'bar']},
    	                	            restore : {show: true},
    	                	            saveAsImage : {show: true}
    	                	        }
    	                	    },
    	                	    
    	                	    grid: {
    	                	        x: 80,
    	                	        y:5,
    	                	        x2:25,
    	                	        y2:30
    	                	    },
    	                	    xAxis : [
    	                	        {
    	                	            type : 'category',
    	                	            position:'bottom',
    	                	            boundaryGap : true,
    	                	            axisTick: {onGap:false},
    	                	            splitLine: {show:false},
    	                	            data : axisData
    	                	        }
    	                	    ],
    	                	    yAxis : [
    	                	        {
    	                	            type : 'value',
    	                	            scale:true,
    	                	            splitNumber:3,
    	                	            boundaryGap: [0.05, 0.05],
    	                	            min : 0,
    	                	            axisLabel: {
    	                	                formatter: function (v) {
    	                	                    return Math.round(v/1000)/10 + ' 万'
    	                	                }
    	                	            },
    	                	            splitArea : {show : true}
    	                	        }
    	                	    ],
    	                	    series : series3
    	                	};
    	                	myChart3 = ec.init(document.getElementById('main3'));
    	                	myChart3.setOption(option3);
    	                
    	                myChart.connect([myChart2,myChart3]);
    	                myChart2.connect([myChart,myChart3]); 
    	                myChart3.connect([myChart,myChart2]); 

    	                setTimeout(function (){
    	                    window.onresize = function () {
    	                        myChart.resize();
    	                        myChart2.resize();
    	                        myChart3.resize();
    	                    }
    	                },200);
        }
	);
    </script>
	
	
	
</body>
</html>