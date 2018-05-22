package com.huitu.sjclub.controller.admin;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.Setting;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.Department;
import com.huitu.sjclub.entity.Role;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.ClubService;
import com.huitu.sjclub.service.DepartmentService;
import com.huitu.sjclub.service.RoleService;
import com.huitu.sjclub.service.UserService;
import com.huitu.sjclub.util.RequestParamManager;
import com.huitu.sjclub.util.SystemUtils;
import org.hibernate.collection.internal.PersistentMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("adminUserController")
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "roleServiceImpl")
    private RoleService roleService;

    @Resource(name = "departmentServiceImpl")
    private DepartmentService departmentService;

    @Resource(name = "clubServiceImpl")
    private ClubService clubService;
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    //加载个人基本资料
    @RequestMapping(value = "/getUserInfo")
    public  String getUserInfo(ModelMap map){
      User user= userService.getCurrent();
      map.addAttribute("user",user);
      //
        if("admin".equals(user.getLoginName())){
            List<Department>  departments= departmentService.findList(user.getClub());
            map.addAttribute("departments",departments);
        }else{
            if(user.getClub()!=null){
                List<Department>  departments= departmentService.findList(user.getClub());
                map.addAttribute("departments",departments);
            }
        }
       return "/admin/user/info";
    }
    @RequestMapping(value = "/getAccount")
    public @ResponseBody Map<String,Object> getAccount(){
        Map<String,Object> map=new HashMap<String, Object>();
        User user=userService.getCurrent();
        if(user!=null){
            map.put("code",200);
            map.put("userName",user.getUserName());
        }else{
            map.put("code",500);
            map.put("userName",user.getUserName());
        }
        return map;
    }
    /**
     * 跳转到密码修改页面
     * @return
     */
    @RequestMapping(value = "/modifypsd")
    public String mofifyPsd(){
        return "/admin/user/modifypsd";
    }
    @RequestMapping(value = "/updatePsd",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> updatePsd(String psw){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
             User user=userService.getCurrent();
             user.setPassword(psw);
             userService.update(user);
            map.put("code",0);
        }catch (Exception ex){
            ex.printStackTrace();
            map.put("code",1);
            return map;
        }
        return map;
    }


    //加载页面
    @RequestMapping("/loadList")
    public String load(){
        return "/admin/user/list";
    }

    @RequestMapping("/list")
    public @ResponseBody Map<String,Object> list(HttpServletRequest request){
        String page=request.getParameter("page");
        String limit=request.getParameter("limit");
        Map<String,Object> map=new HashMap<String,Object>();
        if(page==null||"".equals(page)){
            //查询所有
            List<User> userList= userService.findAll();
            List<Map<String,Object>> mapList=getMapList(userList);
            map.put("count",userList.size());
            map.put("data",mapList);
        }else{
            Pageable pageable=new Pageable();
            Integer pageNumer=Integer.parseInt(page);
            Integer pageSize=Integer.parseInt(limit);
            pageable.setPageNumber(pageNumer);
            pageable.setPageSize(pageSize);
            Page<User> userPage= userService.findPage(pageable);
            List<Map<String,Object>> mapList=getMapList(userPage.getContent());
            map.put("count",userPage.getTotal());

            map.put("data",mapList);
        }
        map.put("code", 0);
        map.put("msg","信息成功");
        return map;
    }
    //*
    private List<Map<String,Object>> getMapList(List<User> list){
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
        for(User user:list){
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("id",user.getId());
                map.put("userName",user.getUserName());
                map.put("sex","0".equals(user.getSex())&&user.getSex()!=null?"男":"女");
                map.put("sg",user.getSg());
                map.put("school",user.getSchool());
                map.put("className",user.getClassName());
                map.put("password",user.getPassword());
                Set<Role> roles= user.getRoles();
                String roleName="";
                for(Role role:roles){
                   roleName= role.getName();
                }
                map.put("loginName",user.getLoginName());
                map.put("roleName",roleName);
                map.put("loginFailureCount",user.getLoginFailureCount());
                map.put("departmentName", user.getDepartment() == null ? "" : user.getDepartment().getName());
                map.put("createDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(user.getCreateDate()));
                map.put("modifyDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(user.getModifyDate()));
                mapList.add(map);
        }
        return mapList;
    }

    @RequestMapping(value = "/add")
    public String add(Long id, ModelMap modelMap){
        List<Department> departments=departmentService.findAll();
        List<Club> clubList= clubService.findAll();
        List<Role> roles=roleService.findAll();
        if(id!=null){
            User user= userService.find(id);
            modelMap.addAttribute("user",user);
        }
        modelMap.addAttribute("departments",departments);
        modelMap.addAttribute("clubList",clubList);
        modelMap.addAttribute("roles",roles);
        return "/admin/user/add";
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>  save(User user,Long[] roleIds,HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            if(roleIds!=null){
                user.setRoles(new HashSet<Role>(roleService.findList(roleIds)));
            }
            if(user.getDepartment()!=null&&user.getDepartment().getId()!=null){
                Department department=departmentService.find(user.getDepartment().getId());
                user.setDepartment(department);
            }
            if(user.getClub()!=null&&user.getClub().getId()!=null){
                Club club=clubService.find(user.getClub().getId());
                user.setClub(club);
            }
            if(user.getId()!=null){
                User user1=  userService.find(user.getId());
                user1.setUserName(user.getUserName());
                user1.setSex(user.getSex());
                user1.setSg(user.getSg());
                user1.setSchool(user.getSchool());
                user1.setClassName(user.getClassName());
                user1.setLoginName(user.getLoginName());
                user1.setUserLogo(user.getUserLogo());
                user1.setBirthDate(user.getBirthDate());
                if(roleIds!=null){
                    user1.setRoles(user.getRoles());
                }
                if(user.getDepartment()!=null&&user.getDepartment().getId()!=null){
                    user1.setDepartment(user.getDepartment());
                }
                if(user.getClub()!=null&&user.getClub().getId()!=null){
                    user1.setClub(user.getClub());
                }
                userService.update(user1);
            }else{
                //Department department=departmentService.find(user.getDepartment().getId());
                Setting setting= SystemUtils.getSetting();
                user.setPassword(setting.getDefaultPassword());
                userService.save(user);
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
                userService.delete(id);
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
