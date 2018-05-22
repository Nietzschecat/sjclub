package com.huitu.sjclub.dao.impl;

import com.huitu.sjclub.dao.ElectronicVotingDao;
import com.huitu.sjclub.entity.ElectronicVoting;
import org.springframework.stereotype.Repository;

@Repository("electronicVotingDaoImpl")
public class ElectronicVotingDaoImpl extends BaseDaoImpl<ElectronicVoting,Long> implements ElectronicVotingDao {
}
