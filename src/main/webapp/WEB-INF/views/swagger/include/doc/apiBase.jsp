<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			<!-- API基本规范 ================================================== -->
	            <section id="idapiRule">
	                <div class="page-header">
	                    <h1>API基本规范</h1>
	                </div>
	                <div class="row-fluid">
	                	<div class="alert alert-info">
                    	<b>使用须知：</b>
                    		<ul>
	                    		<li>
	                    			文档内模块定义格式：模块(中文-实体-模块标识)
	                    		</li>
	                    		<li>文档内所有演示实例以(用户-User-users)模块为例</li>
	                    		<li><a href="http://www.ruanyifeng.com/blog/2014/05/restful_api.html" target="_blank">Rest API规范</a></li>
	                    		<li><a href="http://www.cnblogs.com/DeasonGuan/articles/Hanami.html" target="_blank">Http详解</a></li>
	                    	</ul>
		                </div>
		                
		                <div>
                    	<b>基本规范：</b>  
                    		<ul>
                    			<li><a href="http://www.ruanyifeng.com/blog/2014/05/restful_api.html" target="_blank">Rest API规范</a><br/><br/></li> 
	                    		<li><a href="http://www.cnblogs.com/DeasonGuan/articles/Hanami.html" target="_blank">Http状态规范</a><br/><br/></li>
	                    		<li id="idapiRuleMKGF"><b>模块规范：用户模块(用户-User-users)</b>   
	                    			<div  class="alert alert-info">
		                    			<ul>
		                    				<li>域名-端口：http://localhost:8083/jeesite</li>
		                    				<li>标识-版本：api/(无版本标识)</li> 
		                    				<li>基本路径：http://localhost:8083/jeesite/api/<br/>
		                    					<p>如：http://localhost:8083/jeesite/api/users</p>
		                    				</li>
			                    		</ul> 
		                    		</div>
	                    		</li>
	                    		<li id="idapiRuleMKMMJKGF"><b> 模块命名请求规范：</b>
	                    			<ul>
	                    				<li id="idapiRuleMKMMJKGF_DMK">单模块：<a href="${ctxApi }/api/users" ><span class="icon-info-sign"></span>用户 : api/users </a>
	                    					<div  class="alert alert-info">
		                    					<ul>
				                    				<li class="alert">查询接口：GET:/模块标识（小写模块名+s）<br/>
				                    					<p>如：GET请求:http://localhost:8083/jeesite/api/users/
				                    						<br/>（查询所有用户）<br/>
				                    					</p>
				                    				</li>
				                    				<li class="alert">单个查询：GET:/模块标识（小写模块名+s）/主键标识(id)<br/>
				                    					<p>如：GET请求:http://localhost:8083/jeesite/api/users/{id}
				                    						<br/>（查询id用户）<br/>
				                    					</p>
				                    				</li>
				                    				<li class="alert">新增接口：POST:/模块标识（小写模块名+s）<br/>
				                    					<p>如：POST请求:http://localhost:8083/jeesite/api/users/
				                    						<br/>（新增用户）<br/>
				                    					</p>
				                    				</li>
				                    				<li class="alert">修改接口：PUT:/模块标识（小写模块名+s）/主键标识(id)<br/>
				                    					<p>如：PUT请求:http://localhost:8083/jeesite/api/users/{id}
				                    						<br/>（修改id用户）<br/>
				                    					</p>
				                    				</li>
				                    				<li class="alert">属性修改：PATCH:/模块标识（小写模块名+s）/主键标识(id)<br/>
				                    					<p>如：PATCH请求:http://localhost:8083/jeesite/api/users/{id}
				                    						<br/>（修改id用户）<br/>
				                    					</p>
				                    				</li>
				                    				<li class="alert">删除接口：DELETE:/模块标识（小写模块名+s）/主键标识(id)<br/>
				                    					<p>如：DELETE请求:http://localhost:8083/jeesite/api/users/{id}
				                    						<br/>（删除id用户）<br/>
				                    					</p>
				                    				</li>
					                    		</ul>
	                    					</div>
                    					</li>
                    					<li id="idapiRuleMKMMJKGF_GLMK">关联模块：<a href="${ctxApi }/api/users"><span class="icon-info-sign"></span>用户 (rid): api/users </a>&nbsp;&nbsp;| &nbsp;&nbsp;<a href="${ctxApi }/api/roles"><span class="icon-info-sign"></span>角色 (id): api/roles </a>
	                    					<div  class="alert alert-info">
		                    					<ul>
				                    				<li class="alert">查询接口：GET:/R模块标识（小写模块名+s）/主键标识(rid)/模块标识（小写模块名+s）<br/>
				                    					<p>如：GET请求:http://localhost:8083/jeesite/api/users/{rid}/roles/
				                    						<br/>（查询用户的所有角色）<br/>
				                    					</p>
				                    				</li>
				                    				<li class="alert">单个查询：GET:/R模块标识（小写模块名+s）/主键标识(rid)/模块标识（小写模块名+s）/主键标识(id)<br/>
				                    					<p>如：GET请求:http://localhost:8083/jeesite/api/users/{rid}/roles/{id}
				                    						<br/>（查询rid用户的id角色）<br/>
				                    					</p>
				                    				</li>
				                    				<li class="alert">新增接口：POST:/R模块标识（小写模块名+s）/主键标识(rid)/模块标识（小写模块名+s）<br/>
				                    					<p>如：POST请求:http://localhost:8083/jeesite/api/users/{rid}/roles/
				                    						<br/>（新增rid用户的角色）<br/>
				                    					</p>
				                    				</li>
				                    				<li class="alert">修改接口：PUT:/R模块标识（小写模块名+s）/主键标识(rid)/模块标识（小写模块名+s）/主键标识(id)<br/>
				                    					<p>如：PUT请求:http://localhost:8083/jeesite/api/users/{rid}/roles/{id}
				                    						<br/>（修改rid用户的id角色）<br/>
				                    					</p>
				                    				</li>
				                    				<li class="alert">属性修改：PATCH:/R模块标识（小写模块名+s）/主键标识(rid)/模块标识（小写模块名+s）/主键标识(id)<br/>
				                    					<p>如：PATCH请求:http://localhost:8083/jeesite/api/users/{rid}/roles/{id}
				                    						<br/>（修改rid用户的id角色）<br/>
				                    					</p>
				                    				</li>
				                    				<li class="alert">删除接口：DELETE:/R模块标识（小写模块名+s）/主键标识(rid)/模块标识（小写模块名+s）/主键标识(id)<br/>
				                    					<p>如：DELETE请求:http://localhost:8083/jeesite/api/users/{rid}/roles/{id}
				                    						<br/>（删除rid用户的id角色）<br/>
				                    					</p>
				                    				</li>
					                    		</ul>
	                    					</div>
                    					</li>
                   					</ul>
                				</li>
	                    		<li id="idapiRuleSYGF"><b>使用规范：</b>
	                    			<ul>
	                    				<li id="idapiRuleSYGF_CXGF">查询规范:<a href="${ctxApi }/api/users"><span class="icon-info-sign"></span>用户 : api/users </a>
	                    					<div class="alert alert-info">
		                    					<ul>
				                    				<li class="alert">
					                    				expand:展开详情,默认为false<br/>
					                    					<p>
					                    						true:展开，
					                    						false:关闭<br/>
					                    						如：api/users/?expand=true
					                    					</p>
				                    				</li>
				                    				<li class="alert">fields:显示列数据，默认显示指定列
				                    					<p>
				                    						模块模型列，前提是expand=true，假定模型用户有remarks,name，sex列<br/>
				                    						如：api/users/?expand=true&fields=remarks,name<br/>（显示remark,name列）<br/>
				                    					</p>
				                    				</li>
				                    				<li class="alert">offset:查询开始位置，默认offset=0<br/>
				                    					limit:限定条数，默认limit=30<br/>
				                    					不会影响curPage、perPage属性值
				                    					<p>
				                    						offset与limit配合使用，限定每次获取数据量<br/>
				                    						如：api/users/?offset=0&limit=30<br/>（默认：从第0条开始获取30条）<br/>
				                    						api/users/?offset=10<br/>（从第10条开始获取30条）<br/>
				                    						api/users/?limit=10<br/>（从第0条开始获取10条）<br/>
				                    					</p>
				                    				</li>
				                    				<li class="alert">curPage:当前页（默认：1）<br/>
				                    					perPage:每页显示数（默认：30）<br/>
				                    					会影响offset、limit属性值<br/>
				                    					<p>
				                    						curPage与perPage配合使用，实现分页<br/>
				                    						如：api/users/?curPage=1&perPage=30<br/>（默认：从第1页获取30条）<br/>
				                    						api/users/?curPage=10<br/>（从第10页开始获取30条）<br/>
				                    						api/users/?perPage=10<br/>（从第1页开始获取10条）<br/>
				                    					</p>
				                    				</li>
				                    				<li class="alert">sortby:排序属性，排序KEY<br/>
				                    					orderby:排序方式，排序VAL<br/>
				                    					sortby、orderby一起使用，若sorts存在时，该属性无效<br/>
				                    					<p>
				                    						如：api/users/?sortby=name&orderby=desc<br/>（按name降序排列）<br/>
				                    						api/users/?sortby=name&orderby=desc&sorts=name asc,sex desc<br/>（按name升序，sex降序排列）<br/>
				                    					</p>
				                    				</li> 
				                    				<li class="alert">sorts:排序规则，默认为系统排序（创建时间，修改时间）<br/>
				                    					sorts:排序规则覆盖sortby，orderby属性值<br/>
				                    					<p>
				                    						如：api/users/?sortby=name&orderby=desc<br/>（按name降序排列）<br/>
				                    						api/users/?sortby=name&orderby=desc&sorts=name asc,sex desc<br/>（按name升序，sex降序排列）<br/>
				                    					</p>
				                    				</li>
					                    		</ul>
				                    		</div>
	                    				</li>
	                    				<li id="idapiRuleSYGF_STGF">实体规范:<a href="${ctxApi }/api/users"><span class="icon-info-sign"></span>用户 : api/users </a>
	                    					<div  class="alert alert-info">
					                    		<p class="alert alert-danger">
					                    			1、API实体模型默认带有以下属性，该属性在后面介绍实体属性时，不再做解释！<br/> 
					                    			2、且默认以updateDate、createDate降序排列
					                    		</p>
					                    		<p></p>
		                    					<ul>
				                    				<li class="alert">
					                    				expand:展开详情,默认为false<br/>
				                    					fields:显示列数据，默认显示指定列
				                    					offset:查询开始位置，默认offset=0<br/>
				                    					limit:限定条数，默认limit=30<br/>
				                    					curPage:当前页（默认：1）<br/>
				                    					perPage:每页显示数（默认：30）<br/>
				                    					sortby:排序属性，排序KEY<br/>
				                    					orderby:排序方式，排序VAL<br/>
				                    					sorts:排序规则，默认为系统排序（创建时间，修改时间）<br/>
				                    					sorts:排序规则覆盖sortby，orderby属性值<br/>
				                    					<hr>
				                    					remarks:备注<br/>
				                    					createBy:创建者<br/>
				                    					createDate:创建日期<br/>
				                    					updateBy:更新者<br/>
				                    					updateDate:更新日期<br/>
				                    					delFlag:删除标记（0：正常；1：删除；2：审核）<br/>
				                    					organId:租户标识（必填属性）<br/>
				                    				</li>
					                    		</ul>
				                    		</div>
	                    				</li> 
	                    				<li id="idapiRuleSYGF_JKCSGF">接口及参数规范:
	                    					<div  class="alert alert-info"> 
					                    		<p class="alert alert-danger">
					                    			模型标识：模块模型的主键<br/>
					                    			模型任意属性：在各个模块定义了模块实体属性！ <br/>
					                    			标准查询参数: 在实体规范中标明的属性（查询条件）<br/>
					                    		</p>
					                    		<p class="alert alert-danger">
					                    			Y：必需属性<br/>
					                    			-：可有可无不做必要要求！ <br/>
					                    			其中：属性修改不做任何要求，属性为空，则该属性不做处理，不为空则修改操作，当然唯一性标识属性不为空
					                    		</p>
		                    					<ul>
				                    				<li>标准接口:<a href="${ctxApi }/api/users"><span class="icon-info-sign"></span>用户 : api/users </a>
				                    					<div>
								                    		<p class="alert">GET:api/users/<br>
								                    			参数：模型任意属性、及标准查询参数	
								                    		</p>
								                    		<p class="alert">GET:api/users/{id}<br/>
								                    			参数：模型标识、及标准查询参数	
								                    		</p>
								                    		<p class="alert">POST:api/users/<br>
								                    			参数：模型必填属性
								                    		</p>
								                    		<p class="alert">PUT:api/users/{id}<br/>
								                    			参数：模型标识、及模型任意属性
								                    		</p>
								                    		<p class="alert">DELETE:api/users/{id}<br/>
								                    			参数：模型标识
								                    		</p>
							                    		</div> 
				                    				</li>
				                   					<li>关联接口:<a href="${ctxApi }/api/roles"><span class="icon-info-sign"></span>角色 : api/roles </a>
				                    					<div>关联Users:
								                    		<p class="alert">GET:api/users/{rid}/roles<br>
								                    			参数：关联模型标识、模型任意属性、及标准查询参数	
								                    		</p>
								                    		<p class="alert">GET:api/users/{rid}/roles/{id}<br/>
								                    			参数：关联模型标识、模型标识、及标准查询参数
								                    		</p>
								                    		<p class="alert">POST:api/users/{rid}/roles/<br>
								                    			参数：关联模型标识、模型必填属性
								                    		</p>
								                    		<p class="alert">PUT:api/users/{rid}/roles/{id}<br/>
								                    			参数：关联模型标识、模型标识、及模型任意属性
								                    		</p>
								                    		<p class="alert">DELETE:api/users/{rid}/roles/{id}<br/>
								                    			参数：关联模型标识、模型标识
								                    		</p>
							                    		</div> 
				                    				</li>
					                    		</ul>
				                    		</div>
	                    				</li>
		                    		</ul>
	                    		</li>
                    		</ul>
		                </div>
	                </div>
	            </section>