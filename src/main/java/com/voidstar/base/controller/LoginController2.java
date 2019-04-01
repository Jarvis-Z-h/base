package com.voidstar.base.controller;

import com.voidstar.base.entity.User;
import com.voidstar.base.service.RoleService;
import com.voidstar.base.service.UserServ;
import com.voidstar.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController2 extends BaseController {

    @Autowired
   // UserService userService;  //没有使用强哥的service
    UserServ userService;
    //注入这个一会儿在权限管理中，展示角色列表
    @Autowired
    RoleService roleService;

    //登陆验证
    @PostMapping("/verify")
    public String login(@RequestParam("id")Integer id, @RequestParam("password")String password, Map<String,Object> result, HttpSession session) {

        User user = userService.getUserById(id);
        System.out.println(user);
        if (user != null) {
            //通过用户传过的UrZH调用数据库里的数据，检查这个用户名是否存在？
            if (password.equals(user.getPassword())) {
//密码比较数据库正确过后
                if (user.getStatus() == 0) {
                    //登陆成功，但是账号被禁用
                    result.put("msg","账号被禁用");
                    log.info("账号:" + id + ",暂时被禁用");
                    return "login";
                } else {
                    //登陆成功
                    //这里添加用户角色权限的列表，一会儿进入主页要展示的e（空着）
                    //把用户存进session里
                    session.setAttribute("user", user);
                    log.info("账号:" + id + ",登录成功");
                    session.setAttribute("name",user.getName());
                    return "redirect:/main.html";
                }
            } else {
                //用户账号密码错误
              //  result = "2";
                log.info("账号:" + id + ",用户密码错误");
                result.put("msg","用户名或密码错误");
                return "login";
            }
        } else {
         //   result = "3";
            result.put("msg","用户名不存在");
            log.info("账号:" + id + ",用户不存在");
            return "login";
        }
    }

}
