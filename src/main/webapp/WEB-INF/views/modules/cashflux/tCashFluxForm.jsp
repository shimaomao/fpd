<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>现金流量分析管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cashflux/tCashFlux/">现金流量分析列表</a></li>
		<li class="active"><a
			href="${ctx}/cashflux/tCashFlux/form?id=${tCashFlux.id}">现金流量分析<shiro:hasPermission
					name="cashflux:tCashFlux:edit">${not empty tCashFlux.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="cashflux:tCashFlux:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="tCashFlux"
		action="${ctx}/cashflux/tCashFlux/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />


		<table class="table-form">
			<tr>
				<td>报表名称</td>
				<td colspan="5"><form:input path="reportName"
						htmlEscape="false" maxlength="64" class="input-xlarge required" />
					<span class="help-inline"><font color="red">*</font> </span></td>
			</tr>
			<tr style="text-align: center; line-height: 23px;"
				class="tit_left_bg">
				<td>项目</td>
				<td>行次</td>
				<td>金额</td>
				<td>补充材料</td>
				<td>行次</td>
				<td>金额</td>
			</tr>
			<tr style="line-height: 23px;">
				<td>一、经营活动产生的现金流量：</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>1、将净利润调节为经营活动现金流量：</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>销售商品、提供劳务收到的现金</td>
				<td>1</td>
				<td>
				   <form:input path="rowOne" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>净利润</td>
				<td>57</td>
				<td>
				    <form:input path="rowTwo" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
			<tr>
				<td>收到的税费返还</td>
				<td>3</td>
				<td>
				   <form:input path="row1One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>加：计提的资产减值准备</td>
				<td>58</td>
				<td>
				    <form:input path="row1Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>

			<tr>
				<td>收到的其他与经营活动有关的现金</td>
				<td>8</td>
				<td>
				   <form:input path="row2One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>固定资产折旧</td>
				<td>59</td>
				<td>
				    <form:input path="row2Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
			<tr>
				<td>现金流入小计</td>
				<td>9</td>
				<td>
				   <form:input path="row3One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>无形资产摊销</td>
				<td>60</td>
				<td>
				    <form:input path="row3Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
			<tr>
				<td>购买商品、接受劳务支付的现金</td>
				<td>10</td>
				<td>
				   <form:input path="row4One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>长期待摊费用摊销</td>
				<td>61</td>
				<td>
				    <form:input path="row4Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
			<tr>
				<td>支付给职工以及为职工支付的现金</td>
				<td>12</td>
				<td>
				   <form:input path="row5One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>待摊费用减少（减：增加）</td>
				<td>64</td>
				<td>
				    <form:input path="row5Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
						<tr>
				<td>支付的各项税费</td>
				<td>13</td>
				<td>
				   <form:input path="row6One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td> 预提费用增加（减：减少）</td>
				<td>65</td>
				<td>
				    <form:input path="row6Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
						<tr>
				<td> 支付的其他与经营活动有关的现金</td>
				<td>18</td>
				<td>
				   <form:input path="row7One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td> 处置固定资产、无形资产和其他长期资产的损失（减：收益）</td>
				<td>66</td>
				<td>
				    <form:input path="row7Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
						<tr>
				<td>现金流出小计</td>
				<td>20</td>
				<td>
				   <form:input path="row8One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>  固定资产报废损失</td>
				<td>67</td>
				<td>
				    <form:input path="row8Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
						<tr>
				<td>经营活动产生的现金流量净额</td>
				<td>21</td>
				<td>
				   <form:input path="row9One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td> 财务费用</td>
				<td>68</td>
				<td>
				    <form:input path="row9Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
						<tr>
				<td>二、投资活动产生的现金流量：</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>  投资损失（减：收益）</td>
				<td>69</td>
				<td>
				    <form:input path="row10Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
			<tr>
				<td> 收回投资所收到的现金</td>
				<td>22</td>
				<td>
				   <form:input path="row11One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>  递延税款贷项（减：借项）</td>
				<td>70</td>
				<td>
				    <form:input path="row11Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
						<tr>
				<td>  取得投资收益所收到的现金</td>
				<td>23</td>
				<td>
				   <form:input path="row12One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>存货的减少（减：增加）</td>
				<td>71</td>
				<td>
				    <form:input path="row12Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
			<tr>
				<td>处置固定资产、无形资产和其他长期资产所收回的现金净额</td>
				<td>25</td>
				<td>
				   <form:input path="row13One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td> 经营性应收项目的减少（减：增加）</td>
				<td>72</td>
				<td>
				    <form:input path="row13Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
						<tr>
				<td> 收到的其他与投资活动有关的现金</td>
				<td>28</td>
				<td>
				   <form:input path="row14One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td> 经营性应付项目的增加（减：减少）</td>
				<td>73</td>
				<td>
				    <form:input path="row14Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
						<tr>
				<td>现金流入小计</td>
				<td>29</td>
				<td>
				   <form:input path="row15One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>其它</td>
				<td>74</td>
				<td>
				    <form:input path="row15Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
						<tr>
				<td>    购建固定资产、无形资产和其他长期资产所支付的现金</td>
				<td>30</td>
				<td>
				   <form:input path="row16One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>  经营活动产生的现金流量净额</td>
				<td>75</td>
				<td>
				    <form:input path="row16Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
			<tr>
				<td>投资所支付的现金</td>
				<td>31</td>
				<td>
				   <form:input path="row17One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td> 支付的其他与投资活动有关的现金</td>
				<td>35</td>
				<td>
				   <form:input path="row18One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>现金流出小计</td>
				<td>36</td>
				<td>
				   <form:input path="row19One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td> 投资活动产生的现金流量净额</td>
				<td>37</td>
				<td>
				   <form:input path="row20One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>2、不涉及现金收支的投资和筹资活动：</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>三、筹资活动产生的现金流量：</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>   债务转为资本</td>
				<td>76</td>
				<td>
				    <form:input path="row21Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
						<tr>
				<td>吸收投资所收到的现金</td>
				<td>38</td>
				<td>
				   <form:input path="row22One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td> 一年内到期的可转换公司债券</td>
				<td>77</td>
				<td>
				    <form:input path="row22Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
						<tr>
				<td>借款所收到的现金</td>
				<td>40</td>
				<td>
				   <form:input path="row23One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>融资租入固定资产</td>
				<td>78</td>
				<td>
				    <form:input path="row23Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
			<tr>
				<td>  收到的其他与筹资活动有关的现金</td>
				<td>43</td>
				<td>
				   <form:input path="row24One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>现金流入小计</td>
				<td>44</td>
				<td>
				   <form:input path="row25One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>偿还债务所支付的现金</td>
				<td>45</td>
				<td>
				   <form:input path="row26One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
				<tr>
				<td>分配股利、利润或偿付利息所支付的现金</td>
				<td>46</td>
				<td>
				   <form:input path="row27One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>3、现金及现金等价物净增加情况：</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			
			
			<tr>
				<td>支付的其他与筹资活动有关的现金</td>
				<td>52</td>
				<td>
				   <form:input path="row28One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td> 现金的期末余额</td>
				<td>79</td>
				<td>
				    <form:input path="row28Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
			
									<tr>
				<td>现金流出小计</td>
				<td>53</td>
				<td>
				   <form:input path="row29One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td> 减：现金的期初余额</td>
				<td>80</td>
				<td>
				    <form:input path="row29Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
			
									<tr>
				<td>筹资活动产生的现金流量净额</td>
				<td>54</td>
				<td>
				   <form:input path="row30One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td> 加：现金等价物的期末余额</td>
				<td>81</td>
				<td>
				    <form:input path="row30Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
			
									<tr>
				<td>四、汇率变动对现金的影响</td>
				<td>55</td>
				<td>
				   <form:input path="row31One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td> 减：现金等价物的期初余额</td>
				<td>82</td>
				<td>
				    <form:input path="row31Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
			
									<tr>
				<td>五、现金及现金等价物净增加额</td>
				<td>56</td>
				<td>
				   <form:input path="row32One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
				</td>
				<td>   现金及现金等价物净增加额</td>
				<td>83</td>
				<td>
				    <form:input path="row32Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;" />
			   </td>
			</tr>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			<tr>
				<td>备注</td>
				<td colspan="5"><form:textarea path="remarks"
						htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge " />
				</td>
			</tr>
		</table>






		<div class="form-actions">
			<shiro:hasPermission name="cashflux:tCashFlux:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>