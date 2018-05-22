package com.huitu.sjclub.dao.impl;

import com.huitu.sjclub.dao.MeetingDao;
import com.huitu.sjclub.entity.Meeting;
import org.springframework.stereotype.Repository;

@Repository("meetingDaoImpl")
public class MeetingDaoImpl extends BaseDaoImpl<Meeting,Long> implements MeetingDao{
}
