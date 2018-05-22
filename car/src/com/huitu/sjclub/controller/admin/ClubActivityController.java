package com.huitu.sjclub.controller.admin;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/3/29.
 * 社团活动
 */
@Controller
@RequestMapping("/admin/club_activity")
public class ClubActivityController extends BaseController {

    @Resource(name = "clubActivityServiceImpl")
    private ClubActivityService clubActivityService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "clubServiceImpl")
    private ClubService clubService;

    @RequestMapping("/loadList")
    public String load(){
        return "/admin/activity/list";
    }

    @RequestMapping("/list")
    public @ResponseBody
    Map<String,Object> list(HttpServletRequest request){
        String page=request.getParameter("page");
        String limit=request.getParameter("limit");
        Map<String,Object> map=new HashMap<String,Object>();

        User user=userService.getCurrent();
        Club club=user.getClub();
        if(page==null||"".equals(page)){
            //查询所有
            List<ClubActivity> clubActivities= clubActivityService.findAll();
            List<Map<String,Object>>  mapList= getMapList(clubActivities);
            map.put("count",clubActivities.size());
            map.put("data",mapList);
        }else{
            if("admin".equals(user.getLoginName())){
                Pageable pageable=new Pageable();
                Integer pageNumer=Integer.parseInt(page);
                Integer pageSize=Integer.parseInt(limit);
                pageable.setPageNumber(pageNumer);
                pageable.setPageSize(pageSize);

                Page<ClubActivity> clubActivityPage= clubActivityService.findPage(pageable,null);
                List<Map<String,Object>>  mapList= getMapList(clubActivityPage.getContent());
                map.put("count",pageSize);
                map.put("data",mapList);
            }else{
                if(club!=null){
                    Pageable pageable=new Pageable();
                    Integer pageNumer=Integer.parseInt(page);
                    Integer pageSize=Integer.parseInt(limit);
                    pageable.setPageNumber(pageNumer);
                    pageable.setPageSize(pageSize);

                    Page<ClubActivity> clubActivityPage= clubActivityService.findPage(pageable,club);
                    List<Map<String,Object>>  mapList= getMapList(clubActivityPage.getContent());
                    map.put("count",pageSize);
                    map.put("data",mapList);
                }
            }
        }
        map.put("code", 0);
        map.put("msg","信息成功");
        return map;
    }

    //*
    private List<Map<String,Object>> getMapList(List<ClubActivity> list){
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();

        for(ClubActivity clubActivity:list){
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("id",clubActivity.getId());
                map.put("title",clubActivity.getTitle());
                User user=clubActivity.getUser();
                if(user!=null){
                    map.put("userName",user.getUserName());//活动创建者
                }
                map.put("userSize", clubActivity.getActivityUser().size());
                //map.put("userSize", clubActivity.get);
                //map.put("endTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(clubActivity.getEndTime()));
                map.put("isStart",clubActivity.getIsStart());
                map.put("content",clubActivity.getContent());
                map.put("spotCheckIns",clubActivity.getSpotCheckIns().size());//签到人数（参与人数）
                map.put("createDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(clubActivity.getCreateDate()));
                map.put("modifyDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(clubActivity.getModifyDate()));

                mapList.add(map);
        }
        return mapList;
    }

    @RequestMapping(value = "/add")
    public String add(Long id, ModelMap modelMap){
        if(id!=null){
            ClubActivity clubActivity= clubActivityService.find(id);
            modelMap.addAttribute("clubActivity",clubActivity);
            List<Club> clubs= clubService.findAll();
            modelMap.addAttribute("clubs",clubs);
        }
        return "/admin/activity/add";
    }

    @RequestMapping(value = "/view")
    public String view(Long id, ModelMap modelMap){
        if(id!=null){
            ClubActivity clubActivity= clubActivityService.find(id);
            modelMap.addAttribute("clubActivity",clubActivity);
        }
        return "/admin/activity/view";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>  save(ClubActivity clubActivity,HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            if(clubActivity.getClub()!=null&&clubActivity.getClub().getId()!=null){
              Club club=clubService.find(clubActivity.getClub().getId());
              clubActivity.setClub(club);
            }
            if(clubActivity.getId()!=null){
                ClubActivity clubActivity1=  clubActivityService.find(clubActivity.getId());
                clubActivity1.setTitle(clubActivity.getTitle());
                clubActivity1.setEndTime(clubActivity.getEndTime());
                clubActivity1.setContent(clubActivity.getContent());
                clubActivity1.setIsStart(clubActivity.getIsStart());
                clubActivity1.setImages(clubActivity.getImages());
                if(clubActivity.getClub()!=null){
                    clubActivity1.setClub(clubActivity.getClub());
                }
                clubActivityService.update(clubActivity1);
            }else{
                User user= userService.getCurrent();
                clubActivity.setUser(user);
                if((!"admin".equals(user.getLoginName()))&&user.getClub()==null){
                    //当前用户没有属于社团
                    map.put("code",250);
                    return map;
                }
                clubActivity.setClub(user.getClub());
                clubActivityService.save(clubActivity);
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
                ClubActivity clubActivity=clubActivityService.find(id);
                clubActivity.setClub(null);
                clubActivity.setUser(null);
                clubActivityService.delete(id);
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
