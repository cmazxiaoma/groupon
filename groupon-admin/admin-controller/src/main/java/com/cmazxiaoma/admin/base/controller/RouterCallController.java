package com.cmazxiaoma.admin.base.controller;

import com.cmazxiaoma.admin.base.router.BaseRouter;
import com.cmazxiaoma.framework.base.entity.BaseEntity;
import com.cmazxiaoma.framework.base.exception.BusinessException;
import com.cmazxiaoma.framework.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 生成按钮
 */
@Controller
@RequestMapping(value = "/router/call")
public class RouterCallController extends BaseAdminController {

    /**
     * Router统一入口
     *
     * @param routerName 名
     * @param method     方法
     * @param entityId   实体ID
     * @param <T>        实体泛型
     * @return
     */
    @RequestMapping(value = "/operation", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public <T extends BaseEntity> AjaxResult routerCall(String routerName, String method, Long entityId) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_WARN);

        //参数判断
        if (null == entityId || StringUtil.isEmpty(routerName) || StringUtil.isEmpty(method)) {
            return ajaxResult;
        }

        //获得Router实例
        BaseRouter router = getRouter(routerName);
        if (null == router) {
            return ajaxResult;
        }

        //加载实体
        T entity = router.loadEntity(entityId);
        if (null == entity) {
            return ajaxResult;
        }

        //鉴权
        if (!router.isAuthorizedToCall(getCurrentUser().getId(), method)) {
//            return ajaxResult;
        }

        //call router's method
        try {
            //两个对象有共同的设置
            //Router调用实例的方法(method名的方法)
            AjaxResult routerResult = router.callMethod(getCurrentUser(), entity, method, null);
            ajaxResult.setStatusCode(routerResult.getStatusCode());
        } catch (BusinessException e) {
//            e.printStackTrace();
            ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_ERROR);
            ajaxResult.setMessage(e.getMessage());
        } catch (Exception e) {
//            e.printStackTrace();
            ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_ERROR);
            ajaxResult.setMessage(e.getMessage());
        }
        return ajaxResult;
    }

}
