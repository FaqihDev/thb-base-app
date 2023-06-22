package com.thbdesabase.orderservices.dao;

import com.thbdesabase.orderservices.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemDao extends JpaRepository<Item,Long> {
}
