package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.dao.ClassTableDao;
import com.huitu.sjclub.entity.ClassTable;
import com.huitu.sjclub.service.BaseService;
import com.huitu.sjclub.service.ClassTableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("classTableServiceImpl")
public class ClassTableServiceImpl extends BaseServiceImpl<ClassTable,Long> implements ClassTableService {
    @Resource(name = "classTableDaoImpl")
    private ClassTableDao classTableDao;

    @Override
    public Page<ClassTable> findPage(Pageable pageable, ClassTable classTable) {
        return classTableDao.findPage(pageable,classTable);
    }
}
