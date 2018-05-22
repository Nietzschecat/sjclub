package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.dao.ClubDao;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.service.ClubService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("clubServiceImpl")
public class ClubServiceImpl extends BaseServiceImpl<Club,Long> implements ClubService {

    @Resource(name = "clubDaoImpl")
    private ClubDao clubDao;
}
