package com.voidstar.base.service.Impl;

import com.voidstar.base.entity.Role;
import com.voidstar.base.entity.UserRole;
import com.voidstar.base.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Override
    public int addUserRole(UserRole userRole) {
        return 0;
    }

    @Override
    public List<Role> getUserRoleById(Integer id) {
        return null;
    }

    @Override
    public void editUserRole(UserRole userRole) {

    }

    @Override
    public void deleteUserRole(Integer userId) {

    }
}
