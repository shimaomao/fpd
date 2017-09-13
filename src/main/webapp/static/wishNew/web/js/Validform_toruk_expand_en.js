$.Tipmsg.w["zh"] = "Please fill in Chinese character!";
$.Tipmsg.w["zh6-18"] = "Please fill in 6-18 Chinese character!";
$.Tipmsg.w["a"] = "Please fill in letters !";
$.Tipmsg.w["a6-16"] = "Please fill in 6-16 letters!";
$.Tipmsg.w["an"] = "Please fill in letters or numbers!";
$.Tipmsg.w["an6-16"] = "Please enter at 6-16 letters or numbers!";
$.Tipmsg.w["w"] = "Please fill in letters or numbers!";
$.Tipmsg.w["w6-16"] = "Please enter at 6-16 letters or numbers!";
$.Tipmsg.w["amount"] = "Digit only!";
$.Tipmsg.w["amount1-4"] = "1-4 digit(s) only!";
$.Tipmsg.w["m"] = "Please fill in your mobilephone number!";

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

