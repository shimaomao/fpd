package com.wanfin.fpd.common.persistence;

import java.util.List;



/**
 * DAO批量操作支持类实现
 * @author ThinkGem
 * @version 2014-05-16
 * @param <T>
 */
public interface CrudRDao<T> extends BaseDao {
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id);
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity);
	
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity);
	
	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public List<T> findAllList(T entity);
	
	/**
	 * 查询所有数据列表
	 * @see public List<T> findAllList(T entity)
	 * @return
	 */
	@Deprecated
	public List<T> findAllList();
	
	/**
	 * 根据ids字符串查询数据列表（,分隔符）;
	 * @param ids
	 * @return
	 */
	public List<T> findListByIds(String ids);
	
	/**
	 * 根据ids字符列表查询数据列表;
	 * @param ids
	 * @return
	 */
	public List<T> findListByIds(List<String> ids);
}
