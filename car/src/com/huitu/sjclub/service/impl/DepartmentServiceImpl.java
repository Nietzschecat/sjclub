package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.dao.DepartmentDao;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.Department;
import com.huitu.sjclub.service.BaseService;
import com.huitu.sjclub.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("departmentServiceImpl")
public class DepartmentServiceImpl extends BaseServiceImpl<Department,Long> implements DepartmentService {
    @Resource(name = "departmentDaoImpl")
    private DepartmentDao departmentDao;

    @Override
    public Page<Department> findPage(Pageable pageable, Club club) {
        return departmentDao.findPage(pageable,club);
    }

    @Override
    public List<Department> findList(Club club) {
        return departmentDao.findList(club);
    }
}
