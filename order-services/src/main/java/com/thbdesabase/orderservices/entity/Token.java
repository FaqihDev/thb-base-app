package com.thbdesabase.orderservices.entity;

import com.thbdesabase.orderservices.enumeration.ETokenType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token extends ModelBase {

    @Column(name = "token", unique = true)
    public String token;

    @Column(name = "revoked")
    public boolean isRevoked;

    @Column(name = "is_expired")
    public boolean isExpired;

    @Enumerated(EnumType.STRING)
    public ETokenType tokenType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

}
