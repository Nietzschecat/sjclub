package com.huitu.sjclub.service;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubFile;
import com.huitu.sjclub.entity.User;

public interface ClubFileService  extends BaseService<ClubFile,Long>{
    Page<ClubFile> findPage(Pageable pageable, Club club);
}
