package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.dao.ClubActivityDao;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubActivity;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.ClubActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("clubActivityServiceImpl")
public class ClubActivityServiceImpl extends BaseServiceImpl<ClubActivity,Long> implements ClubActivityService {

    @Resource(name = "clubActivityDaoImpl")
    private ClubActivityDao clubActivityDao;

    @Override
    public Page<ClubActivity> findPage(Pageable pageable, Club club) {
        return clubActivityDao.findPage(pageable,club);
    }
}
