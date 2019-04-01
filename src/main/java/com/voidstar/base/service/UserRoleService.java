package com.voidstar.base.service;

import com.voidstar.base.entity.Role;
import com.voidstar.base.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    //添加一个员工对应一种角色
    public int addUserRole(UserRole userRole);

    //通过Id查询一个用户对应的角色名称
    public List<Role> getUserRoleById(Integer id);

    //修改一个用户的角色
    public void editUserRole(UserRole userRole);

    //删除一个用户，也对应他的角色
    public void deleteUserRole(Integer userId);
}
