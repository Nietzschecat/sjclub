package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.dao.PlanActivityDao;
import com.huitu.sjclub.entity.PlanActivity;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.PlanActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by admin on 2018/4/7.
 */
@Service("planActivityServiceImpl")
public class PlanActivityServiceImpl extends BaseServiceImpl<PlanActivity,Long> implements PlanActivityService{
    @Resource(name = "planActivityDaoImpl")
    private PlanActivityDao planActivityDao;

    @Override
    public Page<PlanActivity> findPage(Pageable pageable, User user) {
        return planActivityDao.findPage(pageable,user);
    }
}
