package com.huitu.sjclub.dao.impl;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.dao.SpotCheckInDao;
import com.huitu.sjclub.entity.PlanActivity;
import com.huitu.sjclub.entity.SpotCheckIn;
import com.huitu.sjclub.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository("spotCheckInDaoImpl")
public class SpotCheckInDaoImpl extends BaseDaoImpl<SpotCheckIn,Long> implements SpotCheckInDao{
    @Override
    public Page<SpotCheckIn> findPage(Pageable pageable, User user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SpotCheckIn> criteriaQuery = criteriaBuilder.createQuery(SpotCheckIn.class);
        Root<SpotCheckIn> root = criteriaQuery.from(SpotCheckIn.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if(user!=null){
            restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.<String>get("user"), user));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery,pageable);
    }
}
