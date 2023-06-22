package com.thbdesabase.orderservices.dao;

import com.thbdesabase.orderservices.dto.request.SearchQueryRequest;
import com.thbdesabase.orderservices.entity.HouseholdLead;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HouseHoldLeadDaoClass {

    private final EntityManager  em;

    public List<HouseholdLead> findAllBySimpleQuery(
            String houseHoldName,
            String educationStatus,
            String email
    ) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<HouseholdLead> criteriaQuery = criteriaBuilder.createQuery(HouseholdLead.class);

        // select * from houseHold
        Root<HouseholdLead> root = criteriaQuery.from(HouseholdLead.class);

        // prepare where clause
        // Where houseHoldName like or phoneNumber like
        Predicate houseHoldNamePredicate = criteriaBuilder.
                like(root.get("houseHoldName"),"%" + houseHoldName + "%");

        Predicate phoneNumberPredicate = criteriaBuilder.
                like(root.get("educationStatus"),"%" + educationStatus + "%");

        Predicate emailPredicate = criteriaBuilder.
                like(root.get("email"),"%" + email + "%");

        Predicate houseHoldNameOrPhoneNumber = criteriaBuilder.or(houseHoldNamePredicate,phoneNumberPredicate);
       var andEmailPredicate  =  criteriaBuilder.and(houseHoldNameOrPhoneNumber, emailPredicate);
       criteriaQuery.where(andEmailPredicate);
        TypedQuery<HouseholdLead>   query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<HouseholdLead> findAllCriteria (SearchQueryRequest request) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<HouseholdLead> criteriaQuery = criteriaBuilder.createQuery(HouseholdLead.class);
        List<Predicate> predicate = new ArrayList<>();

        //Select * from houseHold
        Root<HouseholdLead> root  = criteriaQuery.from(HouseholdLead.class);

        if(Objects.nonNull(request.getHouseHoldName())) {
            Predicate houseHoldNamePredicate = criteriaBuilder
                    .like(root.get("houseHoldName"), "%"+request.getHouseHoldName()+"%");
            predicate.add(houseHoldNamePredicate);
        }

        if(Objects.nonNull(request.getEducationStatus())) {
            Predicate educationStatusPredicate = criteriaBuilder
                    .like(root.get("educationStatus"), "%"+request.getEducationStatus()+"%");
            predicate.add(educationStatusPredicate);
        }

        if(Objects.nonNull(request.getEmail())) {
            Predicate emailPredicate = criteriaBuilder
                    .like(root.get("email"), "%"+request.getEmail()+"%");
            predicate.add(emailPredicate);
        }

        criteriaQuery.where(
                criteriaBuilder.or(predicate.toArray(new Predicate[0]))
        );

        TypedQuery<HouseholdLead> query = em.createQuery(criteriaQuery);
        return query.getResultList();

    }

}
