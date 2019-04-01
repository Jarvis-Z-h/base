package com.voidstar.base.controller;


import com.voidstar.base.entity.Role;
import com.voidstar.base.entity.User;
import com.voidstar.base.entity.UserRole;
import com.voidstar.base.service.RoleServ;
import com.voidstar.base.service.UserRoleService;
import com.voidstar.base.service.UserServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/*
* 这个Controller专门用作主页里的一个或者二级菜单坐跳转到额外页面的用的
*
* */
@Controller
@RequestMapping("/jump")
public class JumpController extends BaseController {
    @Autowired
    private UserServ userService;
    @Autowired   //注入这个，一会儿会对所有的角色列表展现使用
    private RoleServ roleServ;
    @Autowired   //注入用户--角色
    private UserRoleService userRoleService;


    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public String toUserList(Model model){
        //得到用户列表
        List<User> userList = userService.getUserList();

        for(User user:userList){
          //  List<Role> roleList = roleServ.getRoleNameByUserId(user.getId());
            //获取每个用户对应id下对应的角色名字
            List<Role> userRoleList= userRoleService.getUserRoleById(user.getId());
            user.setRoles(userRoleList);

        }
        //放在请求域中
        model.addAttribute("users",userList);
        return "baseArrive/user_manager";
    }

    //跳转到要修改的用户的页面
    //会把选择的这个用户的信息覆写在表单里
    //要修改的用户id在路径上边
    @RequestMapping("/userForm/{id}")
    //这个传入的User,原本还用User的工具实体类
    public ModelAndView toEditUserForm(@PathVariable("id") Integer id){
        List<Role> roleList = roleServ.getRoleList();
        //查询出这个用户
        User user = userService.getUserById(id);
        //把拿到的角色列表放在请求域当中，一会儿返回去做展示
     model.addObject("type", "edit");
        model.addObject("roleList",roleList);
       model.addObject("editUser",user);
        model.setViewName("edit/editUser2");

        return model;
    }

    @RequestMapping("/pasManager")
    public String toEditPassword(){
      return "edit/toEditPage";
    }

    //去到添加一个用户的页面
    @RequestMapping("/toAddPage")
    public ModelAndView toAddPage(){
        //查询出所有的部门做页面下拉选的显示
        List<Role> roleList = roleServ.getRoleList();
        model.addObject("roleList",roleList);

   //   return "edit/addUserPage2";
        //修改、添加用户在同一个页面

     model.setViewName("edit/addUserPage2");
      return model;
    }




}
