package com.huitu.sjclub.service;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.PlanActivity;
import com.huitu.sjclub.entity.User;

/**
 * Created by admin on 2018/4/7.
 */
public interface PlanActivityService extends BaseService<PlanActivity,Long> {
    Page<PlanActivity> findPage(Pageable pageable, User user);
}
