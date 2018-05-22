package com.huitu.sjclub.dao.impl;

import com.huitu.sjclub.dao.RoleDao;
import com.huitu.sjclub.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by admin on 2018/3/31.
 */
@Repository("roleDaoImp")
public class RoleDaoImpl extends BaseDaoImpl<Role,Long> implements RoleDao {
    @Override
    public List<Role> findByAuthorities(String authorities) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> root = criteriaQuery.from(Role.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if(!"".equals(restrictions)){
            restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.<String>get("authorities"), authorities));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery,null,null,null,null);
    }
}
