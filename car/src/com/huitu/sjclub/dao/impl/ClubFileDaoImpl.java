package com.huitu.sjclub.dao.impl;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.dao.ClubFileDao;
import com.huitu.sjclub.entity.Club;
import com.huitu.sjclub.entity.ClubFile;
import com.huitu.sjclub.entity.PlanActivity;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.ClubFileService;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository("clubFileDaoImpl")
public class ClubFileDaoImpl extends BaseDaoImpl<ClubFile,Long> implements ClubFileDao{

    @Override
    public Page<ClubFile> findPage(Pageable pageable, Club club) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClubFile> criteriaQuery = criteriaBuilder.createQuery(ClubFile.class);
        Root<ClubFile> root = criteriaQuery.from(ClubFile.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if(club!=null){
            restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.<String>get("club"), club));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery,pageable);
    }
}
