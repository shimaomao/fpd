$(function(){

    $(".vform").each(function(){
        validformInit($(this));
    });
});

function pageInit($div){
    validformsInit($div);
}

function validformsInit($div){
    $div.find(".vform").each(function(){
        validformInit($(this));
    });
}

var vforms = [];
var linkTimout = 600000;//超链接超时时长
var formTimout = 600000;//表单提交超时时长

$.extend($.Datatype,{
        "idcard":function(gets,obj,curform,datatype) {
            //该方法由佚名网友提供;
            var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1];// 加权因子;
            var ValideCode = [1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2];// 身份证验证位值，10代表X;

            if (gets.length == 15) {
                return isValidityBrithBy15IdCard(gets);
            } else if (gets.length == 18) {
                var a_idCard = gets.split("");// 得到身份证数组
                if (isValidityBrithBy18IdCard(gets) && isTrueValidateCodeBy18IdCard(a_idCard)) {
                    return true;
                }
                return false;
            }
            return false;

            function isTrueValidateCodeBy18IdCard(a_idCard) {
                var sum = 0; // 声明加权求和变量
                if (a_idCard[17].toLowerCase() == 'x') {
                    a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作
                }
                for (var i = 0; i < 17; i++) {
                    sum += Wi[i] * a_idCard[i];// 加权求和
                }
                valCodePosition = sum % 11;// 得到验证码所位置
                if (a_idCard[17] == ValideCode[valCodePosition]) {
                    return true;
                }
                return false;
            }

            function isValidityBrithBy18IdCard(idCard18) {
                var year = idCard18.substring(6, 10);
                var month = idCard18.substring(10, 12);
                var day = idCard18.substring(12, 14);
                var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
                // 这里用getFullYear()获取年份，避免千年虫问题
                if (temp_date.getFullYear() != parseFloat(year) || temp_date.getMonth() != parseFloat(month) - 1 || temp_date.getDate() != parseFloat(day)) {
                    return false;
                }
                return true;
            }

            function isValidityBrithBy15IdCard(idCard15) {
                var year = idCard15.substring(6, 8);
                var month = idCard15.substring(8, 10);
                var day = idCard15.substring(10, 12);
                var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
                // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
                if (temp_date.getYear() != parseFloat(year) || temp_date.getMonth() != parseFloat(month) - 1 || temp_date.getDate() != parseFloat(day)) {
                    return false;
                }
                return true;
            }
        },
        'checkMsg':function(gets,obj,curform,datatype) {
            console.log( gets );
            var text = '本人已阅读全部申请材料，充分了解并清楚知晓该秒收贷款服务的相关信息，愿意遵守领用协议的各项规则';
            if( gets == text ){
                console.log('true');
                return true
            }else{
                console.log('false');
                return false
            }
        },
      
        'checkBank':function(gets,obj,curform,datatype) {
                if( gets === '') return false;
                var lastNum=gets.substr(gets.length-1,1);//取出最后一位（与luhm进行比较）

                var first15Num=gets.substr(0,gets.length-1);//前15或18位
                var newArr=new Array();
                for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
                    newArr.push(first15Num.substr(i,1));
                }
                var arrJiShu=new Array();  //奇数位*2的积 <9
                var arrJiShu2=new Array(); //奇数位*2的积 >9

                var arrOuShu=new Array();  //偶数位数组
                for(var j=0;j<newArr.length;j++){
                    if((j+1)%2==1){//奇数位
                        if(parseInt(newArr[j])*2<9)
                            arrJiShu.push(parseInt(newArr[j])*2);
                        else
                            arrJiShu2.push(parseInt(newArr[j])*2);
                    }
                    else //偶数位
                        arrOuShu.push(newArr[j]);
                }

                var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
                var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
                for(var h=0;h<arrJiShu2.length;h++){
                    jishu_child1.push(parseInt(arrJiShu2[h])%10);
                    jishu_child2.push(parseInt(arrJiShu2[h])/10);
                }

                var sumJiShu=0; //奇数位*2 < 9 的数组之和
                var sumOuShu=0; //偶数位数组之和
                var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
                var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
                var sumTotal=0;
                for(var m=0;m<arrJiShu.length;m++){
                    sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
                }

                for(var n=0;n<arrOuShu.length;n++){
                    sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
                }

                for(var p=0;p<jishu_child1.length;p++){
                    sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
                    sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
                }
                //计算总和
                sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);

                //计算Luhm值
                var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;
                var luhm= 10-k;

                if(lastNum==luhm){
                    return true;
                }
                else{
                    return false;
                }
            }
});

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












function zError(content, closeFn){
    if (artDialog){
        return artDialog.error(content, closeFn);
    } else {
        alert(content);
        closeFn();
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