package com.thbdesabase.orderservices.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class ModelBase extends AuditableBase {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_profile_id_seq"

    )

    @SequenceGenerator(
            name = "user_profile_id_seq",
            sequenceName = "user_profile_id_seq",
            allocationSize = 1
    )
    private Long id;


    private boolean isDeleted;

}
