package com.voidstar.base.auth;

import com.voidstar.base.entity.Permission;
import com.voidstar.base.entity.Role;
import com.voidstar.base.entity.User;
import com.voidstar.base.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 邹强
 * @date 19-3-13
 */
public class Realm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    /**
     * add permissions
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // get login name
        String name= (String) principalCollection.getPrimaryPrincipal();
        User user = userService.getByName(name);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roles = userService.getRoles(user);
        roles.forEach(role -> {
            simpleAuthorizationInfo.addRole(role.getRoleName());
            role.getPermissions().forEach(permission -> {
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
            });
        });
        return simpleAuthorizationInfo;
    }

    /**
     * get authenticationInfo
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String userName = token.getUsername();

        Object principal = userName;

        String credentials = userService.getByName(userName).getPassword();
        String realmName = getName();
        AuthenticationInfo info = null;

        ByteSource credentialsSalt = ByteSource.Util.bytes(userName);

        return new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
    }
}
