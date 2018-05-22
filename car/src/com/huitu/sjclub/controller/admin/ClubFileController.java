package com.huitu.sjclub.controller.admin;

import com.huitu.sjclub.FileType;
import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubFile;
import com.huitu.sjclub.entity.Department;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.ClubFileService;
import com.huitu.sjclub.service.ClubService;
import com.huitu.sjclub.service.FileService;
import com.huitu.sjclub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
@Controller
@RequestMapping("/admin/club_file")
public class ClubFileController extends BaseController {

    @Resource(name = "clubFileServiceImpl")
    private ClubFileService clubFileService;

    @Resource(name = "fileServiceImpl")
    private FileService fileService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "clubServiceImpl")
    private ClubService clubService;

    @RequestMapping("/loadList")
    public String load(){
        return "/admin/file/list";
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
            List<ClubFile> clubFiles= clubFileService.findAll();
            List<Map<String,Object>>  mapList= getMapList(clubFiles);
            map.put("count",clubFiles.size());
            map.put("data",mapList);
        }else{
            if(user.getClub()!=null){
                Pageable pageable=new Pageable();
                Integer pageNumer=Integer.parseInt(page);
                Integer pageSize=Integer.parseInt(limit);
                pageable.setPageNumber(pageNumer);
                pageable.setPageSize(pageSize);
                Page<ClubFile> clubFilePage= clubFileService.findPage(pageable,user.getClub());
                List<Map<String,Object>>  mapList= getMapList(clubFilePage.getContent());
                map.put("count",pageSize);
                map.put("data",mapList);
            }else {
                if("admin".equals(user.getLoginName())){
                    Pageable pageable=new Pageable();
                    Integer pageNumer=Integer.parseInt(page);
                    Integer pageSize=Integer.parseInt(limit);
                    pageable.setPageNumber(pageNumer);
                    pageable.setPageSize(pageSize);
                    Page<ClubFile> clubFilePage= clubFileService.findPage(pageable,null);
                    List<Map<String,Object>>  mapList= getMapList(clubFilePage.getContent());
                    map.put("count",pageSize);
                    map.put("data",mapList);
                }else{
                    map.put("count",0);
                    map.put("data",new ArrayList<Map<String,Object>>());
                }
            }
        }
        map.put("code", 0);
        map.put("msg","信息成功");
        return map;
    }

    //*
    private List<Map<String,Object>> getMapList(List<ClubFile> list){
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
        for(ClubFile clubFile:list){
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("id",clubFile.getId());
                map.put("fileName",clubFile.getFileName());
                map.put("fileUrl",clubFile.getFileUrl());
                map.put("createDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(clubFile.getCreateDate()));
                map.put("modifyDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(clubFile.getModifyDate()));
                mapList.add(map);
        }
        return mapList;
    }

    @RequestMapping(value = "/add")
    public String add(Long id, ModelMap modelMap){
        List<Club> clubs=clubService.findAll();
        modelMap.addAttribute("clubs",clubs);
        if(id!=null){
            ClubFile clubFile= clubFileService.find(id);
            modelMap.addAttribute("clubFile",clubFile);

        }
        return "/admin/file/add";
    }

    @RequestMapping(value = "/view")
    public String view(Long id, ModelMap modelMap){
        if(id!=null){
            ClubFile clubFile= clubFileService.find(id);
            modelMap.addAttribute("clubFile",clubFile);
            List<Club> clubs=clubService.findAll();
            modelMap.addAttribute("clubs",clubs);
        }
        return "/admin/file/view";
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>  save(MultipartFile file,ClubFile clubFile,HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            String url =fileService.upload(FileType.file,file);
            if(clubFile.getId()!=null){
                ClubFile clubFile1=  clubFileService.find(clubFile.getId());
                clubFile1.setFileName(file.getOriginalFilename());
                clubFile1.setFileUrl(url);
                clubFileService.update(clubFile1);
            }else{
                clubFile=new ClubFile();
                User user=userService.getCurrent();
                if((!"admin".equals(user.getLoginName()))&&user.getClub()!=null){
                    clubFile.setClub(user.getClub());
                }
                clubFile.setFileName(file.getOriginalFilename());
                clubFile.setFileUrl(url);
                clubFile.setUser(user);

                clubFileService.save(clubFile);
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
                ClubFile clubFile=clubFileService.find(id);
                clubFile.setClub(null);
                clubFile.setUser(null);
                clubFileService.delete(id);
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
