package com.cmazxiaoma.admin.security.controller;

import com.cmazxiaoma.admin.base.controller.BaseAdminController;
import com.cmazxiaoma.admin.common.cache.NativeCacheOperator;
import com.cmazxiaoma.admin.common.tree.EasyUITreeNode;
import com.cmazxiaoma.admin.security.entity.*;
import com.cmazxiaoma.admin.security.service.AdminRoleService;
import com.cmazxiaoma.framework.util.EncryptionUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * LoginController
 */
@Controller
public class LoginController extends BaseAdminController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private NativeCacheOperator cacheOperator;

    @Autowired
    private AdminRoleService adminRoleService;

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public String login(Model model, HttpServletRequest request) {
        Subject user = SecurityUtils.getSubject();
        if (!user.isAuthenticated()) {
            return "/security/login";
        }

        return "redirect:/main";
    }

    @RequestMapping(value = "/main", method = {RequestMethod.POST, RequestMethod.GET})
    public String index(Model model, AdminUser curUser) {
        UsernamePasswordToken token = null;
        try {
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()) {
                curUser.setPassword(EncryptionUtil.MD5(curUser.getPassword()));
                token = new UsernamePasswordToken(curUser.getName(), curUser.getPassword());
                token.setRememberMe(true);//记住当前用户
                subject.login(token);
            } else {
                curUser = getCurrentUser();
            }

            List<AdminUserRole> adminUserRoles = (super.getCurrentUser()).getAdminUserRoles();
            if (!Objects.equals("admin", curUser.getName()) && (null == adminUserRoles || 0 == adminUserRoles.size())) {
                logger.info("ERP用户没有设置权限 : " + (super.getCurrentUser()).getName());
                return "/security/login";
            }

            List<Long> adminRoleIdList = new ArrayList<>();
            for (AdminUserRole role : adminUserRoles) {
                adminRoleIdList.add(role.getAdminRoleId());
            }
            if (!Objects.equals("admin", curUser.getName())) {
                List<AdminRole> adminRoles = this.adminRoleService.getAdminRoleByIds(adminRoleIdList);
                cacheOperator.setAdminRoles(super.getCurrentUser().getId(), adminRoles);
            }
            logger.info("ERP登录 : " + (super.getCurrentUser()).getName());
            return "/layout/main";
        } catch (Exception e) {
            if (null != token) {
                token.clear();
            }
            model.addAttribute("errorMsg", e);
            return "/security/login";
        }
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    public String logout(Model model, AdminUser currUser) {
        Subject user = SecurityUtils.getSubject();
        user.logout();
        return "/security/login";
    }

    @RequestMapping(value = "/buildFunctionTreeForNavigation", method = RequestMethod.POST)
    @ResponseBody
    public List<EasyUITreeNode> buildFunctionTreeForNavigation() {
        AdminUser user = super.getCurrentUser();

        Map<Long, EasyUITreeNode> nodeMap = new HashMap<>();
        Set<EasyUITreeNode> permissionAdminFunctionSet = new HashSet<>();

        if (Objects.equals("admin", user.getName())) {
            for (AdminFunction func : cacheOperator.getAdminFunctions()) {
                EasyUITreeNode node = new EasyUITreeNode(func.getId(), func.getParentId(), func.getName(), func.getState());
                node.addAttribute("url", func.getUrl());
                nodeMap.put(func.getId(), node);
                permissionAdminFunctionSet.add(node);
            }
        } else {
            List<AdminRole> adminRoles = cacheOperator.getAdminRoles(user.getId());

            List<Long> roleIds = new ArrayList<>();
            adminRoles.forEach(ar -> roleIds.add(ar.getId()));

            List<AdminRoleFunction> roleFunctions = adminRoleService.getAdminRoleFunctionsByRoleIds(roleIds);

            Set<Long> functionIds = new HashSet<>();
            roleFunctions.forEach(rf -> functionIds.add(rf.getAdminFunctionId()));

            for (AdminFunction func : cacheOperator.getAdminFunctions()) {
                EasyUITreeNode node = new EasyUITreeNode(func.getId(), func.getParentId(), func.getName(), func.getState());
                node.addAttribute("url", func.getUrl());
                nodeMap.put(func.getId(), node);
                if (functionIds.contains(func.getId())) {
                    permissionAdminFunctionSet.add(node);
                }
            }

            Set<EasyUITreeNode> tempPermissionSet = new HashSet<>();
            for (EasyUITreeNode node : permissionAdminFunctionSet) {
                completeTreeNode(nodeMap, tempPermissionSet, nodeMap.get(node.getParentId()));
            }

            permissionAdminFunctionSet.addAll(tempPermissionSet);
        }
        EasyUITreeNode root = nodeMap.get(1l);
        List<EasyUITreeNode> treeNodeList = new ArrayList<>(permissionAdminFunctionSet);
        Collections.sort(treeNodeList);
        buildTree(treeNodeList, root);

        List<EasyUITreeNode> result = new ArrayList<>();
        result.add(root);
        return result;
    }

    private void completeTreeNode(Map<Long, EasyUITreeNode> nodeMap, Set<EasyUITreeNode> tempPermissionAdminFunctionSet, EasyUITreeNode node) {
        if (node.getId() != 1) {
            tempPermissionAdminFunctionSet.add(node);
            completeTreeNode(nodeMap, tempPermissionAdminFunctionSet, nodeMap.get(node.getParentId()));
        }
    }

    private void buildTree(List<EasyUITreeNode> treeNodeList, EasyUITreeNode parent) {
        EasyUITreeNode node;
        for (Iterator<EasyUITreeNode> ite = treeNodeList.iterator(); ite.hasNext(); ) {
            node = ite.next();
            if (node.getParentId() == parent.getId()) {
                parent.getChildren().add(node);
                buildTree(treeNodeList, node);
            }
        }
    }

}
