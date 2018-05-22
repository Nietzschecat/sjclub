package com.huitu.sjclub.dao.impl;

import com.huitu.sjclub.dao.ClubDao;
import com.huitu.sjclub.entity.Club;
import org.springframework.stereotype.Repository;

@Repository("clubDaoImpl")
public class ClubDaoImpl extends BaseDaoImpl<Club,Long> implements ClubDao {
}
