package com.thbdesabase.orderservices.dao;

import com.thbdesabase.orderservices.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenDao extends JpaRepository<Token,Long> {


    @Query("SELECT t from Token t " +
                 "INNER JOIN Admin a ON t.admin.id = a.id " +
                 "where a.id = :id and (t.isExpired = false or t.isRevoked = false) " )
    List<Token> findAllValidTokenByAdmin(Long id);

    Optional<Token> findByToken(String token);


}
