package com.huitu.sjclub.dao;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubFile;
import com.huitu.sjclub.entity.User;


public interface ClubFileDao extends BaseDao<ClubFile,Long>  {
    Page<ClubFile> findPage(Pageable pageable, Club club);
}
