package com.huitu.sjclub.controller.admin;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.ClubActivity;
import com.huitu.sjclub.entity.ElectronicVoting;
import com.huitu.sjclub.entity.ElectronicVotingItem;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.ElectronicVotingItemService;
import com.huitu.sjclub.service.ElectronicVotingService;
import com.huitu.sjclub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by admin on 2018/3/31.
 */
@Controller
@RequestMapping("/admin/electronic_voting")
public class ElectronicVotingController  extends BaseController{
    @Resource(name = "electronicVotingServiceImpl")
    private ElectronicVotingService electronicVotingService;

    @Resource(name = "electronicVotingItemServiceImpl")
    private ElectronicVotingItemService electronicVotingItemService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @RequestMapping("/loadList")
    public String load(){
        return "/admin/electronic_voting/list";
    }


    @RequestMapping("/list")
    public @ResponseBody
    Map<String,Object> list(HttpServletRequest request){
        String page=request.getParameter("page");
        String limit=request.getParameter("limit");
        Map<String,Object> map=new HashMap<String,Object>();
        if(page==null||"".equals(page)){
            //查询所有
            List<ElectronicVoting> electronicVotings= electronicVotingService.findAll();
            List<Map<String,Object>>  mapList= getMapList(electronicVotings);
            map.put("count",electronicVotings.size());
            map.put("data",mapList);
        }else{
            Pageable pageable=new Pageable();
            Integer pageNumer=Integer.parseInt(page);
            Integer pageSize=Integer.parseInt(limit);
            pageable.setPageNumber(pageNumer);
            pageable.setPageSize(pageSize);
            Page<ElectronicVoting> electronicVotingPage= electronicVotingService.findPage(pageable);
            List<Map<String,Object>>  mapList= getMapList(electronicVotingPage.getContent());
            map.put("count",pageSize);
            map.put("data",mapList);
        }
        map.put("code", 0);
        map.put("msg","信息成功");
        return map;
    }


    //*
    private List<Map<String,Object>> getMapList(List<ElectronicVoting> list){
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();

        for(ElectronicVoting electronicVoting:list){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("id",electronicVoting.getId());
            map.put("endTime",electronicVoting.getEndTime());
            map.put("startTime",electronicVoting.getStartTime());
            map.put("content",electronicVoting.getContent());
            map.put("createDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(electronicVoting.getCreateDate()));
            map.put("modifyDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(electronicVoting.getModifyDate()));
            mapList.add(map);
        }
        return mapList;
    }

    @RequestMapping(value = "/add")
    public String add(Long id,ModelMap modelMap){
        if(id!=null){
            ElectronicVoting electronicVoting=electronicVotingService.find(id);
            modelMap.addAttribute("electronicVoting",electronicVoting);
            return "/admin/electronic_voting/add_item";
        }
        return "/admin/electronic_voting/add";
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> delete(Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        if(id!=null){
            try {
                electronicVotingService.delete(id);
                map.put("code", 200);
                map.put("message","操作成功!");
            }catch (Exception ex){
                ex.printStackTrace();
                map.put("code",500);
                map.put("message","操作失败!");
            }
        }else{
            map.put("code",500);
            map.put("message","操作失败!");
        }
        return  map;
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>  save(String content,Long id,String item1,String item2,String item3,String item4,String item5,Date endTime,Date startTime){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            if(id!=null){
              ElectronicVoting electronicVoting=  electronicVotingService.find(id);
            }else{
                ElectronicVoting electronicVoting=new ElectronicVoting();
                electronicVoting.setContent(content);
                electronicVoting.setEndTime(endTime);
                electronicVoting.setStartTime(startTime);

                List<ElectronicVotingItem> list=new ArrayList<ElectronicVotingItem>();
                if((!"".equals(item1))||(null!=item1)){
                    ElectronicVotingItem electronicVotingItem=new ElectronicVotingItem();
                    electronicVotingItem.setItemName(item1);
                    list.add(electronicVotingItem);
                }
                if((!"".equals(item2))||(null!=item2)){
                    ElectronicVotingItem electronicVotingItem=new ElectronicVotingItem();
                    electronicVotingItem.setItemName(item2);
                    list.add(electronicVotingItem);
                }
                if((!"".equals(item3))||(null!=item3)){
                    ElectronicVotingItem electronicVotingItem=new ElectronicVotingItem();
                    electronicVotingItem.setItemName(item3);
                    list.add(electronicVotingItem);
                }
                if((!"".equals(item4))||(null!=item4)){
                    ElectronicVotingItem electronicVotingItem=new ElectronicVotingItem();
                    electronicVotingItem.setItemName(item4);
                    list.add(electronicVotingItem);
                }
                if((!"".equals(item5))||(null!=item5)){
                    ElectronicVotingItem electronicVotingItem=new ElectronicVotingItem();
                    electronicVotingItem.setItemName(item5);
                    list.add(electronicVotingItem);
                }
                electronicVotingService.saveElectronicVoting(electronicVoting,list);
            }
            map.put("code","200");
        }catch (Exception ex){
            map.put("code","500");
            ex.printStackTrace();
        }
        return map;
    }


    @RequestMapping(value = "/saveItem",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>  saveItem(Long itemId,Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            ElectronicVotingItem electronicVotingItem= electronicVotingItemService.find(itemId);
            electronicVotingItem.setCount((electronicVotingItem.getCount()==null?0:electronicVotingItem.getCount())+1);
            electronicVotingItemService.update(electronicVotingItem);
            map.put("code","200");
        }catch (Exception ex){
            map.put("code","500");
            ex.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/view")
    public String view(Long id,ModelMap modelMap){
        ElectronicVoting electronicVoting=electronicVotingService.find(id);
        modelMap.addAttribute("electronicVoting",electronicVoting);
        return "/admin/electronic_voting/view";
    }

    @RequestMapping(value = "/check")
    public @ResponseBody Map<String,Object> check(){
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            User user=userService.getCurrent();
            List<String> sss=userService.findAuthorities(user.getId());
            if(sss.contains("sjclub:user")){
                map.put("code",200);
            }else{
                map.put("code",500);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            map.put("code",500);
        }
        return map;
    }

}
