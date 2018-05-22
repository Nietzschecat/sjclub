package com.huitu.sjclub.dao;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.Department;

import java.util.List;

public interface DepartmentDao extends BaseDao<Department,Long> {
    Page<Department> findPage(Pageable pageable, Club club);

    List<Department> findList(Club club);
}
