package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.dao.SpotCheckInDao;
import com.huitu.sjclub.entity.SpotCheckIn;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.BaseService;
import com.huitu.sjclub.service.SpotCheckInService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("spotCheckInServiceImpl")
public class SpotCheckInServiceImpl extends BaseServiceImpl<SpotCheckIn,Long> implements SpotCheckInService {

    @Resource(name = "spotCheckInDaoImpl")
    private SpotCheckInDao spotCheckInDao;

    @Override
    public Page<SpotCheckIn> findPage(Pageable pageable, User user) {
        return  spotCheckInDao.findPage(pageable,user);
    }
}
