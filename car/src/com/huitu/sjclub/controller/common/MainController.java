package com.huitu.sjclub.controller.common;

import com.huitu.sjclub.controller.admin.BaseController;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.RoleService;
import com.huitu.sjclub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller("adminMainController")
@RequestMapping("/admin/common")
public class MainController extends BaseController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "roleServiceImpl")
    private RoleService roleService;
    @RequestMapping("/main")
    public String main(){
        System.out.println(">>>>>>>>>>>>>>>");
        return "/admin/main/index";
       /* User user=userService.getCurrent();

        if(user!=null){
            if(type!=null&&(!"".equals(type))){
                modelMap.addAttribute("type",type);
                return "/admin/main/index";
            }else{
                List<String> authorities= userService.findAuthorities(user.getId());
                if(authorities.contains("sjclub:manager")){
                    //系统管理员
                    return "/admin/main/index";
                }else if(user.getClub()!=null){
                    //社团成员
                    return "/admin/main/index";
                }else {
                    return "/admin/main/select";
                }
            }


        }else{
            //没有登录
            return "/sjclub/index";
        }*/

    }


}
