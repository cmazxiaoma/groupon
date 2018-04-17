package com.cmazxiaoma.admin.security.shirorealm;

import com.cmazxiaoma.admin.security.entity.AdminUser;
import com.cmazxiaoma.admin.security.service.AdminRoleService;
import com.cmazxiaoma.admin.security.service.AdminUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 */
@Service
public class ShiroDbRealm extends AuthorizingRealm {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminRoleService adminRoleService;

    public ShiroDbRealm() {
        super();
        //setAuthenticationTokenClass(UsernamePasswordToken.class);
        //setCredentialsMatcher(new HashedCredentialsMatcher(Sha1Hash.ALGORITHM_NAME));
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if (principalCollection == null) {
            throw new AuthorizationException("Principal对象不能为空");
        }
        //建议把权限放入缓存
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    /**
     * 用户登录的认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        char[] password = usernamePasswordToken.getPassword();

        if (StringUtils.isEmpty(username)) {
            throw new AccountException("用户名不能为空");
        }

        if (password == null) {
            throw new AccountException("密码不能为空");
        }

        AdminUser user = null;
        try {
            user = adminUserService.getUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }

        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }


}
