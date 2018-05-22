package com.huitu.sjclub.controller.admin;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.Department;
import com.huitu.sjclub.entity.Meeting;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.ClubService;
import com.huitu.sjclub.service.MeetingService;
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
 * Created by admin on 2018/3/31.
 */
@Controller("adminMeetingController")
@RequestMapping("/admin/meeting")
public class MeetingController extends BaseController {

    @Resource(name = "meetingServiceImpl")
    private MeetingService meetingService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "clubServiceImpl")
    private ClubService clubService;

    @RequestMapping(value = "/loadList")
    public String loadList(){
        return "/admin/meeting/list";
    }

    @RequestMapping("/list")
    public @ResponseBody
    Map<String,Object> list(HttpServletRequest request){
        String page=request.getParameter("page");
        String limit=request.getParameter("limit");
        Map<String,Object> map=new HashMap<String,Object>();

        if(page==null||"".equals(page)){
            //查询所有
            List<Meeting> meetings= meetingService.findAll();
            List<Map<String,Object>>  mapList= getMapList(meetings);
            map.put("count",meetings.size());
            map.put("data",mapList);
        }else{
            Pageable pageable=new Pageable();
            Integer pageNumer=Integer.parseInt(page);
            Integer pageSize=Integer.parseInt(limit);
            pageable.setPageNumber(pageNumer);
            pageable.setPageSize(pageSize);
            //Page<Meeting> meetingPage= meetingService.findPage(pageable);
            List<Meeting> meetings= meetingService.findAll();
            List<Map<String,Object>>  mapList= getMapList(meetings);
            Page<Map<String,Object>> mapPage=new Page<Map<String, Object>>(mapList,mapList.size(),pageable);
            map.put("count",pageSize);
            map.put("data",mapPage.getContent());
        }
        map.put("code", 0);
        map.put("msg","信息成功");
        return map;
    }

    //*
    private List<Map<String,Object>> getMapList(List<Meeting> list){
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
        User user=userService.getCurrent();
        boolean isTrue=userService.isSystem(user);
        for(Meeting meeting:list){
                if(isTrue){
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("id",meeting.getId());
                    map.put("title",meeting.getTitle());
                    map.put("content",meeting.getContent());
                    map.put("createDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(meeting.getCreateDate()));
                    map.put("modifyDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(meeting.getModifyDate()));
                    map.put("info",meeting.getInfo());
                    mapList.add(map);
                }else{
                    if(user.getClub()!=null){
                        if(user.getClub().getId()==meeting.getClub().getId()){
                            Map<String,Object> map=new HashMap<String, Object>();
                            map.put("id",meeting.getId());
                            map.put("title",meeting.getTitle());
                            map.put("content",meeting.getContent());
                            map.put("createDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(meeting.getCreateDate()));
                            map.put("modifyDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(meeting.getModifyDate()));
                            map.put("info",meeting.getInfo());
                            mapList.add(map);
                        }
                    }
            }
        }
        return mapList;
    }

    @RequestMapping(value = "/add")
    public String add(Long id, ModelMap modelMap){
        List<Club> clubList= clubService.findAll();
        modelMap.addAttribute("clubList",clubList);
        if(id!=null){
            Meeting meeting= meetingService.find(id);
            modelMap.addAttribute("meeting",meeting);
        }
        return "/admin/meeting/add";
    }
    @RequestMapping(value = "/view")
    public String view(Long id, ModelMap modelMap){
        List<Club> clubList= clubService.findAll();
        modelMap.addAttribute("clubList",clubList);
        if(id!=null){
            Meeting meeting= meetingService.find(id);
            modelMap.addAttribute("meeting",meeting);

        }
        return "/admin/meeting/view";
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>  save(Meeting meeting,HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            if(meeting.getId()!=null){
                Meeting meeting1=  meetingService.find(meeting.getId());
                meeting1.setInfo(meeting.getInfo());
                meeting1.setTitle(meeting.getTitle());
                meeting1.setContent(meeting.getContent());
                meetingService.update(meeting1);
            }else{
                User user=userService.getCurrent();
                meeting.setUser(user);
                if(user.getClub()==null){
                    map.put("code",250);
                    return map;
                }
                meeting.setClub(user.getClub());
                meetingService.save(meeting);
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
                meetingService.delete(id);
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
