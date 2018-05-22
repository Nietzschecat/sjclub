package com.huitu.sjclub.dao.impl;

import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.dao.ClassTableDao;
import com.huitu.sjclub.entity.ClassTable;
import com.huitu.sjclub.entity.ClubActivity;
import com.huitu.sjclub.service.ClassTableService;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository("classTableDaoImpl")
public class ClassTableDaoImpl extends BaseDaoImpl<ClassTable,Long> implements ClassTableDao {
    @Override
    public Page<ClassTable> findPage(Pageable pageable, ClassTable classTable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClassTable> criteriaQuery = criteriaBuilder.createQuery(ClassTable.class);
        Root<ClassTable> root = criteriaQuery.from(ClassTable.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if(classTable.getUser()!=null){
            restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.<String>get("user"), classTable.getUser()));
        }
        if(classTable.getNoClass()!=null||"".equals(classTable.getNoClass())){
            restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.<String>get("noClass"), classTable.getNoClass()));
        }
        if(classTable.getClassTime()!=null||"".equals(classTable.getClassTime())){
            restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.<String>get("classTime"), classTable.getClassTime()));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery,pageable);
    }
}
