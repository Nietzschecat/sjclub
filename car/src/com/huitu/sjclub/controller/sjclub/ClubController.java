package com.huitu.sjclub.controller.sjclub;

import com.huitu.sjclub.Message;
import com.huitu.sjclub.controller.admin.*;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.Role;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.ClubService;
import com.huitu.sjclub.service.RoleService;
import com.huitu.sjclub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by admin on 2018/4/1.
 */
@Controller("sjclubClubController")
@RequestMapping("/sjclub/club")
public class ClubController extends BaseController {

    @Resource(name = "clubServiceImpl")
    private ClubService clubService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "roleServiceImpl")
    private RoleService roleService;
    @RequestMapping(value = "/joinClub",method = {RequestMethod.POST,RequestMethod.GET})
    public String  joinClub(Long id,RedirectAttributes redirectAttributes){
        Club club=clubService.find(id);
        User user=  userService.getCurrent();
        Map<String,Object> map=new HashMap<String, Object>();


        if(user!=null){
            //检查用户是否是社团的会员，并返回已经参加的社团数目
            int clubCount = 0;
           /* List<User> users= club.getUsers();
            for(User user1:users){

                if(user1.getClub().getId()==club.getId()){
                    clubCount++;
                }
            }*/

            if(user.getClub()!=null){
                //如果用户已经是社团的成员了
                map.put("code","userIsClub");
                addFlashMessage(redirectAttributes, Message.error("加入失败，用户已经是社团的成员"));
            }/*else if(clubCount > 3){
                //如果用户参加的社团数目大于3，则不允许其再参加社团
                map.put("code","refuse");
                addFlashMessage(redirectAttributes, Message.error("加入失败，用户参加的社团数目大于3"));
            }*/else{
                List<Role> roles=roleService.findAll();
                if(!roles.isEmpty()){
                    Role role1=null;
                    for(Role role:roles){
                       List<String> authorities= role.getAuthorities();
                       if(authorities.contains("sjclub:user")){
                           role1=role;
                           break;
                       }
                    }
                    if(role1!=null){
                        Set<Role> roleset=new HashSet<Role>();
                        roleset.add(role1);
                        user.setRoles(roleset);
                    }
                }
                user.setClub(club);
                userService.update(user);
                //没结束
                map.put("code","ok");
                addFlashMessage(redirectAttributes, Message.error("加入成功"));
            }
        }else{
            map.put("code","error");
            addFlashMessage(redirectAttributes, Message.error("加入失败，没有登录"));
        }
        return "redirect:/sjclub/list.jhtml";
    }

}
