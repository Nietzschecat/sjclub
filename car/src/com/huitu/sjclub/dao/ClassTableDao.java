package com.huitu.sjclub.dao;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.ClassTable;

public interface ClassTableDao extends BaseDao<ClassTable,Long> {
    Page<ClassTable> findPage(Pageable pageable, ClassTable classTable);

}
