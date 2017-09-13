<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>函件模板管理</title>
	<meta name="decorator" content="default"/>
  	<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
  	<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/highcharts-more.js"></script>

 	<style type="text/css">
 	.tab_bor_bottom{ height:36px; line-height:36px;}
	.tab_bor_bottom tr td{ border-bottom:1px #e6e6e6 solid;text-align: center;}
 	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			var chartObj =  new Highcharts.Chart({ //$('#container').highcharts({
					
				    chart: {
				    	renderTo : 'container',
				        type: 'gauge',
				        plotBackgroundColor: null,
				        plotBackgroundImage: null,
				        plotBorderWidth: 0,
				        plotShadow: false
				    },
				    
				    title: {
				        text: ''
				    },
				    
				    pane: {
				        startAngle: -150,
				        endAngle: 150,
				        background: [{
				            backgroundColor: {
				                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
				                stops: [
				                    [0, '#FFF'],
				                    [1, '#333']
				                ]
				            },
				            borderWidth: 0,
				            outerRadius: '109%'
				        }, {
				            backgroundColor: {
				                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
				                stops: [
				                    [0, '#333'],
				                    [1, '#FFF']
				                ]
				            },
				            borderWidth: 1,
				            outerRadius: '107%'
				        }, {
				            // default background
				        }, {
				            backgroundColor: '#DDD',
				            borderWidth: 0,
				            outerRadius: '105%',
				            innerRadius: '103%'
				        }]
				    },
				       
				    // the value axis
				    yAxis: {
				        min: 0,
				        max: 1000,
				        
				        minorTickInterval: 'auto',
				        minorTickWidth: 1,
				        minorTickLength: 10,
				        minorTickPosition: 'inside',
				        minorTickColor: '#fff',
				
				        tickPixelInterval: 30,
				        tickWidth: 2,
				        tickPosition: 'inside',
				        tickLength: 10,
				        tickColor: '#fff',
				        labels: {
				            step: 2,
				            rotation: 'auto'
				        },
				        title: {
				            text: 'BETA'
				        },
				        plotBands: [{
				            from: 0,
				            to: 200,
				            color: '#FF4E32' // green
				        }, {
				            from: 200,
				            to: 400,
				            color: '#FC850D' // yellow
				        }, {
				            from: 400,
				            to: 600,
				            color: '#BEDB11' // red
				        }, {
				            from: 600,
				            to: 800,
				            color: '#50DF4F' // red
				        }, {
				            from: 800,
				            to: 1000,
				            color: '#03DB88' // red
				        }]        
				    },
				
				    series: [{
				        name: '信用度',
				        data: [500],
				        tooltip: {
				            valueSuffix: ''
				        }
				    }]
				
				}, 
				// Add some life
				function (chart) {
					/* if (!chart.renderer.forExport) {
					    setInterval(function () {
					        var point = chart.series[0].points[0],
					            newVal,
					            inc = Math.round((Math.random() - 0.5) * 20);
					        
					        newVal = point.y + inc;
					        if (newVal < 0 || newVal > 200) {
					            newVal = point.y - inc;
					        }
					        
					        point.update(newVal);
					        
					    }, 2000);
					}  */
				});
			$("#btnSubmit").click(function(){
				$.post("${ctx}/contract/tLoanContract/getRiskScore",
						{loanContractId:"${riskScore.loanContractId}",category:$("#category").val()},
						function(data){
							var point = chartObj.series[0].points[0];
					        var newVal = data.grade + Number($('#category').val());
					        point.update(newVal);
					        $("#score").text(parseInt(newVal));
					        $("#rpn").text(data.rpn);
				}); 
			});
			
			$("#btnSubmit").click();
		});
	
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/contract/tLoanContract/">业务办理列表</a></li>
		<li class="active"><a href="${ctx}/contract/tLoanContract/toRiskControl?loanContractId=${riskScore.loanContractId}">风险控制</a></li>
	</ul>
	<br/>
	<table class="table table-bordered">
			<tr><td>
			    <!-- <a class="btn btn-primary" >业务定性评分</a>&nbsp;&nbsp;&nbsp; -->
				<a class="btn btn-primary" style="background-color: #1b6aaa !important;border-color: #428bca;">业务定量评估</a>&nbsp;&nbsp;&nbsp;
			  	<!-- <a class="btn btn-primary" >业务综合评级</a>&nbsp;&nbsp;&nbsp; -->
	  </table>
	<sys:message content="${message}"/>
	<form:form id="searchForm" modelAttribute="riskScore" action="${ctx}/contract/tLoanContract/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li>请选择查看的征信报告：
				<form:select path="category"  class="input-xlarge " onchange="$('#btnSubmit').click();">
					<form:option value="10" label="央行征信数据库1"/>
					<form:option value="50" label="央行征信数据库2"/>
					<form:option value="80" label="央行征信数据库3"/>
					<form:option value="120" label="央行征信数据库4"/>
				</form:select>
			</li>
			<li class="btns" style="display: none;"><input id="btnSubmit" class="btn btn-primary" type="button" value="开始"/></li>
			<li class="clearfix"></li>
		</ul>
		<hr>
		<div id="container" style="min-width:700px;height:400px"></div>
		
		<table class="tab_bor_bottom" width="70%" style="margin:auto;">
			<tr><td class="pl20">项目得分：</td><td id="score">789</td></tr>
			<tr><td class="pl20">风险程度：</td><td id="rpn">A</td></tr>
			<tr><td class="pl20">总体评价：</td><td>项目较安全，建议通过</td></tr>
	  	</table>
		<div class=" w90"  style=" margin-top:20px; line-height:30px;"><h4>业务定量评估：</h4><span>该业务定性评分为<span class="red">xxx</span>,
风险系数为<span class="red">xxx</span>定量评估是指依据统计数据，建立数学模型，并用数学模型计算出分析对象的各项指标及其数值来评估分析的一种方法。与定性分析评估相对应的（主要凭分析者的直觉、经验，凭分析）。
定量评估是一种数据分析模型，所以平时多被称呼定量分析，定量评估分析一般需要较高深的数学知识，对社会现象的数量特征、数量关系与数量变化的分析。其功能在于揭示和描述社会现象的相互作用和发展趋势。
定性分析评估分析方法：概率评价法、主成分分析法、赋权法 。</span></div>
	</form:form>

</body>
</html>