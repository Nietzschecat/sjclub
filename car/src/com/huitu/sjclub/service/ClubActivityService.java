package com.huitu.sjclub.service;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubActivity;
import com.huitu.sjclub.entity.User;

public interface ClubActivityService extends BaseService<ClubActivity,Long> {
    Page<ClubActivity> findPage(Pageable pageable, Club club);
}
