package com.spring.daxa.repositories.impl;

import com.spring.daxa.entity.Attraction;
import com.spring.daxa.enums.AttractionFields;
import com.spring.daxa.repositories.AttractionRepositoryOwn;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@Repository
public class AttractionRepositoryOwnImpl implements AttractionRepositoryOwn {
    private final EntityManager entityManager;

    public AttractionRepositoryOwnImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Attraction> getAttractionsByCriteria(Map<AttractionFields, Object> criteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Attraction> criteriaQuery = criteriaBuilder.createQuery(Attraction.class);
        Root<Attraction> root = criteriaQuery.from(Attraction.class);
        Predicate[] predicates = new Predicate[criteria.size()];
        int i = 0;
        for (Map.Entry<AttractionFields, Object> af : criteria.entrySet()) {
            switch (af.getKey()) {
                case LONGITUDE -> predicates[i] = criteriaBuilder.between(root.get("longitude"), Math.abs((Double) af.getValue() - 15), ((Double) af.getValue() + 15));
                case LATITUDE -> predicates[i] = criteriaBuilder.between(root.get("latitude"), Math.abs((Double) af.getValue() - 15), ((Double) af.getValue() + 15));
                case CATEGORY -> predicates[i] = criteriaBuilder.equal(root.get("category"), af.getValue());
                case MIDRATE -> predicates[i] = criteriaBuilder.gt(root.get("midRate"), ((Double) af.getValue() - 0.1));
                case CITY -> predicates[i] = root.get("city").get("name").in(af.getValue());
            }
            i++;
        }
        criteriaQuery.select(root).where(predicates).orderBy(criteriaBuilder.asc(root.get("longitude")), criteriaBuilder.asc(root.get("latitude")));
        TypedQuery<Attraction> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
