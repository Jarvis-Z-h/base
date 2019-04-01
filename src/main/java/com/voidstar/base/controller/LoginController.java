package com.voidstar.base.controller;

import com.voidstar.base.entity.StarSession;
import com.voidstar.base.entity.User;
import com.voidstar.base.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 邹强
 * @date 19-3-13
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    UserService userService;

    //退出的时候是get请求，主要是用于退出
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    //post登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestBody Map map){
        //添加用户认证信息
        return userService.login(map.get("username").toString(), map.get("password").toString()) ? "OK" : "^*^";
    }

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    //登出
    @RequestMapping(value = "/logout")
    public String logout(){
        return "logout";
    }

    //错误页面展示
    @RequestMapping(value = "/error",method = RequestMethod.POST)
    public String error(){
        return "error ok!";
    }

    //数据初始化
    @RequestMapping(value = "/addUser")
    public String addUser(HttpServletRequest request){
        StarSession session = getSession(request);
        User user = new User();
        user.setCode("mic-01");
        user.setName("michael1");
        user.setPassword("void");
        user = userService.addUser(user);
        return "addUser is ok! \n" + user;
    }
}
