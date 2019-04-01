package com.voidstar.base.service;

import com.voidstar.base.entity.Role;


import java.util.List;

public interface RoleServ {

    //添加一种角色
    public int addRole(Role role);

    //通过Id查询一种角色
    public Role getRoleById(Integer id);

    //得到角色列表
    public List<Role> getRoleList();

    //删除一种角色
    public void deleteRoleById(Integer id);



}
