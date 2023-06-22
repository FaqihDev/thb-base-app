package com.thbdesabase.orderservices.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrderShoppingCartDto {

    private Long shoppingCartId;
    private Date createdAt;
    private List<ResponseOrderShoppingCartItemDto> responseShoppingCartItemDtos;
}
