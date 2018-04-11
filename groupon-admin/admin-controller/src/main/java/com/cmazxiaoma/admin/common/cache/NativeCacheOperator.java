package com.cmazxiaoma.admin.common.cache;

import com.cmazxiaoma.admin.security.entity.AdminFunction;
import com.cmazxiaoma.admin.security.entity.AdminRole;
import com.cmazxiaoma.admin.security.service.AdminFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地缓存操作
 */
@Component
public class NativeCacheOperator {

    private List<AdminFunction> adminFunctionList;

    private Map<Long, List<AdminRole>> adminUserRoleMap = new HashMap<>();

    private NativeCacheOperator() {
    }

    @Autowired
    private AdminFunctionService adminFunctionService;

    @PostConstruct
    public void init() {
        adminFunctionList = adminFunctionService.getAll();
    }

    public List<AdminFunction> getAdminFunctions() {
        if (null == adminFunctionList) {
            adminFunctionList = adminFunctionService.getAll();
        }
        return adminFunctionList;
    }

    public void addAdminFunction(AdminFunction adminFunction) {
        adminFunctionList.add(adminFunction);
    }

    public void replaceAdminFunction(AdminFunction adminFunction) {
        for (AdminFunction function : adminFunctionList) {
            if (function.getId() == adminFunction.getId()) {
                adminFunctionList.remove(function);
                adminFunctionList.add(adminFunction);
                break;
            }
        }
    }

    public void removeAdminFunction(AdminFunction adminFunction) {
        for (AdminFunction function : adminFunctionList) {
            if (function.getId() == adminFunction.getId()) {
                adminFunctionList.remove(function);
                break;
            }
        }
    }

    public void setAdminRoles(Long adminUserId, List<AdminRole> adminRoles) {
        adminUserRoleMap.put(adminUserId, adminRoles);
    }

    public List<AdminRole> getAdminRoles(Long adminUserId) {
        return adminUserRoleMap.get(adminUserId);
    }

}
