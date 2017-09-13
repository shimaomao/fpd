/**  
 * @Project fpd 
 * @Title ApiUriInfo.java
 * @Package com.wanfin.fpd.core
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年6月4日 下午6:50:19 
 * @version V1.0   
 */
package com.wanfin.fpd.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.wanfin.fpd.common.config.Global;

/**
 * @Description [[_xxx_]] ApiUriInfo类
 * @author Chenh
 * @date 2016年6月4日 下午6:50:19
 */
public class ApiUriInfo implements UriInfo {
	private String path;
	private URI baseUri;
	private URI requestUri;
	private URI absolutePath;
	private UriBuilder baseUriBuilder;
	private UriBuilder requestUriBuilder;
	private UriBuilder absolutePathBuilder;
	private List<String> matchedURIs;
	private List<Object> matchedResources;
	private List<PathSegment> pathSegments;
	private MultivaluedMap<String, String> pathParameters;
	private MultivaluedMap<String, String> queryParameters;

	public ApiUriInfo() {
		super();
		try {
			this.baseUri = new URI(Global.getConfig("api.baseUri"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public String getPath(boolean decode) {
		if (decode) {
			return getPath();
		}
		return getPath();
	}

	@Override
	public List<PathSegment> getPathSegments() {
		return this.pathSegments;
	}

	@Override
	public List<PathSegment> getPathSegments(boolean decode) {
		if (decode) {
			return getPathSegments();
		}
		return getPathSegments();
	}

	@Override
	public URI getRequestUri() {
		return this.requestUri;
	}

	@Override
	public UriBuilder getRequestUriBuilder() {
		return this.requestUriBuilder;
	}

	@Override
	public URI getAbsolutePath() {
		return this.absolutePath;
	}

	@Override
	public UriBuilder getAbsolutePathBuilder() {
		return this.absolutePathBuilder;
	}

	@Override
	public URI getBaseUri() {
		return this.baseUri;
	}

	@Override
	public UriBuilder getBaseUriBuilder() {
		return this.baseUriBuilder;
	}

	@Override
	public MultivaluedMap<String, String> getPathParameters() {
		return this.pathParameters;
	}

	@Override
	public MultivaluedMap<String, String> getPathParameters(boolean decode) {
		if (decode) {
			return getPathParameters();
		}
		return getPathParameters();
	}

	@Override
	public MultivaluedMap<String, String> getQueryParameters() {
		return this.queryParameters;
	}

	@Override
	public MultivaluedMap<String, String> getQueryParameters(boolean decode) {
		if (decode) {
			return getQueryParameters();
		}
		return getQueryParameters();
	}

	@Override
	public List<String> getMatchedURIs() {
		return this.matchedURIs;
	}

	@Override
	public List<String> getMatchedURIs(boolean decode) {
		if (decode) {
			return getMatchedURIs();
		}
		return getMatchedURIs();
	}

	@Override
	public List<Object> getMatchedResources() {
		return this.matchedResources;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setPathSegments(List<PathSegment> pathSegments) {
		this.pathSegments = pathSegments;
	}

	public void setRequestUri(URI requestUri) {
		this.requestUri = requestUri;
	}

	public void setRequestUriBuilder(UriBuilder requestUriBuilder) {
		this.requestUriBuilder = requestUriBuilder;
	}

	public void setAbsolutePath(URI absolutePath) {
		this.absolutePath = absolutePath;
	}

	public void setAbsolutePathBuilder(UriBuilder absolutePathBuilder) {
		this.absolutePathBuilder = absolutePathBuilder;
	}

	public void setBaseUri(URI baseUri) {
		this.baseUri = baseUri;
	}

	public void setBaseUriBuilder(UriBuilder baseUriBuilder) {
		this.baseUriBuilder = baseUriBuilder;
	}

	public void setPathParameters(MultivaluedMap<String, String> pathParameters) {
		this.pathParameters = pathParameters;
	}

	public void setQueryParameters(
			MultivaluedMap<String, String> queryParameters) {
		this.queryParameters = queryParameters;
	}

	public void setMatchedURIs(List<String> matchedURIs) {
		this.matchedURIs = matchedURIs;
	}

	public void setMatchedResources(List<Object> matchedResources) {
		this.matchedResources = matchedResources;
	}

	/**   
	* 
	* @author Chenh
	* @param uri
	* @return    
	* @return    
	* @throws
	*/
	@Override
	public URI resolve(URI uri) {
		return null;
	}

	/**   
	* 
	* @author Chenh
	* @param uri
	* @return    
	* @return    
	* @throws
	*/
	@Override
	public URI relativize(URI uri) {
		return null;
	}
}