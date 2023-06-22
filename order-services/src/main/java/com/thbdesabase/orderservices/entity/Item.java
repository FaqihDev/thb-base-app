package com.thbdesabase.orderservices.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "master_item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item  {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "master_item_generator"

    )

    @SequenceGenerator(
            name = "master_item_generator",
            sequenceName = "master_item_generator",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "price")
    private Long price;

    @Column(name = "catalogue_id")
    private Long catalogueId;

    @Column(name = "sold_time")
    private Long soldTime;

}
