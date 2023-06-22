package com.thbdesabase.orderservices.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bike")
public class Bike extends ModelBase {

    @Column(name = "number_of_bike")
    private Integer numberOfBikes;

    @ManyToOne
    @JoinColumn(name = "house_hold_id", referencedColumnName = "id")
    private HouseholdLead householdLead;

}
