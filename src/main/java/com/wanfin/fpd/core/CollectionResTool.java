package com.wanfin.fpd.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import com.wanfin.fpd.common.persistence.ApiEntity;

@SuppressWarnings("rawtypes")
public abstract class CollectionResTool<T extends ApiEntity> extends Link{
	private static final long serialVersionUID = 1L;

	public CollectionResTool() {
		super();
	}

	public CollectionResTool(ApiEntity entity) {
		super(entity);
	}

	public CollectionResTool(String fqBasePath, ApiEntity entity) {
		super(fqBasePath, entity);
	}

	public CollectionResTool(String fqBasePath, String subPath) {
		super(fqBasePath, subPath);
	}

	public CollectionResTool(String subPath) {
		super(subPath);
	}

	public CollectionResTool(UriInfo info, ApiEntity entity) {
		super(info, entity);
	}

	public CollectionResTool(UriInfo info, String subPath) {
		super(info, subPath);
	}

	/** 公共方法 ***********************************************************************************/
	/**
	 * @Description 获取[[_模块_]]资源列表
	 * @param entity [[_模块_]]配置参数
	 * @param entitys [[_模块_]]列表数据
	 * @param linkM	[[_模块_]]模块KEY
	 * @return
	 * @author Chenh 
	 * @date 2016年6月7日 下午1:32:41
	 */
	 public CollectionRes getCollectionRes(T entity, Collection<T> entitys, String linkM) {
		 return getCollectionRes(entity, entitys, linkM, 0, ApiEntity.DEFAULT_LIMIT);
	 }

	/**
	 * @Description 获取[[_模块_]]资源列表
	 * @param entity [[_模块_]]配置参数
	 * @param entitys [[_模块_]]列表数据
	 * @param linkM	[[_模块_]]模块KEY
	 * @param offset [[_模块_]]列表开始位置
	 * @param limit [[_模块_]]列表返回记录的数量
	 * @return
	 * @author Chenh 
	 * @date 2016年6月7日 下午1:32:41
	 */
	 public CollectionRes getCollectionRes(T entity, Collection<T> entitys, String linkM, Integer offset, Integer limit) {
		if((entitys == null) || (entitys.size() <= 0)) {
			entitys = Collections.emptyList();
        }
        return new CollectionRes(linkM, getApiResourcesByExpand(entity, entitys), entity);
	}

	/**
	 * @Description 获取[[_模块_]]和展现资源
	 * @param entitys [[_模块_]]数据列表
	 * @return
	 * @author Chenh 
	 * @date 2016年6月7日 下午1:35:27
	 */
	 public Link getApiResourceByExpand(T entity, T enty) {
		if (entity.getExpand()) {
			 enty.setOper(entity.getOper());
			 return filterApiResourceByFields(entity, enty);
	    } else {
	         return new Link(enty);
	    }
	}

	/**
	 * @Description 获取[[_模块_]]和展现资源
	 * @param entity [[_模块_]]配置参数
	 * @param entitys [[_模块_]]数据列表
	 * @return
	 * @author Chenh 
	 * @date 2016年6月7日 下午1:35:27
	 */
	public Collection<Link> getApiResourcesByExpand(T entity, Collection<T> entitys) {
		Collection<Link> items = null;
		if((entitys != null) && (entitys.size() > 0)) {
			items = new ArrayList<Link>(entitys.size());
		}

        if (entity.getExpand()) {
			for(T enty : entitys) {
				enty.setOper(entity.getOper());
            	items.add(filterApiResourceByFields(entity, enty));
	        }
        }else{
        	for(T enty : entitys) {
                items.add(new Link(enty));
	        }
        }
		return items;
	}
	
	/**
	 * @Description 获取[[_模块_]]资源列表
	 * @param info API链接[[_模块_]]
	 * @param entitys [[_模块_]]列表数据
	 * @param linkM	[[_模块_]]模块KEY
	 * @return
	 * @author Chenh 
	 * @date 2016年6月7日 下午1:32:41
	 */
	public CollectionRes getCollectionRes(ApiUriInfo info, T entity, Collection<T> entitys, String linkM) {
		return getCollectionRes(info, entity, entitys, linkM, 0, ApiEntity.DEFAULT_LIMIT);
	}
	
	/**
	 * @Description 获取[[_模块_]]资源列表
	 * @param info API链接[[_模块_]]
	 * @param entitys [[_模块_]]列表数据
	 * @param linkM	[[_模块_]]模块KEY
	 * @param offset [[_模块_]]列表开始位置
	 * @param limit [[_模块_]]列表返回记录的数量
	 * @return
	 * @author Chenh 
	 * @date 2016年6月7日 下午1:32:41
	 */
	public CollectionRes getCollectionRes(ApiUriInfo info, T entity, Collection<T> entitys, String linkM, Integer offset, Integer limit) {
		if((entitys == null) || (entitys.size() <= 0)) {
			entitys = Collections.emptyList();
		}
		return new CollectionRes(info, linkM, getApiResourcesByExpand(info, entity, entitys), entity);
	}
	
	/**
	 * @Description 获取[[_模块_]]和展现资源
	 * @param info API链接[[_模块_]]
	 * @param entity [[_模块_]]参数
	 * @param enty [[_模块_]]数据
	 * @return
	 * @author Chenh 
	 * @date 2016年6月7日 下午1:35:27
	 */
	public Link getApiResourceByExpand(ApiUriInfo info, T entity, T enty) {
		if (entity.getExpand()) {
			return filterApiResourceByFields(info, entity, enty);
		} else {
			return new Link(info, enty);
		}
	}
	
	/**
	 * @Description 获取[[_模块_]]和展现资源
	 * @param info API链接[[_模块_]]
	 * @param entity [[_模块_]]参数
	 * @param entitys [[_模块_]]数据列表
	 * @return
	 * @author Chenh 
	 * @date 2016年6月7日 下午1:35:27
	 */
	public Collection<Link> getApiResourcesByExpand(ApiUriInfo info, T entity, Collection<T> entitys) {
		Collection<Link> items = null;
		if((entitys != null) && (entitys.size() > 0)) {
			items = new ArrayList<Link>(entitys.size());
		}
		
		if (entity.getExpand()) {
			for(T enty : entitys) {
				items.add(filterApiResourceByFields(info, entity, enty));
			}
		}else{
			for(T enty : entitys) {
				items.add(new Link(info, enty));
			}
		}
		return items;
	}

	public List<String> filterIgnore() {
		return new ArrayList<String>();
	}

	/**
	 * @Description 自定义ApiResource实体
	 * @param entity [[_模块_]]参数
	 * @param enty [[_模块_]]数据
	 * @return
	 * @author Chenh 
	 * @date 2016年6月12日 下午12:49:17
	 */
	public abstract Link filterApiResourceByFields(T entity, T enty);
	
	/**
	 * @Description 自定义ApiResource实体
	 * @param info API链接[[_模块_]]
	 * @param entity [[_模块_]]参数
	 * @param enty [[_模块_]]数据
	 * @return
	 * @author Chenh 
	 * @date 2016年6月12日 下午12:49:17
	 */
	public abstract Link filterApiResourceByFields(ApiUriInfo info, T entity, T enty);
	
	/**
	 * @Description 过滤器处理
	 * @param enty [[_模块_]]数据
	 * @return
	 * @author Chenh 
	 * @date 2016年6月12日 下午12:49:17
	 */
	public abstract void filterDeal(T enty);
}