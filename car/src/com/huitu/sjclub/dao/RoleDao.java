package com.huitu.sjclub.dao;

import com.huitu.sjclub.entity.Role;

import java.util.List;

/**
 * Created by admin on 2018/3/31.
 */
public interface RoleDao extends BaseDao<Role,Long> {
    List<Role> findByAuthorities(String authorities);

}
