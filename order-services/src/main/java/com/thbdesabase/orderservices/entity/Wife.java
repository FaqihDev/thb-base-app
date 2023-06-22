package com.thbdesabase.orderservices.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "wife")
public class Wife extends ModelBase {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "dob")
    private Date dob;

    @Column(name="last_education")
    private String lastEducation;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "hobby")
    private String hobby;

    @ManyToOne
    @JoinColumn(name = "household_lead_id",referencedColumnName = "id")
    private HouseholdLead householdLead;

}
