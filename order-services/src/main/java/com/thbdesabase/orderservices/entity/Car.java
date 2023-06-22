package com.thbdesabase.orderservices.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car")
public class Car extends ModelBase {

    @Column(name = "number_of_car")
    private Integer numberOfCar;

    @Column(name = "deadline_car1")
    private Date deadlineCar1;

    @Column(name = "deadline_car2")
    private Date deadlineCar2;

    @Column(name = "deadline_car3")
    private Date deadlineCar3;

    @Column(name = "deadline_car4")
    private Date deadlineCar4;

    @Column(name = "deadline_car5")
    private Date deadlineCar5;

    @Column(name = "deadline_car6")
    private Date deadlineCar6;

    @Column(name = "deadline_car7")
    private Date deadlineCar7;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_lead_id")
    private HouseholdLead householdLead;

}
