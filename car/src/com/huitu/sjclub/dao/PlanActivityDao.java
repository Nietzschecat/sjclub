package com.huitu.sjclub.dao;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.PlanActivity;
import com.huitu.sjclub.entity.User;

/**
 * Created by admin on 2018/4/7.
 */
public interface PlanActivityDao extends BaseDao<PlanActivity,Long> {
    Page<PlanActivity> findPage(Pageable pageable, User user);
}
