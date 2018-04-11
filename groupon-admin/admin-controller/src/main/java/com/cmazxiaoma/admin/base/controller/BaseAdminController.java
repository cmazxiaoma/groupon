package com.cmazxiaoma.admin.base.controller;

import com.cmazxiaoma.admin.base.constant.Constants;
import com.cmazxiaoma.admin.base.router.BaseRouter;
import com.cmazxiaoma.admin.security.entity.AdminUser;
import com.cmazxiaoma.framework.base.context.SpringApplicationContext;
import com.cmazxiaoma.framework.base.entity.BaseEntity;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.framework.util.image.ImageUtil;
import com.cmazxiaoma.framework.web.context.RequestContext;
import com.cmazxiaoma.framework.web.controller.BaseController;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.SecurityUtils;

/**
 *
 */
public class BaseAdminController extends BaseController {

    public BaseAdminController() {
    }

    public AdminUser getCurrentUser() {
        return (AdminUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获得Router实例
     *
     * @param routerName 简称,不需要包含"Router"(所有的Router必须以"**Router"格式命名)
     * @return
     */
    protected BaseRouter getRouter(String routerName) {
        Object router = SpringApplicationContext.getBean(routerName + "Router");
        if (null == router || !(router instanceof BaseRouter)) {
            return null;
        }
        return (BaseRouter) router;
    }

    /**
     * 生成Router按钮(HTML)
     *
     * @param pagingResult
     * @param search
     * @param <T>
     */
    public <T extends BaseEntity> void generateRouterCallButtons(PagingResult<T> pagingResult, Search search) {
        if (!search.containsRouterCall()) {
            return;
        }
        //获得Router实例
        BaseRouter router = getRouter(search.getRouterName());
        if (null == router) {
            return;
        }

        //支持的方法
        String[] methods = search.getMethods();
        //遍历每条数据,写入routerCall属性
        for (T entity : pagingResult.getRows()) {
            StringBuilder routerCall = new StringBuilder();
            //循环生成每个method的html
            for (String method : methods) {
                String button = generateRouterCallButton(router, search.getRouterName(), entity, method,
                        ArrayUtils.contains(search.getConfirmMethods(), method), search.getGridId());
                if (null != button) {
                    routerCall.append(button);
                }
            }
            //装载到routerCall中
            entity.setRouterCall(routerCall.toString());
        }
    }

    /**
     * 生成button
     * @param router
     * @param routerName
     * @param entity
     * @param method
     * @param confirm
     * @param gridId
     * @param <T>
     * @return
     */
    private <T extends BaseEntity> String generateRouterCallButton(BaseRouter router, String routerName, T entity,
                                                                   String method, boolean confirm, String gridId) {
        if (!router.isAuthorizedToCall(getCurrentUser().getId(), method)) {
//			return "";
        }
        String methodName = router.getMethodDisplayName(method);//method显示名称

        StringBuilder html = new StringBuilder();
        html.append("<button onclick=\"javascript:ajaxRouterCall('");
        html.append(RequestContext.getCurrentContext().getContextPath()).append("','").append(routerName).append("','");
        html.append(method).append("','").append(methodName).append("','");
        html.append(entity.getId()).append("',").append(confirm ? "true" : "false")
                .append(",'").append(gridId).append("')\" class='easyui-linkbutton'");
        if (router.isButtonDisabled(entity, method)) {
            html.append(" disabled = 'true' ");
        }
        html.append(">").append(methodName).append("</button>");
        return html.toString();
    }

    /**
     * 获取图片URL
     *
     * @param imageId           图片ID
     * @param moduleName        模块名称，如：deal
     * @param imageConfigNumber 详见image.properties
     * @return
     */
    protected String getObjectImageUrl(long imageId, String moduleName, int imageConfigNumber) {
        return ImageUtil.getImageUrl(Constants.IMAGE_DOMAIN + "/images/", imageId, moduleName, imageConfigNumber);
    }
}