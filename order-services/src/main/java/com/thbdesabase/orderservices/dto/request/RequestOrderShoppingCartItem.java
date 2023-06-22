package com.thbdesabase.orderservices.dto.request;
import lombok.*;




@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestOrderShoppingCartItem {

    private Long itemId;
    private Long quantity;


}
