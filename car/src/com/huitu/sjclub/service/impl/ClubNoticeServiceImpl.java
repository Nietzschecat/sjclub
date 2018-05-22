package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.dao.ClubNoticeDao;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubNotice;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.ClubNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("clubNoticeServiceImpl")
public class ClubNoticeServiceImpl extends BaseServiceImpl<ClubNotice,Long> implements ClubNoticeService {
    @Resource(name = "clubNoticeDaoImpl")
    private ClubNoticeDao clubNoticeDao;

    @Override
    public Page<ClubNotice> findPage(Pageable pageable, Club club) {
        return clubNoticeDao.findPage(pageable,club);
    }
}
