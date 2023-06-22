package com.thbdesabase.orderservices.entity;


import com.thbdesabase.orderservices.enumeration.EChildrenCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;


import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "children")
public class Children extends ModelBase {

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private EChildrenCategory category;

    @Column(name = "last_education")
    private String lastEducation;

    @Column(name = "occupation")
    private String occupation;

    @ManyToOne
    @JoinColumn(name = "household_lead_id",referencedColumnName = "id")
    private HouseholdLead householdLead;


}
