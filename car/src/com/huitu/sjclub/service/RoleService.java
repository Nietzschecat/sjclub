package com.huitu.sjclub.service;

import com.huitu.sjclub.entity.Role;

import java.util.List;

/**
 * Created by admin on 2018/3/31.
 */
public interface RoleService extends BaseService<Role,Long> {
    List<Role> findByAuthorities(String authorities);

}
