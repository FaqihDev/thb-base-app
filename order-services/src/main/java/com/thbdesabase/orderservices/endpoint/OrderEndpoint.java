package com.thbdesabase.orderservices.endpoint;

import com.thbdesabase.orderservices.common.BaseResponse;
import com.thbdesabase.orderservices.common.CommonCode;
import com.thbdesabase.orderservices.common.CommonMessage;
import com.thbdesabase.orderservices.dto.request.RequestOrderShoppingCartItem;
import com.thbdesabase.orderservices.dto.response.ResponseOrderShoppingCartDto;
import com.thbdesabase.orderservices.service.IOrderService;
import com.thbdesabase.orderservices.statval.IApplicationConstant;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@RequestMapping(IApplicationConstant.ContextPath.ORDER)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderEndpoint {

    private final IOrderService orderService;

    @PostMapping(IApplicationConstant.Path.Cart.ADD_CART)
    BaseResponse<ResponseOrderShoppingCartDto> addCart( @RequestBody RequestOrderShoppingCartItem request,
                                                       @RequestParam Long userId) {
        try{
            if (Objects.isNull(request.getItemId() ) && Objects.isNull(request.getQuantity())) {
                return new BaseResponse<>(CommonMessage.DATA_NULL,CommonCode.BAD_REQUEST);
            }
            orderService.addCart(request,userId);

            return new BaseResponse<>(CommonMessage.OK, CommonCode.SUCCESS);
        } catch (ServiceException e) {
            return new BaseResponse<>(CommonMessage.ERROR,CommonCode.SERVER_ERROR);
        }
    }

    @GetMapping(IApplicationConstant.Path.Cart.GET_CART)
    BaseResponse<ResponseOrderShoppingCartDto> getCart(@RequestParam Long userId) {

       try  {
           ResponseOrderShoppingCartDto orderShoppingCartDto = orderService.getCart(userId).getData();
           return new BaseResponse<>(CommonMessage.OK,CommonCode.SUCCESS, orderShoppingCartDto);
       } catch (ServiceException e) {
           return new BaseResponse<>(CommonMessage.ERROR,CommonCode.SERVER_ERROR);
       }

    }

}
