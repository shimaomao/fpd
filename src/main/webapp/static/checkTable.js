var ckBox = {
	"ckAll":{
		"fid":"#dids",
		"selName":"selAll",
		"selClass":".selAll"
	},"ckDQ":{
		"fid":"#dqIds",
		"fname":"dqIds",
		"fclass":".dqIds",
		"selName":"selAllDQ",
		"selClass":".selAllDQ"
	},"ckDZ":{
		"fid":"#dzIds",
		"fname":"dzIds",
		"fclass":".dzIds",
		"selName":"selAllDZ",
		"selClass":".selAllDZ"
	},"ckDH":{
		"fid":"#dhIds",
		"fname":"dhIds",
		"fclass":".dhIds",
		"selName":"selAllDH",
		"selClass":".selAllDH"
	},"btnSubmit":{
		
	}
};
function init(){
	checkGroup();
    bindEv();
}

function toSubmit(){
   var daiQian = getCheckValue(ckBox.ckDQ.fname);
   var daiZhong = getCheckValue(ckBox.ckDZ.fname);
   var daiHou = getCheckValue(ckBox.ckDH.fname);
   $(ckBox.ckAll.fid).val(daiQian+","+daiZhong+","+daiHou);
   document.forms[0].submit();
}

function bindEv(){
	$(ckBox.ckDQ.fclass).click(function(){
		checkGroup();
	});
	$(ckBox.ckDZ.fclass).click(function(){
		checkGroup();
	});
	$(ckBox.ckDH.fclass).click(function(){
		checkGroup();
	});

	$(ckBox.ckDQ.selClass).click(function(){
		if($(this).prop("checked")){
			$(ckBox.ckDQ.fclass).attr("checked", true);
		}else{
			$(ckBox.ckDQ.fclass).attr("checked", false);
		}
		checkGroup();
	});
	$(ckBox.ckDZ.selClass).click(function(){
		if($(this).prop("checked")){
			$(ckBox.ckDZ.fclass).attr("checked", true);
		}else{
			$(ckBox.ckDZ.fclass).attr("checked", false);
		}
		checkGroup();
	});
	$(ckBox.ckDH.selClass).click(function(){
		if($(this).prop("checked")){
			$(ckBox.ckDH.fclass).attr("checked", true);
		}else{
			$(ckBox.ckDH.fclass).attr("checked", false);
		}
		checkGroup();
	});
	$(ckBox.ckAll.selClass).click(function(){
		if($(this).prop("checked")){
			$(ckBox.ckDQ.fclass).attr("checked", true);
			$(ckBox.ckDZ.fclass).attr("checked", true);
			$(ckBox.ckDH.fclass).attr("checked", true);
		}else{
			$(ckBox.ckDQ.fclass).attr("checked", false);
			$(ckBox.ckDZ.fclass).attr("checked", false);
			$(ckBox.ckDH.fclass).attr("checked", false);
		}
		checkGroup();
	});
}
//jquery获取选中单选框或复选框的值
function getCheckValue(tagName){  
	var chk_value =[];    
	$("input[name='"+tagName+"']:checked").each(function(){    
	   chk_value.push($(this).val());    
	});   
	return chk_value;    
}
/**是否选中所有**/
function isHasCheckAll(name){
	var isCheck = true;
	var checkboxs = $("input[name='"+name+"']:checkbox");
	for(var i=0; i<checkboxs.length; i++){
		if(checkboxs[i].checked == false){
			isCheck = false;
		}
	}
	return isCheck;
}

function isCheckDQ(){ return isHasCheckAll(ckBox.ckDQ.fname); }
function isCheckDZ(){ return isHasCheckAll(ckBox.ckDZ.fname); }
function isCheckDH(){ return isHasCheckAll(ckBox.ckDH.fname); }
function isCheckAll(){
	var isCheckAll = false;
	if(!isCheckDQ()){ return isCheckAll; }
	if(!isCheckDZ()){ return isCheckAll; }
	if(!isCheckDH()){ return isCheckAll; }
	return true;
}
function checkGroup(){
	if(isCheckDQ()){$(ckBox.ckDQ.selClass).attr("checked", true); }else{ $(ckBox.ckDQ.selClass).attr("checked", false); }
	if(isCheckDZ()){ $(ckBox.ckDZ.selClass).attr("checked", true); }else{ $(ckBox.ckDZ.selClass).attr("checked", false); }
	if(isCheckDH()){ $(ckBox.ckDH.selClass).attr("checked", true); }else{ $(ckBox.ckDH.selClass).attr("checked", false); }
	if(isCheckAll()){ $(ckBox.ckAll.selClass).attr("checked", true); }else{ $(ckBox.ckAll.selClass).attr("checked", false); }
}