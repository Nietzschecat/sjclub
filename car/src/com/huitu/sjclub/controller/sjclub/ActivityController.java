package com.huitu.sjclub.controller.sjclub;

import com.huitu.sjclub.Message;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubActivity;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.ClubActivityService;
import com.huitu.sjclub.service.ClubService;
import com.huitu.sjclub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/4/1.
 */
@Controller("sjclubActivityController")
@RequestMapping("/sjclub/activity")
public class ActivityController extends BaseController {

    @Resource(name = "clubActivityServiceImpl")
    private ClubActivityService clubActivityService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @RequestMapping(value = "/active")
    public String active(ModelMap modelMap){
        List<ClubActivity> list= clubActivityService.findAll();
        modelMap.addAttribute("clubActivitys",list);
        modelMap.addAttribute("url","active");
        return "/sjclub/active";
    }
    //加入活动
    @RequestMapping(value = "/joinActivity")
    public String joinActivity(Long id,RedirectAttributes redirectAttributes){
        ClubActivity clubActivity=clubActivityService.find(id);

            User user=  userService.getCurrent();
            if(user!=null){
                if(user.getClubActivity()!=null){
                    if(user.getClubActivity().getId()==clubActivity.getId()){
                        addFlashMessage(redirectAttributes, Message.error("加入失败，已经参加了"));
                        return "redirect:active.jhtml";
                    }
                }
                user.setClubActivity(clubActivity);
                userService.update(user);
                //没结束
                addFlashMessage(redirectAttributes, Message.success("加入成功"));
            }else{
                addFlashMessage(redirectAttributes, Message.error("加入失败，没有登录"));
            }
        return "redirect:active.jhtml";
    }

}
