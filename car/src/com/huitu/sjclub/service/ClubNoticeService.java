package com.huitu.sjclub.service;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubNotice;
import com.huitu.sjclub.entity.User;


public interface ClubNoticeService extends BaseService<ClubNotice,Long> {
    Page<ClubNotice> findPage(Pageable pageable, Club club);
}
