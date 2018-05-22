package com.huitu.sjclub.controller.admin;

import com.huitu.sjclub.Filter;
import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.ClassTable;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.ClassTableService;
import com.huitu.sjclub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
@Controller()
@RequestMapping("/admin/class_table")
public class ClassTableController extends BaseController {
    @Resource(name = "classTableServiceImpl")
    private ClassTableService classTableService;

    @Resource(name = "userServiceImpl")
    private UserService userService;


    @RequestMapping(value = "/loadList")
    public String loadList(){
        return "/admin/class_table/list";
    }


    //只查自己的
    @RequestMapping("/list")
    public @ResponseBody
    Map<String,Object> list(HttpServletRequest request){
        User user= userService.getCurrent();
        String page=request.getParameter("page");
        String limit=request.getParameter("limit");
        String noClass=request.getParameter("noClass");
        String classTime=request.getParameter("classTime");

        ClassTable classTable=new ClassTable();
        classTable.setNoClass(noClass);
        classTable.setClassTime(classTime);

        Map<String,Object> map=new HashMap<String,Object>();
        List<String> authorities=userService.findAuthorities(user.getId());
        if(authorities.indexOf("sjclub:manager")!=-1||authorities.indexOf("sjclub:admin")!=-1){
            if(page==null||"".equals(page)){
                //查询所有
                List<ClassTable> classTables= classTableService.findAll();
                List<Map<String,Object>>  mapList= getMapList(classTables);
                map.put("count",classTables.size());
                map.put("data",mapList);
            }else{
                Pageable pageable=new Pageable();
                Integer pageNumer=Integer.parseInt(page);
                Integer pageSize=Integer.parseInt(limit);
                pageable.setPageNumber(pageNumer);
                pageable.setPageSize(pageSize);
                Page<ClassTable> classTablePage= classTableService.findPage(pageable,classTable);
                List<Map<String,Object>>  mapList= getMapList(classTablePage.getContent());
                map.put("count",pageSize);
                map.put("data",mapList);
            }
        }else {
            //只查询自己的
            Filter filter=new Filter();
            filter.setProperty("user");
            filter.setValue(user.getId());
            filter.setOperator(Filter.Operator.eq);
            List<Filter> filters=new ArrayList<Filter>();
            filters.add(filter);
            if(page==null||"".equals(page)){
                //查询所有
                List<ClassTable> classTables= classTableService.findList(null,null,filters,null);
                List<Map<String,Object>>  mapList= getMapList(classTables);
                map.put("count",classTables.size());
                map.put("data",mapList);
            }else{
                Pageable pageable=new Pageable();
                Integer pageNumer=Integer.parseInt(page);
                Integer pageSize=Integer.parseInt(limit);
                pageable.setPageNumber(pageNumer);
                pageable.setPageSize(pageSize);
                pageable.setFilters(filters);
                Page<ClassTable> classTablePage= classTableService.findPage(pageable,classTable);
                List<Map<String,Object>>  mapList= getMapList(classTablePage.getContent());
                map.put("count",pageSize);
                map.put("data",mapList);
            }
        }
        map.put("code", 0);
        map.put("msg","信息成功");
        return map;
    }

    //*
    private List<Map<String,Object>> getMapList(List<ClassTable> list){
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();

        for(ClassTable classTable:list){
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("id",classTable.getId());
                map.put("noClass",classTable.getNoClass());
                map.put("classTime",classTable.getClassTime());
                map.put("address",classTable.getAddress());
                map.put("createDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(classTable.getCreateDate()));
                map.put("modifyDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(classTable.getModifyDate()));
                map.put("userName",classTable.getUser().getUserName());
                mapList.add(map);
        }
        return mapList;
    }

    @RequestMapping(value = "/add")
    public String add(Long id, ModelMap modelMap){
        if(id!=null){
            ClassTable classTable= classTableService.find(id);
            modelMap.addAttribute("classTable",classTable);
        }
        return "/admin/class_table/add";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>  save(ClassTable classTable,HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            if(classTable.getId()!=null){
                ClassTable classTable1=  classTableService.find(classTable.getId());
                classTable1.setAddress(classTable.getAddress());
                classTable1.setClassTime(classTable.getClassTime());
                classTable1.setNoClass(classTable.getNoClass());
                classTableService.update(classTable1);
            }else{
                User user=userService.getCurrent();
                classTable.setUser(user);
                classTableService.save(classTable);
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
                classTableService.delete(id);
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
