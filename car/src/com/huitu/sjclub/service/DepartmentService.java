package com.huitu.sjclub.service;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.Department;
import com.huitu.sjclub.entity.User;

import java.util.List;

public interface DepartmentService extends BaseService<Department,Long> {
    Page<Department> findPage(Pageable pageable, Club club);

    List<Department> findList(Club club);
}
