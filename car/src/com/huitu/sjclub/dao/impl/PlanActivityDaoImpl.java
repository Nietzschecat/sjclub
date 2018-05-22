package com.huitu.sjclub.dao.impl;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.dao.PlanActivityDao;
import com.huitu.sjclub.entity.ClubActivity;
import com.huitu.sjclub.entity.PlanActivity;
import com.huitu.sjclub.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by admin on 2018/4/7.
 */
@Repository("planActivityDaoImpl")
public class PlanActivityDaoImpl extends BaseDaoImpl<PlanActivity,Long> implements PlanActivityDao {

    @Override
    public Page<PlanActivity> findPage(Pageable pageable, User user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PlanActivity> criteriaQuery = criteriaBuilder.createQuery(PlanActivity.class);
        Root<PlanActivity> root = criteriaQuery.from(PlanActivity.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if(user!=null){
            restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.<String>get("user"), user));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery,pageable);
    }
}
