package com.thbdesabase.orderservices.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;



@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderShoppingCartItem extends ModelBase {


    @Column(name = "houseHold_id")
    private Long houseHoldId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "item_id")
    private Long itemId;

    @ManyToOne()
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "order_shopping_cart_id")
    private OrderShoppingCart orderShoppingCart;
}
