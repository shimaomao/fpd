package com.wanfin.fpd.core.filter;

import java.util.List;

/**
 * 链接属性过滤器
 * @author Chenh
 * @param <T>
 */
public interface LinkFilter<T> {
	/**
	 * @Description 属性过滤器(公有属性)
     * @param entity
	 * @return
	 * @author Chenh 
	 * @date 2016年6月12日 下午12:49:17
	 */
    public void filter(T entity);
    
    /**
     * @Description 属性过滤器(新增、修改返回属性POST/PUT/DELETE)
     * @param entity
     * @return
     * @author Chenh 
     * @date 2016年6月12日 下午12:49:17
     */
    public void filterChange(T entity);
    
    /**
     * @Description 属性过滤器(查询返回属性GET_ALL/GET_BY_ID)
     * @param entity
     * @return
     * @author Chenh 
     * @date 2016年6月12日 下午12:49:17
     */
    public void filterQuery(T entity);
    
	/**
	 * @Description fields列属性过滤器
     * @param entity
     * @param fields
	 * @return
	 * @author Chenh 
	 * @date 2016年6月12日 下午12:49:17
	 */
    public void filterByFields(T entity, List<String> fields);
    
	/**
	 * @Description 忽略列属性过滤器
	 * @return
	 * @author Chenh 
	 * @date 2016年6月12日 下午12:49:17
	 */
    public List<String> filterIgnore();
}
