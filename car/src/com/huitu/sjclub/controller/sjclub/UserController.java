package com.huitu.sjclub.controller.sjclub;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huitu.sjclub.Filter;
import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.controller.admin.BaseController;
import com.huitu.sjclub.entity.Role;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.RoleService;
import com.huitu.sjclub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller("sjclubUserController")
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "roleServiceImpl")
    private RoleService roleService;

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @RequestMapping(value = "/checkAccount")
    public @ResponseBody String checkAccount(String account){
        boolean flag=userService.usernameExists(account);
        if(!flag){
            return "notExist";
        }else{
            return "ok";
        }
    }

    @RequestMapping(value = "/Register",method = RequestMethod.POST)
    public @ResponseBody String Register(User user){
        Role roles=roleService.find(4L);
        boolean flag=userService.usernameExists(user.getLoginName());
        if(flag){
            return "notExist";
        }else{
            Set<Role> roleSet=new HashSet<Role>();
            roleSet.add(roles);
            user.setRoles(roleSet);
            userService.save(user);
            return "ok";
        }

    }



}
