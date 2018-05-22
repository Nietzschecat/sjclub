package com.huitu.sjclub.dao.impl;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.dao.ClubActivityDao;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubActivity;
import com.huitu.sjclub.entity.Role;
import com.huitu.sjclub.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository("clubActivityDaoImpl")
public class ClubActivityDaoImpl extends BaseDaoImpl<ClubActivity,Long> implements ClubActivityDao {

    @Override
    public Page<ClubActivity> findPage(Pageable pageable, Club club) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClubActivity> criteriaQuery = criteriaBuilder.createQuery(ClubActivity.class);
        Root<ClubActivity> root = criteriaQuery.from(ClubActivity.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if(club!=null){
            restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.<String>get("club"), club));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery,pageable);
    }
}
