package com.thbdesabase.orderservices.entity;

import com.thbdesabase.orderservices.enumeration.ELivingStatus;
import com.thbdesabase.orderservices.enumeration.EPreferredSambungTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "household_lead")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdLead extends ModelBase {

    @Column(name = "house_hold_name")
    private String houseHoldName;

    @Column(name = "kk_id")
    private String kkId;

    @Column(name = "education_status")
    private String educationStatus;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "is_active")
    private boolean isActive;

    @Email(message = "email is not valid")
    @NotNull(message = "email can not be null")
    @Column(name = "email")
    private String email;

    @Column(name = "phone_numb")
    private String phoneNumb;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "kelompok")
    private String kelompok;

    @Column(name = "address")
    private String address;

    @Column(name = "life_status")
    private boolean lifeStatus;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "sambung_time")
    private EPreferredSambungTime sambungTime;

    @Column(name = "living_status")
    @Enumerated(EnumType.STRING)
    private ELivingStatus livingStatus;

    @OneToMany(
            mappedBy = "householdLeadId",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<OrderShoppingCart> orderShoppingCart;

    @OneToMany(
            mappedBy = "householdLead",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Bike> bikes;

    @OneToOne(
            cascade = CascadeType.PERSIST,
            mappedBy = "householdLead")
    private Wife wife;

    @OneToOne(
            cascade = CascadeType.ALL,
            mappedBy = "householdLead")
    private Car car;

    @OneToOne(
            cascade = CascadeType.ALL,
            mappedBy = "householdLead")
    private MotorCycle motorCycle;

    @OneToOne(
            cascade = CascadeType.ALL,
            mappedBy = "householdLead")
    private Children children;
}
