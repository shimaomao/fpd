<%@page import="com.wanfin.fpd.common.config.Cons.FModel" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8" %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
<html>
<head>
    <title>业务办理管理</title>
    <%-- 	<link href="${ctxStatic}/bootstrap/3.3.5/css/bootstrap.min.css" type="text/css" rel="stylesheet" /> --%>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/util.js"></script>
    <script type="text/javascript" src="${ctxStatic}/vow/contract_view.js?v=1"></script>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        function updateMoveStatus(loanContractId, moveStatus) {

            $.ajax({
                type: "POST",
                url: "${ctx}/contract/tLoanContract/updateMoveStatus",
                data: {'loanContractId': loanContractId, "moveStatus": moveStatus},
                dataType: "json",
                success: function (data) {
                    if (data.status == '1') {
                        location.reload(true);
                    } else {
                        showTip(data.message);
                    }
                }
            });

        }
        //导出文件
        function toDownload() {
            var customerName = $("#customerName").val();
            var starttime = $("#starttime").val();
            var endtime = $("#endtime").val();
            var url = "${ctx}/wish/contract/wishContract/wishLendExcel?customerName=" + customerName + "&starttime=" + starttime + "&endtime=" + endtime + "&status=6";
            location.href = url;
        }

        //还款并发送通知
        function repayMsg() {
            var array = getCheckValue("returnedMoneyId");
            if (array.length == 0) {
                showTip("请选择一条业务合同!");
                return;
            }
            
            var tLoanContractIds="";
		    for(var i=0;i<array.length;i++){
		    	  if(i==0){
		    		  tLoanContractIds="'"+array[i]+"'";
		    	  }else{
		    		  tLoanContractIds=tLoanContractIds+","+"'"+array[i]+"'";
		    	  }
		    	  
		    }
            
            $.ajax({
                type: "POST",
                url: "${ctx}/wish/contract/wishContract/realRepayMoney",
                data: {returnedMoneyIds: tLoanContractIds},
                dataType: "json",
                success: function (data) {
                    if (data.msg == 'success') {
                        location.reload(true);
                    } else {
                        showTip(data.msg);
                    }
                }
            });

        }
        //发送扣款通知易联
        function returnMoney() {

            $.ajax({
                type: "POST",
                url: "${ctx}/deductMoney/execute",
                dataType: "json",
                success: function (data) {
                    if (data.msg == 'success') {
                        location.reload(true);
                    } else {
                        showTip(data.msg);
                    }
                }
            });

        }

        //发送p2p资方还款通知
        function sendReturnMsg() {
        	 var array = getCheckValue("returnedMoneyId");
             if (array.length == 0) {
                 showTip("请选择还款信息!");
                 return;
             }
           /*  $.ajax({
                type: "POST",
                url: "${ctx}/deductMoney/execute",
                dataType: "json",
                success: function (data) {
                    if (data.msg == 'success') {
                        location.reload(true);
                    } else {
                        showTip(data.msg);
                    }
                }
            }); */
            top.$.jBox.open("iframe:${ctx}/wish/contract/wishContract/sendReturnMsg?ids="+array, 
	    			"打款明细",1000,600,{
	    		buttons:{"提交":"submit", "刷新":"refresh", "关闭":true}, 
	    		bottomText:"",
	    		submit:function(v, h, f){
	    			var ifrWin = h.find("iframe")[0].contentWindow;
	    			if(v=="refresh"){
	    				ifrWin.location.reload(true);
	                	//ifrWin.clearAssign();
	    				return false;
	                }else if(v=="submit"){
	                	ifrWin.btnSubmit.click();
	    				return false;
	                }
	    		}, loaded:function(h){
	    			$(".jbox-content", top.document).css("overflow-y","hidden");
	    		}
	    	});
        }

        //重新发送通知
        function sendMsg() {
        	var array = getCheckValue("returnedMoneyId");
            if (array.length == 0) {
                showTip("请选择还款信息!");
                return;
            }
            
            var tLoanContractIds="";
		    for(var i=0;i<array.length;i++){
		    	  if(i==0){
		    		  tLoanContractIds="'"+array[i]+"'";
		    	  }else{
		    		  tLoanContractIds=tLoanContractIds+","+"'"+array[i]+"'";
		    	  }
		    	  
		    }
            
            $.ajax({
                type: "POST",
                url: "${ctx}/wish/contract/wishContract/sendMsg",
                dataType: "json",
                data: {"returnedMoneyIds": tLoanContractIds},
                success: function (data) {
                    if (data.msg == 'success') {
                        location.reload(true);
                    } else {
                        showTip(data.message);
                    }
                }
            });

        }
    </script>

<script type="text/javascript">
	$(function() {
		//全选/全不选
		$("#checkrev").click(function() {
			$("[name=returnedMoneyId]:checkbox").attr("checked", this.checked);
		});
		$("[name=returnedMoneyId]:checkbox").click(function() {
			var flag = true;
			$("[name=returnedMoneyId]:checkbox").each(function() {
				if (!this.checked) {
					flag = false;
				}
			});
			$("#checkrev").attr("checked", flag);
		})
	})
</script>

</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/wish/contract/wishContract/wishRetrunMoneylist">打款待确认列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="returnedMoney" action="${ctx}/wish/contract/wishContract/wishRetrunMoneylist"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
            <%-- <li><label>业务编号：</label>
                <form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
            </li> --%>
        <li><label>客户名称：</label>
            <form:input path="userName" htmlEscape="false" maxlength="255" class="input-medium"/>
        </li>
        <li><label>开始时间：</label>
            <input id="starttime" name="starttime" type="text" readonly="readonly" maxlength="20"
                   class="input-medium Wdate"
                   value="<fmt:formatDate value="${returnedMoney.starttime}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li><label>结束时间：</label>
            <input id="endtime" name="endtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${returnedMoney.endtime}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
            &nbsp;&nbsp;<a class="btn btn-primary" onclick="toDownload();">导出</a></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>

<table class="table table-bordered">
    <tr>
        <td>
            <a class="btn btn-primary" onclick="returnMoney()">发送扣款通知易联</a>
            <a class="btn btn-primary" onclick="repayMsg();">确认收到款项</a>
            <a class="btn btn-primary" onclick="sendMsg();">重新发送通知</a>
            <a class="btn btn-primary" onclick="sendReturnMsg();">发送p2p资方还款通知</a>
        </td>

    </tr>
</table>
<table id="contentTable" class="table table-center table-striped table-bordered table-condensed">
    <thead>
    <th>选择</th>
    <th>卖家编号</th>
    <th>收款人</th>
    <th>订单金额</th>
    <th>应扣款金额</th>
    <!-- <th>剩余还款金额</th> -->
    <th>操作状态</th>
    <th>状态</th>
    <th>操作</th>
    <!-- 	<th>操作</th>	 -->
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="returnedMoney">
        <tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td><input type="checkbox" name="returnedMoneyId"
						id="returnedMoneyId" value="${returnedMoney.id}"
						data-ukey="${returnedMoney.ukey}" /></td>
           <%--  <td><input type="radio" name="returnedMoneyId"
                       id="returnedMoneyId" value="${returnedMoney.id}"
                       data-ukey="${returnedMoney.ukey}"/></td> --%>

                <%-- <td><input type="radio" name="returnedMoneyId" id="returnedMoneyId" value="${returnedMoney.id}" data-ukey="${returnedMoney.ukey}" /></td> --%>
                <%-- <td><a href="${ctx}/wish/contract/wishContract/wishLoanContractView?contractId=${returnedMoney.id}">
                    ${returnedMoney.userId}
                </a>
                </td> --%>
            <td>
                    ${returnedMoney.userId}
            </td>
            <td>
                    ${returnedMoney.userName}
            </td>
            <td>
                    ${returnedMoney.realPayMoney}
            </td>
            <td>
                    ${returnedMoney.repayLoanMoney}
            </td>
                <%-- <td>
                    ${returnedMoney.lastBackAmount}
                </td> --%>
            <td>
                    ${returnedMoney.isDeal}
            </td>
            <td>
                <c:if test="${returnedMoney.status}==0">
                    未处理
                </c:if>
                <c:if test="${returnedMoney.status}==1">
                    通知易联生成扣款报表
                </c:if>
                <c:if test="${returnedMoney.status}==2">
                    通知资方p2p还款通知
                </c:if>
            </td>
            <td>
                <c:if test="${returnedMoney.status}==2">
                    <a href="${ctx}/wish/contract/wishContract/wishRetrunMoneylist">贷款详情预览</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<label for="checkrev">全选/全不选<input type="checkbox" id="checkrev"
                                   onclick="checkrev();"></label>
<div class="pagination">${page}</div>
</body>
</html>