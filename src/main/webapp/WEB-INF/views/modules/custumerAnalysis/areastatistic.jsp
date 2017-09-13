<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>经营地统计</title>
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
				<a href="#">经营地统计</a>
			</div>
		</div>
	</div>
	<div class="box-down" style="margin-top: 20px;">
		<div class="box">
			<div id="main" class="col-md-12 col-sm-12" style="height:90%"></div>
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
                'echarts/chart/map' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main')); 
                
                var ecConfig = require('echarts/config');
                var zrEvent = require('zrender/tool/event');
var curIndx = 0;
var mapType = [
    'china',
    // 23个省
    '广东', '青海', '四川', '海南', '陕西', 
    '甘肃', '云南', '湖南', '湖北', '黑龙江',
    '贵州', '山东', '江西', '河南', '河北',
    '山西', '安徽', '福建', '浙江', '江苏', 
    '吉林', '辽宁', '台湾',
    // 5个自治区
    '新疆', '广西', '宁夏', '内蒙古', '西藏', 
    // 4个直辖市
    '北京', '天津', '上海', '重庆',
    // 2个特别行政区
    '香港', '澳门'
];
document.getElementById('main').onmousewheel = function (e){
    var event = e || window.event;
    curIndx += zrEvent.getDelta(event) > 0 ? (-1) : 1;
    if (curIndx < 0) {
        curIndx = mapType.length - 1;
    }
    var mt = mapType[curIndx % mapType.length];
    if (mt == 'china') {
        option.tooltip.formatter = '{b}';
    }
    else{
        option.tooltip.formatter = '{b}';
    }
    option.series[0].mapType = mt;
    option.title.subtext = mt + ' （滚轮或点击切换）';
    myChart.setOption(option, true);
    
    zrEvent.stop(event);
};
myChart.on(ecConfig.EVENT.MAP_SELECTED, function (param){
    var len = mapType.length;
    var mt = mapType[curIndx % len];
    if (mt == 'china') {
        // 全国选择时指定到选中的省份
        var selected = param.selected;
        for (var i in selected) {
            if (selected[i]) {
                mt = i;
                while (len--) {
                    if (mapType[len] == mt) {
                        curIndx = len;
                    }
                }
                break;
            }
        }
        option.tooltip.formatter = '{b}';
    }
    else {
        curIndx = 0;
        mt = 'china';
        option.tooltip.formatter = '{b}';
    }
    option.series[0].mapType = mt;
    option.title.subtext = mt + ' （滚轮或点击切换）';
    myChart.setOption(option, true);
});
option = {
    title: {
        text : '全国34个省市自治区',
        subtext : 'china （滚轮或点击切换）'
    },
    tooltip : {
        trigger: 'item',
        formatter: '{b}'
    },
    legend: {
        orient: 'vertical',
        x:'right',
        data:['数据']
    },
    dataRange: {
        min: 0,
        max: 1000,
        color:['orange','yellow'],
        text:['高','低'],           // 文本，默认为数值文本
        calculable : true
    },
    series : [
        {
            name: '数据',
            type: 'map',
            mapType: 'china',
            selectedMode : 'single',
            itemStyle:{
                normal:{label:{show:true}},
                emphasis:{label:{show:true}}
            },
            data:[
                {name: '重庆市',value: Math.round(Math.random()*1000)},
                {name: '北京市',value: Math.round(Math.random()*1000)},
                {name: '天津市',value: Math.round(Math.random()*1000)},
                {name: '上海市',value: Math.round(Math.random()*1000)},
                {name: '香港',value: Math.round(Math.random()*1000)},
                {name: '澳门',value: Math.round(Math.random()*1000)},
                {name: '巴音郭楞蒙古自治州',value: Math.round(Math.random()*1000)},
                {name: '和田地区',value: Math.round(Math.random()*1000)},
                {name: '哈密地区',value: Math.round(Math.random()*1000)},
                {name: '阿克苏地区',value: Math.round(Math.random()*1000)},
                {name: '阿勒泰地区',value: Math.round(Math.random()*1000)},
                {name: '喀什地区',value: Math.round(Math.random()*1000)},
                {name: '塔城地区',value: Math.round(Math.random()*1000)},
                {name: '昌吉回族自治州',value: Math.round(Math.random()*1000)},
                {name: '克孜勒苏柯尔克孜自治州',value: Math.round(Math.random()*1000)},
                {name: '吐鲁番地区',value: Math.round(Math.random()*1000)},
                {name: '伊犁哈萨克自治州',value: Math.round(Math.random()*1000)},
                {name: '博尔塔拉蒙古自治州',value: Math.round(Math.random()*1000)},
                {name: '乌鲁木齐市',value: Math.round(Math.random()*1000)},
                {name: '克拉玛依市',value: Math.round(Math.random()*1000)},
                {name: '阿拉尔市',value: Math.round(Math.random()*1000)},
                {name: '图木舒克市',value: Math.round(Math.random()*1000)},
                {name: '五家渠市',value: Math.round(Math.random()*1000)},
                {name: '石河子市',value: Math.round(Math.random()*1000)},
                {name: '那曲地区',value: Math.round(Math.random()*1000)},
                {name: '阿里地区',value: Math.round(Math.random()*1000)},
                {name: '日喀则地区',value: Math.round(Math.random()*1000)},
                {name: '林芝地区',value: Math.round(Math.random()*1000)},
                {name: '昌都地区',value: Math.round(Math.random()*1000)},
                {name: '山南地区',value: Math.round(Math.random()*1000)},
                {name: '拉萨市',value: Math.round(Math.random()*1000)},
                {name: '呼伦贝尔市',value: Math.round(Math.random()*1000)},
                {name: '阿拉善盟',value: Math.round(Math.random()*1000)},
                {name: '锡林郭勒盟',value: Math.round(Math.random()*1000)},
                {name: '鄂尔多斯市',value: Math.round(Math.random()*1000)},
                {name: '赤峰市',value: Math.round(Math.random()*1000)},
                {name: '巴彦淖尔市',value: Math.round(Math.random()*1000)},
                {name: '通辽市',value: Math.round(Math.random()*1000)},
                {name: '乌兰察布市',value: Math.round(Math.random()*1000)},
                {name: '兴安盟',value: Math.round(Math.random()*1000)},
                {name: '包头市',value: Math.round(Math.random()*1000)},
                {name: '呼和浩特市',value: Math.round(Math.random()*1000)},
                {name: '乌海市',value: Math.round(Math.random()*1000)},
                {name: '海西蒙古族藏族自治州',value: Math.round(Math.random()*1000)},
                {name: '玉树藏族自治州',value: Math.round(Math.random()*1000)},
                {name: '果洛藏族自治州',value: Math.round(Math.random()*1000)},
                {name: '海南藏族自治州',value: Math.round(Math.random()*1000)},
                {name: '海北藏族自治州',value: Math.round(Math.random()*1000)},
                {name: '黄南藏族自治州',value: Math.round(Math.random()*1000)},
                {name: '海东地区',value: Math.round(Math.random()*1000)},
                {name: '西宁市',value: Math.round(Math.random()*1000)},
                {name: '甘孜藏族自治州',value: Math.round(Math.random()*1000)},
                {name: '阿坝藏族羌族自治州',value: Math.round(Math.random()*1000)},
                {name: '凉山彝族自治州',value: Math.round(Math.random()*1000)},
                {name: '绵阳市',value: Math.round(Math.random()*1000)},
                {name: '达州市',value: Math.round(Math.random()*1000)},
                {name: '广元市',value: Math.round(Math.random()*1000)},
                {name: '雅安市',value: Math.round(Math.random()*1000)},
                {name: '宜宾市',value: Math.round(Math.random()*1000)},
                {name: '乐山市',value: Math.round(Math.random()*1000)},
                {name: '南充市',value: Math.round(Math.random()*1000)},
                {name: '巴中市',value: Math.round(Math.random()*1000)},
                {name: '泸州市',value: Math.round(Math.random()*1000)},
                {name: '成都市',value: Math.round(Math.random()*1000)},
                {name: '资阳市',value: Math.round(Math.random()*1000)},
                {name: '攀枝花市',value: Math.round(Math.random()*1000)},
                {name: '眉山市',value: Math.round(Math.random()*1000)},
                {name: '广安市',value: Math.round(Math.random()*1000)},
                {name: '德阳市',value: Math.round(Math.random()*1000)},
                {name: '内江市',value: Math.round(Math.random()*1000)},
                {name: '遂宁市',value: Math.round(Math.random()*1000)},
                {name: '自贡市',value: Math.round(Math.random()*1000)},
                {name: '黑河市',value: Math.round(Math.random()*1000)},
                {name: '大兴安岭地区',value: Math.round(Math.random()*1000)},
                {name: '哈尔滨市',value: Math.round(Math.random()*1000)},
                {name: '齐齐哈尔市',value: Math.round(Math.random()*1000)},
                {name: '牡丹江市',value: Math.round(Math.random()*1000)},
                {name: '绥化市',value: Math.round(Math.random()*1000)},
                {name: '伊春市',value: Math.round(Math.random()*1000)},
                {name: '佳木斯市',value: Math.round(Math.random()*1000)},
                {name: '鸡西市',value: Math.round(Math.random()*1000)},
                {name: '双鸭山市',value: Math.round(Math.random()*1000)},
                {name: '大庆市',value: Math.round(Math.random()*1000)},
                {name: '鹤岗市',value: Math.round(Math.random()*1000)},
                {name: '七台河市',value: Math.round(Math.random()*1000)},
                {name: '酒泉市',value: Math.round(Math.random()*1000)},
                {name: '张掖市',value: Math.round(Math.random()*1000)},
                {name: '甘南藏族自治州',value: Math.round(Math.random()*1000)},
                {name: '武威市',value: Math.round(Math.random()*1000)},
                {name: '陇南市',value: Math.round(Math.random()*1000)},
                {name: '庆阳市',value: Math.round(Math.random()*1000)},
                {name: '白银市',value: Math.round(Math.random()*1000)},
                {name: '定西市',value: Math.round(Math.random()*1000)},
                {name: '天水市',value: Math.round(Math.random()*1000)},
                {name: '兰州市',value: Math.round(Math.random()*1000)},
                {name: '平凉市',value: Math.round(Math.random()*1000)},
                {name: '临夏回族自治州',value: Math.round(Math.random()*1000)},
                {name: '金昌市',value: Math.round(Math.random()*1000)},
                {name: '嘉峪关市',value: Math.round(Math.random()*1000)},
                {name: '普洱市',value: Math.round(Math.random()*1000)},
                {name: '红河哈尼族彝族自治州',value: Math.round(Math.random()*1000)},
                {name: '文山壮族苗族自治州',value: Math.round(Math.random()*1000)},
                {name: '曲靖市',value: Math.round(Math.random()*1000)},
                {name: '楚雄彝族自治州',value: Math.round(Math.random()*1000)},
                {name: '大理白族自治州',value: Math.round(Math.random()*1000)},
                {name: '临沧市',value: Math.round(Math.random()*1000)},
                {name: '迪庆藏族自治州',value: Math.round(Math.random()*1000)},
                {name: '昭通市',value: Math.round(Math.random()*1000)},
                {name: '昆明市',value: Math.round(Math.random()*1000)},
                {name: '丽江市',value: Math.round(Math.random()*1000)},
                {name: '西双版纳傣族自治州',value: Math.round(Math.random()*1000)},
                {name: '保山市',value: Math.round(Math.random()*1000)},
                {name: '玉溪市',value: Math.round(Math.random()*1000)},
                {name: '怒江傈僳族自治州',value: Math.round(Math.random()*1000)},
                {name: '德宏傣族景颇族自治州',value: Math.round(Math.random()*1000)},
                {name: '百色市',value: Math.round(Math.random()*1000)},
                {name: '河池市',value: Math.round(Math.random()*1000)},
                {name: '桂林市',value: Math.round(Math.random()*1000)},
                {name: '南宁市',value: Math.round(Math.random()*1000)},
                {name: '柳州市',value: Math.round(Math.random()*1000)},
                {name: '崇左市',value: Math.round(Math.random()*1000)},
                {name: '来宾市',value: Math.round(Math.random()*1000)},
                {name: '玉林市',value: Math.round(Math.random()*1000)},
                {name: '梧州市',value: Math.round(Math.random()*1000)},
                {name: '贺州市',value: Math.round(Math.random()*1000)},
                {name: '钦州市',value: Math.round(Math.random()*1000)},
                {name: '贵港市',value: Math.round(Math.random()*1000)},
                {name: '防城港市',value: Math.round(Math.random()*1000)},
                {name: '北海市',value: Math.round(Math.random()*1000)},
                {name: '怀化市',value: Math.round(Math.random()*1000)},
                {name: '永州市',value: Math.round(Math.random()*1000)},
                {name: '邵阳市',value: Math.round(Math.random()*1000)},
                {name: '郴州市',value: Math.round(Math.random()*1000)},
                {name: '常德市',value: Math.round(Math.random()*1000)},
                {name: '湘西土家族苗族自治州',value: Math.round(Math.random()*1000)},
                {name: '衡阳市',value: Math.round(Math.random()*1000)},
                {name: '岳阳市',value: Math.round(Math.random()*1000)},
                {name: '益阳市',value: Math.round(Math.random()*1000)},
                {name: '长沙市',value: Math.round(Math.random()*1000)},
                {name: '株洲市',value: Math.round(Math.random()*1000)},
                {name: '张家界市',value: Math.round(Math.random()*1000)},
                {name: '娄底市',value: Math.round(Math.random()*1000)},
                {name: '湘潭市',value: Math.round(Math.random()*1000)},
                {name: '榆林市',value: Math.round(Math.random()*1000)},
                {name: '延安市',value: Math.round(Math.random()*1000)},
                {name: '汉中市',value: Math.round(Math.random()*1000)},
                {name: '安康市',value: Math.round(Math.random()*1000)},
                {name: '商洛市',value: Math.round(Math.random()*1000)},
                {name: '宝鸡市',value: Math.round(Math.random()*1000)},
                {name: '渭南市',value: Math.round(Math.random()*1000)},
                {name: '咸阳市',value: Math.round(Math.random()*1000)},
                {name: '西安市',value: Math.round(Math.random()*1000)},
                {name: '铜川市',value: Math.round(Math.random()*1000)},
                {name: '清远市',value: Math.round(Math.random()*1000)},
                {name: '韶关市',value: Math.round(Math.random()*1000)},
                {name: '湛江市',value: Math.round(Math.random()*1000)},
                {name: '梅州市',value: Math.round(Math.random()*1000)},
                {name: '河源市',value: Math.round(Math.random()*1000)},
                {name: '肇庆市',value: Math.round(Math.random()*1000)},
                {name: '惠州市',value: Math.round(Math.random()*1000)},
                {name: '茂名市',value: Math.round(Math.random()*1000)},
                {name: '江门市',value: Math.round(Math.random()*1000)},
                {name: '阳江市',value: Math.round(Math.random()*1000)},
                {name: '云浮市',value: Math.round(Math.random()*1000)},
                {name: '广州市',value: Math.round(Math.random()*1000)},
                {name: '汕尾市',value: Math.round(Math.random()*1000)},
                {name: '揭阳市',value: Math.round(Math.random()*1000)},
                {name: '珠海市',value: Math.round(Math.random()*1000)},
                {name: '佛山市',value: Math.round(Math.random()*1000)},
                {name: '潮州市',value: Math.round(Math.random()*1000)},
                {name: '汕头市',value: Math.round(Math.random()*1000)},
                {name: '深圳市',value: Math.round(Math.random()*1000)},
                {name: '东莞市',value: Math.round(Math.random()*1000)},
                {name: '中山市',value: Math.round(Math.random()*1000)},
                {name: '延边朝鲜族自治州',value: Math.round(Math.random()*1000)},
                {name: '吉林市',value: Math.round(Math.random()*1000)},
                {name: '白城市',value: Math.round(Math.random()*1000)},
                {name: '松原市',value: Math.round(Math.random()*1000)},
                {name: '长春市',value: Math.round(Math.random()*1000)},
                {name: '白山市',value: Math.round(Math.random()*1000)},
                {name: '通化市',value: Math.round(Math.random()*1000)},
                {name: '四平市',value: Math.round(Math.random()*1000)},
                {name: '辽源市',value: Math.round(Math.random()*1000)},
                {name: '承德市',value: Math.round(Math.random()*1000)},
                {name: '张家口市',value: Math.round(Math.random()*1000)},
                {name: '保定市',value: Math.round(Math.random()*1000)},
                {name: '唐山市',value: Math.round(Math.random()*1000)},
                {name: '沧州市',value: Math.round(Math.random()*1000)},
                {name: '石家庄市',value: Math.round(Math.random()*1000)},
                {name: '邢台市',value: Math.round(Math.random()*1000)},
                {name: '邯郸市',value: Math.round(Math.random()*1000)},
                {name: '秦皇岛市',value: Math.round(Math.random()*1000)},
                {name: '衡水市',value: Math.round(Math.random()*1000)},
                {name: '廊坊市',value: Math.round(Math.random()*1000)},
                {name: '恩施土家族苗族自治州',value: Math.round(Math.random()*1000)},
                {name: '十堰市',value: Math.round(Math.random()*1000)},
                {name: '宜昌市',value: Math.round(Math.random()*1000)},
                {name: '襄樊市',value: Math.round(Math.random()*1000)},
                {name: '黄冈市',value: Math.round(Math.random()*1000)},
                {name: '荆州市',value: Math.round(Math.random()*1000)},
                {name: '荆门市',value: Math.round(Math.random()*1000)},
                {name: '咸宁市',value: Math.round(Math.random()*1000)},
                {name: '随州市',value: Math.round(Math.random()*1000)},
                {name: '孝感市',value: Math.round(Math.random()*1000)},
                {name: '武汉市',value: Math.round(Math.random()*1000)},
                {name: '黄石市',value: Math.round(Math.random()*1000)},
                {name: '神农架林区',value: Math.round(Math.random()*1000)},
                {name: '天门市',value: Math.round(Math.random()*1000)},
                {name: '仙桃市',value: Math.round(Math.random()*1000)},
                {name: '潜江市',value: Math.round(Math.random()*1000)},
                {name: '鄂州市',value: Math.round(Math.random()*1000)},
                {name: '遵义市',value: Math.round(Math.random()*1000)},
                {name: '黔东南苗族侗族自治州',value: Math.round(Math.random()*1000)},
                {name: '毕节地区',value: Math.round(Math.random()*1000)},
                {name: '黔南布依族苗族自治州',value: Math.round(Math.random()*1000)},
                {name: '铜仁地区',value: Math.round(Math.random()*1000)},
                {name: '黔西南布依族苗族自治州',value: Math.round(Math.random()*1000)},
                {name: '六盘水市',value: Math.round(Math.random()*1000)},
                {name: '安顺市',value: Math.round(Math.random()*1000)},
                {name: '贵阳市',value: Math.round(Math.random()*1000)},
                {name: '烟台市',value: Math.round(Math.random()*1000)},
                {name: '临沂市',value: Math.round(Math.random()*1000)},
                {name: '潍坊市',value: Math.round(Math.random()*1000)},
                {name: '青岛市',value: Math.round(Math.random()*1000)},
                {name: '菏泽市',value: Math.round(Math.random()*1000)},
                {name: '济宁市',value: Math.round(Math.random()*1000)},
                {name: '德州市',value: Math.round(Math.random()*1000)},
                {name: '滨州市',value: Math.round(Math.random()*1000)},
                {name: '聊城市',value: Math.round(Math.random()*1000)},
                {name: '东营市',value: Math.round(Math.random()*1000)},
                {name: '济南市',value: Math.round(Math.random()*1000)},
                {name: '泰安市',value: Math.round(Math.random()*1000)},
                {name: '威海市',value: Math.round(Math.random()*1000)},
                {name: '日照市',value: Math.round(Math.random()*1000)},
                {name: '淄博市',value: Math.round(Math.random()*1000)},
                {name: '枣庄市',value: Math.round(Math.random()*1000)},
                {name: '莱芜市',value: Math.round(Math.random()*1000)},
                {name: '赣州市',value: Math.round(Math.random()*1000)},
                {name: '吉安市',value: Math.round(Math.random()*1000)},
                {name: '上饶市',value: Math.round(Math.random()*1000)},
                {name: '九江市',value: Math.round(Math.random()*1000)},
                {name: '抚州市',value: Math.round(Math.random()*1000)},
                {name: '宜春市',value: Math.round(Math.random()*1000)},
                {name: '南昌市',value: Math.round(Math.random()*1000)},
                {name: '景德镇市',value: Math.round(Math.random()*1000)},
                {name: '萍乡市',value: Math.round(Math.random()*1000)},
                {name: '鹰潭市',value: Math.round(Math.random()*1000)},
                {name: '新余市',value: Math.round(Math.random()*1000)},
                {name: '南阳市',value: Math.round(Math.random()*1000)},
                {name: '信阳市',value: Math.round(Math.random()*1000)},
                {name: '洛阳市',value: Math.round(Math.random()*1000)},
                {name: '驻马店市',value: Math.round(Math.random()*1000)},
                {name: '周口市',value: Math.round(Math.random()*1000)},
                {name: '商丘市',value: Math.round(Math.random()*1000)},
                {name: '三门峡市',value: Math.round(Math.random()*1000)},
                {name: '新乡市',value: Math.round(Math.random()*1000)},
                {name: '平顶山市',value: Math.round(Math.random()*1000)},
                {name: '郑州市',value: Math.round(Math.random()*1000)},
                {name: '安阳市',value: Math.round(Math.random()*1000)},
                {name: '开封市',value: Math.round(Math.random()*1000)},
                {name: '焦作市',value: Math.round(Math.random()*1000)},
                {name: '许昌市',value: Math.round(Math.random()*1000)},
                {name: '濮阳市',value: Math.round(Math.random()*1000)},
                {name: '漯河市',value: Math.round(Math.random()*1000)},
                {name: '鹤壁市',value: Math.round(Math.random()*1000)},
                {name: '大连市',value: Math.round(Math.random()*1000)},
                {name: '朝阳市',value: Math.round(Math.random()*1000)},
                {name: '丹东市',value: Math.round(Math.random()*1000)},
                {name: '铁岭市',value: Math.round(Math.random()*1000)},
                {name: '沈阳市',value: Math.round(Math.random()*1000)},
                {name: '抚顺市',value: Math.round(Math.random()*1000)},
                {name: '葫芦岛市',value: Math.round(Math.random()*1000)},
                {name: '阜新市',value: Math.round(Math.random()*1000)},
                {name: '锦州市',value: Math.round(Math.random()*1000)},
                {name: '鞍山市',value: Math.round(Math.random()*1000)},
                {name: '本溪市',value: Math.round(Math.random()*1000)},
                {name: '营口市',value: Math.round(Math.random()*1000)},
                {name: '辽阳市',value: Math.round(Math.random()*1000)},
                {name: '盘锦市',value: Math.round(Math.random()*1000)},
                {name: '忻州市',value: Math.round(Math.random()*1000)},
                {name: '吕梁市',value: Math.round(Math.random()*1000)},
                {name: '临汾市',value: Math.round(Math.random()*1000)},
                {name: '晋中市',value: Math.round(Math.random()*1000)},
                {name: '运城市',value: Math.round(Math.random()*1000)},
                {name: '大同市',value: Math.round(Math.random()*1000)},
                {name: '长治市',value: Math.round(Math.random()*1000)},
                {name: '朔州市',value: Math.round(Math.random()*1000)},
                {name: '晋城市',value: Math.round(Math.random()*1000)},
                {name: '太原市',value: Math.round(Math.random()*1000)},
                {name: '阳泉市',value: Math.round(Math.random()*1000)},
                {name: '六安市',value: Math.round(Math.random()*1000)},
                {name: '安庆市',value: Math.round(Math.random()*1000)},
                {name: '滁州市',value: Math.round(Math.random()*1000)},
                {name: '宣城市',value: Math.round(Math.random()*1000)},
                {name: '阜阳市',value: Math.round(Math.random()*1000)},
                {name: '宿州市',value: Math.round(Math.random()*1000)},
                {name: '黄山市',value: Math.round(Math.random()*1000)},
                {name: '巢湖市',value: Math.round(Math.random()*1000)},
                {name: '亳州市',value: Math.round(Math.random()*1000)},
                {name: '池州市',value: Math.round(Math.random()*1000)},
                {name: '合肥市',value: Math.round(Math.random()*1000)},
                {name: '蚌埠市',value: Math.round(Math.random()*1000)},
                {name: '芜湖市',value: Math.round(Math.random()*1000)},
                {name: '淮北市',value: Math.round(Math.random()*1000)},
                {name: '淮南市',value: Math.round(Math.random()*1000)},
                {name: '马鞍山市',value: Math.round(Math.random()*1000)},
                {name: '铜陵市',value: Math.round(Math.random()*1000)},
                {name: '南平市',value: Math.round(Math.random()*1000)},
                {name: '三明市',value: Math.round(Math.random()*1000)},
                {name: '龙岩市',value: Math.round(Math.random()*1000)},
                {name: '宁德市',value: Math.round(Math.random()*1000)},
                {name: '福州市',value: Math.round(Math.random()*1000)},
                {name: '漳州市',value: Math.round(Math.random()*1000)},
                {name: '泉州市',value: Math.round(Math.random()*1000)},
                {name: '莆田市',value: Math.round(Math.random()*1000)},
                {name: '厦门市',value: Math.round(Math.random()*1000)},
                {name: '丽水市',value: Math.round(Math.random()*1000)},
                {name: '杭州市',value: Math.round(Math.random()*1000)},
                {name: '温州市',value: Math.round(Math.random()*1000)},
                {name: '宁波市',value: Math.round(Math.random()*1000)},
                {name: '舟山市',value: Math.round(Math.random()*1000)},
                {name: '台州市',value: Math.round(Math.random()*1000)},
                {name: '金华市',value: Math.round(Math.random()*1000)},
                {name: '衢州市',value: Math.round(Math.random()*1000)},
                {name: '绍兴市',value: Math.round(Math.random()*1000)},
                {name: '嘉兴市',value: Math.round(Math.random()*1000)},
                {name: '湖州市',value: Math.round(Math.random()*1000)},
                {name: '盐城市',value: Math.round(Math.random()*1000)},
                {name: '徐州市',value: Math.round(Math.random()*1000)},
                {name: '南通市',value: Math.round(Math.random()*1000)},
                {name: '淮安市',value: Math.round(Math.random()*1000)},
                {name: '苏州市',value: Math.round(Math.random()*1000)},
                {name: '宿迁市',value: Math.round(Math.random()*1000)},
                {name: '连云港市',value: Math.round(Math.random()*1000)},
                {name: '扬州市',value: Math.round(Math.random()*1000)},
                {name: '南京市',value: Math.round(Math.random()*1000)},
                {name: '泰州市',value: Math.round(Math.random()*1000)},
                {name: '无锡市',value: Math.round(Math.random()*1000)},
                {name: '常州市',value: Math.round(Math.random()*1000)},
                {name: '镇江市',value: Math.round(Math.random()*1000)},
                {name: '吴忠市',value: Math.round(Math.random()*1000)},
                {name: '中卫市',value: Math.round(Math.random()*1000)},
                {name: '固原市',value: Math.round(Math.random()*1000)},
                {name: '银川市',value: Math.round(Math.random()*1000)},
                {name: '石嘴山市',value: Math.round(Math.random()*1000)},
                {name: '儋州市',value: Math.round(Math.random()*1000)},
                {name: '文昌市',value: Math.round(Math.random()*1000)},
                {name: '乐东黎族自治县',value: Math.round(Math.random()*1000)},
                {name: '三亚市',value: Math.round(Math.random()*1000)},
                {name: '琼中黎族苗族自治县',value: Math.round(Math.random()*1000)},
                {name: '东方市',value: Math.round(Math.random()*1000)},
                {name: '海口市',value: Math.round(Math.random()*1000)},
                {name: '万宁市',value: Math.round(Math.random()*1000)},
                {name: '澄迈县',value: Math.round(Math.random()*1000)},
                {name: '白沙黎族自治县',value: Math.round(Math.random()*1000)},
                {name: '琼海市',value: Math.round(Math.random()*1000)},
                {name: '昌江黎族自治县',value: Math.round(Math.random()*1000)},
                {name: '临高县',value: Math.round(Math.random()*1000)},
                {name: '陵水黎族自治县',value: Math.round(Math.random()*1000)},
                {name: '屯昌县',value: Math.round(Math.random()*1000)},
                {name: '定安县',value: Math.round(Math.random()*1000)},
                {name: '保亭黎族苗族自治县',value: Math.round(Math.random()*1000)},
                {name: '五指山市',value: Math.round(Math.random()*1000)},
            ]
        }
    ]
};
                    
        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
    </script>
</body>
</html>