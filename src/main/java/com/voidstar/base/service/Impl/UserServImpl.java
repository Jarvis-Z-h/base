package com.voidstar.base.service.Impl;

import com.voidstar.base.entity.User;
import com.voidstar.base.mapper.UserDao;
import com.voidstar.base.service.UserServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserServImpl implements UserServ {
    @Autowired
    private UserDao userDao;

    public User getUserByName(String name) {
        User user = userDao.getUserByName(name);
        return user;
    }

    @Override
    public Map<String, String> addUser(User user) {
        //这个Map是设置一个返回结果，一会儿前端那边还有两个参数：code 和 msg
        //无论成功与否，否在这儿设置这两个参数值
        User mgrMser = userDao.getUserById(user.getId());
        Map<String,String> result=new HashMap<String,String>();
        if (mgrMser == null) {
            userDao.addUser(user);
            result.put("code", "success");
            result.put("msg", "用户保存成功了");
        } else {
            result.put("code", "fail");
            result.put("msg", "添加人员已存在");
        }
        return result;
    }


    public Map<String,String> getUserByCode(String code) {
        Map<String,String> result=new HashMap<String,String>();

        User user = userDao.getUserByCode(code);
        if(user==null){
            result.put("code", "fail");
            result.put("msg", "人员不存在");
        }else {
            result.put("code", "success");
            result.put("msg", "用户存在");
        }
        return result;
    }

    //重置一个用户的密码，管理员修改
    public void editUserPassword(User user) {
        userDao.editUserPassword(user);

    }


    public User getUserById(Integer id) {
        User user = userDao.getUserById(id);
        return user;
    }

    public List<User> getUserList() {
        List<User> list = userDao.getUserList();
        return list;
    }


    public void editUser(User user) {
userDao.editUser(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        userDao.deleteUserById(id);
    }


    public void deleteUserByCode(String code) {
        userDao.deleteUserByCode(code);
    }

}
