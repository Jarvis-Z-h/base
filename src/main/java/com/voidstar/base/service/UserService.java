package com.voidstar.base.service;

import com.voidstar.base.entity.Permission;
import com.voidstar.base.entity.Role;
import com.voidstar.base.entity.StarSession;
import com.voidstar.base.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 邹强
 * @date 19-2-27
 */
@Service
public class UserService extends BaseService<User> {

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    /**
     * login
     *
     * @param userName
     * @param password
     * @return
     */
    public boolean login(String userName, String password) {
        Subject loginUser = SecurityUtils.getSubject();
        if (!loginUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, encryptPassword(userName, password));
            token.setRememberMe(true);
            try {
                loginUser.login(token);
            }
            catch (AuthenticationException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * encrypt password
     *
     * @param userName
     * @param password
     * @return
     */
    public String encryptPassword(String userName, String password) {
        ByteSource credentialsSalt = ByteSource.Util.bytes(userName);
        Object object = new SimpleHash(Md5Hash.ALGORITHM_NAME, password, credentialsSalt, 1024);
        return object.toString();
    }


    /**
     * add user with md5
     *
     * @param user
     * @return
     */
    public User addUser(User user) {
        user.setPassword(encryptPassword(user.getName(), user.getPassword()));
        insert(user);

        return user;
    }

    /**
     * get user by name
     *
     * @param name
     * @return
     */
    public User getByName(String name) {
        Map<String, Object> condition = new ConcurrentHashMap<>();
        condition.put("name", name);
        return getByMap(condition);
    }

    /**
     * add role and it's permissions
     *
     * @param user
     * @param role
     * @param permissions
     * @return
     */
  /*  public Role addRole(User user, Role role, List<Permission> permissions) {
        role.setUserId(user.getId());
        permissions.forEach(permission -> {
            permissionService.insert(permission);
        });
        roleService.insert(role);

        return role;
    }*/

    /**
     *
     *
     * @param user
     * @return
     */
    public List<Role> getRoles(User user) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", user.getId());
        List<Role> roles = roleService.findByConditions(condition);
        roles.forEach(role -> {
            Map<String, Object> cv = new HashMap<>();
            cv.put("roleId", role.getId());
            role.setPermissions(permissionService.findByConditions(cv));
        });

        return roles;
    }
}