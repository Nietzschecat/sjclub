package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.dao.ClubFileDao;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubFile;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.BaseService;
import com.huitu.sjclub.service.ClubFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("clubFileServiceImpl")
public class ClubFileServiceImpl extends BaseServiceImpl<ClubFile,Long> implements ClubFileService {
    @Resource(name = "clubFileDaoImpl")
    private ClubFileDao clubFileDao;

    @Override
    public Page<ClubFile> findPage(Pageable pageable, Club club) {
        return clubFileDao.findPage(pageable,club);
    }
}
