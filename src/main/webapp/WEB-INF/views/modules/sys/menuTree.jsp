<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>

<div class="accordion" id="menu-${param.parentId}">
	<c:set var="menuList" value="${fns:getMenuList()}" />
	<c:set var="firstMenu" value="true" />
	<c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
		<c:if
			test="${menu.parent.id eq (not empty param.parentId ? param.parentId:1)&&menu.isShow eq '1'}">
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse"
						data-parent="#menu-${param.parentId}"
						data-href="#collapse-${menu.id}" href="#collapse-${menu.id}"
						title="${menu.remarks}"><i
						class="icon-chevron-${not empty firstMenu && firstMenu ? 'down' : 'right'}"></i>&nbsp;${menu.name}</a>
				</div>
				<div id="collapse-${menu.id}"
					class="accordion-body collapse ${not empty firstMenu && firstMenu ? 'in' : ''}">
					<div class="accordion-inner">
						<ul class="nav nav-list">
							<c:forEach items="${menuList}" var="menu2">
								<c:if test="${menu2.parent.id eq menu.id&&menu2.isShow eq '1'}">
									<li><a data-href=".menu3-${menu2.id}"
										href="${fn:indexOf(menu2.href, '://') eq -1 ? ctx : ''}${not empty menu2.href ? menu2.href : '/404'}"
										target="${not empty menu2.target ? menu2.target : 'mainFrame'}"><i
											class="icon-${not empty menu2.icon ? menu2.icon : 'circle-arrow-right'}"></i>&nbsp;
											    <c:if test="${fns:getUser().company.primaryPerson==danbao}">
												<c:choose>
													<c:when  test="${fn:contains(menu2.name, '贷前') == true}">
														 保前管理
													</c:when>
													<c:when  test="${fn:contains(menu2.name, '贷中管理') == true}">
														 保中管理
													</c:when>
													<c:when  test="${fn:contains(menu2.name, '贷后管理') == true}">
														 保后管理
													</c:when>
													<c:otherwise>
													 	${menu2.name}
													</c:otherwise>	
												</c:choose>
								                </c:if>
								                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
								                      ${menu2.name}
								                </c:if>
									    </a>
										<ul class="nav nav-list hide"
											style="margin: 0; padding-right: 0;">
											<c:forEach items="${menuList}" var="menu3">
												<c:if
													test="${menu3.parent.id eq menu2.id&&menu3.isShow eq '1'}">
													<li class="menu3-${menu2.id} hide"><a style="padding-left: 20px;"
														href="${fn:indexOf(menu3.href, '://') eq -1 ? ctx : ''}${not empty menu3.href ? menu3.href : '/404'}"
														target="${not empty menu3.target ? menu3.target : 'mainFrame'}"><i
															class="icon-${not empty menu3.icon ? menu3.icon : 'circle-arrow-right'}"></i>&nbsp;${menu3.name}</a></li>
												</c:if>
											</c:forEach>
										</ul></li>
									<c:set var="firstMenu" value="false" />
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</c:if>
	</c:forEach>
</div>