$(function(){	
	
	$(".vform").each(function(){
    		validformInit($(this));
    	});
	
	$(".vlink").each(function(){
		linkAjaxInit($(this));
	});
	
	$(".vtip").each(function(){
		vtipInit($(this));
	});
});

function pageInit($div){
	validformsInit($div);
	linkAjaxsInit($div);
}

function validformsInit($div){
	$div.find(".vform").each(function(){
		validformInit($(this));
	});
}

function linkAjaxsInit($div){
	$div.find(".vlink").each(function(){
		linkAjaxInit($(this));
	});
}

function vtipsInit($div){
	$div.find(".vtip").each(function(){
		vtipInit($(this));
	});
}

function vtipInit($obj){
	var timer, msg = $obj.attr("tipMsg");
	if(!msg) return;
	$obj.css("text-decoration","underline");
	$obj.css("cursor","default");
	if (!$obj.is("[showHtml]")) {
		if (msg.indexOf("<") > -1)msg = msg.replace(/</g, "&lt;");
		if (msg.indexOf(">") > -1)msg = msg.replace(/>/g, "&gt;");
	}
	$obj.hover(function(){
		var _this = this;
		timer = setTimeout(function(){
			oTip(_this, msg);
			timer = null;
		}, 500);
	}, function(){
		if (timer) {
			clearTimeout(timer);
			timer = null;
		}
	});
}

var vforms = [];
var linkTimout = 600000;//超链接超时时长
var formTimout = 600000;//表单提交超时时长

function validformInit($form){
	var _this = $form[0];
	var okUrl = $form.attr("okUrl");
	var action = $form.attr("action");
	var okDoit = $form.attr("okDoit");
	var errDoit = $form.attr("errDoit");
	var targetFor = $form.attr("targetFor");
	var loadOk = $form.attr("loadOk");
	var igErrCode = $form.attr("igErrCode");
	var waitTipType = $form.is("[waitTipType]") ? $form.attr("waitTipType") : "loading"; //等待提醒类型
	var waitTip = $form.is("[waitTip]") ? $form.attr("waitTip") : " "; //等待提醒字样
	if (okDoit) okDoit = okDoit.replace(/\bthis\b/g, "_this");
	if (errDoit) errDoit = errDoit.replace(/\bthis\b/g, "_this");
	
	//以下为validform 参数
	var _tiptype = $form.is("[tiptype]") ? eval($form.attr("tiptype")) : 4; 
	var _ajaxPost = $form.is("[ajaxPost]") ? ($form.attr("ajaxPost") == "true") : true;
	var _showAllError = $form.is("[showAllError]") ? ($form.attr("showAllError") == "true") : false;
	var _postonce = $form.is("[postonce]") ? ($form.attr("postonce") == "true") : false;
	var _label = $form.is("[label]") ? $form.attr("label") : ".input-label";
	var _tipSweep = $form.is("[tipSweep]") ? ($form.attr("tipSweep") == "true") : false;
	
	//回调
	var _callback = function(retData){
		if(retData.status=="0" && retData.statusText =="timeout"){//通讯超时
			doneLoading(waitTipType, waitTip);
			alertTimeoutErr();
			return false;
		}
		if(retData.status=="404"){
			doneLoading(waitTipType, waitTip);
			alert404Err();
			return false;
		}
		if(retData.status=="500"){
			doneLoading(waitTipType, waitTip);
			alert500Err();
			return false;
		}
		//返回的数据为html格式
		if(retData.status =="200" && retData.responseText){
			doneLoading(waitTipType, waitTip);
			try{
				//成功处理
				if (okDoit) {
					eval(okDoit);
					return true;
				}
				//局部加载
				if (targetFor) {
					$(targetFor).html(retData.responseText);
					pageInit($(targetFor));
					if(loadOk){
						eval(loadOk);
					}
					return true;
				}
				
				$("body").html(retData.responseText);
				return true;
			}catch(e){
				zError("加载页面失败！");
				return false;
			}
		}
		
		if (retData.status=="n") {
			doneLoading(waitTipType, waitTip);
			if (retData.errCode != igErrCode)
				zError($.i18n.prop("cwm")+"："+retData.errCode+"<p/>"+$.i18n.prop("cwxx")+"："+retData.info);
			if (errDoit) eval(errDoit);
			if($form.is("[isDisabled]")){		//针对重复提交作控制
				$form.find("input[type='submit']").removeAttr("disabled");
			}
			return false;
		} else if(retData.status!="y") {
			doneLoading(waitTipType, waitTip);
			zError("未知错误： 响应状态["+retData.status+"]，描述：" + retData.statusText);
			if($form.is("[isDisabled]")){    //针对重复提交作控制
				$form.find("input[type='submit']").removeAttr("disabled");
			}
			return false;
		}
		
		//成功处理
		if (okDoit) {
			doneLoading(waitTipType, waitTip);
			eval(okDoit);
			return true;
		}
		//优先跳转至服务器返回的okUrl
		if (retData.okUrl) {
			location.href = retData.okUrl;
			return true;
		}
		
		//其次跳转至配置的url
		if (okUrl) {
			location.href = okUrl;
			return true;
		}
		
		//默认跳转至action地址
		location.href = action;
		return true;
	};
	if (!_ajaxPost) _callback = null;
	
	var vform = $form.Validform({
		tiptype: _tiptype,
		label: _label,
		showAllError:_showAllError,
		tipSweep: _tipSweep,
		beforeCheck: function(curform){
			curform.isSubmit = true;
			return true;
		},
		beforeSubmit: function(curform){
			//添加两次输入的密码是否一致的校验
			var loginPwd = curform.find("input[id='login_pwd']");
			var repeatPwd = curform.find("input[id='repeat_pwd']");
			if(loginPwd.length>0&&repeatPwd.length>0){
				if(loginPwd.val()!=repeatPwd.val()){
					zError($.i18n.prop("lcsrdmmbyz"));
					return false;
				}
			}
			if(curform.is("[isDisabled]")){  //针对重复提交作控制
				curform.find("input[type='submit']").attr("disabled","true");
			}
			if(!beforeCheck(curform)) return false;
			doLoading(waitTipType, waitTip);
			return true;
		},
		postonce: _postonce,
		ajaxPost: _ajaxPost,
		callback: _callback
	});
	
	vform.config({
		ajaxpost:{
			timeout: formTimout//设置通讯60秒超时时间
		}
	});
	
	$form.data("vform", vform);
	vforms.push(vform);
	return vform;
}

//超链接改造为ajax异步页面加载
function linkAjaxInit($link){
	$link.click(function(){
		var _this = $link[0];
		
		var href = $link.attr("href");
		var targetFor = $link.attr("targetFor");
		var okDoit = $link.attr("okDoit");
		var confirmTip = $link.attr("confirmTip");
		var waitTipType = $link.is("[waitTipType]") ? $link.attr("waitTipType") : "loading"; //等待提醒类型
		var waitTip = $link.is("[waitTip]") ? $link.attr("waitTip") : $.i18n.prop("qsd2"); //等待提醒字样
		if (okDoit) okDoit = okDoit.replace(/\bthis\b/g, "_this");
		
		var _params = $link.attr("params"); 
		//清除浏览器缓存
		if (_params){
			_params += "&v="+Math.random();
		} else {
			_params = "v="+Math.random();
		}
		
		var _type = _params ? "post" : "get"; //等待提醒字样
		
		if(/^\s*javascript:/.test(href)){
			if (confirmTip) {
				zConfirm(confirmTip, function(){
					//成功处理
					if (okDoit) {
						eval(okDoit);
						return false;
					}
				});
			} else {
				//成功处理
				if (okDoit) {
					eval(okDoit);
					return false;
				}
			}
			return false;
		}
		
		var exeLinkGo = function(){
			doLoading(waitTipType, waitTip);
			var ajacComm = $.ajax({
				url:href,
				type: _type,
				data: _params,
				timeout: linkTimout,
				complete:function(retData){
					doneLoading(waitTipType, waitTip);
					if(retData.status=="0" && retData.statusText =="timeout"){//通讯超时
						//超时处理
						ajacComm.abort();
						alertTimeoutErr();
						return false;
					}
					if(retData.status=="404"){
						alert404Err()
						return false;
					}
					if(retData.status=="500"){
						alert500Err();
						return false;
					}
					if(retData.status=="0"){
						zError("未知错误： 响应状态["+retData.status+"]，描述：" + retData.statusText);
						return false;
					}
				},
				success:function(data){
					doneLoading(waitTipType, waitTip);
					var re = /^\s*\{/;
					if(re.test(data)){
						try{
							var retData =  eval("("+data+")");
							if(retData.status=="n"){
								zError("错误码："+retData.errCode+"<br/>错误消息："+retData.info);
								return false;
							} else if(retData.status!="y") {
								zError("未知错误： 响应状态["+retData.status+"]，描述：" + retData.statusText);
								return false;
							}
							//成功处理
							if (okDoit) {
								eval(okDoit);
								return true;
							}
							return true;
						} catch(e) {}
					}
					//成功处理
					if (okDoit) {
						eval(okDoit);
						return true;
					}
					//局部加载
					if (targetFor) {
						$(targetFor).html(retData.responseText);
						pageInit($(targetFor));
						return true;
					}
					$("body").html(retData.responseText);
					return true;
				}
			});
		}
		
		if (confirmTip) {
			zConfirm(confirmTip, exeLinkGo);
		} else {
			exeLinkGo();
		}
		return false;
	});
}

function doLoading(loadingType, loadingMsg) {
	if (loadingType == "loading" && loadingMsg) {
		loadingShow(loadingMsg);
	} else if (loadingType == "function" && loadingMsg) {
		eval(loadingMsg);
	}
}

function doneLoading(loadingType, loadingMsg) {
	if (loadingType == "loading" && loadingMsg) {
		loadingClose();
	} else if (loadingType == "function" && loadingMsg) {
		eval(loadingMsg);
	}
}

function beforeCheck(curform){
	return true;
}

function tiptype_login(msg,o,cssctl){
	if(o.obj.is("form")){
		return false;
	}
	if(o.type==2){
		o.obj.css({'background-color':'#ffffff'});
	}else{
		o.obj.css({'background-color':'#ffe7e7'});
		if(o.curform.isSubmit){
			o.curform.isSubmit = null; 
			oTip(o.obj[0], msg);
		}
	}
}

function oTip(obj , content){
	if(dialog){
		return dialog({
		    content: content,
		    quickClose: true// 点击空白处快速关闭
		}).show(obj);
	} else {
		alert(content);
	}
}

function zTip(content, times) {
	if (artDialog){
		return artDialog.tips(content, times);
	} else {
		alert(content);
	}
}

function zShow(content, title, divid){
	var dialog = artDialog({
		id: 'zShow',
		title: title,
		fixed: true,
		lock: true,
		content: content,
		yesFn: false
	});
	
	$(divid+" .close").click(function(){
		dialog.close();
	});
	
	linkAjaxsInit($(divid));
	validformsInit($(divid));
}

/*
function zShowUrl(url, title, divid){
	var waitTipType = "loading"; //等待提醒类型
	var waitTip = "正在加载，请稍等..."; //等待提醒字样
	doLoading(waitTipType, waitTip);
	var ajacComm = $.ajax({
		url: url,
		type: "post",
		timeout: linkTimout,
		complete:function(retData){
			doneLoading(waitTipType, waitTip);
			if(retData.status=="0" && retData.statusText =="timeout"){//通讯超时
				//超时处理
				ajacComm.abort();
				alertTimeoutErr();
				return false;
			}
			if(retData.status=="404"){
				alert404Err()
				return false;
			}
			if(retData.status=="500"){
				alert500Err();
				return false;
			}
			if(retData.status=="0"){
				zError("未知错误： 响应状态["+retData.status+"]，描述：" + retData.statusText);
				return false;
			}
		},
		success:function(data){
			doneLoading(waitTipType, waitTip);
			zShow(data, title, divid);
			return true;
		}
	});
}*/

function zSucceed(content, closeFn){
	if (artDialog){
		return artDialog.succeed(content, closeFn);
	} else {
		alert(content);
		closeFn();
	}
}

function zError(content, closeFn){
	if (artDialog){
		return artDialog.error(content, closeFn);
	} else {
		alert(content);
		closeFn();
	}
}

function zAlert(content, closeFn){
	if (artDialog){
		return artDialog.alert(content, closeFn);
	} else {
		alert(content);
		closeFn();
	}
}

function zConfirm(content, okFun, cancelFun){
	if (artDialog){
		return artDialog.confirm(content, okFun, cancelFun);
	} else {
		if ( confirm(content) ) {
			okFun();
		} else {
			cancelFun();
		}
	}
}

//加载框
function zLoading(msg){
	if ( artDialog) {
		return artDialog.waiting(msg);
	} else if (mDialog) {
	    return mDialog('<div class="loadDiv"><div class="img"></div><p>'+msg+'</p></div>', {
	        id: 'art_loading',
	        okBtn: { hide: true},
	        isLoading: true,
	        mask: true
	    });
	}
}

//操时提示
function alertTimeoutErr(){
	zError("请求超时，请检查网络后重试！");
}
//404错误提示
function alert404Err(){
	zError("您访问的页面不存在！");
}
//500错误提示
function alert500Err(){
	zError("发生500错误！");
}

//加载显示
var loading = null;
function loadingShow(msg){
	if (!msg) msg = $.i18n.prop("qsd2");
    loading = zLoading(msg);
}
function loadingClose(){
    if(loading){
        loading.close();
        loading = null;
    }
}