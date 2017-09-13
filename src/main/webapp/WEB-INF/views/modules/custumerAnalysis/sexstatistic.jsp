<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>性别统计</title>
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
				<a href="#">性别统计</a>
			</div>
		</div>
	</div>
	<div class="box-down" style="margin-top: 20px;">
		<div class="box">
			<div id="main" class="col-md-6 col-sm-12" style="height:90%;"></div>
			<div id="main1" class="col-md-6 col-sm-12" style="height:90%;"></div>
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
	            'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
	        ],
	        
	        function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main')); 
                var myChart1 = ec.init(document.getElementById('main1')); 

                
                var ecConfig = require('echarts/config');
                var zrEvent = require('zrender/tool/event');
			
                var dataStyle = {
                	    normal: {
                	        label: {show:false},
                	        labelLine: {show:false}
                	    }
                	};
                	var placeHolderStyle = {
                	    normal : {
                	        color: 'rgba(0,0,0,0)',
                	        label: {show:false},
                	        labelLine: {show:false}
                	    },
                	    emphasis : {
                	        color: 'rgba(0,0,0,0)'
                	    }
                	};
                	option = {
                	    title: {
                	        text: '个人客户',
                	        subtext: '',
                	        sublink: '',
                	        x: 'center',
                	        y: 'center',
                	        itemGap: 20,
                	        textStyle : {
                	            color : 'rgba(30,144,255,0.8)',
                	            fontFamily : '微软雅黑',
                	            fontSize : 35,
                	            fontWeight : 'bolder'
                	        }
                	    },
                	    tooltip : {
                	        show: true,
                	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                	    },
                	    legend: {
                	        orient : 'vertical',
                	        x : 150,
                	        y : 55,
                	        itemGap:12,
                	        data:['男','女']
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            mark : {show: true},
                	            dataView : {show: true, readOnly: false},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    series : [
                	        {
                	            name:'',
                	            type:'pie',
                	            clockWise:false,
                	            radius : [125, 150],
                	            itemStyle : dataStyle,
                	            data:[
                	                {
                	                    value:'${EManAmount}',
                	                    name:'男'
                	                },
                	                {
                	                    value:Number('${EManAmount}')+Number('${EWomanAmount}') -'${EManAmount}',
                	                    name:'invisible',
                	                    itemStyle : placeHolderStyle
                	                }
                	            ]
                	        },
                	        {
                	            name:'',
                	            type:'pie',
                	            clockWise:false,
                	            radius : [100, 125],
                	            itemStyle : dataStyle,
                	            data:[
                	                {
                	                    value:'${EWomanAmount}', 
                	                    name:'女'
                	                },
                	                {
                	                    value:Number('${EManAmount}')+Number('${EWomanAmount}') -'${EWomanAmount}',
                	                    name:'invisible',
                	                    itemStyle : placeHolderStyle
                	                }
                	            ]
                	        },
                	    ]
                	};
                	
                	
                	option1 = {
                    	    title: {
                    	        text: '企业客户',
                    	        subtext: '',
                    	        sublink: '',
                    	        x: 'center',
                    	        y: 'center',
                    	        itemGap: 20,
                    	        textStyle : {
                    	            color : 'rgba(30,144,255,0.8)',
                    	            fontFamily : '微软雅黑',
                    	            fontSize : 35,
                    	            fontWeight : 'bolder'
                    	        }
                    	    },
                    	    tooltip : {
                    	        show: true,
                    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    	    },
                    	    legend: {
                    	        orient : 'vertical',
                    	        x : 150,
                    	        y : 55,
                    	        itemGap:12,
                    	        data:['男','女']
                    	    },
                    	    toolbox: {
                    	        show : true,
                    	        feature : {
                    	            mark : {show: true},
                    	            dataView : {show: true, readOnly: false},
                    	            restore : {show: true},
                    	            saveAsImage : {show: true}
                    	        }
                    	    },
                    	    series : [
                    	        {
                    	            name:'',
                    	            type:'pie',
                    	            clockWise:false,
                    	            radius : [125, 150],
                    	            itemStyle : dataStyle,
                    	            data:[
                    	                {
                    	                    value:'${CManAmount}',
                    	                    name:'男'
                    	                },
                    	                {
                    	                    value:Number('${CManAmount}') + Number('${CWomanAmount}') -'${CManAmount}',
                    	                    name:'invisible',
                    	                    itemStyle : placeHolderStyle
                    	                }
                    	            ]
                    	        },
                    	        {
                    	            name:'',
                    	            type:'pie',
                    	            clockWise:false,
                    	            radius : [100, 125],
                    	            itemStyle : dataStyle,
                    	            data:[
                    	                {
                    	                    value:'${CWomanAmount}', 
                    	                    name:'女'
                    	                },
                    	                {
                    	                    value:Number('${CManAmount}') + Number('${CWomanAmount}') -'${CWomanAmount}',
                    	                    name:'invisible',
                    	                    itemStyle : placeHolderStyle
                    	                }
                    	            ]
                    	        },
                    	    ]
                    	};
                
                // 为echarts对象加载数据 
                 myChart.setOption(option); 
                 myChart1.setOption(option1); 
            }
		 );
	    </script>
		
</body>
</html>
