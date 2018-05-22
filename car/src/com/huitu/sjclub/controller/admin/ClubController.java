package com.huitu.sjclub.controller.admin;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.Department;
import com.huitu.sjclub.entity.Role;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.ClubService;
import com.huitu.sjclub.service.RoleService;
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
 * Created by admin on 2018/3/29.
 */
@Controller
@RequestMapping("/admin/club")
public class ClubController extends BaseController {

    @Resource(name = "clubServiceImpl")
    private ClubService clubService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "roleServiceImpl")
    private RoleService roleService;
    @RequestMapping("/loadList")
    public String load(){
        return "/admin/club/list";
    }

    @RequestMapping("/clubInfo")
    public String clubInfo(ModelMap modelMap){
      User user=  userService.getCurrent();

      if(user.getClub()!=null){
          modelMap.addAttribute("club",user.getClub());
      }
        return "/admin/club/clubInfo";
    }

    @RequestMapping("/list")
    public @ResponseBody
    Map<String,Object> list(HttpServletRequest request){
        String page=request.getParameter("page");
        String limit=request.getParameter("limit");
        Map<String,Object> map=new HashMap<String,Object>();
        if(page==null||"".equals(page)){
            //查询所有
            /*List<Club> clubs= clubService.findAll();
            List<Map<String,Object>>  mapList= getMapList(clubs);
            map.put("count",clubs.size());
            map.put("data",mapList);*/
        }else{
            //如果当前用户当前不是管理员登录，
            User user=userService.getCurrent();
            if("admin".equals(user.getLoginName())){
                Pageable pageable=new Pageable();
                Integer pageNumer=Integer.parseInt(page);
                Integer pageSize=Integer.parseInt(limit);
                pageable.setPageNumber(pageNumer);
                pageable.setPageSize(pageSize);
                Page<Club> clubPage= clubService.findPage(pageable);
                List<Map<String,Object>>  mapList= getMapList(clubPage.getContent(),null);
                map.put("count",pageSize);
                map.put("data",mapList);
            }else{
                Pageable pageable=new Pageable();
                Integer pageNumer=Integer.parseInt(page);
                Integer pageSize=Integer.parseInt(limit);
                pageable.setPageNumber(pageNumer);
                pageable.setPageSize(pageSize);
                Page<Club> clubPage= clubService.findPage(pageable);
                List<Map<String,Object>>  mapList= getMapList(clubPage.getContent(),null);
                Page<Map<String,Object>> mapPage=new Page<Map<String,Object>>(mapList,mapList.size(),pageable);
                map.put("count",pageSize);
                map.put("data",mapPage.getContent());
            }

        }
        map.put("code", 0);
        map.put("msg","信息成功");
        return map;
    }

    //*
    private List<Map<String,Object>> getMapList(List<Club> list,User user){
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
        if(user!=null){
            for(Club club:list){
                if(user.getClub().getId()==club.getId()){
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("id",club.getId());
                    map.put("name",club.getName());
                    map.put("createDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(club.getCreateDate()));
                    map.put("modifyDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(club.getModifyDate()));
                    map.put("info",club.getInfo());
                    map.put("users",club.getUsers().size());
                    mapList.add(map);
                }
            }
        }else{
            for(Club club:list){
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("id",club.getId());
                map.put("name",club.getName());
                map.put("createDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(club.getCreateDate()));
                map.put("modifyDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(club.getModifyDate()));
                map.put("info",club.getInfo());
                map.put("users",club.getUsers().size());
                mapList.add(map);
            }
        }

        return mapList;
    }

    @RequestMapping(value = "/add")
    public String add(Long id, ModelMap modelMap){
        if(id!=null){

            Club club= clubService.find(id);
            modelMap.addAttribute("club",club);
        }
        return "/admin/club/add";
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>  save(Club club,HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            if(club.getId()!=null){
                //更新club信息
                Club club1=  clubService.find(club.getId()); //根据id查找单条信息
                club1.setInfo(club.getInfo());
                club1.setName(club.getName());
                club1.setLogo(club.getLogo());
                club1.setTenGood(club.getTenGood());
                clubService.update(club1);
            }else{
                //保存club
                clubService.save(club);
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
                clubService.delete(id);
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
    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> deleteUser(Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        if(id!=null){
            try {
                User user=userService.find(id);
                user.setClub(null);
                List<Role> roles=roleService.findAll();
                if(!roles.isEmpty()){
                    Role role1=null;
                    for(Role role:roles){
                        List<String> authorities= role.getAuthorities();
                        if(authorities.contains("sjclub:visitors")){
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
                userService.update(user);
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
    @RequestMapping(value = "/club_user_list_load")
    public String  loadClubUserList(){
        return "/admin/club/club_user_list";
    }
    //社团成员
    @RequestMapping(value = "/club_user_list")
    public @ResponseBody Map<String,Object> clubUserList(HttpServletRequest request){
        String page=request.getParameter("page");
        String limit=request.getParameter("limit");
        Map<String,Object> map=new HashMap<String,Object>();
        if(page==null||"".equals(page)){
            //查询所有
            List<Club> clubs= clubService.findAll();

            List<Map<String,Object>>  mapList= getMapListUser(clubs);
            map.put("count",clubs.size());
            map.put("data",mapList);
        }else{
            Pageable pageable=new Pageable();
            Integer pageNumer=Integer.parseInt(page);
            Integer pageSize=Integer.parseInt(limit);
            pageable.setPageNumber(pageNumer);
            pageable.setPageSize(pageSize);
            //Page<Club> clubPage= clubService.findPage(pageable);
            User user= userService.getCurrent();
            List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
            if(user.getClub()!=null){
                mapList= getMapListUser(user.getClub());
            }
            Page<Map<String,Object>> pageList=new Page<Map<String, Object>>(mapList,mapList.size(),pageable);
            map.put("count",pageSize);
            map.put("data",pageList.getContent());
        }
        map.put("code", 0);
        map.put("msg","信息成功");
        return  map;
    }
    /**
     * 获取社团成员
     * @return
     */
    private List<Map<String,Object>> getMapListUser(Club club){
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
            for(User user:club.getUsers()){
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("id",user.getId());
                map.put("clubId",club.getId());
                map.put("clubName",club.getName());
                map.put("userName",user.getUserName());
                map.put("loginName",user.getLoginName());
                map.put("sg",user.getSg());
                map.put("sex","0".equals(user.getSex())&&user.getSex()!=null?"男":"女");
                map.put("school",user.getSchool());
                map.put("className",user.getClassName());
                map.put("department",user.getDepartment()==null?"":user.getDepartment().getName());
                mapList.add(map);
            }
        return mapList;
    }
    /**
     * 获取社团成员
     * @return
     */
    private List<Map<String,Object>> getMapListUser(List<Club> list){
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
        for(Club club:list){
            for(User user:club.getUsers()){
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("id",user.getId());
                map.put("clubId",club.getId());
                map.put("clubName",club.getName());
                map.put("userName",user.getUserName());
                map.put("loginName",user.getLoginName());
                map.put("sex","0".equals(user.getSex())&&user.getSex()!=null?"男":"女");
                map.put("sg",user.getSg());
                map.put("school",user.getSchool());
                map.put("className",user.getClassName());
                mapList.add(map);
            }
        }
        return mapList;
    }


}
