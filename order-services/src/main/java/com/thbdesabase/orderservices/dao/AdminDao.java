package com.thbdesabase.orderservices.dao;

import com.thbdesabase.orderservices.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDao extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

}
