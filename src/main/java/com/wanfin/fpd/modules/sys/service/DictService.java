/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.CacheUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.sys.dao.DictDao;
import com.wanfin.fpd.modules.sys.entity.Dict;
import com.wanfin.fpd.modules.sys.utils.DictUtils;

/**
 * 字典Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	public List<Dict> findByCondition(Dict dict){
		return dao.findByCondition(dict);
	}

	/**
	 * @Description TODO
	 * @param dicts
	 * @author Chenh 
	 * @date 2016年6月1日 下午2:42:30  
	 */
	@Transactional(readOnly = false)
	public void savePL(List<Dict> dicts) {
		dao.savePL(dicts);
	}

	/**
	 * @Description TODO
	 * @param dicts
	 * @author Chenh 
	 * @date 2016年6月1日 下午3:19:46  
	 */
	@Transactional(readOnly = false)
	public void deletePLWL(List<Dict> dicts) {
		if((dicts != null) && (dicts.size() > 0)){
			dao.deletePLWL(dicts);
		}
	}

	/**
	 * Code不能为空
	 * @param dict
	 * @return
	 */
	public Dict getByCode(Dict dict) {
		if(StringUtils.isEmpty(dict.getType()) || StringUtils.isEmpty(dict.getValue())){
			return null;
		}
		List<Dict> dicts = dao.findByCondition(dict);
		if((dict != null) && (dicts.size() == 1)){
			return dicts.get(0);
		}
		return null;
	}
}
