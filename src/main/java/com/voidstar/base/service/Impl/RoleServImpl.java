package com.voidstar.base.service.Impl;

import com.voidstar.base.entity.Role;
import com.voidstar.base.mapper.RoleDao;
import com.voidstar.base.service.RoleServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServImpl implements RoleServ {
    @Autowired
   private RoleDao roleDao;

    public int addRole(Role role) {
        int n = roleDao.addRole(role);
        return n;
    }

    @Override
    public Role getRoleById(Integer id) {
        Role role = roleDao.getRoleById(id);
        return role;
    }

    @Override
    public List<Role> getRoleList() {
        List<Role> roleList = roleDao.getRoleList();
        return roleList;
    }

    @Override
    public void deleteRoleById(Integer roleId) {
roleDao.deleteRoleById(roleId);
    }
}
