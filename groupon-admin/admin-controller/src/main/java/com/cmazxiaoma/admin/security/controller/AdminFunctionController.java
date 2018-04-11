package com.cmazxiaoma.admin.security.controller;

import com.cmazxiaoma.admin.base.controller.AjaxResult;
import com.cmazxiaoma.admin.base.controller.BaseAdminController;
import com.cmazxiaoma.admin.common.cache.NativeCacheOperator;
import com.cmazxiaoma.admin.common.tree.EasyUITreeNode;
import com.cmazxiaoma.admin.security.entity.AdminFunction;
import com.cmazxiaoma.admin.security.service.AdminFunctionService;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * AdminFunctionController
 */
@Controller
@RequestMapping(value = "/security/adminFunction")
public class AdminFunctionController extends BaseAdminController {

    @Autowired
    private NativeCacheOperator cacheOperator;

    @Autowired
    private AdminFunctionService adminFunctionService;

    @RequestMapping(value = "/index")
    public String functionList(Model model, Search search) {
        return "/security/menu/menu_list";
    }

    @RequestMapping(value = "/listFunction")
    @ResponseBody
    public PagingResult<AdminFunction> listFunction(Model model, Search search) {
        if (search.getConditionList().size() == 0) {
            return new PagingResult<AdminFunction>(0, new ArrayList<AdminFunction>(), 0, 0);
        }

        return this.adminFunctionService.getPage(search);
    }

    @RequestMapping(value = "/addEditFunction", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addEditFunction(AdminFunction adminFunction) {
        AjaxResult ajaxResult = new AjaxResult();
        if (null == adminFunction.getId() || 0 == adminFunction.getId()) {
            adminFunction.setCreateTime(new Date());
            cacheOperator.addAdminFunction(adminFunctionService.save(adminFunction));
        } else {
            adminFunction.setUpdateTime(new Date());
            adminFunctionService.update(adminFunction);
            cacheOperator.replaceAdminFunction(adminFunction);
        }
        ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
        return ajaxResult;
    }

    @RequestMapping(value = "/deleteFunction", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult deleteFunction(AdminFunction adminFunction) {
        AjaxResult ajaxResult = new AjaxResult();
        if (null != adminFunction.getId() && 0 != adminFunction.getId()) {
            adminFunctionService.delete(adminFunction.getId());
            cacheOperator.removeAdminFunction(adminFunction);
            ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
        } else {
            ajaxResult.setMessage("删除错误");
            ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_WARN);
        }
        return ajaxResult;
    }

    @RequestMapping(value = "/buildFunctionTreeForEdit", method = RequestMethod.POST)
    @ResponseBody
    public List<EasyUITreeNode> buildFunctionTreeForEdit() {
        EasyUITreeNode root = null;
        List<AdminFunction> list = cacheOperator.getAdminFunctions();
        for (AdminFunction func : list) {
            if (func.getId() == 1) {
                root = new EasyUITreeNode(func.getId(), func.getParentId(), func.getName(), func.getState());
                root.addAttribute("id", func.getId());
                break;
            }
        }
        buildTree(list, root);
        List<EasyUITreeNode> result = new ArrayList<>();
        result.add(root);
        return result;
    }

    private void buildTree(List<AdminFunction> list, EasyUITreeNode parent) {
        EasyUITreeNode node;
        AdminFunction func;
        for (Iterator<AdminFunction> ite = list.iterator(); ite.hasNext(); ) {
            func = ite.next();
            if (func.getParentId() == parent.getId()) {
                node = new EasyUITreeNode(func.getId(), func.getParentId(), func.getName(), func.getState());
                node.addAttribute("id", func.getId());
                parent.getChildren().add(node);
                buildTree(list, node);
            }
        }
    }

}
