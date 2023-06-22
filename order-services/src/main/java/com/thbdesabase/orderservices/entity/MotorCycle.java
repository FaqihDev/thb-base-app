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
@Table(name = "MotorCycle")
public class MotorCycle extends ModelBase {

    @Column(name = "number_of_motorcycle")
    private Integer numberOfMotorCycle;

    @Column(name = "deadline_bike1")
    private Date deadlineBike1;

    @Column(name = "deadline_bike2")
    private Date deadlineBike2;

    @Column(name = "deadline_bike3")
    private Date deadlineBike3;

    @Column(name = "deadline_bike4")
    private Date deadlineBike4;

    @Column(name = "deadline_bike5")
    private Date deadlineBike5;

    @Column(name = "deadline_bike6")
    private Date deadlineBike6;

    @Column(name = "deadline_bike7")
    private Date deadlineBike7;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_lead_id")
    private HouseholdLead householdLead;

}
