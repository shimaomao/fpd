/**
 */

var loanContractId;

function createRepayPlansGrid(settings, div, gridParams) {
	//console.info('===还款计划===');
	editContent = settings.editContent;//是否可编辑，是：true；否：false
	var lastsel;
	loanContractId = settings.businessId;
	$(div).html("");
	var grid = $("<table id=\"grid_" + $(div).attr("id") + "\"></table>");
	var prop = {
		jqg : "#showplansDiv",
		pager : "#jqGridPager",
		lastSelId : null
	};
	var param = {
		//caption : "还款计划",
		modal : false,
		//url : ctx + "/repayplan/tRepayPlan/ajaxFormsList?loanContractId=" + loanContractId,
		url : ctx + "/repayplan/tRepayPlan/ajaxGetRepayPlan",
		postData : {
			dealType : settings.dealType,
			businessId : settings.businessId,
			businessType : settings.businessType,  //apply|extend|earlyrepay...
			amount : settings.amount,
			loanRate : settings.loanRate,
			loanRateType : settings.loanRateType,//add by srf #3121
			loanPeriod : settings.loanPeriod,
			loanDate : settings.loanDate,
			payType : settings.payType,//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
			periodType : settings.periodType,//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
			payDay : settings.payDay,//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
			payOptions : settings.payOptions,//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
			payPrincipalDate : settings.payPrincipalDate,//#3371
			ifRealityDay : settings.ifRealityDay //大小月
		},
		//height : 300,
		height: 'auto',
		autowidth : true,
		shrinkToFit : true,
		jsonReader : {
			repeatitems : false,
			id : "id"
		},
		colNames : [ '', /*'', */'期数', '还款到账日', '应还本金(元)', '应还利息(元)', '计息开始日期', '计息结束日期'/*, '状态'*/ ],
		colModel : [ {
			name : "id",
			index : "id",
			width : 0,
			hidden : true,
			search : false,
			sortable : false,
			editable : false
		/*}, {
			name : "loanContractId",
			index : "loanContractId",
			width : 0,
			hidden : true,
			search : false,
			sortable : false,
			editable : true*/
		}, {
			name : "num",
			index : "num",
			label : "期数",
			width : 60,
			hidden : false,
			align : "center",
			search : false,
			sortable : false,
			editable : true,
			editrules : {
				edithidden : true,
				required : true,
				integer : true,
				minValue : 1,
				maxValue : 100
			}
		}, {
			name : "accountDate",
			index : "accountDate",
			label : "还款到账日",
			width : 80,
			hidden : false,
			align : "center",
			search : false,
			sortable : false,
			formatter : "date",
			editoptions : {
				sopt : [ 'gt', 'kt' ],
				dataInit : function(element) {
					$(element).click(function() {
						WdatePicker({
							onpicked : function() {
								$(element).change();
							}
						});
					})
				},
				editrules : {
					edithidden : true,
					required : true,
					date : true
				}
			},
			editable : true
		}, {
			name : "principal",
			index : "principal",
			label : "应还本金(元)",
			width : 100,
			hidden : false,
			search : false,
			sortable : false,
			editable : true,
			editable : true,
			align : "center",
			formatter : 'currency',
			editable : true,
			editoptions : {
				money : true,
				maxlength : '18'
			},
			editable : true,
			editrules : {
				edithidden : true,
				required : true,
				number : true,
				minValue : 0
			}
		}, {
			name : "interest",
			index : "interest",
			label : "应还利息(元)",
			width : 100,
			hidden : false,
			search : false,
			sortable : false,
			editable : true,
			editable : true,
			align : "center",
			formatter : 'currency',
			editable : true,
			editoptions : {
				money : true,
				maxlength : '18'
			},
			editable : true,
			editrules : {
				edithidden : true,
				required : true,
				number : true,
				minValue : 0
			}
		}, {
			name : "startDate",
			index : "startDate",
			label : "计息开始日期",
			width : 80,
			hidden : false,
			align : "center",
			search : false,
			sortable : false,
			formatter : "date",
			editoptions : {
				sopt : [ 'gt', 'kt' ],
				dataInit : function(element) {
					$(element).click(function() {
						WdatePicker({
							onpicked : function() {
								$(element).change();
							}
						});
					})
				},
				editrules : {
					edithidden : true,
					required : true,
					date : true
				}
			},
			editable : true
		}, {
			name : "endDate",
			index : "endDate",
			label : "计息结束日期",
			width : 80,
			hidden : false,
			align : "center",
			search : false,
			sortable : false,
			formatter : "date",
			editoptions : {
				sopt : [ 'gt', 'kt' ],
				dataInit : function(element) {
					$(element).click(function() {
						WdatePicker({
							onpicked : function() {
								$(element).change();
							}
						});
					})
				},
				editrules : {
					edithidden : true,
					required : true,
					date : true
				}
			},
			editable : true
		}/*, {
			name : "status",
			index : "status",
			label : "状态",
			width : 60,
			hidden : false,
			search : false,
			align : "center",
			sortable : false,
			editable : true,
			edittype : "select",
			formatter : 'select',
			editoptions : {
				value : "0:未还;1:未结清;2:已结清"
			}

		}*/
		],
		rowNum : 'all',
		pager : '#jqGridPager',
		sortname : 'id',
		pgbuttons : false,
		pginput : false,
		recordtext : "共&nbsp;{2}&nbsp;条&nbsp;&nbsp;&nbsp;",
		viewrecords : true,
		footerrow : true,
		gridComplete : function completeMethod() {
			var sum_principal = $("#grid_showplansDiv").getCol('principal', false, 'sum');
			var sum_interest = $("#grid_showplansDiv").getCol('interest', false, 'sum');
			$("#grid_showplansDiv").footerData('set', {
				"num" : '合计',
				principal : sum_principal,
				interest : sum_interest
			});
		},
		sortorder : "desc",
		autowidth : true,
		editurl : ctx + "/repayplan/tRepayPlan/ajaxRepayPlanForms?loanContractId="+loanContractId,
		datatype : "json",
		mtype : 'POST',
		//caption : "还本计划",
		reloadAfterSubmit : true,
		ondblClickRow : function(id) {
			var taid = "grid_" + $(div).attr("id");
			if (id && id !== lastsel) {
				jQuery('#' + taid).jqGrid('restoreRow', lastsel);
				jQuery('#' + taid).jqGrid('editRow', id, true);
				lastsel = id;
			}
		},
		afterSubmit : function(response, postdata){
			var r=eval('('+response.responseText+')');
			alert(r.msg);
			return r.msg;
		}
	}

	$(div).append(grid);
	grid.jqGrid(param);

	
	if(editContent){
		//console.info("可以编辑");
		$(grid).jqGrid('navGrid', "#jqGridPager", {
			search: false,
			refresh: true,
			refreshtext:"刷新 ",
			refreshtitle :"刷新",
			edit: false, 
			//edittext:"修改 ",
			//edittitle :"修改",
			add: false, 
			//addtext:"添加 ",
			//addtitle :"添加",
			del: true, 
			deltext:"删除 ",
			deltitle :"删除"
		},{ // 添加时
			left : 300,
			height : 450,
			width : 650,
			align : "center",
			delCaption : "删除信息",
			// recreateForm: true,
			checkOnUpdate : true,
			checkOnSubmit : true,
			closeAfterAdd : true,
			afterSubmit : function(response, postdata){
				var r=eval('('+response.responseText+')');
				alert(r.msg);
				return r.msg;
			},
			errorTextFormat : function(data) {
				return 'Error: ' + data.responseText
			}/*,successfunc: function (response) {
            //alert(response.responseText)//response是ajax原始对象，没有ajax基础自己补习下
            var r=eval('('+response.responseText+')');
			alert(r.msg);
			return r.msg;
        }*/
		}).trigger("reloadGrid");
		
		$(grid).jqGrid('inlineNav', "#jqGridPager",
				{ 
			search: false,
			refresh: true,
			refreshtext:"刷新 ",
			refreshtitle :"刷新",
			edit: true,
			edittext:"修改 ",
			edittitle :"修改",
			save: true,
			savetext:"保存",
			savetitle :"保存",
			cancel: true,
			canceltext:"取消",
			canceltitle :"取消",
			add: true,
			addtext:"添加 ",
			addtitle :"添加",
			del: true,
			deltext:"删除 ",
			deltitle :"删除",
			addParams : {
				useFormatter : false,
				position : 'last',
				useDefValues : true,
				addRowParams: {//点击添加的时候弹出
					position: "afterSelected",
					// the parameters of editRow used to edit new row
					//keys: true,//设置为true可以使用 [Enter]保存数据或者[Esc] 取消编辑
					successfunc: function (response) {//传递给editRow方法用的
						//alert(response.responseText)
						var r=eval('('+response.responseText+')');
						alert(r.msg);
						$(grid).trigger("reloadGrid");
						return r.msg;
					}
				}
			},editParams:{
				successfunc: function (response) {
					//alert(response.responseText)
					var r=eval('('+response.responseText+')');
					alert(r.msg);
					$(grid).trigger("reloadGrid");
					return r.msg;
				}
			},saveParams:{
				successfunc: function (response) {
					//alert(response.responseText)//response是ajax原始对象，没有ajax基础自己补习下
					var r=eval('('+response.responseText+')');
					alert(r.msg);
					$(grid).trigger("reloadGrid");
					return r.msg;
				},errorfunc: function(rowid, res){ 
					console.log(rowid); console.log(res); 
					alert("保存失败");
				},afterrestorefunc: function(rowid, res){ 
					console.log(rowid); console.log(res); 
					alert("保存失败!");
				}
			}
				}
		).trigger("reloadGrid");
	}else{
		//console.info("禁止编辑");
		$(grid).jqGrid('navGrid', "#jqGridPager", {
			search: false,
			refresh: false,
			edit: false, 
			add: false, 
			del: false
		});
	}

	/** 显示消息* */
	function reloadGrid(grid) {
		grid.jqGrid("setGridParam", {
			search : true
		}).trigger("reloadGrid");
	}

	/** 显示消息* */
	function showMessage(msg) {
		$("#messageBox").text(msg);
	}

	return grid;
}

/**
 * 根据还款计划数据生成隐藏表单
 * @param index 数组下标	
 * @param planData	
 */
function getPlansInputHtml(index,planData){
	return '<input type="hidden" name="repayPlanList['+index+'].interest" value="'+planData.interest+'" />'
			+ '<input type="hidden" name="repayPlanList['+index+'].principal" value="'+planData.principal+'" />'
			+ '<input type="hidden" name="repayPlanList['+index+'].num" value="'+planData.num+'" />'
			+ '<input type="hidden" name="repayPlanList['+index+'].accountDate" value="'+planData.accountDate+'" />'
			+ '<input type="hidden" name="repayPlanList['+index+'].startDate" value="'+planData.startDate+'" />'
			+ '<input type="hidden" name="repayPlanList['+index+'].endDate" value="'+planData.endDate+'" />'
}


/**
 * 获取等额**的还款计划（等额本息、等额本金）
 *
 * @param amount 		贷款金额
 * @param loanRate		利率 
 * @param loanPeriod	期限（正整数） 
 * @param loanDate  	放款日期
 * @param payType  		还款方式	1|2|3 = 等额本息|等额本金|按月付息到期还款
 * return [{
 *  		principal : 计划收入本金
 *  		interest  :  计划收入利息
 *  		num : 第几笔
 *  		accountDate : 计划到账日期
 *  		startDate : 计息开始时间
 *  		endDate : 计息结束时间
 *  	}]
 */
function getEqualPayPlans(amount,loanRate,loanPeriod,loanDate,payType) {
	var plans = [];
	var payPrincipalDate = getPayPrincipalDate(payType,"2",loanDate,loanPeriod);
	var startDate;//计息开始时间
	var endDate;	//计息结束时间
	var remainPrincipal = amount;//剩余本金
	
	if(payType == 1){
		//等额本息每一期要归还的本金 : a*[i*(1+i)^n]/[(1+i)^n-1] （注 : a : 贷款本金 ，i : 贷款利率（月）， n : 贷款期数 ）
		var monthRate = (parseFloat(loanRate*3) / 100).toFixed(4);
		month_to_pay_principal = (amount * ((monthRate * Math.pow(1 + Number(monthRate), loanPeriod)) 
								/ (Math.pow(1 + Number(monthRate), loanPeriod) - 1))).toFixed(2);
	}else if(payType == 2){
		//等额本金每一期要归还的本金 = 总本金/还款期数	     
		month_to_pay_principal = (amount/loanPeriod).toFixed(2);
	}
	
	var loanDateObj = NewDate(loanDate);
	//每期还款日，等额本息、等额本金的每期还款日固定为放款日期对应日
	var payDay = loanDateObj.format("dd");
	//首期计息开始时间
	startDate = loanDate;
	endDate = getEndDateByStartDate(payType,"2",startDate,payDay);
	for(var i=0;i<loanPeriod;i++){
		if(compareDate(payPrincipalDate,endDate) < 0){
			//计算得出的计息结束日期大于还本金日期时，结束日期 = 还本金日期
			endDate = payPrincipalDate;
		}
		var interest = countInterest(remainPrincipal,loanRate,startDate,endDate);//本期利息
		var principal;
		if(payType == 1){
			//等额本息还款本金
			principal = month_to_pay_principal - interest;
		}else if(payType == 2){
			//等额本金还款本金
			principal = month_to_pay_principal;
		}
		if(i == (loanPeriod-1)) {
			//最后一期还的本金 = 剩余的所有本金
			principal = remainPrincipal;
		}
		var planData={
				principal : principal,
				interest : interest,
				num : (i+1),
				accountDate : endDate,
				startDate : startDate,
				endDate : endDate
		};
		
		plans.push(planData);
		if(i < (loanPeriod - 1)){
			//下一期的开始时间  = 本期计息结束时间+1天
			startDate = addDate(endDate, 0, 0, 1, 0);
			//下一期的结束时间  = 本期计息结束时间+1个月
			endDate = getEndDateByStartDate(payType,"2",startDate,payDay);
			//下一期剩余本金
			remainPrincipal = remainPrincipal - principal;
		}
	}
	
	return plans;
}



/**
 * 获取还款计划（按月付息到期还款）
 *
 * @param amount 		贷款金额
 * @param loanRate		利率（日）
 * @param loanPeriod	期限（正整数） 
 * @param loanDate  	放款日期
 * @param periodType	还款周期 : 1|2|3  = 年|月|日 
 * @param payDay		每期还款日期，为null默认为放款日 
 * @param payOptions 	还款选项	1|2|null = 前置付息|一次性付息|常规 
 * return [{
 *  		principal : 计划收入本金
 *  		interest  :  计划收入利息
 *  		num : 第几笔
 *  		accountDate : 计划到账日期
 *  		startDate : 计息开始时间
 *  		endDate : 计息结束时间
 *  	}]
 */
function getMonthlyPaymentDuePayPlans(amount,loanRate,loanPeriod,loanDate,periodType,payDay,payOptions) {
	var plans = [];
	var payPrincipalDate = getPayPrincipalDate("3",periodType,loanDate,loanPeriod);
	//一次性付息
	if(payOptions == 2){
		//还利息计划
		var planInterest={
				principal : 0,
				interest : countInterest(amount,loanRate,loanDate,payPrincipalDate),
				num : loanPeriod,
				accountDate : loanDate,
				startDate : loanDate,
				endDate : payPrincipalDate
		};
		//还本金计划
		var planPrincipal={
				principal : amount,
				interest : 0,
				num : loanPeriod,
				accountDate : payPrincipalDate,
				startDate : loanDate,
				endDate : payPrincipalDate
		};
		plans.push(planInterest);
		plans.push(planPrincipal);
	}
	//非一次性付息
	else{
		var startDate;//计息开始时间
		var endDate;	//计息结束时间
		var remainPrincipal = amount;//剩余本金
		var loanDateObj = NewDate(loanDate);
		//确定每期还款日
		if(!payDay || payDay == "" || payDay == "0"){
			payDay = loanDateObj.format("dd");
		}
		//首期计息开始时间
		startDate = loanDate;
		//计算首期计息结束时间
		endDate = getEndDateByStartDate("3",periodType,startDate,payDay);
		for(var i=0;i<loanPeriod;i++){
			if(compareDate(payPrincipalDate,endDate) < 0){
				//计算得出的计息结束日期大于还本金日期时，结束日期 = 还本金日期
				endDate = payPrincipalDate;
			}
			var interest = countInterest(amount,loanRate,startDate,endDate);//本期利息
			var payInterestDate;	//计划到账日期
			if(payOptions != 1){
				//非前置付息
				payInterestDate = endDate;
			} else {
				//前置付息
				payInterestDate = startDate;
			}
			var planInterest={
					principal : 0,
					interest  :  interest,
					num : (i+1),
					accountDate : payInterestDate,
					startDate : startDate,
					endDate : endDate
			};
			plans.push(planInterest);
			if(i < (loanPeriod - 1)){
				//下一期的开始时间  = 本期计息结束时间+1天
				startDate = addDate(endDate, 0, 0, 1, 0);
				//下一期计息结束时间
				endDate = getEndDateByStartDate("3",periodType,startDate,payDay);
			}
			
		}
		
		var interest = 0;//还本金时需要付的利息
		if(compareDate(payPrincipalDate,endDate) > 0){
			//还本金日期大于最后一期还款时间，计算多出时间的利息
			interest = countInterest(amount,loanRate,startDate,endDate);
		}
		var planPrincipal={
				principal : amount,
				interest  :  interest,
				num : loanPeriod,
				accountDate : payPrincipalDate,
				startDate : loanDate,
				endDate : payPrincipalDate
		};
		plans.push(planPrincipal);
	}
	
	return plans;
}


/**
 * 计算利息 : 剩余本金*利率（日）*天数
 * @param principal	纳入计息的本金
 * @param loanRate	利率（日）
 * @param startDate	开始计息时间
 * @param endDate	结束数计息时间
 * 
 */
function countInterest(principal,loanRate,startDate,endDate){
	var days = differenceDay(startDate, endDate);
	return (principal * loanRate/100 * days).toFixed(2);
}

/**
 * 根据还款方式、还款周期、计划开始日期计算本期计划结束时间 
 * @param payType		还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
 * @param periodType	还款周期 : 1|2|3  = 年|月|日 
 * @param date		日期
 */
function getEndDateByStartDate(payType,periodType,date,payDay) {
	var endDate ;
	if (payType == "3") { //按期收息，到期还本
		if (periodType == "3") {//日
			endDate = date;
		} else if (periodType == "2") {//月
			if(payDay && Number(NewDate(date).format("dd")) >= Number(payDay)){
				endDate = addMonth(date,payDay);
			} else {
				endDate = NewDate(date).format("yyyy-MM") + "-" + payDay;
			}
		} else if (periodType == "1") {//年
			endDate = addDate(date, 0, 12, -1, 0);
		}
	} else if (payType == "1" || payType == "2") {//等额本息|等额本金
		if(payDay && Number(NewDate(date).format("dd")) >= Number(payDay)){
			endDate = addMonth(date,payDay);
		} else {
			endDate = NewDate(date).format("yyyy-MM") + "-" + payDay;
		}
	}
	return endDate;
}

/**
 * 根据还款方式、还款周期、放款日期和贷款期限计算还本金日期
 * @param payType		还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
 * @param periodType	还款周期 : 1|2|3|4  = 年|月|日|季
 * @param loanDate		放款日期
 * @param loanPeriod	期限
 */
function getPayPrincipalDate(payType,periodType,loanDate,loanPeriod) {
	var payPrincipalDate ;
	if (payType == "1" || payType == "2" || payType == "3") {//等额本息|等额本金|等本等息
		payPrincipalDate = addotherMonth(loanDate,0,loanPeriod);//addDate(loanDate, 0, loanPeriod, -1, 0);//根据期限增加月份
	}else{
		if (periodType == "3") {//日
			payPrincipalDate = addDate(loanDate, 0, 0, loanPeriod - 1, 0);
		} else if (periodType == "2") {//月
			payPrincipalDate = addotherMonth(loanDate,0,loanPeriod);//addDate(loanDate, 0, loanPeriod, -1, 0);
		} else if (periodType == "1") {//年
			payPrincipalDate = addDate(loanDate, 0, loanPeriod * 12, -1, 0);
		} else if (periodType == "4") {//季
			payPrincipalDate = addDate(loanDate, 0, loanPeriod * 3, -1, 0);
		}
	}
	return payPrincipalDate;
}


/**
 * 日期的加减
 */    //addDate(loanDate, 0, loanPeriod, -1, 0);
function addDate(date, years, months, days, interestDay) {
	date = date.replace(/-/g, "/"); // 更改日期格式
	var cdate;
	var nd = new Date(date);
	if (days != 0) {
		nd = nd.valueOf();
		nd = nd + days * 24 * 60 * 60 * 1000;
		nd = new Date(nd);
	}
	if (months != 0) {
		nd.setMonth(nd.getMonth() + parseInt(months));
	}
	if (years != 0) {
		nd.setFullYear(nd.getFullYear() + parseInt(years));
	}
	var y = nd.getFullYear();
	var m = nd.getMonth() + 1;
	var d = nd.getDate();
	if (m <= 9)
		m = "0" + m;
	if (d <= 9)
		d = "0" + d;
	if (interestDay != 0) {
		cdate = y + "-" + m + "-" + interestDay;
	} else {
		cdate = y + "-" + m + "-" + d;
	}
	return cdate;
}

/**
 * 加多一个月
 */
function addMonth(date, interestDay) {
	var currentDate = new Date(date.replace(/-/g, "/"));
	var targetDate = new Date(currentDate.getFullYear(),currentDate.getMonth()+1+1,0);
	var maxDate = targetDate.getDate();//获取当月最后一天
	//interestDay为null或0，怎取转换日期
	if(!interestDay || Number(interestDay) == 0){
		interestDay = currentDate.getDate();
	} 
	
	if(Number(interestDay) < maxDate){
		targetDate = targetDate.format("yyyy-MM") + "-" + interestDay;
	}
	return targetDate;
}

/**
 * 加多一个月2
 */
function addotherMonth(date, interestDay,months) {
	var currentDate = new Date(date.replace(/-/g, "/"));
	var targetDate = new Date(currentDate.getFullYear(),currentDate.getMonth()+1+months,0);
	var maxDate = targetDate.getDate();//获取当月最后一天
	//interestDay为null或0，怎取转换日期
	if(!interestDay || Number(interestDay) == 0){
		interestDay = currentDate.getDate();
	} 
	if(Number(interestDay) < maxDate){
		targetDate = targetDate.format("yyyy-MM") + "-" + interestDay;
	}
	
	var myDate = new Date(targetDate);
	myDate.setDate(myDate.getDate() - 1);
	var nowdate;
	var y = myDate.getFullYear();
	var m = myDate.getMonth() + 1;
	var d = myDate.getDate();
	if (m <= 9)
		m = "0" + m;
	if (d <= 9)
		d = "0" + d;
	nowdate = y + "-" + m + "-" + d; 
	
	return nowdate;
}



/**
 * 两个日期的相差天数
 */
function differenceDay(startDate, endDate) {
	var s1 = new Date(startDate.replace(/-/g, "/"));
	var s2 = new Date(endDate.replace(/-/g, "/"));
	var time = s2.getTime() - s1.getTime();
	var days = parseInt(time / (1000 * 60 * 60 * 24));
	return days + 1;
}

/** 
 * 比较两个日期，返回结果 : -1 : 第一个日期小于第二个日期；0 : 两个日期相等；1:第一个日期大于第二个日期
 * @param day1
 * @param day2
 * @returns
 */
function compareDate(day1, day2) {
	var result = 0;
	var s1 = new Date(day1.replace(/-/g, "/"));
	var s2 = new Date(day2.replace(/-/g, "/"));
	if (s1.getTime() < s2.getTime()) {
		result = -1;
	} else if (s1.getTime() == s2.getTime()) {
		result = 0;
	} else {
		result = 1;
	}
	return result;
}

function NewDate(str) {
	str = str.split('-');
	var date = new Date();
	date.setUTCFullYear(str[0], str[1] - 1, str[2]);
	date.setUTCHours(0, 0, 0, 0);
	return date;
}