package com.huitu.sjclub.controller.admin;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.Department;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.DepartmentService;
import com.huitu.sjclub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("adminDepartmentController")
@RequestMapping("/admin/department")
public class DepartmentController extends BaseController {

    @Resource(name = "departmentServiceImpl")
    private DepartmentService departmentService;

    @Resource(name = "userServiceImpl")
    private UserService userService;
    @RequestMapping(value = "/division")
    public String division(ModelMap modelMap){
        User user=userService.getCurrent();
        if(user.getDepartment()!=null){
            modelMap.addAttribute("department",user.getDepartment());
        }
        return "/admin/department/division";
    }

    @RequestMapping(value = "/loadList")
    public String loadList(){
        return "/admin/department/list";
    }
    @RequestMapping("/list")
    public @ResponseBody
    Map<String,Object> list(HttpServletRequest request){
        String page=request.getParameter("page");
        String limit=request.getParameter("limit");
        Map<String,Object> map=new HashMap<String,Object>();
        User user= userService.getCurrent();
        if(page==null||"".equals(page)){
            //查询所有
            List<Department> departments= departmentService.findAll();
            List<Map<String,Object>>  mapList= getMapList(departments);
            map.put("count",departments.size());
            map.put("data",mapList);
        }else{
            if("admin".equals(user.getLoginName())){
                Pageable pageable=new Pageable();
                Integer pageNumer=Integer.parseInt(page);
                Integer pageSize=Integer.parseInt(limit);
                pageable.setPageNumber(pageNumer);
                pageable.setPageSize(pageSize);
                Page<Department> departmentPage= departmentService.findPage(pageable);
                List<Map<String,Object>>  mapList= getMapList(departmentPage.getContent());
                map.put("count",pageSize);
                map.put("data",mapList);
            }else{
                Pageable pageable=new Pageable();
                Integer pageNumer=Integer.parseInt(page);
                Integer pageSize=Integer.parseInt(limit);
                pageable.setPageNumber(pageNumer);
                pageable.setPageSize(pageSize);
                Page<Department> departmentPage= departmentService.findPage(pageable,user.getClub());
                List<Map<String,Object>>  mapList= getMapList(departmentPage.getContent());
                map.put("count",pageSize);
                map.put("data",mapList);
            }

        }
        map.put("code", 0);
        map.put("msg","信息成功");
        return map;
    }

    //*
    private List<Map<String,Object>> getMapList(List<Department> list){
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();

        for(Department department:list){

                Map<String,Object> map=new HashMap<String, Object>();
                map.put("id",department.getId());
                map.put("name",department.getName());
                map.put("createDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(department.getCreateDate()));
                map.put("modifyDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(department.getModifyDate()));
                map.put("introduction",department.getIntroduction());
                map.put("users",department.getUser().size());
                mapList.add(map);
        }
        return mapList;
    }

    @RequestMapping(value = "/add")
    public String add(Long id, ModelMap modelMap){
        if(id!=null){
           Department department= departmentService.find(id);
           modelMap.addAttribute("department",department);
        }
        return "/admin/department/add";
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>  save(Department department,HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            if(department.getId()!=null){
                Department department1=  departmentService.find(department.getId());
                department1.setIntroduction(department.getIntroduction());
                department1.setName(department.getName());
                departmentService.update(department1);
            }else{
                User user=userService.getCurrent();
                if((!"admin".equals(user.getLoginName()))&&user.getClub()==null){
                    map.put("code",250);
                    return map;
                }
                department.setClub(user.getClub());
                departmentService.save(department);
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
                departmentService.delete(id);
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
