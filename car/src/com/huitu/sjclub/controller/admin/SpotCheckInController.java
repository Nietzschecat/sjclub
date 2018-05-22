package com.huitu.sjclub.controller.admin;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.*;
import com.huitu.sjclub.service.ClubActivityService;
import com.huitu.sjclub.service.SpotCheckInService;
import com.huitu.sjclub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/3/31.
 */
@Controller("adminSpotCheckInController")
@RequestMapping("/admin/spot_check_in")
public class SpotCheckInController extends BaseController {

    @Resource(name = "spotCheckInServiceImpl")
    private SpotCheckInService spotCheckInService;

    @Resource(name = "clubActivityServiceImpl")
    private ClubActivityService clubActivityService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @RequestMapping("/loadList")
    public String load(){
        return "/admin/spot_check_in/list";
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
            List<SpotCheckIn> spotCheckIns= spotCheckInService.findAll();
            List<Map<String,Object>>  mapList= getMapList(spotCheckIns);
            map.put("count",spotCheckIns.size());
            map.put("data",mapList);
        }else{
            Pageable pageable=new Pageable();
            Integer pageNumer=Integer.parseInt(page);
            Integer pageSize=Integer.parseInt(limit);
            pageable.setPageNumber(pageNumer);
            pageable.setPageSize(pageSize);
            Page<SpotCheckIn> spotCheckInPage= spotCheckInService.findPage(pageable,user);
            List<Map<String,Object>>  mapList= getMapList(spotCheckInPage.getContent());
            map.put("count",pageSize);
            map.put("data",mapList);
        }
        map.put("code", 0);
        map.put("msg","信息成功");
        return map;
    }
    //*
    private List<Map<String,Object>> getMapList(List<SpotCheckIn> list){
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
        for(SpotCheckIn spotCheckIn:list){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("id",spotCheckIn.getId());
            map.put("userName",spotCheckIn.getUser().getUserName());
            map.put("bz",spotCheckIn.getBz());
            map.put("sportCheckInDate",spotCheckIn.getSportCheckInDate());
            map.put("title",spotCheckIn.getActivity().getTitle());
            map.put("createDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(spotCheckIn.getCreateDate()));
            map.put("modifyDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(spotCheckIn.getModifyDate()));
            mapList.add(map);
        }
        return mapList;
    }

    @RequestMapping("/getSpotCheckIn")
    public Map<String,Object> getSpotCheckIn(Long id){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            SpotCheckIn spotCheckIn=spotCheckInService.find(id);
            List<ClubActivity> clubActivities= clubActivityService.findAll();
            List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
            for(ClubActivity clubActivity:clubActivities){
                Map<String,Object> map1=new HashMap<String,Object>();
                map1.put("id",clubActivity.getId());
                map1.put("title",clubActivity.getTitle());
                list.add(map1);
            }
            map.put("list",list);
            map.put("activityId",spotCheckIn.getActivity()!=null?spotCheckIn.getActivity().getTitle():"");
            map.put("userName",spotCheckIn.getUser()!=null?spotCheckIn.getUser().getUserName():"");
            map.put("id",spotCheckIn.getId());
            map.put("bz",spotCheckIn.getBz());
            map.put("code",200);
        }catch (Exception ex){
            ex.printStackTrace();
            map.put("code",500);
        }
        return map;
    }

    @RequestMapping(value = "/add")
    public String add(Long id, ModelMap modelMap){
        if(id!=null){
            SpotCheckIn spotCheckIn= spotCheckInService.find(id);
            modelMap.addAttribute("spotCheckIn",spotCheckIn);
        }
        List<ClubActivity> clubActivities= clubActivityService.findAll();
        modelMap.addAttribute("clubActivities",clubActivities);
        return "/admin/spot_check_in/add";
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>  save(SpotCheckIn spotCheckIn,HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            if(spotCheckIn.getActivity()!=null){
                ClubActivity clubActivity=clubActivityService.find(spotCheckIn.getActivity().getId());
                spotCheckIn.setActivity(clubActivity);
            }
            if(spotCheckIn.getId()!=null){
                SpotCheckIn spotCheckIn1=  spotCheckInService.find(spotCheckIn.getId());
                spotCheckIn1.setActivity(spotCheckIn.getActivity());
                spotCheckIn1.setUser(userService.getCurrent());
                spotCheckIn1.setBz(spotCheckIn.getBz());
                spotCheckIn1.setSportCheckInDate(spotCheckIn.getSportCheckInDate());
                spotCheckInService.update(spotCheckIn1);
            }else{
            ;
            User user=userService.getCurrent();
            spotCheckIn.setUser(user);
            spotCheckInService.save(spotCheckIn);
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
               SpotCheckIn spotCheckIn= spotCheckInService.find(id);
                spotCheckIn.setUser(null);
                spotCheckIn.setActivity(null);
                spotCheckInService.delete(id);
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
