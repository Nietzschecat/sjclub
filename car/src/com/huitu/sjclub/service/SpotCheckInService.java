package com.huitu.sjclub.service;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.SpotCheckIn;
import com.huitu.sjclub.entity.User;

public interface SpotCheckInService extends BaseService<SpotCheckIn,Long>  {
    Page<SpotCheckIn> findPage(Pageable pageable, User user);
}
