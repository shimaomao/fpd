
$.Tipmsg.w["zh"] = "请输入中文字符！";
$.Tipmsg.w["zh6-18"] = "请输入6到18个中文字符！";
$.Tipmsg.w["a"] = "请输入字母！";
$.Tipmsg.w["a6-16"] = "请输入6到16位字母！";
$.Tipmsg.w["an"] = "请输入字母或数字！";
$.Tipmsg.w["an6-16"] = "请输入6到16字母或数字！";
$.Tipmsg.w["w"] = "请输入字母或数字！";
$.Tipmsg.w["w6-16"] = "请输入6到16字母或数字！";
$.Tipmsg.w["amount"] = "请输入金额格式！";
$.Tipmsg.w["amount1-4"] = "请输入1到4位整数的金额格式！";
$.Tipmsg.w["m"] = "请填写手机号码！";


$.Datatype["zh"]=/^[\u4E00-\u9FA5\uf900-\ufa2d]+$/;
$.Datatype["zh6-18"]=/^[\u4E00-\u9FA5\uf900-\ufa2d]{6,18}$/;
$.Datatype["a"]=/^[a-zA-Z]+$/;
$.Datatype["a6-16"]=/^[a-zA-Z]{6,16}$/;
$.Datatype["an"]=/^[a-zA-Z\d]+$/;
$.Datatype["an6-16"]=/^[a-zA-Z\d]{6,16}$/;
$.Datatype["w"]=/^\w+$/;
$.Datatype["w6-16"]=/^\w{6,16}$/;
$.Datatype["amount"]=/^\d+(\.\d\d?)?$/;
$.Datatype["amount1-4"]=/^\d{1,4}(\.\d\d?)?$/;
$.Datatype["m"]=/^1[0-9]{10}$/;

