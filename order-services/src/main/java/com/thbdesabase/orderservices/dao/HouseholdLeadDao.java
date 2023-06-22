package com.thbdesabase.orderservices.dao;

import com.thbdesabase.orderservices.entity.HouseholdLead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface HouseholdLeadDao extends JpaRepository<HouseholdLead, Long> {
}
