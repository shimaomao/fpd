/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.form.dao.tpl;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.init.vo.SysInitFormtplVo;

/**
 * 自定义表单DAO接口
 * @author chenh
 * @version 2016-05-04
 */
@MyBatisDao
public interface DfFormTplDao extends CrudDao<DfFormTpl> {
	
	/**
	 * @Description 获取表单模板
	 * @param model 模板类型
	 * @return
	 * @author zzm
	 * @date 2016-5-5 下午4:15:47  
	 */
	public List<DfFormTpl> findTplList(DfFormTpl dfFormTpl);

	/**
	 * @Description 获取关联表单模板
	 * @param model 模板类型
	 * @return
	 * @author zzm
	 * @date 2016-5-5 下午4:15:47  
	 */
	public List<DfFormTpl> findRelTplList(DfFormTpl dfFormTpl);
	
	/**
	 * @Description 获取机构初始化模板列表
	 * 满足条件：
	 * 1、create_by = '1'
	 * 2、parent_id IS NULL
	 * 3、modsub = 'tpl'
	 * 查询条件：model和Office
	 * @param model 模板类型
	 * @return
	 * @author chenh
	 * @date 2016-5-5 下午4:15:47  
	 */
	public List<DfFormTpl> findInitTplList(SysInitFormtplVo entity);
	
	/**
	 * @Description 获取机构初始化模板列表
	 * 满足条件：
	 * 2、parent_id IS NULL
	 * 3、modsub = 'tpl'
	 * 查询条件：model和Office
	 * @param model 模板类型
	 * @return
	 * @author chenh
	 * @date 2016-5-5 下午4:15:47  
	 */
	public List<DfFormTpl> findInitTplListByOrgan(SysInitFormtplVo entity);
	
	/**
	 * @Description 通过relId删除表单
	 * @param relId	表单关联的id
	 * @author zzm
	 * @date 2016-5-5 下午5:09:54  
	 */
	public void deleteByRelId(String relId);

	/**
	 * @Description TODO
	 * @param tempDfFormTpl
	 * @author Chenh 
	 * @date 2016年5月6日 下午3:08:21  
	 */
	public DfFormTpl getByModelRelId(DfFormTpl tempDfFormTpl);
	
	public List<DfFormTpl> getByProductHasRelId(DfFormTpl tempDfFormTpl);
	
	/**
	 * @Description TODO
	 * @param tempDfFormTpl
	 * @author Chenh 
	 * @date 2016年5月6日 下午3:08:21  
	 */
	public List<DfFormTpl> getByModelHasRel(DfFormTpl tempDfFormTpl);

	/**
	 * @Description TODO
	 * @param dfFormTpl
	 * @return
	 * @author Chenh 
	 * @date 2016年5月11日 上午9:56:01  
	 */
	public void deleteWL(DfFormTpl dfFormTpl);
	
	/**
	 * @Description 通过relId删除表单
	 * @param relId	表单关联的id
	 * @author Chenh
	 * @date 2016-5-5 下午5:09:54  
	 */
	public void deleteWLByRelId(String relId);

	/**
	 * @Description 通过Product.id删除表单
	 * @param dfFormTpl	表单关联产品的id
	 * @author Chenh
	 * @date 2016-5-5 下午5:09:54  
	 */
	public void deleteWLByProductId(String productId);
	
	/**
	 * @Description 通过Product.id删除表单
	 * @param dfFormTpl	表单关联产品的id
	 * @author Chenh
	 * @date 2016-5-5 下午5:09:54  
	 */
	public void deleteWLByProduct(DfFormTpl dfFormTpl);
	
	/**
	 * @Description 通过Product.id删除表单
	 * @param dfFormTpl	表单关联产品的id
	 * @author Chenh
	 * @date 2016-5-5 下午5:09:54  
	 */
	public void deleteByProductId(DfFormTpl dfFormTpl);
	
	/**
	 * 更新标准模板
	 * @param dfFormTpl
	 * @param officeIds
	 * @author Zxj
	 */
	public void updateByInit(@Param("dfFormTpl")DfFormTpl dfFormTpl,@Param("officeIds")String[] officeIds);
		
	/**
	 * 获取更新标准模板的ID,关联更新关联模板用
	 * @param model
	 * @param officeId
	 * @return
	 */
	public DfFormTpl getFormTplId(@Param("model")String model,@Param("organId")String organId,@Param("delFlag")String delFlag);
	
	/**
	 * 通过parentId获取关联模板 
	 * @param relateId
	 * @return
	 */
	public List<DfFormTpl> getRelateDfFormTpl(@Param("relateId")String relateId); 
	
	/**
	 * 通过标准模板更新关联模板
	 * @param dfFormTpl
	 * @param relateIds
	 */
	public void updateRelateDfFormTpl(@Param("dfFormTpl")DfFormTpl dfFormTpl,@Param("relateIds")String[] relateIds);
}