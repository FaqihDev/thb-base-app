package com.thbdesabase.orderservices.service;

import com.thbdesabase.orderservices.common.BaseResponse;
import com.thbdesabase.orderservices.dto.request.RequestOrderShoppingCartItem;
import com.thbdesabase.orderservices.dto.response.ResponseOrderShoppingCartDto;
import com.thbdesabase.orderservices.exception.DataNotFoundException;

public interface IOrderService {

    BaseResponse<ResponseOrderShoppingCartDto> addCart (RequestOrderShoppingCartItem request, Long userId) throws DataNotFoundException;

    BaseResponse<ResponseOrderShoppingCartDto> getCart(Long userId);

}
