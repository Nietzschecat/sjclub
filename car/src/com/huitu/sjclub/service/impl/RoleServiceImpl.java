package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.dao.RoleDao;
import com.huitu.sjclub.entity.Role;
import com.huitu.sjclub.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by admin on 2018/3/31.
 */
@Service("roleServiceImpl")
public class RoleServiceImpl extends BaseServiceImpl<Role,Long> implements RoleService {
    @Resource(name = "roleDaoImp")
    private RoleDao roleDao;

    @Override
    public List<Role> findByAuthorities(String authorities) {
        return roleDao.findByAuthorities(authorities);
    }
}
