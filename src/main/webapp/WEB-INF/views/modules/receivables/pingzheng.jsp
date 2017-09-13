<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<style type="text/css">
</style>
<title>业务凭证</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${ctxStatic}/util.js"></script>
<script type="text/javascript">
     
	$(document).ready(
	   function() {
		 //把贷款金额转化为完整大写
			 var amount = chineseNumber('${loanContract.loanAmount}');
			 if(amount != "0"){
				 var units = ['亿','仟','佰','拾','万','仟','佰','拾','元','角','分'];
				 var amount_length = amount.length/2;
				 var start = units.length - amount_length;
				 var temp = new Array();
				 for(var i=0,k = 0; i< amount.length; i++,k++){
					 var j = i;
					 j++;
					 temp[k]=amount.substring(i,j);
					 i++;
			     }
			     for(var i = 0; i<temp.length; i++){
				     //把结果填充到表格里
					 $("#amount_"+(start+i)).html(temp[i]);
				 }
			 }
			 var amount = chineseNumber('${repayreal}');
			 if(amount != "0"){
				 var units = ['亿','仟','佰','拾','万','仟','佰','拾','元','角','分'];
				 var amount_length = amount.length/2;
				 var start = units.length - amount_length;
				 var temp = new Array();
				 for(var i=0,k = 0; i< amount.length; i++,k++){
					 var j = i;
					 j++;
					 temp[k]=amount.substring(i,j);
					 i++;
			     }
			     for(var i = 0; i<temp.length; i++){
				     //把结果填充到表格里
					 $("#backamount_"+(start+i)).html(temp[i]);
				 }
			 }
			 var amount = chineseNumber('${total}');
			 if(amount != "0"){
				 var units = ['亿','仟','佰','拾','万','仟','佰','拾','元','角','分'];
				 var amount_length = amount.length/2;
				 var start = units.length - amount_length;
				 var temp = new Array();
				 for(var i=0,k = 0; i< amount.length; i++,k++){
					 var j = i;
					 j++;
					 temp[k]=amount.substring(i,j);
					 i++;
			     }
			     for(var i = 0; i<temp.length; i++){
				     //把结果填充到表格里
					 $("#total_"+(start+i)).html(temp[i]);
				 }
			 }
			 
			//数字转完整中文
			 function chineseNumber(dValue) {
			 	var maxDec = 2;
			 	dValue = dValue.toString().replace(/,/g, "");
			 	dValue = dValue.replace(/^0+/, ""); 
			 	if (dValue == "") {
			 		return "0";
			 	} // （错误：金额为空！）
			 	else if (isNaN(dValue)) {
			 		return "isnotNaN";
			 	}
			 	var minus = ""; // 负数的符号“-”的大写：“负”字。可自定义字符，如“（负）”。
			 	var CN_SYMBOL = ""; // 币种名称（如“人民币”，默认空）
			 	if (dValue.length > 1) {
			 		if (dValue.indexOf('-') == 0) {
			 			dValue = dValue.replace("-", "");
			 			minus = "负";
			 		} // 处理负数符号“-”
			 		if (dValue.indexOf('+') == 0) {
			 			dValue = dValue.replace("+", "");
			 		} // 处理前导正数符号“+”（无实际意义）
			 	}
			 	// 变量定义：
			 	var vInt = "";
			 	var vDec = ""; // 字符串：金额的整数部分、小数部分
			 	var resAIW; // 字符串：要输出的结果
			 	var parts; // 数组（整数部分.小数部分），length=1时则仅为整数。
			 	var digits, radices, bigRadices, decimals; // 数组：数字（0~9——零~玖）；基（十进制记数系统中每个数字位的基是10——拾,佰,仟）；大基（万,亿,兆,京,垓,杼,穰,沟,涧,正）；辅币（元以下，角/分/厘/毫/丝）。
			 	var zeroCount; // 零计数
			 	var i, p, d; // 循环因子；前一位数字；当前位数字。
			 	var quotient, modulus; // 整数部分计算用：商数、模数。
			 	// 金额数值转换为字符，分割整数部分和小数部分：整数、小数分开来搞（小数部分有可能四舍五入后对整数部分有进位）。
			 	var NoneDecLen = (typeof (maxDec) == "undefined" || maxDec == null || Number(maxDec) < 0 || Number(maxDec) > 5); // 是否未指定有效小数位（true/false）
			 	parts = dValue.split('.'); // 数组赋值：（整数部分.小数部分），Array的length=1则仅为整数。
			 	if (parts.length > 1) {
			 		vInt = parts[0];
			 		vDec = parts[1]; // 变量赋值：金额的整数部分、小数部分
			 		if (NoneDecLen) {
			 			maxDec = vDec.length > 5 ? 5 : vDec.length;
			 		} // 未指定有效小数位参数值时，自动取实际小数位长但不超5。
			 		var rDec = Number("0." + vDec);
			 		rDec *= Math.pow(10, maxDec);
			 		rDec = Math.round(Math.abs(rDec));
			 		rDec /= Math.pow(10, maxDec); // 小数四舍五入
			 		var aIntDec = rDec.toString().split('.');
			 		if (Number(aIntDec[0]) == 1) {
			 			vInt = (Number(vInt) + 1).toString();
			 		} // 小数部分四舍五入后有可能向整数部分的个位进位（值1）
			 		if (aIntDec.length > 1) {
			 			vDec = aIntDec[1];
			 		} else {
			 			vDec = "";
			 		}
			 	} else {
			 		vInt = dValue;
			 		vDec = "";
			 		if (NoneDecLen) {
			 			maxDec = 0;
			 		}
			 	}
			 	// 准备各字符数组 Prepare the characters corresponding to the digits:
			 	digits = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); // 零~玖
			 	radices = new Array("", "拾", "佰", "仟"); // 拾,佰,仟
			 	bigRadices = new Array("", "万", "亿", "兆", "京", "垓", "杼", "穰", "沟", "涧", "正"); // 万,亿,兆,京,垓,杼,穰,沟,涧,正
			 	decimals = new Array("角", "分", "厘", "毫", "丝"); // 角/分/厘/毫/丝
			 	resAIW = ""; // 开始处理
			 	// 处理整数部分（如果有）
			 	if (Number(vInt) > 0) {
			 		zeroCount = 0;
			 		for (i = 0; i < vInt.length; i++) {
			 			p = vInt.length - i - 1;
			 			d = vInt.substr(i, 1);
			 			quotient = p / 4;
			 			modulus = p % 4;
			 			resAIW += digits[Number(d)] + radices[modulus];
			 			if (modulus == 0 && zeroCount < 4) {
			 				resAIW += bigRadices[quotient];
			 			}
			 		}
			 		resAIW += "元";
			 	}
			 	// 处理小数部分（如果有）
			 	for (i = 0; i < vDec.length; i++) {
			 		d = vDec.substr(i, 1);
			 		resAIW += digits[Number(d)] + decimals[i];
			 	}
			 	// 处理结果
			 	if (resAIW == "") {
			 		resAIW = "零" + "元";
			 	} // 零元
			 	resAIW = CN_SYMBOL + minus + resAIW; // 人民币/负......元角分/整
			 	if(resAIW.indexOf('角')<0){
			 		resAIW+="零角"
			    }
			 	if(resAIW.indexOf('分')<0){
			 		resAIW+="零分";
			    }
			 	return resAIW;
			 }
	});

</script>
</head>
<body>
	<!-- 以下合同部分 -->
	<table cellpadding="0" cellspacing="0" width="100%" id="loanContractTable" 	class="table table-striped table-bordered table-condensed">
		<h3 align="center">凭证编制</h3>
		<tbody>
			<tr>
				<td style="width: 150px;" rowspan="2"><font style="padding-left: 50px;">摘要</font></td>
				<td style="width: 350px;" rowspan="2"><font style="padding-left: 100px;">业务编号</font></td>
				<td style="width: 250px;" colspan="11"><font style="padding-left: 150px;">借方金额</font></td>
				<td style="width: 250px;" colspan="11"><font style="padding-left: 150px;">贷方金额</font></td>
			</tr>
			<tr>
				<td width="17px;">亿</td>
				<td width="17px;">千</td>
				<td width="17px;">百</td>
				<td width="17px;">十</td>
				<td width="17px;">万</td>
				<td width="17px;">千</td>
				<td width="17px;">百</td>
				<td width="17px;">十</td>
				<td width="17px;">元</td>
				<td width="17px;">角</td>
				<td width="17px;">分</td>
				<td width="17px;">亿</td>
				<td width="17px;">千</td>
				<td width="17px;">百</td>
				<td width="17px;">十</td>
				<td width="17px;">万</td>
				<td width="17px;">千</td>
				<td width="17px;">百</td>
				<td width="17px;">十</td>
				<td width="17px;">元</td>
				<td width="17px;">角</td>
				<td width="17px;">分</td>
			</tr>
			<tr height="55px;">
			    <td>放款金额</td>
			    <td>${loanContract.contractNumber}</td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;" id="amount_0"></td>
				<td width="17px;" id="amount_1"></td>
				<td width="17px;" id="amount_2"></td>
				<td width="17px;" id="amount_3"></td>
				<td width="17px;" id="amount_4"></td>
				<td width="17px;" id="amount_5"></td>
				<td width="17px;" id="amount_6"></td>
				<td width="17px;" id="amount_7"></td>
				<td width="17px;" id="amount_8"></td>
				<td width="17px;" id="amount_9"></td>
				<td width="17px;" id="amount_10"></td>
			</tr>
			<tr height="55px;">
			    <td>回收金额</td>
			    <td>${loanContract.contractNumber}</td>
				<td width="17px;" id="backamount_0"></td>
				<td width="17px;" id="backamount_1"></td>
				<td width="17px;" id="backamount_2"></td>
				<td width="17px;" id="backamount_3"></td>
				<td width="17px;" id="backamount_4"></td>
				<td width="17px;" id="backamount_5"></td>
				<td width="17px;" id="backamount_6"></td>
				<td width="17px;" id="backamount_7"></td>
				<td width="17px;" id="backamount_8"></td>
				<td width="17px;" id="backamount_9"></td>
				<td width="17px;" id="backamount_10"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
			</tr>
			<tr height="55px;">
			    <td colspan="2">合计：</td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;"></td>
				<td width="17px;" id="total_0"></td>
				<td width="17px;" id="total_1"></td>
				<td width="17px;" id="total_2"></td>
				<td width="17px;" id="total_3"></td>
				<td width="17px;" id="total_4"></td>
				<td width="17px;" id="total_5"></td>
				<td width="17px;" id="total_6"></td>
				<td width="17px;" id="total_7"></td>
				<td width="17px;" id="total_8"></td>
				<td width="17px;" id="total_9"></td>
				<td width="17px;" id="total_10"></td>
			</tr>
		</tbody>
	</table>
	<h6 align="left" style="padding-left: 30px;">制单人：${fns:getUser().name} 
      <font style="float: right;padding-right: 200px;"> 时间：<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd" /></font></h6>
	
	<hr>
</body>
</html>