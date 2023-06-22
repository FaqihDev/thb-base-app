package com.thbdesabase.orderservices.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderShoppingCart extends ModelBase {


    @OneToMany(
            mappedBy = "orderShoppingCart",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<OrderShoppingCartItem> items;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "household_id")
    private HouseholdLead householdLeadId;


}
