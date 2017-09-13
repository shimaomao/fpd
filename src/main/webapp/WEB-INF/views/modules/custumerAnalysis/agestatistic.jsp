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
				<a href="#">年龄分布统计</a>
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
                		        text: '个人客户年龄分布',
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
                		        data:['18以下','18-25','26-35','36-45','46以上']
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
                		            name:'年龄段：该段总数（占比）',
                		            type:'pie',
                		            radius : '55%',
                		            center: ['50%', '60%'],
                		            data:[
                		                {value:'${eoneoption}', name:'18以下'},
                		                {value:'${etwooption}', name:'18-25'},
                		                {value:'${ethreeoption}', name:'26-35'},
                		                {value:'${efouroption}', name:'36-45'},
                		                {value:'${efiveoption}', name:'46以上'},
                		            ]
                		        }
                		    ]
                		};
                	
                	option1 = {
                		    title : {
                		        text: '企业客户年龄分布',
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
                		        data:['18以下','18-25','26-35','36-45','46以上']
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
                		            name:'年龄段：该段总数（占比）',
                		            type:'pie',
                		            radius : '55%',
                		            center: ['50%', '60%'],
                		            data:[
                		                  {value:'${oneoption}', name:'18以下'},
                  		                {value:'${twooption}', name:'18-25'},
                  		                {value:'${threeoption}', name:'26-35'},
                  		                {value:'${fouroption}', name:'36-45'},
                  		                {value:'${fiveoption}', name:'46以上'},
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
