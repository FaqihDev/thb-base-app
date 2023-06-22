package com.thbdesabase.orderservices.dao;

import com.thbdesabase.orderservices.entity.HouseholdLead;
import com.thbdesabase.orderservices.entity.OrderShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderShoppingCartDao extends JpaRepository<OrderShoppingCart, Long> {

    OrderShoppingCart findByhouseholdLeadId(HouseholdLead householdLead);

}
