package com.cmazxiaoma.framework.web.context;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 保存当前请求信息
 */
public final class RequestContext {
	
	private ThreadLocal<HttpServletRequest> tl = new ThreadLocal<>();
	
	private static RequestContext context = new RequestContext();
	
	public static RequestContext getCurrentContext() {
		return context;
	}
	
	public synchronized HttpServletRequest getRequest() {
		return tl.get();
	}
	
	public void setRequest(HttpServletRequest request) {
		tl.set(request);
	}
	
	/**
	 * 获取当前上下文路径
	 * @return	上下文路径
	 */
	public String getContextPath() {
		StringBuffer ctx = new StringBuffer();
		ctx.append(tl.get().getScheme());
		ctx.append("://");
		ctx.append(tl.get().getServerName());
		if (tl.get().getServerPort() != 80) {
			ctx.append(":");
			ctx.append(tl.get().getServerPort());
		}
		ctx.append(tl.get().getContextPath());
		return ctx.toString();
	}
	
	/**
	 * 根据name从request中获取相应的参数值
	 * @param name	参数nema
	 * @return	参数值
	 */
	public String getParameter(String name) {
		return tl.get().getParameter(name);
	}
	
	/**
	 * 从Request中获取参数Map（name和value的键值对）
	 * @return	参数Map
	 */
	public Map<?, ?> getParameterMap() {
		return (Map<?, ?>)tl.get().getParameterMap();
	}

}
