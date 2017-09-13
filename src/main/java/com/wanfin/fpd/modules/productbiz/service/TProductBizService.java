/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.productbiz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.productbiz.dao.TProductBizDao;
import com.wanfin.fpd.modules.productbiz.entity.TProductBiz;
import com.wanfin.fpd.modules.productbiz.vo.ProductBizVo;

/**
 * 业务功能Service
 * @author lx
 * @version 2016-03-10
 */
@Service
@Transactional(readOnly = true)
public class TProductBizService extends CrudService<TProductBizDao, TProductBiz> {

	public TProductBiz get(String id) {
		return super.get(id);
	}
	
	public List<TProductBiz> findList(TProductBiz tProductBiz) {
		return super.findList(tProductBiz);
	}
	
	public Page<TProductBiz> findPage(Page<TProductBiz> page, TProductBiz tProductBiz) {
		return super.findPage(page, tProductBiz);
	}
	
	@Transactional(readOnly = false)
	public void save(TProductBiz tProductBiz) {
		super.save(tProductBiz);
	}
	
	@Transactional(readOnly = false)
	public void delete(TProductBiz tProductBiz) {
		super.delete(tProductBiz);
	}

	public List<TProductBiz> findListByCompany(TProductBiz tProductBiz) {
		return dao.findListByCompany(tProductBiz);
	}
	

	public List<TProductBiz> findAllList(TProductBiz tProductBiz) {
		return dao.findAllList(tProductBiz);
	}

	/**
	 * @Description TODO
	 * @param tProductBiz
	 * @author Chenh 
	 * @date 2016年5月19日 下午4:50:18  
	 */
	public void update(TProductBiz tProductBiz) {
		dao.update(tProductBiz);
	}

	/**
	 * @Description TODO
	 * @param e
	 * @author Chenh 
	 * @date 2016年5月19日 下午4:51:05  
	 */
	public void updateParentIds(TProductBiz tProductBiz) {
		dao.updateParentIds(tProductBiz);
	}
	
	@Transactional(readOnly = false)
	public void updateSort(TProductBiz tProductBiz) {
		dao.updateSort(tProductBiz);
	}

	/**
	 * @Description TODO
	 * @param tProductBiz
	 * @return
	 * @author Chenh 
	 * @date 2016年5月19日 下午4:56:23  
	 */
	public List<TProductBiz> findByParentIdsLike(TProductBiz tProductBiz) {
		return dao.findByParentIdsLike(tProductBiz);
	}
	
	@Transactional(readOnly = false)
	public void saveTProductBiz(TProductBiz tProductBiz) {
		// 获取父节点实体
		tProductBiz.setParent(this.get(tProductBiz.getParent().getId()));
		
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = tProductBiz.getParentIds(); 
		
		// 设置新的父节点串
		tProductBiz.setParentIds(tProductBiz.getParent().getParentIds()+tProductBiz.getParent().getId()+",");

		// 保存或更新实体
		if (StringUtils.isBlank(tProductBiz.getId())){
			tProductBiz.preInsert();
			dao.insert(tProductBiz);
		}else{
			tProductBiz.preUpdate();
			dao.update(tProductBiz);
		}
		// 更新业务节点与业务菜单关联
		dao.deleteBizMenu(tProductBiz);
		if (tProductBiz.getMenuList().size() > 0){
			dao.insertBizMenu(tProductBiz);
		}
		
		// 更新子节点 parentIds
		TProductBiz m = new TProductBiz();
		m.setParentIds("%,"+tProductBiz.getId()+",%");
		List<TProductBiz> list = dao.findByParentIdsLike(m);
		for (TProductBiz e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, tProductBiz.getParentIds()));
			dao.updateParentIds(e);
		}
	}

	/**
	 * @Description TODO
	 * @param productBizCfg
	 * @return
	 * @author Chenh 
	 * @date 2016年5月24日 下午1:08:42  
	 */
	public List<ProductBizVo> findByBizCfg(ProductBizVo productBizVo) {
		return super.dao.findByBizCfg(productBizVo);
	}

	public List<String> getMenuIdsListByBizId(TProductBiz tProductBiz) {
		List<String> bizIds = Lists.newArrayList();
		bizIds.add(tProductBiz.getId());
//		return getMenuIdsListByBizListIds(bizIds);
		return getMenuIdsListByBizIds(StringUtils.listToSql(bizIds));
	}
	
	public List<String> getMenuIdsListByBizListIds(List<String> bizIds) {
		List<Record> rds = Db.find("SELECT DISTINCT a.menu_id FROM t_biz_menu a WHERE a.biz_id IN ("+StringUtils.listToSql(bizIds)+")");
		List<String> list = new ArrayList<String>();
		for (Record record : rds) {
			list.add(record.getStr("menu_id"));
		}
		return list;
//		return dao.getMenuIdsListByBizListIds(bizIds);
	}
	public List<String> getMenuIdsListByBizIds(String bizIds) {
		List<Record> rds = Db.find("SELECT DISTINCT a.menu_id FROM t_biz_menu a WHERE a.biz_id IN ("+bizIds+")");
		List<String> list = new ArrayList<String>();
		for (Record record : rds) {
			list.add(record.getStr("menu_id"));
		}
		return list;
//		return dao.getMenuIdsListByBizIds(bizIds);
	}
	
	
	public List<String> findAllByMenuSysCode(String productId) {
		return dao.findAllByMenuSysCode(productId);
	}
}