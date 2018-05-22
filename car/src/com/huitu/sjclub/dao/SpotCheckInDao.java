package com.huitu.sjclub.dao;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.SpotCheckIn;
import com.huitu.sjclub.entity.User;

public interface SpotCheckInDao extends BaseDao<SpotCheckIn,Long> {
    Page<SpotCheckIn> findPage(Pageable pageable, User user);
}
