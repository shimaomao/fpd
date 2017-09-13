
/* 
 * 功能: 访问url，显示操作结果
 * 说明: 使用基本的ajax方式请求，返回json格式：
 * 		操作成功，返回的json格式应该为：{status:'y',info:'提示信息'}
 * 		操作失败，返回的json格式应该为：{status:'n',info:'错误信息',errCode:'错误码'}
 * 参数:
 *     url: 访问url和请求参数
 */

function jqAjax(url) {
	$.ajax({
		url : url,
		dataType : "json",
		async : false,
		success : function(retData) {
			if (retData.status == "y") {
				if (retData.info) {
					alert("操作成功:" + retData.info);
				} else {
					alert("操作成功！");
				}
				return true;
			}

			if (retData.status == "n") {
				if (retData.info) {
					var err = "错误信息：" + retData.info;
					if (retData.errCode) {
						err = err + "，错误码：" + retData.errCode;
					}
					alert(err);
				} else if (retData.errCode) {
					alert("错误码：" + retData.errCode);
				} else {
					alert("操作失败！");
				}
				return false;
			} else if (retData.info) {
				var err = "错误信息：" + retData.info;
				if (retData.errCode) {
					err = err + "，错误码：" + retData.errCode;
				}
				alert(err);
			} else if (retData.errCode) {
				alert("错误码：" + retData.errCode);
			} else {
				alert("操作失败！");
			}
		}
	});
}

/*
 * 功能:ajax post请求,提交内容或执行处理
 * 说明：该函数是简写的 Ajax 函数，等价于：
	$.ajax({
	  type: 'POST',
	  url: url,
	  data: data,
	  success: success,
	  dataType: dataType
	});
	url:需要请求的地址
 * param;处理的相关内容或参数{param1:value1,param2:value2}
 * callBackFun:回调的函数
 * 
 * */
function ajax_post(url,param,callBackFun){
  $.post(url,param,function(responseText){
  	if (responseText.indexOf("错误提示") != -1){
  		alert(responseText);
  	}else{
  		if (callBackFun instanceof Function){
  			callBackFun.call(this,responseText);
  		}
  	}
  });
}