package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.dao.MeetingDao;
import com.huitu.sjclub.entity.Meeting;
import com.huitu.sjclub.service.MeetingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("meetingServiceImpl")
public class MeetingServiceImpl extends BaseServiceImpl<Meeting,Long> implements MeetingService{
    @Resource(name = "meetingDaoImpl")
    private MeetingDao meetingDao;
}
