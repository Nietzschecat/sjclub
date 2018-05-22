package com.huitu.sjclub.controller.admin;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubNotice;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.ClubNoticeService;
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
@RequestMapping("/admin/club_notice")
public class ClubNoticeController extends BaseController {

    @Resource(name = "clubNoticeServiceImpl")
    private ClubNoticeService clubNoticeService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "clubServiceImpl")
    private ClubService clubService;

    @RequestMapping("/loadList")
    public String load(){
        return "/admin/club_notice/list";
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
            List<ClubNotice> clubNotices= clubNoticeService.findAll();
            List<Map<String,Object>>  mapList= getMapList(clubNotices);
            map.put("count",clubNotices.size());
            map.put("data",mapList);
        }else{
            if("admin".equals(user.getLoginName())){
                Pageable pageable=new Pageable();
                Integer pageNumer=Integer.parseInt(page);
                Integer pageSize=Integer.parseInt(limit);
                pageable.setPageNumber(pageNumer);
                pageable.setPageSize(pageSize);
                Page<ClubNotice> clubNoticePage= clubNoticeService.findPage(pageable,null);
                List<Map<String,Object>>  mapList= getMapList(clubNoticePage.getContent());
                map.put("count",pageSize);
                map.put("data",mapList);
            }else{
                Club club=user.getClub();
                Pageable pageable=new Pageable();
                Integer pageNumer=Integer.parseInt(page);
                Integer pageSize=Integer.parseInt(limit);
                pageable.setPageNumber(pageNumer);
                pageable.setPageSize(pageSize);
                Page<ClubNotice> clubNoticePage= clubNoticeService.findPage(pageable,club);
                List<Map<String,Object>>  mapList= getMapList(clubNoticePage.getContent());
                map.put("count",pageSize);
                map.put("data",mapList);
            }

        }
        map.put("code", 0);
        map.put("msg","信息成功");
        return map;
    }

    //*
    private List<Map<String,Object>> getMapList(List<ClubNotice> list){
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();

        for(ClubNotice clubNotice:list){
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("id",clubNotice.getId());
                map.put("title",clubNotice.getTitle());
                User user=clubNotice.getUser();
                if(user!=null){
                    map.put("userName",user.getUserName());//活动创建者
                }
                map.put("content", clubNotice.getContent());
                map.put("endTime", clubNotice.getEndTime());
                map.put("isStart",clubNotice.getIsStart());
                map.put("createDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(clubNotice.getCreateDate()));
                map.put("modifyDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(clubNotice.getModifyDate()));
                mapList.add(map);
        }
        return mapList;
    }

    @RequestMapping(value = "/add")
    public String add(Long id, ModelMap modelMap){
        List<Club> clubList= clubService.findAll();
        modelMap.addAttribute("clubs",clubList);
        if(id!=null){
            ClubNotice clubNotice= clubNoticeService.find(id);
            modelMap.addAttribute("clubNotice",clubNotice);
        }
        return "/admin/club_notice/add";
    }
    @RequestMapping(value = "/view")
    public String view(Long id, ModelMap modelMap){
        if(id!=null){
            ClubNotice clubNotice= clubNoticeService.find(id);
            modelMap.addAttribute("clubNotice",clubNotice);
        }
        return "/admin/club_notice/view";
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>  save(ClubNotice clubNotice,HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            if(clubNotice.getClub()!=null&&clubNotice.getClub().getId()!=null){
               Club club= clubService.find(clubNotice.getClub().getId());
               clubNotice.setClub(club);
            }
            if(clubNotice.getId()!=null){
                ClubNotice clubNotice1=  clubNoticeService.find(clubNotice.getId());
                clubNotice1.setTitle(clubNotice.getTitle());
                clubNotice1.setEndTime(clubNotice.getEndTime());
                clubNotice1.setContent(clubNotice.getContent());
                clubNotice1.setIsStart(clubNotice.getIsStart());
                if(clubNotice.getClub()!=null){
                    clubNotice1.setClub(clubNotice.getClub());
                }
                clubNoticeService.update(clubNotice);
            }else{
                User user= userService.getCurrent();
                clubNotice.setUser(user);
                if(user.getClub()==null){
                    map.put("code",250);
                    return map;
                }
                clubNotice.setClub(user.getClub());
                clubNoticeService.save(clubNotice);
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
                clubNoticeService.delete(id);
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
