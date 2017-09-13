/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.act.entity.Act;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.product.dao.ProductBizCfgDao;
import com.wanfin.fpd.modules.product.dao.TProductDao;
import com.wanfin.fpd.modules.product.entity.ProductBizCfg;
import com.wanfin.fpd.modules.product.entity.TExtendColumn;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.vo.TProductVo;
import com.wanfin.fpd.modules.productbiz.entity.TProductBiz;
import com.wanfin.fpd.modules.sys.dao.AuthenticatorRelDao;
import com.wanfin.fpd.modules.sys.dao.MenuDao;
import com.wanfin.fpd.modules.sys.entity.AuthenticationUserRel;
import com.wanfin.fpd.modules.sys.entity.Menu;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 产品管理Service
 * @author lx
 * @version 2016-03-23
 */
@Service
@Transactional(readOnly = true)
public class TProductService extends CrudService<TProductDao, TProduct> {
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private ProductBizCfgDao productBizCfgDao;
	@Autowired
	private DfFormTplService dfFormTplService;
	@Autowired
	private AuthenticatorRelDao authenticatorRelDao;

	public TProduct get(String id) {
		return super.get(id);
	}
	
	public List<TProduct> findList(TProduct tProduct) {
		return super.findList(tProduct);
	}
	
	public List<TProductVo> findListVo(TProductVo tProductVo) {
		return super.dao.findListVo(tProductVo);
	}

	@SuppressWarnings("deprecation")
	public List<TProduct> findAllList() {
		return super.dao.findAllList();
	}
	
	public Page<TProduct> findPage(Page<TProduct> page, TProduct tProduct) {
		tProduct.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, tProduct);
	}

	public Page<TProduct> findPage2(Page<TProduct> page, TProduct tProduct) {
		tProduct.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "", ""));
		return super.findPage(page, tProduct);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<TProductVo> findPageVo(Page<TProductVo> page, TProductVo entity) {
		entity.setPage(new Page<TProduct>(page.getPageNo(), page.getPageSize(), page.getCount()));
		page.setList(dao.findListVo(entity));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(TProduct tProduct) {
		if(StringUtils.isEmpty(tProduct.getStatus())){
			tProduct.setStatus(Cons.ProductStatus.NEW);
		}
		Boolean isNew = tProduct.getIsNewRecord();
		super.save(tProduct);
		addProduct(tProduct, isNew);
		
	}
	
	@Transactional(readOnly = false)
	public void delete(TProduct tProduct) {
		super.delete(tProduct);		
		dfFormTplService.deleteByRelId(tProduct.getId());
		//this.deleteProductAndRel(tProduct);
	}

	/**
	 * 增加自定义字段
	 * @param extendColumn 用于动态添加字段的实体类
	 */
	public void addColumn(String chineseName,String tableName,User user){
		//获取用于产生列名的最大流水号
				Object max = Db.queryInt("select max(max_num) from t_extend_column");
				Integer maxNum = max==null || max.equals("") ? 0 : Integer.parseInt(max.toString());
				TExtendColumn extendColumn = new TExtendColumn();
				extendColumn.setMaxNum(maxNum+1);
				extendColumn.setColumnName("column"+(maxNum+1));//构造列名(数据格式：column1,column2,column3...递增)
				extendColumn.setFormName(extendColumn.getColumnName());
				extendColumn.setTableName(tableName);
				//在表t_company中增加一个字段:extendColumn.getColumnName()
				String addColumnSQL = "alter table "+tableName+" add "+extendColumn.getColumnName()+" varchar(100)";
				Db.update(addColumnSQL);
				Db.update("insert into t_extend_column (chinese_name,column_name,form_name,max_num,table_name,organ_id) values (?,?,?,?,?,?)",
						                               chineseName,extendColumn.getColumnName(),extendColumn.getFormName(),extendColumn.getMaxNum(),extendColumn.getTableName(),user.getOffice().getParent().getId());
	}

	public List<?> getColumnList(User currentUser) {
		return Db.find("select * from t_extend_column e where e.table_name=? and  e.organ_id =?", "t_product",currentUser.getOffice().getParent().getId());
	}
	
	public String getWindsList(TProduct tProduct,User currentUser) {
		String id = Db.queryStr("select DISTINCT(wind_id) from t_product_peizhi p where p.form_id =?",tProduct.getId());
		id=(id==null||"".equals(id))?"0":id.replaceAll(",", "','"); 
		return id;
	}
	
	public List<?> getProductbizList(TProduct tProduct, User currentUser, List<?> columnlist) {
		String id = tProduct.getId();
		List<TProductBiz> productbizList = null;
		if(tProduct!=null&&id!=null){
			for (int i = 0; i < columnlist.size(); i++) {
				Record r = (Record)columnlist.get(i);
				String column = r.getStr("form_name");
				String columnvaluelist = Db.queryStr("select "+column+" from t_product e where e.id='"+id+"'");
				r.set("form_value",columnvaluelist);
			}
			//查询出配置表里面的功能点id
			List<?> peizhilist =  Db.query("select biz_id from t_product_peizhi where form_id=?",id);
			if(peizhilist.size()>0){
				String biz_id = "";
				for (int i = 0; i < peizhilist.size(); i++) {
					if(i==peizhilist.size()-1){
						biz_id += peizhilist.get(i);
					}else{
						biz_id += peizhilist.get(i)+",";
					}
				}
				biz_id = "'"+biz_id.replaceAll(",","','")+"'";
				//业务功能点集合
				 productbizList =  Db.query("select * from t_product_biz where office_id='"+currentUser.getOffice().getParent().getId()+"' and id in("+biz_id+")");
			}
		}
		return productbizList;
	}  

	@Transactional(readOnly = false)
	public void saveAll(User currentUser,TProduct tProduct,HttpServletRequest request) {
		//获取更新扩展字段值,并保存扩展字段对应的值
	       List<?> list = Db.find("select form_name from t_extend_column e where e.table_name=? and e.organ_id =?", "t_product",currentUser.getOffice().getParent().getId());
			StringBuilder sqlBuffer = new StringBuilder();
			if (list != null && list.size()>0) {
				for (int i =0;i<list.size();i++) {
					Record c = (Record)list.get(i);
					//获取表单元素名称
					String formName = c.getStr("form_name");
					//获取要更新的值
					String formValue = request.getParameter(formName)==null ? "" : request.getParameter(formName);
					//累加更新sql语句
					if(formValue!=null){
						if(i==list.size()-1){//处理最后一个的问题
							sqlBuffer.append(formName+"='"+formValue+"'");
						}else{
							sqlBuffer.append(formName+"='"+formValue+"',");
						}
					}
				}
			}
			if(sqlBuffer.length()>0){//注意更新条件：entityId
				Db.update("update t_product set "+sqlBuffer.toString()+" where id=?",tProduct.getId());
			}
			if(StringUtils.isBlank(tProduct.getStatus())){
				tProduct.setStatus(Cons.ProductStatus.NEW);
			}
			
			save(tProduct);
			
		
			
			DfFormTpl dfFormTpl = dfFormTplService.get(tProduct.getModelId());
			if(dfFormTpl != null && StringUtils.isBlank(dfFormTpl.getRelId())){
				dfFormTpl.setParent(new DfFormTpl(dfFormTpl.getId()));
				dfFormTpl.setRelId(tProduct.getId());
				dfFormTpl.setProduct(tProduct);
				dfFormTpl.setId(null);
				dfFormTpl.setName(null);
				dfFormTpl.setOrganId(null);
				dfFormTpl.setRemarks(null);
				request.setAttribute("productId", tProduct.getId());
				dfFormTplService.save(dfFormTpl);
			}
	}
	
	//修改关联主表
	private void updateProductAndRel(User currentUser, TProduct tProduct) {
		// 修改主表
	
		String productId = Db.queryStr("select product_id from pro_product_rel  where del_flag != 1 and t_product_id='"+tProduct.getId()+"'");
		if (!StringUtils.isBlank(productId)) {
			String sqlProductRecord = "update pro_product set user_id=?,type=?,name=?,rate=?,"
					+ "status=?,pub_type=?,pub_end_date=?,"
					+ "update_by=?,create_by=?,update_date=?,create_date=?"
					+ " where id=?";
			Db.update(sqlProductRecord, tProduct.getOrganId(), tProduct.getType(),tProduct.getName(),
					tProduct.getRate(),"new",2,new Date(),currentUser.getId(),currentUser.getId(),new Date(),new Date(),productId);
			
			//修改关联表
			String sqlProductRelRecord = "update pro_product_rel set product_id=?,t_product_id=?,update_by=?,update_date=?"			
					+ " where id=?";
			Db.update(sqlProductRelRecord, tProduct.getId(),tProduct.getId(),new Date(),new Date(),tProduct.getId());	
		}			
	
	}
	
	
	//删除关联主表
	private void deleteProductAndRel(TProduct tProduct) {
		// 删除主表		
		String productId = Db.queryStr("select product_id from t_product  where del_flag != 1 and t_product_id='"+tProduct.getId()+"'");
		
		String sqlProductRecord = "update pro_product set del_flag=?"
				+ " where id=?";
		Db.update(sqlProductRecord, "1",productId);
		
		//修改关联表
		String sqlProductRelRecord = "update pro_product_rel set del_flag=?"			
				+ " where id=?";
		Db.update(sqlProductRelRecord, "1",tProduct.getId());			
	}
	
	
	//保存主表
	private void saveProductAndRel(User currentUser, TProduct tProduct) {
		//主表
		Record productRecord = new Record();		
		String productId =  IdGen.uuid();
		productRecord.set("id", productId);// ID
		productRecord.set("user_id", tProduct.getOrganId());// 用户ID
		productRecord.set("type", tProduct.getType());// 产品类型 
		productRecord.set("name", tProduct.getName());// 产品名称	 
		productRecord.set("rate", tProduct.getRate());// 年化收益率
		productRecord.set("status", "new");//new 新建、 published 已发布	
		productRecord.set("pub_type", 2);//发布类型:1 、永久;2、指定日期
		productRecord.set("pub_end_date", new Date());//发布截止日期	
		
		productRecord.set("update_by", currentUser.getId());
		productRecord.set("create_by", currentUser.getId());
		productRecord.set("update_date", new Date());
		productRecord.set("create_date", new Date());
		
		//保存关联表
		Record productRelRecord = new Record();			
		productRelRecord.set("id", IdGen.uuid());// ID
		productRelRecord.set("product_id", productId);// 主产品ID
		productRelRecord.set("t_product_id", tProduct.getId());// 借款产品Id	
		
		productRelRecord.set("update_by", currentUser.getId());
		productRelRecord.set("create_by", currentUser.getId());
		productRelRecord.set("update_date", new Date());
		productRelRecord.set("create_date", new Date());
      
		
        Db.save("pro_product", productRecord);
        Db.save("pro_product_rel", productRelRecord);  
	}

	public List<?> findIsexist(User currentUser,String chineseName,String tableName) {
		return  Db.query("select * from t_extend_column e where e.table_name=? and e.chinese_name=? and e.organ_id =?",tableName,chineseName,currentUser.getOffice().getParent().getId());
	}

	public List<TProductBiz> getAllProductbizList(User currentUser) {
		return  Db.query("select * from t_product_biz where office_id='"+currentUser.getOffice().getParent().getId()+"'");
	}

	public List<Act> getActReProcdef() {
		//审批流程集合
		//List<Act> actList =  Db.query("select * from ACT_RE_PROCDEF where office_id='"+currentUser.getOffice().getParent().getId()+"'");
		return Db.query("select * from ACT_RE_PROCDEF");
	}

	public List<TProductBiz> getAllproductbizListInIds(User currentUser,HttpServletRequest request,String biz_id) {
		return Db.query("select * from t_product_biz where organ_id='"+currentUser.getOffice().getParent().getId()+"' and id in("+biz_id+")");
	}

	public String getAllSelectProductbizIds(HttpServletRequest request) {
		String biz_id = "";
		
		String qian  = request.getParameter("qian");
		if(!"".equals(qian)){
			qian = "'"+qian.replaceAll(",","','")+"'";
			biz_id += qian;
		}
		
		String zhong = request.getParameter("zhong");
		if(!"".equals(zhong)){
			zhong = ",'"+zhong.replaceAll(",","','")+"'";
			biz_id += zhong;
		}
		
		String hou   = request.getParameter("hou");
		if(!"".equals(hou)){
			hou = ",'"+hou.replaceAll(",","','")+"'";
			zhong+=hou;
		}
		return biz_id;
	}
	
	/**topMeanid  最高父级id
	 * meanids 产品选择的菜单id
	 */
	@Transactional(readOnly = false)
	public void saveProdctMeans(String topMeanid, String id, String meanids, String sysCode) {
		User currentUser = UserUtils.getUser();//当前登录人
		List<Menu> newlist = UserUtils.getBusinessMenuList();//模板全部菜单
		Menu parentmean = null;
		 if(id==null){
			 parentmean = menuDao.get(topMeanid+"");
         }else{
        	 parentmean = menuDao.get(id+"");
         }
		for (int i = 0; i < newlist.size(); i++) {
			Menu menu = newlist.get(i);
			if(menu.getParentId().equals(topMeanid)&&meanids.contains(menu.getId())){
				String menuid = menu.getId();
				menu.preInsert();
	        	menu.setCreateBy(currentUser);
	        	menu.setParent(parentmean);
	        	if(id==null){
	        		menu.setParentIds(parentmean.getParentIds()+topMeanid+",");
	        	}else{
	        		menu.setParentIds(parentmean.getParentIds()+id+",");
	        	}
	        	menu.setSysCode(sysCode);
	        	menu.setRelId(menuid);
	        	menuDao.insert(menu);
	        	saveProdctMeans(menuid,menu.getId(), meanids, sysCode);
			}
		}
	}

	/**
	 * @Description 保存业务节点配置
	 * @param bizCfgList
	 * @author zzm 
	 * @date 2016-5-9 下午6:07:19  
	 */
	@Transactional(readOnly = false)
	public void saveBizCfgs(TProduct tProduct) {
		List<ProductBizCfg> list = tProduct.getBizCfgList();
		if(list != null){
			for(ProductBizCfg bizCfg : list){
				if(StringUtils.isNotBlank(bizCfg.getId())){
					ProductBizCfg bizCfgOld = productBizCfgDao.get(bizCfg.getId());
					bizCfgOld.setBiz(bizCfg.getBiz());
					bizCfgOld.setProcDefId(bizCfg.getProcDefId());
					bizCfgOld.setRiskId(bizCfg.getRiskId());
					bizCfg = bizCfgOld;
					productBizCfgDao.update(bizCfg);
				}else {
					bizCfg.setProduct(tProduct);
					productBizCfgDao.insert(bizCfg);
				}
			}
		}
	}
	
	/**
	 * @Description 根据W端产品ID获取B端产品
	 * @param wtypeId
	 * @return
	 */
	public TProduct getByWtypeId(String wtypeId) {
		return dao.getByWtypeId(wtypeId);
	}
	
	/**
	 * @Description 根据租户ID获取所有产品
	 * @param organId
	 * @return
	 */
	public List<TProduct> getAllByOrganId() {
		TProduct entity = new TProduct();
		entity.setOrganId(UserUtils.getUser().getCompany().getId());
		entity.setOrganId(UserUtils.getUser().getCompany().getId());
		return dao.findByOrganId(entity);
	}
	public List<TProduct> getAllByOrganId(String organId) {
		if(StringUtils.isNotEmpty(organId)){
			TProduct entity = new TProduct();
			entity.setOrganId(organId);
			return dao.findByOrganId(entity);
		}
		return new ArrayList<TProduct>();
	}

	/**
	* @Description:  更新微服务产品表 
	* @author chenJianpeng
	* @param entity  
	* @throws
	 */
	@Transactional(readOnly = false)
	public void addProduct(TProduct entity, boolean isNew) {
		if (entity != null) {
			User user = UserUtils.getUser();
			if(user != null){
				System.out.println("-----------------------------微服务调用开始-----------------------------");
				if(isNew){
					System.out.println("-----------------------------新增-----------------------------");
					try {
						AuthenticationUserRel authenticationUserRel = authenticatorRelDao.findAuthByBuserId(user.getId());
						if ((authenticationUserRel != null) && (StringUtils.isNotEmpty(authenticationUserRel.getAnuthenuserId()))) {
							String proProductId = IdGen.uuid();
					    	StringBuffer sqlProProductRecordBuffer = new StringBuffer();
					    	sqlProProductRecordBuffer.append("INSERT INTO pro_product (id, user_id,  type, name, status, pub_type, pub_end_date, create_by, create_date, update_by, update_date, remarks, del_flag) ");
					    	sqlProProductRecordBuffer.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					    	
					    	Db.update(sqlProProductRecordBuffer.toString(), proProductId , authenticationUserRel.getAnuthenuserId(), Cons.ProProductType.LOAN_1, entity.getName(), entity.getStatus(), "new", new Date(), user.getId(), new Date(), user.getId(), new Date(), "", "0");
	 
					    	String proProductRelId = IdGen.uuid();
							Record record = new Record();
							record.set("id", proProductRelId);
							record.set("product_id", proProductId);
							record.set("product_manager_id", "");
							record.set("tfinancial_product_id", "");
							record.set("t_product_id", entity.getId());
							record.set("create_by", user.getId());
							record.set("create_date", new Date());
							record.set("update_by", user.getId());
							record.set("update_date", new Date());
							record.set("remarks", "");
							record.set("del_flag", "0");
							Db.save("pro_product_rel", record);
						}else{
							System.out.println("B："+user.getId()+"对应的认证用户不存在！");
						}
					} catch (Exception e) {
						System.out.println("-----------------------------微服务-----------------------------");
						System.out.println("微服务产品新增执行失败！ 地址："+Cons.Ips.WF_PRODUCT_SAVE+"请完善数据！");
						e.printStackTrace();
						System.out.println("-----------------------------微服务-----------------------------");
					}
				}else{
					System.out.println("-----------------------------修改-----------------------------");
					try {
				    	StringBuffer sqlProProductRecordBuffer = new StringBuffer();
				    	sqlProProductRecordBuffer.append("SELECT pp.id as \"id\", pp.user_id as \"userId\", pp.type as \"type\", pp.name as \"name\", pp.status as \"status\", pp.pub_type as \"pubType\", ppr.id as \"rid\", ppr.t_product_id as \"loanId\" FROM pro_product pp ");
				    	sqlProProductRecordBuffer.append("LEFT JOIN pro_product_rel ppr ON pp.id = ppr.product_id ");
				    	sqlProProductRecordBuffer.append("WHERE ppr.t_product_id = '"+entity.getId()+"' and pp.del_flag = '0' and ppr.del_flag = '0' ");
				    	System.out.println(sqlProProductRecordBuffer.toString());
				    	List<Record> records = Db.find(sqlProProductRecordBuffer.toString());
				    	
				    	if((records != null) && (records.size() == 1)){
				    		Record tempRecord = records.get(0);
				    		
				    		StringBuffer sqlProProductUpdateRecordBuffer = new StringBuffer();
					    	sqlProProductUpdateRecordBuffer.append("UPDATE pro_product SET name=?, status=?, pub_type=?, update_by=?, update_date=?, remarks=?, del_flag=? WHERE id=? ");
				    		Db.update(sqlProProductUpdateRecordBuffer.toString(), entity.getName(), entity.getStatus(), entity.getStatus(), entity.getUpdateBy().getId(), new Date(), entity.getRemarks(), entity.getDelFlag(), tempRecord.get("id"));
				    	}else{
							System.out.println("微服务产品更新状态执行失败！ 数据异常！");
				    	}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println("-----------------------------微服务调用结束-----------------------------");
			}else{
				System.out.println("-----------------------------当前用户获取失败-----------------------------");
			}
		}
	}
}