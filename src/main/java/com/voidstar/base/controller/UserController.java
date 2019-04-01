package com.voidstar.base.controller;

import com.voidstar.base.entity.Role;
import com.voidstar.base.entity.StarSession;
import com.voidstar.base.entity.User;


import com.voidstar.base.entity.UserRole;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.voidstar.base.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author 邹强
 * @date 19-2-27
 */
//@RestController
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    //对自动生成的创建日期和最后修改日期进行格式化

    @Autowired
    //private UserService userService;  (没有注入强哥的service层)
    private UserServ userService;
    //注入角色，一会儿修改用户需要调用该用户对应的角色id
    @Autowired
    private RoleServ roleService;
    @Autowired
    private UserRoleService userRoleService;

  /*  @RequestMapping(value = "test")
    public String test(HttpServletRequest request) {
        Map<String, Object> cv = new HashMap<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        cv.put("createdTime", LocalDateTime.parse("2019-03-13 18:26:03", df));
        User user = userService.getByMap(cv);
        return user.getName();
    }*/

    //通过名字查询一个用户
    @RequestMapping(value = "/getUser")
    public User getUserByName(@RequestParam("name") String name){
        User user = userService.getUserByName(name);
        System.out.println(user);
        return user;
    }
    /**
     * @return java.lang.String 返回类型
     * @Title: add
     * @Description: (添加用户)
     * params [User, result]
     */
   @PostMapping("/user/addUser")
    @ResponseBody
    public String add(User user) {
        String code;
        String msg;
       User userById = userService.getUserById(user.getId());

       System.out.println("添加人员开始");
       System.out.println("添加的人员的角色id是："+user.getRoles().indexOf(0));
        if (userById==null) try {
//如果这个添加的用户在数据库是不存在的，才开始添加
            User mgrUser = new User();
            mgrUser.setId(user.getId());
            mgrUser.setGender(user.getGender());
            mgrUser.setCode(user.getCode());
            mgrUser.setName(user.getName());
            mgrUser.setPassword(user.getPassword());
            mgrUser.setCreatedTime(LocalDateTime.now());
            mgrUser.setUpdatedTime(LocalDateTime.now());
            mgrUser.setTelephone(user.getTelephone());
            mgrUser.setStatus(user.getStatus());

            //给这个用户添加角色
            UserRole userRole=new UserRole() ;
            userRole.setUserId(user.getId());
            userRole.setRoleId(user.getRoles().indexOf(0));
            userRoleService.addUserRole(userRole);

            Map<String, String> returnMap = userService.addUser(mgrUser);
            code = returnMap.get("code");
            msg = returnMap.get("msg");
        } catch (Exception e) {
            e.printStackTrace();
            code = returnState.fail.name();
            msg = "保存失败";
        }
        else {
            code = returnState.fail.name();
            msg = "添加的用户存在";
        }

        return "{\"code\":\"" + code + "\",\"msg\":\"" + msg + "\"}";
    }

    //判断一个用户是否存在，通过code查询
    //code:success/fail
    //msg:人员不存在/用户存在
    @RequestMapping(path = "/getUserByCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> getUserByCode(String code) {
        Map<String, String> result = userService.getUserByCode(code);
        return  result;
    }

    //通过Id查询一个用户
    @RequestMapping(path = "/getUserById", method = RequestMethod.GET)
    @ResponseBody
    public User getUserById(Integer id) {
        User user = userService.getUserById(id);
        return user;
    }

    //查询用户列表
    @RequestMapping(path = "/getUserList", method = RequestMethod.POST)
    @ResponseBody
    public String getUserList() {
        Map<String,Object> res=new HashMap<String,Object>();
      JSONArray tree = null;
    JSONObject t=null;
        List<User> userList = userService.getUserList();
        int n = userList.size();
        System.out.println(n);
       tree = JSONArray.fromObject(userList);
        res.put("code",0);
        res.put("msg","");
        res.put("count",n);
        res.put("data",tree);
        JSONObject object = JSONObject.fromObject(res);

        return object.toString();
    }

    //修改一个用户
    @RequestMapping(path = "/editUser", method = RequestMethod.POST)
    @ResponseBody
    public void editUser(User user,@RequestParam("role") Integer roleId){

        //设置修改这个用户的时间
        user.setUpdatedTime(LocalDateTime.now());
        System.out.println("修改的用户信息为"+user);
        //拿到用户修改的这个角色id
        UserRole userRole=new UserRole();
        userRole.setUserId(user.getId());
        System.out.println("修改的用户id是："+user.getId());
        userRole.setRoleId(roleId);
        userRoleService.editUserRole(userRole);
        //修改这个用户除角色以外的字段
        userService.editUser(user);
    }

    //通过id删除一个用户
    @RequestMapping(path = "/deleteUserById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteUserById(@PathVariable("id") Integer id) {
        String code;
        String msg;
        System.out.println("开始删除用户"+id);
       try {
           userService.deleteUserById(id);
           userRoleService.deleteUserRole(id);
           code="success";
           msg="删除用户成功";
       }catch (Exception e){
           code="fail";
           msg="删除用户失败";
       }
        System.out.println("删除用户完毕"+id);
        return "{\"code\":\"" + code + "\",\"msg\":\"" + msg + "\"}";
    }

    @RequestMapping(path = "/deleteUserByCode", method = RequestMethod.POST)
    @ResponseBody
    public void deleteUserByCode(String code){
        userService.deleteUserByCode(code);
    }

    //修改登陆的这个管理员的密码
    @PostMapping("/editPassword")
    @ResponseBody
    public String editPassword( String password,String newPassword1,String newPassword2){
        System.out.println("开始修改登陆的这个管理员的密码了");
        String code=null;
        String msg=null;
        //拿到在登陆成功时就存在session里的user

        try {
            System.out.println("用户新改的密码是："+newPassword1);
            User user =(User) session.getAttribute("user");
            System.out.println("登陆这个用户，修改用户的id是"+user.getId());
            System.out.println(user.getPassword());
            if(password.equalsIgnoreCase(user.getPassword())&&newPassword1.equalsIgnoreCase(newPassword2)){
                //原密码验证成功,且两次，确认密码相同
                user.setPassword(newPassword1);
                user.setUpdatedTime(LocalDateTime.now());
                userService.editUser(user);
                code="success";
                msg="修改密码成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            code="fail";
            msg="修改密码失败";
        }
        return "{\"code\":\"" + code + "\",\"msg\":\"" + msg + "\"}";
    }

    //重置用户的密码，(管理员修改成一个固定的)
    @GetMapping("/resetPassword/{id}")
    @ResponseBody
    public String resetPassword(@PathVariable("id") Integer id){
        System.out.println("开始修改用户的密码了");
        String code=null;
        String msg=null;
        //拿到在登陆成功时就存在session里的user
        try {
            User user = userService.getUserById(id);
           user.setUpdatedTime(LocalDateTime.now());
           user.setPassword("123456");
           userService.editUserPassword(user);
                code="success";
                msg="重置密码成功，重置为123456";

        }catch (Exception e){
            e.printStackTrace();
            code="fail";
            msg="重置密码失败";
        }
        return "{\"code\":\"" + code + "\",\"msg\":\"" + msg + "\"}";
    }

    //搜索一个用户
    @PostMapping("/selectUser")
    //@ResponseBody
    public String selectUser(@RequestParam("id") Integer id, Model model){
        System.out.println("开始一个用户，通过id了");
            User user = userService.getUserById(id);
            List<User> list=new ArrayList<User>();
          if(user!=null){
              List<Role> userRoleList= userRoleService.getUserRoleById(user.getId());
              user.setRoles(userRoleList);
              list.add(user);
              //把数据放在请求域当中
              model.addAttribute("users",list);
              model.addAttribute("code","success");
              model.addAttribute("msg","查询用户成功");
          }else {
              model.addAttribute("code","fail");
              model.addAttribute("msg","查询用户不存在");
          }
        return "baseArrive/user_manager";
    }


}
