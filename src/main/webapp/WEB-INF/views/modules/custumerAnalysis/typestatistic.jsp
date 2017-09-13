<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>类别统计</title>
    <link href="${ctxStatic}/echarts/2.2.7/doc/asset/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/productConfig/css/common.css"/>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="ptag">
				<a href="#">业务中心&gt;</a>
				<a href="#">统计分析&gt;</a>
				<a href="#">客户统计&gt;</a>
				<a href="#">类别统计</a>
			</div>
		</div>
	</div>
	<div class="box-down" style="margin-top: 20px;">
	    <font size=4px;>${now_year}年数据分析</font>
		<div class="box">
			<div id="main" class="col-md-12 col-sm-12" style="height:88%"></div>
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
	            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
	        ],
	        
	        function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main')); 
                
                var ecConfig = require('echarts/config');
                var zrEvent = require('zrender/tool/event');
			
                option = {
                	    tooltip : {
                	        trigger: 'axis',
                	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                	        }
                	    },
                	    legend: {
                	        data:['个人客户','企业客户']
                	    },
                	    toolbox: {
                	        show : true,
                	        orient: 'vertical',
                	        x: 'right',
                	        y: 'center',
                	        feature : {
                	            mark : {show: true},
                	            dataView : {show: true, readOnly: false},
                	            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
                	    xAxis : [
                	        {
                	            type : 'category',
                	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value'
                	        }
                	    ],
                	    series : [
                	        {
                	            name:'个人客户',
                	            type:'bar',
                	            data:['${eJanuary}', '${eFebruary}', '${eMarch}', '${eApril}', '${eMay}', '${eJune}',
                	                  '${eJuly}', '${eAugust}', '${eSeptember}', '${eOctober}', '${eNovember}', '${eDecember}']
                	        },
                	        {
                	            name:'企业客户',
                	            type:'bar',
                	            stack: '广告',
                	            data:['${cJanuary}', '${cFebruary}', '${cMarch}', '${cApril}', '${cMay}', '${cJune}',
                	                  '${cJuly}', '${cAugust}', '${cSeptember}', '${cOctober}', '${cNovember}', '${cDecember}']
                	        },
                	    ]
                	};
						
                // 为echarts对象加载数据 
                 myChart.setOption(option); 
            }
		 );
	    </script>
		
</body>
</html>
