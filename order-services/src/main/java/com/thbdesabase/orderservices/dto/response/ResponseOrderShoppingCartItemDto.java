package com.thbdesabase.orderservices.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseOrderShoppingCartItemDto {

    private Long shoppingCartItemId;

    private Long itemId;

    private String itemName;

    private Long quantity;

    private Long price;

    private Long catalogueId;
}
