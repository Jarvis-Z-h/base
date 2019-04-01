package com.voidstar.base.service;

import com.voidstar.base.entity.User;

import java.util.List;
import java.util.Map;

public interface UserServ {
    public User getUserByName(String name);

    public Map<String,String> addUser(User user);

    public User getUserById(Integer id) ;

    public List<User> getUserList();

    public void editUser(User mgrUser);

    public void deleteUserById(Integer id);

    public void deleteUserByCode(String code);

    public Map<String,String> getUserByCode(String userCode);
//重置一个用户的密码，管理员修改
    public void editUserPassword(User user);
}
