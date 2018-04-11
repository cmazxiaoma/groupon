package com.cmazxiaoma.admin.security.controller;

import com.cmazxiaoma.admin.base.controller.AjaxResult;
import com.cmazxiaoma.admin.base.controller.BaseAdminController;
import com.cmazxiaoma.admin.security.entity.AdminRole;
import com.cmazxiaoma.admin.security.entity.AdminUser;
import com.cmazxiaoma.admin.security.service.AdminRoleService;
import com.cmazxiaoma.admin.security.service.AdminUserService;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.framework.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping(value = "/security/adminuser")
public class AdminUserController extends BaseAdminController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminRoleService adminRoleService;

    @RequestMapping(value = "/index")
    public String adminuserList(Model model, Search search) {
        return "/security/user/adminuser_list";
    }

    @RequestMapping(value = "/listAdminUser", method = RequestMethod.POST)
    @ResponseBody
    public PagingResult<AdminUser> listAdminUser(Model model, Search search) {
        return adminUserService.findAdminUserForPage(search);
    }

    @RequestMapping(value = "/addEditAdminUser", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addEditAdminUser(AdminUser adminUser, Integer resetPasswordCheck) {
        if (null == adminUser.getId() || 0 == adminUser.getId()) {
            adminUser.setPassword(EncryptionUtil.MD5(adminUser.getPassword()));
            adminUserService.addAdminUser(adminUser);
        } else {
            if (null != resetPasswordCheck && 1 == resetPasswordCheck) {
                adminUser.setPassword(EncryptionUtil.MD5(adminUser.getPassword()));
            }
            adminUserService.updateAdminUser(adminUser);
        }
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
        return ajaxResult;
    }

    @RequestMapping(value = "/deleteAdminUser", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult deleteAdminUser(AdminUser adminUser) {
        adminUserService.deleteAdminUser(adminUser.getId());
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
        return ajaxResult;
    }

    @RequestMapping(value = "/getAdminRole", method = RequestMethod.POST)
    @ResponseBody
    public List<AdminRole> getAdminRole(String idList) {
        if (null == idList || "".equals(idList)) {
            return null;
        }
        List<Long> ids = new ArrayList<>();
        for (String id : idList.split(",")) {
            ids.add(Long.valueOf(id));
        }
        return this.adminRoleService.getAdminRoleByIds(ids);
    }

    @RequestMapping(value = "/loadAll", method = RequestMethod.POST)
    @ResponseBody
    public List<AdminUser> loadAll() {
        return adminUserService.getAll();
    }
}