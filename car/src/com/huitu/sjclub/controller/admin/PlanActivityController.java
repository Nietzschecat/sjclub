package com.huitu.sjclub.controller.admin;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.*;
import com.huitu.sjclub.service.ClubActivityService;
import com.huitu.sjclub.service.DepartmentService;
import com.huitu.sjclub.service.PlanActivityService;
import com.huitu.sjclub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/4/7. 活动安排
 */
@Controller("adminPlanActivityController")
@RequestMapping("/admin/plan_activity")
public class PlanActivityController extends BaseController {

    @Resource(name = "planActivityServiceImpl")
    private PlanActivityService planActivityService;

    @Resource(name = "departmentServiceImpl")
    private DepartmentService departmentService;

    @Resource(name = "clubActivityServiceImpl")
    private ClubActivityService clubActivityService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @RequestMapping("/loadList")
    public String load(){
        return "/admin/plan_activity/list";
    }

    @RequestMapping("/list")
    public @ResponseBody
        Map<String,Object> list(HttpServletRequest request){
        String page=request.getParameter("page");
        String limit=request.getParameter("limit");
        Map<String,Object> map=new HashMap<String,Object>();
        User user=userService.getCurrent();
        if(page==null||"".equals(page)){
            //查询所有
            List<PlanActivity> planActivities= planActivityService.findAll();
            List<Map<String,Object>>  mapList= getMapList(planActivities,user);
            map.put("count",planActivities.size());
            map.put("data",mapList);
        }else{
            Pageable pageable=new Pageable();
            Integer pageNumer=Integer.parseInt(page);
            Integer pageSize=Integer.parseInt(limit);
            pageable.setPageNumber(pageNumer);
            pageable.setPageSize(pageSize);
            Page<PlanActivity> planActivityPage= planActivityService.findPage(pageable,null);
            List<Map<String,Object>>  mapList= getMapList(planActivityPage.getContent(),user);
            map.put("count",pageSize);
            map.put("data",mapList);
        }
        map.put("code", 0);
        map.put("msg","信息成功");
        return map;
    }

    //*
    private List<Map<String,Object>> getMapList(List<PlanActivity> list,User user){
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
        Club club=user.getClub();
        if(club!=null){
            for(PlanActivity planActivity:list){
                if(planActivity.getClubActivity()!=null&&planActivity.getClubActivity().getClub().getId()==club.getId()){
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("id",planActivity.getId());
                    map.put("title",planActivity.getClubActivity().getTitle());
                    map.put("content", planActivity.getContent());
                    map.put("time", planActivity.getTime());
                    map.put("timeLen",planActivity.getTimeLen());
                    map.put("department",planActivity.getDepartment().getName());//签到人数（参与人数）
                    mapList.add(map);
                }
            }
        }

        return mapList;
    }

    @RequestMapping(value = "/add")
    public String add(Long id, ModelMap modelMap){
        List<ClubActivity> activities= clubActivityService.findAll();
        modelMap.addAttribute("clubActivitys",activities);
        List<Department> departments=departmentService.findAll();
        modelMap.addAttribute("departments",departments);
        if(id!=null){
            PlanActivity planActivity= planActivityService.find(id);
            modelMap.addAttribute("planActivity",planActivity);
        }
        return "/admin/plan_activity/add";
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>  save(PlanActivity planActivity,HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();

        try{
            ClubActivity clubActivity=clubActivityService.find(planActivity.getClubActivity().getId());
            Department department=departmentService.find(planActivity.getDepartment().getId());
            if(planActivity.getId()!=null){
                PlanActivity planActivity1=  planActivityService.find(planActivity.getId());
                planActivity1.setClubActivity(clubActivity);
                planActivity1.setTime(planActivity.getTime());
                planActivity1.setContent(planActivity.getContent());
                planActivity1.setTimeLen(planActivity.getTimeLen());
                planActivity1.setBz(planActivity.getBz());
                planActivity1.setDepartment(department);
                planActivityService.update(planActivity1);
            }else{
                User user=userService.getCurrent();
                planActivity.setClubActivity(clubActivity);
                planActivity.setDepartment(department);
                planActivity.setUser(user);
                planActivityService.save(planActivity);
            }
            map.put("code","200");
        }catch (Exception ex){
            map.put("code","500");
            ex.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> delete(Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        if(id!=null){
            try {
                PlanActivity planActivity=planActivityService.find(id);
                planActivity.setDepartment(null);
                planActivity.setClubActivity(null);
                planActivity.setUser(null);
                planActivityService.delete(id);
                map.put("code",200);
            }catch (Exception ex){
                ex.printStackTrace();
                map.put("code",500);
            }
        }else{
            map.put("code",500);
        }
        return  map;
    }

}
