<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
			 <!-- WOrder ================================================== -->
	            <section id="idwOrder">
	                <div class="page-header">
	                    <h1>订单</h1>（WOrder-wOrder）
	                </div>
		            <div>
                   		<ul>
                   			<li><b>模型属性：<a href="${ctxApi }/api/wOrders"><span class="icon-info-sign"></span>订单 : api/wOrders </a></b>   
                   				<table class="table table-bordered table-striped">
									<thead><tr><th>属性</th><th>说明</th><th>数据类型</th><th>必填</th><th>插入</th><th>查询</th></tr></thead>
									<tbody>
										<tr >
											<td>id(PK)</td>
											<td>订单主键</td>
											<td>String</td>
											<td>Y</td>
											<td>-</td>
											<td>-</td>
	            						</tr>
										<tr>
											<td>orderSn</td>
											<td>订单号</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>uid</td>
											<td>借款人主键</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>userType</td>
											<td>借款人类型(角色:1、机构,2、个人)</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>customerName</td>
											<td>借款人名称</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>loanId</td>
											<td>借款产品主键</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>productname</td>
											<td>产品名称</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>agencyId</td>
											<td>贷款产品所属的机构主键</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>catId</td>
											<td><a href="${ctxApi }/api/dicts?expand=true&fields=label,value&type=loan_type"><span class="icon-info-sign"></span>借款分类</a>：
												<c:forEach items="${fns:getDictList('loan_type')}" var="item">
												${item.value}、${item.label};
												</c:forEach></td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										
										<tr>
											<td>loanDate</td>
											<td>放款日期</td>
											<td>Date</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>loanPeriod</td>
											<td>贷款期限</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>loanRate</td>
											<td>贷款利率 </td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>loanRateType</td>
											<td>利率类型（年、月、日） </td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>payPrincipalDate</td>
											<td>还本金日期 </td>
											<td>Date</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>payDay</td>
											<td>付息日</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>periodType</td>
											<td><a href="${ctxApi }/api/dicts?expand=true&fields=label,value&type=period_type"><span class="icon-info-sign"></span>还款周期</a>：
												<c:forEach items="${fns:getDictList('period_type')}" var="item">
												${item.value}、${item.label};
												</c:forEach></td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>status</td>
											<td><a href="${ctxApi }/api/dicts?expand=true&fields=label,value&type=loan_contract_status"><span class="icon-info-sign"></span>状态</a>：
												<c:forEach items="${fns:getDictList('loan_contract_status')}" var="item">
												${item.value}、${item.label};
												</c:forEach>
											</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>loanFee</td>
											<td>借款手续费</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>repayWay</td>
											<td><a href="${ctxApi }/api/dicts?expand=true&fields=label,value&type=product_paytype"><span class="icon-info-sign"></span>还款方式</a>：
												<c:forEach items="${fns:getDictList('loan_contract_status')}" var="item">
												${item.value}、${item.label};
												</c:forEach>
											</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>applyAmount</td>
											<td>申请借款金额</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>installment</td>
											<td>借款期限</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>rstatus</td>
											<td>订单状态 1待审核 2不通过，3已通过 4待放款，5已放款，6已拒绝</td>
											<td>String</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
										<tr>
											<td>addTime</td>
											<td>添加时间</td>
											<td>Date</td>
											<td>Y</td>
											<td>Y</td>
											<td>-</td>
										</tr>
									</tbody>
								</table> 
                   			</li>
                   			<li>
								<b>关联模型属性：</b>
								<table class="table table-bordered table-striped">
									<thead><tr><th>属性</th><th>说明</th><th>数据类型</th><th>必填</th><th>查询</th></tr></thead>
								</table>
                   			</li>
                   			<li><b>模型接口：</b>
                   				<ul>
                   					<li>标准接口: 参阅 <code>API基本规范-使用规范</code>
                   						<ul>
					       					<li> 
								            	<b>必填参数：<a href="${ctxApi }/swagger/test" target="_blank"><span class="icon-info-sign"></span>测试</a></b>   
												<textarea placeholder="新增Json值" style="width:98%; height: 50px;">{"customerName":"张三","productname":"测试产品1","loanPeriod":"11","loanRate":"11","loanRateType":"1","payDay":"5","periodType":"1","status":"1","id":"123456789","orderSn":"SN10000","loanId":"122222222","agencyId":"fd5a746df8724316a3318a21d016c893","catId":"122222","uid":"1222222","userType":"2","loanFee":"11","repayWay":"1","applyAmount":"212","installment":"11","rstatus":"1","product":{"id":"1","wtypeId":"1"},"employee":{"id":"1","wtypeId":"1"}}</textarea>
								            </li>
								            <li>
								            	<b>Post参数：<a href="${ctxApi }/swagger/test" target="_blank"><span class="icon-info-sign"></span>测试</a></b>   
												<textarea placeholder="新增Json值" style="width:98%; height: 150px;">{"customerName":"张三","productname":"测试产品1","loanPeriod":"11","loanRate":"11","loanRateType":"1","payDay":"5","periodType":"1","status":"1","id":"123456789","orderSn":"SN10000","loanId":"122222222","agencyId":"fd5a746df8724316a3318a21d016c893","catId":"122222","uid":"1222222","userType":"2","loanFee":"11","repayWay":"1","applyAmount":"212","installment":"11","rstatus":"1","product":{"id":"1","wtypeId":"1"},"employee":{"id":"1","wtypeId":"1"}}</textarea>
								            </li>
								            <li>
								            	<b>默认响应参数：<a href="${ctxApi }/swagger/test" target="_blank"><span class="icon-info-sign"></span>测试</a></b>   
												<textarea placeholder="新增Json值" style="width:98%; height: 150px;">11{"gracePeriod":"1","mangeFee":"1","loanType":"1","fiveLevel":"1","gatheringName":"1","latePenaltyFee":"1","purposeId":"1","createBy.id":"1","gatheringNumber":"1","type":"1","delFlag":"1","gatheringBank":"1","customerType":"1","industryId":"1","payDayType":"1","maxNumber":"1","payType":"1","guarantNumber":"1","loanRateType":"1","isDeal":"1","customerId":"1","wtypeid":"1","id":"1","rateDiscont":"1","payPrincipalDate":"1","productId":"1","contractNumber":"1","payOptions":"1","ifAdvance":"1","customerName":"1","loanAmount":"1","gracePeriodPenalty":"1","isExtend":"1","periodType":"1","latePenalty":"1","updateBy.id":"1","lateFee":"1","payDay":"1","serverFee":"1","organId":"1","ifInterestRelief":"1","advanceDamages":"1","loanRate":"1","loanPeriod":"1","remarks":"1","status":"1"}</textarea>
								            </li>
								         </ul>
                   					</li>
                   					<li>关联接口: 参阅 <code>API基本规范-使用规范</code></li>
                   					<li>定制接口:无</li>
                   				</ul>
                   			</li>
                   		</ul>
					</div>
	            </section>