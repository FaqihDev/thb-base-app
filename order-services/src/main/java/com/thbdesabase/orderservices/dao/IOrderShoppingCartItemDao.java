package com.thbdesabase.orderservices.dao;

import com.thbdesabase.orderservices.entity.OrderShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderShoppingCartItemDao extends JpaRepository<OrderShoppingCartItem, Long> {

    List<OrderShoppingCartItem> findAllByhouseHoldId(Long houseHoldId);

    OrderShoppingCartItem findByhouseHoldId(Long houseHoldId);

}
