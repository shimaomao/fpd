//jquery获取选中单选框或复选框的值
function getCheckValue(tagName){  
	var chk_value =[];    
	$("input[name='"+tagName+"']:checked").each(function(){    
	   chk_value.push($(this).val());    
	});   
	return chk_value;    
}    



//显示/隐藏层
function showDiv(div_id,op){
 if(op=="display"){
      $("#"+div_id).show();
 }else{
   	$("#"+div_id).hide();
 }
}


//清除层提示消息
function cleanDivMsg(div_id){
   $("#"+div_id).html("");
}  


//动态控制约定还本付息日
function initPayDay(payDay) {	
	    var flag = false;//用于判断是否是更新状态
		var opt = document.getElementById("payDay");
		if (opt == null) {
		   return;
		}
		opt.options.length = 0;//清空	
		var day;	  
	    for (var i = 0; i < 31; i++) {
	         if (i < 9) {
	           day = "0" + (i + 1);
	         } else {
	           day = i + 1;
	         }
	         if (payDay == day) {// 更新状态
	         	opt[i] = new Option(day, day, true, true);
	         	flag = true;
	         } else {
	         	if (flag==false && day==20) {// 银行默认20日是付息日
	    	        opt[i] = new Option(day, day, true, true);
	    	    }else{
	    	        opt[i] = new Option(day, day);
	    	    }
	        }
	   }
}	
/**
 * 显示 "生成还款计划" 按钮
 * @param type 还款方式:  '等额本息1','等额本金2','按期收息到期还本3'
 * @param   periodType (1日，2月，3年)
 * @return
 */
function initToPayUI(type) {
	if(type == "1" || type == "2"){//还款方式是：等额本金   或   等额本息
		$("input[name='periodType']").each(function(){
			if($(this).val() == "2"){
		    	$(this).attr("checked",true);
			}
			$(this).attr("disabled", "disabled");
		});      //设置不可用
		$("input[name='payOptions']").each(function(){
		    $(this).attr("checked",false);
			$(this).attr("disabled", "true");
		});      //设置不可用
		
	}else if(type == "3"){//还款方式是：按期收息，到期还本
		$("input[name='periodType']").each(function(){
			$(this).removeAttr("disabled");
		});      //设置可用
		$("input[name='payOptions']").each(function(){
			$(this).removeAttr("disabled");
		});      //设置可用
	}
}	    


//禁用form表单中所有的选项
function disableForm(formId,isDisabled) { 
    
    var attr="disable"; 
    if(!isDisabled){ 
       attr="enable"; 
    } 
    $("form[id='"+formId+"'] :text").attr("disabled",isDisabled); 
    $("form[id='"+formId+"'] textarea").attr("disabled",isDisabled); 
    $("form[id='"+formId+"'] select").attr("disabled",isDisabled); 
    $("form[id='"+formId+"'] :radio").attr("disabled",isDisabled); 
    $("form[id='"+formId+"'] :checkbox").attr("disabled",isDisabled); 
     
    //禁用jquery easyui中的下拉选（使用input生成的combox） 
 
    $("#" + formId + " input[class='combobox-f combo-f']").each(function () { 
        if (this.id) {alert("input"+this.id); 
            $("#" + this.id).combobox(attr); 
        } 
    }); 
     
    //禁用jquery easyui中的下拉选（使用select生成的combox） 
    $("#" + formId + " select[class='combobox-f combo-f']").each(function () { 
        if (this.id) { 
        alert(this.id); 
            $("#" + this.id).combobox(attr); 
        } 
    }); 
     
    //禁用jquery easyui中的日期组件dataBox 
    $("#" + formId + " input[class='datebox-f combo-f']").each(function () { 
        if (this.id) { 
        alert(this.id) 
            $("#" + this.id).datebox(attr); 
        } 
    }); 
    
    //禁用treeSelect中的search 
    $("#" + formId + " i[class='icon-search']").each(function () { 
    	if(isDisabled && $(this).parent("a")){
    		$(this).parent("a").removeAttr('onclick');
    	}
    }); 
}

function initForm(formId, isDisabled) {
	var attr = "disable";
	if (!isDisabled) {
		attr = "enable";
	}
	var inputstr="enable";
	$("form[id='" + formId + "'] :text").attr("disabled", isDisabled);
	$("form[id='" + formId + "'] textarea").attr("disabled", isDisabled);
	$("form[id='" + formId + "'] select").attr("disabled", isDisabled);
	$("form[id='" + formId + "'] :radio").attr("disabled", isDisabled);
	$("form[id='" + formId + "'] :checkbox").attr("disabled",
			isDisabled);
	//禁用jquery easyui中的下拉选（使用input生成的combox） 
	$("#" + formId + " input[class='combobox-f combo-f']").each(
			function() {
				if (this.id) {
					alert("input" + this.id);
					$("#" + this.id).combobox(attr);
				}
			});
	
	//禁用jquery easyui中的下拉选（使用select生成的combox） 
	$("#" + formId + " select[class='combobox-f combo-f']").each(
			function() {
				if (this.id) {
					alert(this.id);
					$("#" + this.id).combobox(attr);
				}
			});

	//禁用jquery easyui中的日期组件dataBox 
	$("#" + formId + " input[class='datebox-f combo-f']").each(
			function() {
				if (this.id) {
					alert(this.id)
					$("#" + this.id).datebox(attr);
				}
			});

	//禁用treeSelect中的search 
	$("#" + formId + " i[class='icon-search']").each(function() {
		if (isDisabled && $(this).parent("a")) {
			$(this).parent("a").removeAttr('onclick');
		}
	});
	//禁用jquery easyui中的下拉选（使用input生成的combox） 
	$("#" + formId + " input[class='input-xlarge number required']").each(
			function() {
				if (this.id) {
					alert("input" + this.id);
					//$("#" + this.id).attr("disabled",false);
					$("#" + this.id).removeAttr("disabled");
				}
			});
};

//
//
//function showjBox(title, url){
//	top.$.jBox.open(
//			"iframe:"+url, title,
//			$(window).width()-100,//$(top.document).width()-100,
//			$(top.document).height()-150,//$(top.document).height()-100, 
//			{
//				buttons : {
//					//"刷新" : "refresh",
//					"关闭" : true
//				},
//				bottomText : "",
//				submit : function(v, h, f) {
//					var ifrWin = h.find("iframe")[0].contentWindow;
//					if (v == "refresh") {
//						ifrWin.location.reload(true);
//						//ifrWin.clearAssign();
//						return false;
//					}
//				},
//				loaded : function(h) {
//					$(".jbox-content", top.document).css("overflow-y","hidden");
//				}
//			});
//}


function showjBox(title, url , width , heigth){
	
	var new_width = width;
	var new_heigth = heigth;
	if(width==''||typeof(width)=='undefined'){
		new_width  = $(window).width()-100;
		new_heigth = $(top.document).height()-150;
	}
	console.log(new_width+"========="+new_heigth);
	top.$.jBox.open(
			"iframe:"+url, title,new_width,new_heigth, 
			{
				buttons : {
					"关闭" : true
				},
				bottomText : "",
				submit : function(v, h, f) {
					var ifrWin = h.find("iframe")[0].contentWindow;
					if (v == "refresh") {
						ifrWin.location.reload(true);
						return false;
					}
				},
				loaded : function(h) {
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
}

