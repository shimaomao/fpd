<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>业务办理</title>

<meta name="decorator" content="default" />
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/jqGrid/4.6/css/metor/jquery-ui.css"></link>
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css"></link>
<script type="text/javascript"
	src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.extend.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/jqGrid/4.6/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctxStatic}/util.js"></script>
<script type="text/javascript" src="${ctxStatic}/customRepayPlans.js"></script>
<script type="text/javascript">
	//require(['helper/api','app/repayPlan'], function(api,rp){

	var loanTypeStr = "";
	$(document)
			.ready(
					function() {
						var form = $("#inputForm");

						$("#inputForm")
								.validate(
										{
											submitHandler : function(form) {

												var optFlag = true;
												if (dataId != ''
														&& dataId
																.indexOf('tmp_') >= 0) {
													//alert("3:" + loanTypeStr);
													$(
															"input:checkbox[name='"
																	+ loanTypeStr
																	+ "']:checked")
															.each(
																	function() {
																		var tempVal = $(
																				this)
																				.val();
																		if (tempVal == '1') {
																			//$("#loanTypeDiv1").show();
																			var tempContentTable1 = $('#contentTable1 tr').length;
																			//alert(tempContentTable1);
																			if (tempContentTable1 < 2) {
																				optFlag = false;
																				alertx("请添加质押物！");
																				return optFlag;
																			}

																		} else if (tempVal == '2') {
																			var tempContentTable2 = $('#contentTable2 tr').length;

																			if (tempContentTable2 < 2) {
																				optFlag = false;
																				alertx("请添加抵押物！");
																				return optFlag;
																			}
																		} else if (tempVal == '4') {
																			var tempContentTable4 = $('#contentTable4 tr').length;
																			//alert(tempContentTable1);
																			if (tempContentTable4 < 2) {
																				optFlag = false;
																				alertx("请添加保证信息！");
																				return optFlag;
																			}
																		}

																	});

												}

												if (optFlag) {
													loading('正在提交，请稍等...');
													form.submit();
												}

											},
											errorContainer : "#messageBox",
											errorPlacement : function(error,
													element) {
												$("#messageBox").text(
														"输入有误，请先更正。");
												if (element.is(":checkbox")
														|| element.is(":radio")
														|| element
																.parent()
																.is(
																		".input-append")) {
													error.appendTo(element
															.parent().parent());
												} else {
													error.insertAfter(element);
												}
											}

										});

						//读取或生成还款计划
						$("#repayid").show();
						initRepayPlan(form, "read");

						$("#loanPeriod").blur(function() {//贷款期限
							//console.info("贷款期限");
							initPayPrincipalDate();
						});
						$("#loanDate").blur(function() {//放款时间
							//console.info("放款时间");
							initPayPrincipalDate();
						});

						//var radio_periodType = document.getElementById("periodType");//还款周期
						$("input[name='periodType']").click(function() {
							//console.info("还款周期");
							//alert("选择的是：" + $(this).val());
							initPayPrincipalDate();
						});
						/* var radio_periodType = document.getElementById("radio_periodType");//还款周期
						radio_periodType.addEventListener("click", function() {
							initPayPrincipalDate();
						});  */

						$("input[name='payType']").click(function() {
							//console.info("还款方式");
							//alert("选择的是：" + $(this).val());
							initPayPrincipalDate();
							var array = getCheckValue("payType");
							checkPeriodType(array[0]);
						});
						/* 
						var radio_payType = document.getElementById("payType");//还款方式
						radio_payType.addEventListener("click", function() {
							initPayPrincipalDate();
							 var array = getCheckValue("payType");
							 checkPeriodType(array[0]);
						}); */

						$("#buildRepayPlan").click(
										function() {
											$("#repayid")
													.html(
															'<div class="span12"><fieldset><legend>还款计划</legend><div id="showplansDiv"></div><div id="jqGridPager"></div></fieldset></div>');
											$("#repayid").show();
										
											//alert($("#repayid").css("display"));
											initRepayPlan(form, "new");
										});

						$("#readRepayPlan")
								.click(
										function() {
											$("#repayid")
													.html(
															'<div class="span12"><fieldset><legend>还款计划</legend><div id="showplansDiv"></div><div id="jqGridPager"></div></fieldset></div>');
											$("#repayid").show();
											initRepayPlan(form, "read");
										});
						/* 
						//对于sys:treeselect无效
						$('#loanAmount').bind('input propertychange', function(){
							console.info("选择客户"+$("#customerId").val());
						});
						$('#loanAmount').live('input on', function(){
							console.info("选择客户"+$("#customerId").val());
						}) */

						var tempLoanType = document
								.getElementsByName("loanTypeItem");
						if (tempLoanType.length == 0) {
							//alert('不存在');
							loanTypeStr = "loanType";
						} else {
							//alert('存在');
							loanTypeStr = "loanTypeItem";
						}
						//alert("1:" + loanTypeStr);

						var dataId = '${tLoanContract.id}';

						//初始化 先 判断 质押  抵押 保证
						controlLoanType();
						loanTypeCallBack('', '', '', '', 1, dataId);
						loanTypeCallBack('', '', '', '', 2, dataId);
						loanTypeCallBack('', '', '', '', 4, dataId);

						$("input[name='" + loanTypeStr + "']").bind("click",
								function() {
									controlLoanType();
								});

					});

	//质押 抵押 保证 显示 隐藏 操作
	function controlLoanType() {
		$("#loanTypeDiv1").hide();
		$("#loanTypeDiv2").hide();
		$("#loanTypeDiv4").hide();

		//alert('aa');
		//alert("2:" + loanTypeStr);
		$("input:checkbox[name='" + loanTypeStr + "']:checked").each(
				function() {
					var tempVal = $(this).val();
					//alert(tempVal);
					if (tempVal == '1') {
						$("#loanTypeDiv1").show();
					} else if (tempVal == '2') {
						$("#loanTypeDiv2").show();
					} else if (tempVal == '4') {
						$("#loanTypeDiv4").show();
					}

				});

	}

	//质押 抵押 保证  添加 修改
	function formForLoanType(title, id, num) {
		var url = "";
		if (num == 1) {
			url = "iframe:${ctx}/pledge/pledgeContract/formForLoanType";
		} else if (num == 2) {
			url = "iframe:${ctx}/mortgage/mortgageContract/formForLoanType";
		} else if (num == 4) {
			url = "iframe:${ctx}/guarantee/tGuaranteeContract/formForLoanType";
		}

		if (id != '') {
			url += "?id=" + id;
		}

		top.$.jBox.open(url, title, $(window).width() - 300, $(top.document)
				.height() - 150, {
			buttons : {
				"关闭" : "callback",
				"刷新" : "refresh"
			},
			bottomText : "",
			submit : function(v, h, f) {
				//alert('aaffs');
				var ifrWin = h.find("iframe")[0].contentWindow;
				if (v == "refresh") {
					ifrWin.location.reload(true);
					//ifrWin.clearAssign();
					return false;
				} else if (v == "return") {
					ifrWin.history.go(-1);
					ifrWin.location.reload();
					return false;
				} else if (v == "ok") {
					ifrWin.inputForm.submit();
					return false;
				} else if (v == "callback") {
					loanTypeCallBack(v, h, f, title, num, '');
					return true;
				}
			},
			loaded : function(h) {
				$(".jbox-content", top.document).css("overflow-y", "hidden");
			},
			closed : function() {
				//alert('aa');
				loanTypeCallBack('', '', '', title, num, '');
			} /* 窗口关闭后执行的函数 */
		});
	}

	//质押 抵押 保证  回调 
	function loanTypeCallBack(v, h, f, title, num, businessId) {
		//alert('aaa');
		var url = "";
		if (num == 1) {
			url = "${ctx}/pledge/pledgeContract/listForLoanType";
		} else if (num == 2) {
			url = "${ctx}/mortgage/mortgageContract/listForLoanType";
		} else if (num == 4) {
			url = "${ctx}/guarantee/tGuaranteeContract/listForLoanType";
		}

		if (businessId != '') {
			url += "?businessId=" + businessId;
		}

		$.post(url, function(data) {
			loanTypeCreateTable(data.colist, num);
		});
	}

	//质押 抵押 保证  生成表格
	function loanTypeCreateTable(colist, num) {
		var tempTable = $('#contentTable' + num);
		$('#contentTable' + num + ' tr:not(:first)').remove();

		$.each(colist, function(idx, item) {
			var tempTr = "";
			if (num == 1) {
				tempTr += "<tr><td>";
				tempTr += "<a onclick=\"formForLoanType('修改质押','" + item.id
						+ "','" + num + "');\" style=\"cursor: pointer;\">"
						+ item.name + "</a>";
				tempTr += "</td><td>";
				tempTr += item.unit;
				tempTr += "</td><td>";
				tempTr += item.worth;
				tempTr += "</td><td>";
				tempTr += "<a onclick=\"deleteLoanType('" + item.id + "','"
						+ num + "');\" style=\"cursor: pointer;\">删除</a>";
				tempTr += "&nbsp;&nbsp;<a onclick=\"formForLoanType('修改质押','"
						+ item.id + "','" + num
						+ "');\" style=\"cursor: pointer;\">修改</a>";
				tempTr += "</td>";
				tempTr += "</tr>";
			} else if (num == 2) {
				tempTr += "<tr><td>";
				tempTr += "<a onclick=\"formForLoanType('修改抵押','" + item.id
						+ "','" + num + "');\" style=\"cursor: pointer;\">"
						+ item.name + "</a>";
				tempTr += "</td><td>";
				tempTr += (item.unit == null ? "" : item.unit);
				tempTr += "</td><td>";
				tempTr += (item.worth == null ? "" : item.worth);
				tempTr += "</td><td>";
				tempTr += "<a onclick=\"deleteLoanType('" + item.id + "','"
						+ num + "');\" style=\"cursor: pointer;\">删除</a>";
				tempTr += "&nbsp;&nbsp;<a onclick=\"formForLoanType('修改抵押','"
						+ item.id + "','" + num
						+ "');\" style=\"cursor: pointer;\">修改</a>";
				tempTr += "</td>";
				tempTr += "</tr>";
			} else if (num == 4) {
				tempTr += "<tr><td>";
				tempTr += "<a onclick=\"formForLoanType('修改保证','" + item.id
						+ "','" + num + "');\" style=\"cursor: pointer;\">"
						+ item.guarantorName + "</a>";
				tempTr += "</td><td>";
				tempTr += item.amount;
				tempTr += "</td><td>";
				tempTr += item.contractDate;
				tempTr += "</td><td>";
				tempTr += "<a onclick=\"deleteLoanType('" + item.id + "','"
						+ num + "');\" style=\"cursor: pointer;\">删除</a>";
				tempTr += "&nbsp;&nbsp;<a onclick=\"formForLoanType('修改保证','"
						+ item.id + "','" + num
						+ "');\" style=\"cursor: pointer;\">修改</a>";
				tempTr += "</td>";
				tempTr += "</tr>";
			}

			if (tempTr != '') {
				//alert(tempTr);
				tempTable.append(tempTr);
			}

		});

	}

	//质押 抵押 保证  删除
	function deleteLoanType(id, num) {
		if (!id || !num) {
			alertx("删除失败!");
			return;
		}

		var url = "";
		if (num == 1) {
			url = "${ctx}/pledge/pledgeContract/deleteForLoanType";
		} else if (num == 2) {
			url = "${ctx}/mortgage/mortgageContract/deleteForLoanType";
		} else if (num == 4) {
			url = "${ctx}/guarantee/tGuaranteeContract/deleteForLoanType";
		}

		if (confirm("确定删除?")) {
			$.post(url, {
				id : id
			}, function(data) {
				loanTypeCreateTable(data.colist, num);
			});
		}

	}

	function initRepayPlan(form, readType) {
		var id = form.find("input[name='id']").val();
		var loanAmount = form.find("input[name='loanAmount']").val();
		var loanRate = form.find("input[name='loanRate']").val();
		var loanRateType = form.find("input[name='loanRateType']:checked")
				.val();//add by srf #3121
		var loanPeriod = form.find("input[name='loanPeriod']").val();
		var loanDate = form.find("input[name='loanDate']").val();
		var payType = form.find("input[name='payType']:checked").val();
		var periodType = form.find("input[name='periodType']:checked").val();
		var payDay = form.find("select[name='payDay']").val();
		var payOptions = getCheckValue('payOptions').join();
		var payPrincipalDate = $("#payPrincipalDate").val();//#3371
		var ifRealityDay = form.find("input[name='ifRealityDay']:checked")
				.val();
		if (payPrincipalDate)
			payPrincipalDate = payPrincipalDate.substr(0, 10);
		if (loanDate)
			loanDate = loanDate.substr(0, 10);
		//console.info('loanRateType='+loanRateType);
		var dealType = readType;//"new";
		var editContent = true;
		/* var editContent = false;
		
		if(",1,2,3,4,5,".indexOf(payType) > 0){
			editContent = false;
		}else{
			editContent = true;
		} */

		//判断参数是否为空(为空时为添加  不调用还款计划[后台报错])
		if (loanAmount.length == 0 || loanRate.length == 0
				|| loanRateType.length == 0 || loanPeriod.length == 0
				|| loanDate.length == 0 || payType.length == 0
				|| periodType.length == 0) {
			return;
		}

		createRepayPlansGrid({
			editContent : editContent,//是否可编辑，是：true；否：false
			dealType : dealType,//"new",//new表示直接新生成；否则读取数据库中为先，没有则新生成
			businessId : id,
			businessType : "apply", //apply|extend|earlyrepay...
			amount : loanAmount,
			loanRate : loanRate,
			loanRateType : loanRateType,//add by srf #3121
			loanPeriod : loanPeriod,
			loanDate : loanDate,
			payType : payType,//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
			periodType : periodType,//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
			payDay : payDay,//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
			payOptions : payOptions,//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
			payPrincipalDate : payPrincipalDate,//#3371
			ifRealityDay : ifRealityDay
		//大小月
		}, "#showplansDiv");
	
		$("#btnSubmit").show();
	};
	//});

	function checkPeriodType(peytype) {//当还款方式为等额本金  等额本息 等本等息时，还款周期只能是月
		if (peytype == "1" || peytype == "2" || peytype == "3") {
			//还款周期只能是月
			$("input[name='periodType']").each(function() {
				if ($(this).val() == "2") {
					$(this).attr("checked", true);
				}
				//$(this).attr("disabled", "disabled");
			}); //设置不可用
		} else {
			$("input[name='periodType']").each(function() {
				$(this).removeAttr("disabled");
			}); //设置可用
		}
	}

	//计算还本金日期
	function initPayPrincipalDate() {
		var payType = getCheckValue("payType")[0];//还款方式
		var periodType = getCheckValue("periodType")[0];// 还款周期
		var loanDate = $("#loanDate").val();// 放款日期
		var loanPeriod = $("#loanPeriod").val();// 贷款期限
		if (!payType || !loanDate || !loanPeriod)
			return;
		loanPeriod = Number(loanPeriod);
		var payPrincipalDate = getPayPrincipalDate(payType, periodType,
				loanDate, loanPeriod);
		//console.info("还本金日期：" + payPrincipalDate);
		$("#payPrincipalDate").val(payPrincipalDate);
	};

	//选择客户的回调函数，获取共同借款人
	function customerTreeselectCallBack(v, h, f) {
		console.info("选择客户" + $("#customerId").val());
		var customerId = $("#customerId").val()
		if (!customerId) {
			return;
		}

		$.post("${ctx}/company/tCompany/getCustomer", {
			id : customerId
		}, function(data) {
			var form = $("#inputForm");
			if (data.gatheringBank) {
				form.find("input[name='gatheringBank']")
						.val(data.gatheringBank);
			} else {
				form.find("input[name='gatheringBank']").val("");
			}
			if (data.gatheringName) {
				form.find("input[name='gatheringName']")
						.val(data.gatheringName);
			} else {
				form.find("input[name='gatheringBank']").val("");
			}
			if (data.gatheringNumber) {
				form.find("input[name='gatheringNumber']").val(
						data.gatheringNumber);
			} else {
				form.find("input[name='gatheringBank']").val("");
			}
		});

		$
				.post(
						"${ctx}/contract/tLoanContract/getJsonCoborrower",
						{
							customerId : customerId
						},
						function(data) {
							console.info("data.status=" + data.status);
							console.info("data.message=" + data.message);
							console.info("----------------------------");
							var hasContent = false;
							var coborContent = "&nbsp;";

							if (data.status == 1) {
								//console.info("成功==");
								$
										.each(
												data.colist,
												function(idx, item) {
													//console.info(idx+",id:"+item.id+",creditApplyId:"+item.creditApplyId+",customerName:"+item.customerName+",customerType:"+item.customerType); 
													if (idx == 0) {
														coborContent += "<table id=\"contentTable\" class=\"table table-striped table-bordered table-condensed\">";
														coborContent += "<thead><tr><th>客户姓名</th><th>客户类型</th><th>操作</th></tr></thead><tbody>";
													}
													coborContent += "<tr><td>";
													coborContent += item.customerName;
													coborContent += "</td><td>";
													if (item.customerType == '1') {
														coborContent += "企业";
													} else if (item.customerType == '2') {
														coborContent += "个人";
													} else {
														coborContent += item.customerType;
													}
													coborContent += "</td>";
													coborContent += "<td>";
													if ("${ifEdit}" == "edit") {
														coborContent += "<a onclick=\"toDelCo('"
																+ item.id
																+ "','"
																+ item.creditApplyId
																+ "');\" style=\"cursor: pointer;\">删除</a>";
													} else {
														coborContent += "无";
													}
													coborContent += "</td>";
													coborContent += "</tr>";

													hasContent = true;
												});
								//console.info("----------------------------");
								if (hasContent) {
									coborContent += "</tbody></table>";
								}
								//console.info(coborContent);
							} else if (data.status == 4) {
								//console.info("失败=4=");
								coborContent += "无";
								showTip(data.message);
							} else {
								//console.info("失败==");
								coborContent += "无";
							}
							$("#coborrowerInfo").html(coborContent);

							//return;
						});
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/contract/tLoanContract/">业务信息列表</a></li>
		<li class="active"><a
			href="${ctx}/contract/tLoanContract/form?id=${tLoanContract.id}">业务信息<shiro:hasPermission
					name="contract:tLoanContract:edit">${(empty tLoanContract.id || fn:contains(tLoanContract.id, 'tmp_')) ? '添加' : '修改' }</shiro:hasPermission>
				<shiro:lacksPermission name="contract:tLoanContract:edit">查看</shiro:lacksPermission></a></li>

	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="tLoanContract"
		action="${ctx}/contract/tLoanContract/save" method="post"
		class="form-horizontal">
		<div style="padding-left: 0px;">
			<form:hidden path="id" />
			<form:hidden path="act.procInsId" />
			<sys:message content="${message}" />
			<table class="table-form" style="line-height: 25px;">
				<tr>
					<td class="tit" colspan="4"><font
						style="float: left; font-weight: bold; color: #317eac;">客户基本信息</font></td>
				</tr>
				<tr>
					<td class="tit">客户类型：</td>
					<td>
							<form:select id="customerType" path="customerType"
								class="input-xlarge required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('customer_type')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
					<td class="tit">客户：</td>
					<td>
							<sys:treeselect id="customer" isAll="false" name="customerId"
								value="${tLoanContract.customerId}" labelName="customerName"
								labelValue="${tLoanContract.customerName}"
								parentName="customerType" cssClass="required"
								extId="__customerType"
								parentValue="${tLoanContract.customerType}" title="客户"
								url="/company/tCompany/treeData" allowClear="true"
								notAllowSelectParent="true" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
				</tr>
				<tr>
					<td class="tit">地区:</td>
					<td>
							<sys:treeselect id="area" name="area.id"
								value="${tLoanContract.area.id}" labelName="area.name"
								labelValue="${tLoanContract.area.name}" title="区域"
								url="/sys/area/treeData" allowClear="true"
								notAllowSelectParent="true" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
					<td class="tit">行业:</td>
					<td>
							<form:select path="industryId" class="input-xlarge required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('industry_id')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
				</tr>
				<tr>
					<td class="tit" colspan="4"><font
						style="float: left; font-weight: bold; color: #317eac;">合同业务信息</font></td>
				</tr>
				<tr>
					<td class="tit">申请日期：</td>
					<td>
							<input name="applyDate" type="text" readonly="readonly"
								maxlength="20" class="input-medium Wdate required"
								value="<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
					<td class="tit">贷款方式：</td>
					<td>
							<c:if test="${empty tLoanContract.loanType}">
								<form:checkboxes path="loanType"
									items="${fns:getDictList('loan_type')}" itemLabel="label"
									itemValue="value" htmlEscape="false" class="required" />
								<span class="help-inline"><font color="red">*</font> </span>
							</c:if>
							<c:if test="${not empty tLoanContract.loanType}">
								<form:checkboxes path="loanTypeItem"
									items="${fns:getDictList('loan_type')}" itemLabel="label"
									itemValue="value" htmlEscape="false" class="required" />
								<span class="help-inline"><font color="red">*</font> </span>
							</c:if>
					</td>
				</tr>
				<tr>
					<td class="tit">贷款金额(元)：</td>
					<td>
							<form:input path="loanAmount" htmlEscape="false"
								class="input-xlarge number required" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
					<td class="tit">贷款利率(%)：</td>
					<td>
								<form:input path="loanRate" htmlEscape="false"
									class="input-xlarge number required" />
								<span class="help-inline"><font color="red">*</font> </span>
						</td>
				</tr>
				<tr>
					<td class="tit">利率类型：</td>
					<td>
							<form:radiobuttons path="loanRateType"
								items="${fns:getDictList('cycleType')}" itemLabel="label"
								itemValue="value" htmlEscape="false" class="required" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
					<td class="tit">贷款期数(期)：</td>
					<td>
							<form:input path="loanPeriod" htmlEscape="false" maxlength="50"
								class="input-xlarge digits required" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
				</tr>
				<tr>
					<td class="tit">贷款用途：</td>
					<td>
							<form:select path="purposeId" class="input-xlarge required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('product_purpose_new')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
					</td>

					<td class="tit">放款日期：</td>
					<td>
							<input id="loanDate" name="loanDate" type="text"
								readonly="readonly" maxlength="20"
								class="input-medium Wdate required"
								value="<fmt:formatDate value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
							<span class="help-inline"><font color="red">*</font> </span>

					</td>
				</tr>
				<tr>
					<td class="tit">还款方式：</td>
					<td>
							<form:radiobuttons path="payType"
								items="${fns:getDictList('product_paytype')}" itemLabel="label"
								itemValue="value" htmlEscape="false" class="required" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
					<td class="tit">还款周期：</td>
					<td>
							<form:radiobuttons id="radio_periodType" path="periodType"
								items="${fns:getDictList('period_type')}" itemLabel="label"
								itemValue="value" htmlEscape="false" class="required" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
				</tr>
				<tr>
					<td class="tit">固定还款日：</td>
					<td>
							<form:select path="payDay"
								items="${fns:getDictList('extends_days')}" itemLabel="label"
								itemValue="value" class="input-xlarge required"></form:select>
					</td>

					<td class="tit">还款选项：</td>
					<td>
							<form:checkboxes path="payOptions"
								items="${fns:getDictList('pay_options')}" itemLabel="label"
								itemValue="value" htmlEscape="false" class="" />
					</td>
				</tr>
				<tr>
					<td class="tit">是否大小月：</td>
					<td>
							<c:if test="${empty tLoanContract.ifRealityDay}">
								<form:radiobuttons path="ifRealityDay"
									items="${fns:getDictList('yes_no')}" itemLabel="label"
									itemValue="value" htmlEscape="false" checked="false" class="" />
							</c:if>
							<c:if test="${not empty tLoanContract.ifRealityDay}">
								<form:radiobuttons path="ifRealityDay"
									items="${fns:getDictList('yes_no')}" itemLabel="label"
									itemValue="value" htmlEscape="false" class="" />
							</c:if>
					</td>
					<td class="tit">是否可提前还款：</td>
					<td>
							<c:if test="${empty tLoanContract.ifAdvance}">
								<form:radiobuttons path="ifAdvance"
									items="${fns:getDictList('yes_no')}" itemLabel="label"
									itemValue="value" htmlEscape="false" checked="false" class="" />
							</c:if>
							<c:if test="${not empty tLoanContract.ifAdvance}">
								<form:radiobuttons path="ifAdvance"
									items="${fns:getDictList('yes_no')}" itemLabel="label"
									itemValue="value" htmlEscape="false" class="" />
							</c:if>
					</td>
				</tr>
				<tr>
					<td class="tit">允许利息减免(%)：</td>
					<td>
							<c:if test="${empty tLoanContract.ifInterestRelief}">
								<form:radiobuttons path="ifInterestRelief"
									items="${fns:getDictList('yes_no')}" itemLabel="label"
									itemValue="value" htmlEscape="false" checked="false" class="" />
							</c:if>
							<c:if test="${not empty tLoanContract.ifInterestRelief}">
								<form:radiobuttons path="ifInterestRelief"
									items="${fns:getDictList('yes_no')}" itemLabel="label"
									itemValue="value" htmlEscape="false" class="" />
							</c:if>
					</td>
					<td class="tit">贷款到期日：</td>
					<td>
							<input id="payPrincipalDate" name="payPrincipalDate" type="text"
								readonly="readonly" maxlength="20" class="input-medium Wdate "
								value="<fmt:formatDate value="${tLoanContract.payPrincipalDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
					</td>
				</tr>


				<tr>
					<td class="tit" colspan="4"><font
						style="float: left; font-weight: bold; color: #317eac;">各项费用信息</font></td>
				</tr>
				<tr>
					<td class="tit">管理费(%)：</td>
					<td>
							<form:input path="mangeFee" htmlEscape="false" maxlength="50"
								class="input-xlarge number" />
					</td>
					<td class="tit">宽限期(天)：</td>
					<td>
							<form:input path="gracePeriod" htmlEscape="false" maxlength="50"
								class="input-xlarge number" />
				   </td>
				</tr>
				<tr>
					<td class="tit">宽限期罚息(%)：</td>
					<td>
							<form:input path="gracePeriodPenalty" htmlEscape="false"
								maxlength="50" class="input-xlarge number" />
					</td>
					<td class="tit">逾期罚费(%)：</td>
					<td>
							<form:input path="latePenaltyFee" htmlEscape="false"
								maxlength="50" class="input-xlarge number" />
				  </td>
				</tr>
				<tr>
					<td class="tit">逾期罚息(%)：</td>
					<td>
							<form:input path="latePenalty" htmlEscape="false" maxlength="50"
								class="input-xlarge number" />
					</td>
					<td class="tit">费利优惠率(%)：</td>
					<td>
							<form:input path="rateDiscont" htmlEscape="false" maxlength="50"
								class="input-xlarge number" />
				   </td>
				</tr>
				<tr>
					<td class="tit">前期服务费(%)：</td>
					<td>
							<form:input path="serverFee" htmlEscape="false" maxlength="50"
								class="input-xlarge number" />
				    </td>
					<td class="tit">违约金(%)：</td>
					<td>
							<form:input path="lateFee" htmlEscape="false" maxlength="50"
								class="input-xlarge number" />
					</td>
				</tr>
				<tr>
					<td class="tit">提前还款违约金(%)：</td>
					<td>
							<form:input path="advanceDamages" htmlEscape="false"
								maxlength="50" class="input-xlarge number" />
					</td>
					<td class="tit"></td>
					<td></td>
				</tr>
				<tr>
					<td class="tit">开户名：</td>
					<td>
							<form:input path="gatheringName" htmlEscape="false"
								maxlength="255" class="input-xlarge " />
					</td>
					<td class="tit">开户行：</td>
					<td>
							<form:input path="gatheringBank" htmlEscape="false"
								maxlength="255" class="input-xlarge" />
					</td>
				</tr>
				<tr>
					<td class="tit">开户账号：</td>
					<td>
							<form:input path="gatheringNumber" htmlEscape="false"
								maxlength="255" class="input-xlarge" />
					</td>
					<td class="tit"></td>
					<td></td>
				</tr>
			</table>

		</div>
		<br>
		<div class="control-group" id="loanTypeDiv1">
			<label class="control-label"> <input class="btn btn-primary"
				type="button" onclick="formForLoanType('添加质押', '', 1)" value="添加质押"
				title="添加质押" />
			</label>
			<div id="loanTypeInfo1" class="controls">
				<table id="contentTable1"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>质押名称</th>
							<th>质押数量</th>
							<th>质押估值</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>

		<div class="control-group" id="loanTypeDiv2">
			<label class="control-label"> <input class="btn btn-primary"
				type="button" onclick="formForLoanType('添加抵押', '', 2)" value="添加抵押"
				title="添加抵押" />
			</label>
			<div id="loanTypeInfo2" class="controls">
				<table id="contentTable2"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>抵押名称</th>
							<th>抵押数量</th>
							<th>抵押价值</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>


		<div class="control-group" id="loanTypeDiv4">
			<label class="control-label"> <input class="btn btn-primary"
				type="button" onclick="formForLoanType('添加保证', '', 4)" value="添加保证"
				title="添加保证" />
			</label>
			<div id="loanTypeInfo4" class="controls">
				<table id="contentTable4"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>担保人名称</th>
							<th>担保金额(元)</th>
							<th>担保日期</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>

		<br>

		<!-- 还款计划-- -->
		<div style="padding-left: 0px;">
			<div class="row clearfix" style="display: none" id="repayid">
				<div class="span12">
					<fieldset>
						<legend>还款计划</legend>
						<!-- <div id="showplansDiv"></div> -->
						<div id="showplansDiv"></div>
						<div id="jqGridPager"></div>
					</fieldset>
				</div>
			</div>
			<div class="form-actions">
				<input id="buildRepayPlan" type="button" class="btn btn-primary"
					onclick="" value="更新还款计划" />&nbsp;
				<!-- <input id="readRepayPlan" type="button" class="btn btn-primary" onclick="" value="查看还款计划" />&nbsp; -->
				<shiro:hasPermission name="contract:tLoanContract:edit">
					<input id="btnSubmit" class="btn btn-primary" type="submit"
						value="保 存" style="display: none"/>&nbsp;</shiro:hasPermission>
				<input id="btnCancel" class="btn" type="button" value="返 回"
					onclick="history.go(-1)" />&nbsp;
			</div>
		</div>
	</form:form>
</body>
</html>