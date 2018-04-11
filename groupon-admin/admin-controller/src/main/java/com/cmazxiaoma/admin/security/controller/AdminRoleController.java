package com.cmazxiaoma.admin.security.controller;

import com.cmazxiaoma.admin.base.controller.AjaxResult;
import com.cmazxiaoma.admin.base.controller.BaseAdminController;
import com.cmazxiaoma.admin.security.entity.AdminRole;
import com.cmazxiaoma.admin.security.entity.AdminRoleFunction;
import com.cmazxiaoma.admin.security.service.AdminRoleService;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AdminRoleController
 */
@Controller
@RequestMapping(value = "/security/adminRole")
public class AdminRoleController extends BaseAdminController {

    @Autowired
    private AdminRoleService adminRoleService;

    @RequestMapping(value = "/index")
    public String adminRoleList(Model model, Search search) {
        return "/security/adminrole/admin_role_list";
    }

    @RequestMapping(value = "/listAdminRole", method = RequestMethod.POST)
    @ResponseBody
    public PagingResult<AdminRole> getAdminRole(Model model, Search search) {
        return adminRoleService.getAdminRole(search);
    }

    @RequestMapping(value = "/getAdminRoleFunctionIdsByRoleId", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getAdminRoleFunctionIdsByRoleId(Long roleId) {
        if (null == roleId) {
            return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "参数角色ID为空");
        }
        List<AdminRoleFunction> roleFunctions = this.adminRoleService.getAdminRoleFunctionsByRoleId(roleId);
        if (null == roleFunctions || roleFunctions.size() == 0) {
            return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_SUCCESS, "");
        }
        StringBuilder ids = new StringBuilder();
        roleFunctions.forEach(rf -> ids.append(rf.getAdminFunctionId()).append(","));
        ids.deleteCharAt(ids.length() - 1);
        return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_SUCCESS, ids.toString());
    }

    @RequestMapping(value = "/addEditAdminRole", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addEditAdminRole(AdminRole adminRole, String functionIds) {
        List<String> idList = Arrays.asList(functionIds.split(","));
        List<Long> ids = idList.stream().map(id -> Long.valueOf(id)).collect(Collectors.toList());
        AjaxResult ajaxResult = new AjaxResult();
        if (null == adminRole.getId() || 0 == adminRole.getId()) {
            adminRole.setCreateTime(new Date());
            adminRoleService.saveAdminRole(adminRole, ids);
        } else {
            adminRole.setUpdateTime(new Date());
            adminRoleService.updateAdminRole(adminRole, ids);
        }
        ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
        return ajaxResult;
    }

}
