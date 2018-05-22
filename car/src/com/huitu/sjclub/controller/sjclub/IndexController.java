package com.huitu.sjclub.controller.sjclub;

import com.huitu.sjclub.Filter;
import com.huitu.sjclub.Message;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubActivity;
import com.huitu.sjclub.entity.ClubNotice;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.ClubActivityService;
import com.huitu.sjclub.service.ClubNoticeService;
import com.huitu.sjclub.service.ClubService;
import com.huitu.sjclub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by admin on 2018/3/28.
 */
@Controller("sjclubIndexController")
@RequestMapping(value = "/sjclub")
public class IndexController extends BaseController {

    @Resource(name = "clubServiceImpl")
    private ClubService clubService;

    @Resource(name = "clubActivityServiceImpl")
    private ClubActivityService clubActivityService;


    @Resource(name = "clubNoticeServiceImpl")
    private ClubNoticeService clubNoticeService;

    //首页
    @RequestMapping(value = "/index")
    public String index(ModelMap modelMap){
        List<Filter> filters=new ArrayList<Filter>();
        Filter filter=new Filter();
        filter.setOperator(Filter.Operator.eq);
        filter.setValue("0");
        filter.setProperty("isStart");
        List<ClubNotice> clubNotices= clubNoticeService.findList(null,filters,null);
        modelMap.addAttribute("clubNotices",clubNotices);

        List<Filter> filters1=new ArrayList<Filter>();
        Filter filter1=new Filter();
        filter1.setOperator(Filter.Operator.eq);
        filter1.setValue("1");
        filter1.setProperty("tenGood");
        List<Club> clubs= clubService.findList(null,filters1,null);
        modelMap.addAttribute("clubs",clubs);


        List<ClubActivity> clubActivities=clubActivityService.findAll();
        List<ClubActivity> activies=new ArrayList<ClubActivity>();
        for(ClubActivity activie:clubActivities){
            if(activies.size()<=3){
                activies.add(activie);
            }
        }
        modelMap.addAttribute("activies",activies);
        modelMap.addAttribute("url","index");
        return "/sjclub/index";
    }

    @RequestMapping(value = "/active")
    public String active(ModelMap modelMap){
        List<ClubActivity> list= clubActivityService.findAll();
        modelMap.addAttribute("clubActivitys",list);
        modelMap.addAttribute("url","active");
        return "/sjclub/active";
    }
    @RequestMapping(value = "/list")
    public String list(ModelMap modelMap){
        List<Club> list= clubService.findAll();
        modelMap.addAttribute("clubList",list);
        modelMap.addAttribute("url","list");
        return "/sjclub/list";
    }
    @RequestMapping(value = "/about")
    public String about(ModelMap modelMap){
        modelMap.addAttribute("url","about");
        return "/sjclub/about";
    }

    @RequestMapping(value = "/list_detail")
    public String listDetail(Long id,ModelMap modelMap){
        Club club=clubService.find(id);
        modelMap.addAttribute("club",club);
        modelMap.addAttribute("url","list");
        return "/sjclub/list";
    }
    @RequestMapping(value = "/active_detail")
    public String activeDetail(Long id,ModelMap modelMap){
        ClubActivity clubActivity=clubActivityService.find(id);
        modelMap.addAttribute("clubActivity",clubActivity);
        List<ClubActivity> clubActivities= clubActivityService.findAll();
        List<ClubActivity> otherActivitys=new ArrayList<ClubActivity>();
        for(ClubActivity clubActivity1:clubActivities){
            if(clubActivity1.getId()!=id){
                otherActivitys.add(clubActivity1);
            }
        }
        modelMap.addAttribute("otherActivitys",otherActivitys);
        modelMap.addAttribute("url","active");
        return "/sjclub/active_detail";
    }



}
