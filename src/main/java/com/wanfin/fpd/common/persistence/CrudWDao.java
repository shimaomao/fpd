package com.wanfin.fpd.common.persistence;

import java.util.List;



/**
 * DAO批量操作支持类实现
 * @author ThinkGem
 * @version 2014-05-16
 * @param <T>
 */
public interface CrudWDao<T> extends BaseDao {
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public Integer insert(T entity);
	
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public Integer update(T entity);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	@Deprecated
	public Integer delete(String id);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param entity
	 * @return
	 */
	public Integer delete(T entity);
	
	/**
	 * 批量插入数据
	 * @param entity
	 * @return
	 */
	public Integer insertPL(List<T> entitys);
	
	/**
	 * 批量更新数据
	 * @param entity
	 * @return
	 */
	public Integer updatePL(List<T> entitys);
	
	/**
	 * 批量删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int deletePL(T entity)
	 * @return
	 */
	@Deprecated
	public Integer deletePL(String ids);
	
	/**
	 * 批量删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param entity
	 * @return
	 */
	public Integer deletePL(List<T> entitys);
	
	/**
	 * 删除数据（一般为物理删除）
	 * @param id
	 * @see public int deleteWL(T entity)
	 * @return
	 */
	@Deprecated
	public Integer deleteWL(String id);
	
	/**
	 * 删除数据（一般为物理删除）
	 * @param entity
	 * @return
	 */
	public Integer deleteWL(T entity);
	
	/**
	 * 批量删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int deleteWLPL(T entity)
	 * @return
	 */
	@Deprecated
	public Integer deleteWLPL(String ids);
	
	/**
	 * 批量删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param entity
	 * @return
	 */
	public Integer deleteWLPL(List<T> entity);
}
