package com.huitu.sjclub.dao;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubActivity;
import com.huitu.sjclub.entity.User;

public interface ClubActivityDao extends BaseDao<ClubActivity,Long> {
    Page<ClubActivity> findPage(Pageable pageable, Club club);
}
