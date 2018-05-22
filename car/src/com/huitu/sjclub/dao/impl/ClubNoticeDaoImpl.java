package com.huitu.sjclub.dao.impl;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.dao.ClubNoticeDao;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubFile;
import com.huitu.sjclub.entity.ClubNotice;
import com.huitu.sjclub.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository("clubNoticeDaoImpl")
public class ClubNoticeDaoImpl extends BaseDaoImpl<ClubNotice,Long> implements ClubNoticeDao {
    @Override
    public Page<ClubNotice> findPage(Pageable pageable, Club club) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClubNotice> criteriaQuery = criteriaBuilder.createQuery(ClubNotice.class);
        Root<ClubNotice> root = criteriaQuery.from(ClubNotice.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if(club!=null){
            restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.<String>get("club"), club));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery,pageable);
    }
}
