package com.huitu.sjclub.service;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.ClassTable;

public interface ClassTableService extends BaseService<ClassTable,Long> {
    Page<ClassTable> findPage(Pageable pageable, ClassTable classTable);

}
