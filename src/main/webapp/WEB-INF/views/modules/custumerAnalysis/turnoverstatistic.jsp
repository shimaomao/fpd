<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>企业营业额统计</title>
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
				<a href="#">企业营业额统计</a>
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
                		    title : {
                		        text: '企业客户月营业额分布',
                		        subtext: '',
                		        x:'center'
                		    },
                		    tooltip : {
                		        trigger: 'item',
                		        formatter: "{a} <br/>{b} : {c} ({d}%)"
                		    },
                		    legend: {
                		        orient : 'vertical',
                		        x : 'left',
                		        data:['10万以下','10-50万','50-100万','100-500万','500-1000万','1000万以上']
                		    },
                		    toolbox: {
                		        show : true,
                		        feature : {
                		            mark : {show: true},
                		            dataView : {show: true, readOnly: false},
                		            magicType : {
                		                show: true, 
                		                type: ['pie', 'funnel'],
                		                option: {
                		                    funnel: {
                		                        x: '25%',
                		                        width: '50%',
                		                        funnelAlign: 'left',
                		                        max: 1548
                		                    }
                		                }
                		            },
                		            restore : {show: true},
                		            saveAsImage : {show: true}
                		        }
                		    },
                		    calculable : true,
                		    series : [
                		        {
                		            name:'营业额阶段：该阶段总额（占比）',
                		            type:'pie',
                		            radius : '55%',
                		            center: ['50%', '60%'],
                		            data:[
                		                {value:'${oneoption}', name:'10万以下'},
                		                {value:'${twooption}', name:'10-50万'},
                		                {value:'${threeoption}', name:'50-100万'},
                		                {value:'${fouroption}', name:'100-500万'},
                		                {value:'${fiveoption}', name:'500-1000万'},
                		                {value:'${sixoption}', name:'1000万以上'},
                		            ]
                		        }
                		    ]
                		};
                	
                	
                	option1 = {
                		    title : {
                		        text: '个人客户月收入分布',
                		        subtext: '',
                		        x:'center'
                		    },
                		    tooltip : {
                		        trigger: 'item',
                		        formatter: "{a} <br/>{b} : {c} ({d}%)"
                		    },
                		    legend: {
                		        orient : 'vertical',
                		        x : 'left',
                		        data:['1万以下','1-2万','2-5万','5-10万','10-50万','50万以上']
                		    },
                		    toolbox: {
                		        show : true,
                		        feature : {
                		            mark : {show: true},
                		            dataView : {show: true, readOnly: false},
                		            magicType : {
                		                show: true, 
                		                type: ['pie', 'funnel'],
                		                option: {
                		                    funnel: {
                		                        x: '25%',
                		                        width: '50%',
                		                        funnelAlign: 'left',
                		                        max: 1548
                		                    }
                		                }
                		            },
                		            restore : {show: true},
                		            saveAsImage : {show: true}
                		        }
                		    },
                		    calculable : true,
                		    series : [
                		        {
                		            name:'收入额阶段：该阶段总额（占比）',
                		            type:'pie',
                		            radius : '55%',
                		            center: ['50%', '60%'],
                		            data:[
                		                {value:'${eoneoption}', name:'1万以下'},
                		                {value:'${etwooption}', name:'1-2万'},
                		                {value:'${ethreeoption}', name:'2-5万'},
                		                {value:'${efouroption}', name:'5-10万'},
                		                {value:'${efiveoption}', name:'10-50万'},
                		                {value:'${esixoption}', name:'50万以上'},
                		            ]
                		        }
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
