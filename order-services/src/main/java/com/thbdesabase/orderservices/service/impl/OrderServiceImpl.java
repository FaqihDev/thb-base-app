package com.thbdesabase.orderservices.service.impl;

import com.thbdesabase.orderservices.common.BaseResponse;
import com.thbdesabase.orderservices.common.CommonCode;
import com.thbdesabase.orderservices.common.CommonMessage;
import com.thbdesabase.orderservices.dao.IItemDao;
import com.thbdesabase.orderservices.dao.IOrderShoppingCartDao;
import com.thbdesabase.orderservices.dao.IOrderShoppingCartItemDao;
import com.thbdesabase.orderservices.dto.request.RequestOrderShoppingCartItem;
import com.thbdesabase.orderservices.dto.response.ResponseOrderShoppingCartDto;
import com.thbdesabase.orderservices.dto.response.ResponseOrderShoppingCartItemDto;
import com.thbdesabase.orderservices.entity.*;
import com.thbdesabase.orderservices.exception.DataNotFoundException;
import com.thbdesabase.orderservices.exception.OutOfStockException;
import com.thbdesabase.orderservices.service.IHouseHoldLeadService;
import com.thbdesabase.orderservices.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.*;

import static com.thbdesabase.orderservices.statval.IApplicationConstant.DefaultNumber.OUT_OF_STOCK;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements IOrderService {

    private final IOrderShoppingCartDao orderShoppingCartDao;

    private final IItemDao iItemDao;

    private final IOrderShoppingCartItemDao orderShoppingCartItemDao;

    private final IHouseHoldLeadService houseHoldLeadService;



    @Override
    public BaseResponse<ResponseOrderShoppingCartDto> addCart(RequestOrderShoppingCartItem request,
                                                              Long userId) throws DataNotFoundException {



        OrderShoppingCart orderShoppingCart = new OrderShoppingCart();
        HouseholdLead householdLead = null;
        try {
            householdLead = houseHoldLeadService.findById(userId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (java.util.concurrent.ExecutionException e) {
            throw new RuntimeException(e);
        }
        if (Objects.isNull(householdLead)) {
            return new BaseResponse<>(CommonMessage.NOT_FOUND,CommonCode.BAD_REQUEST);
        }
        orderShoppingCart.setHouseholdLeadId(householdLead);
        List<OrderShoppingCartItem> orderShoppingCartItems = new ArrayList<>();
        OrderShoppingCartItem orderShoppingCartItem = new OrderShoppingCartItem();

            String  itemIdNotFoundMessage = String.format("Item with id %d is not found",request.getItemId());
            Optional<Item> item = Optional.of(iItemDao.findById(request.getItemId())
                    .filter(x -> x.getId().equals(request.getItemId()))
                    .orElseThrow(() ->  new DataNotFoundException(CommonCode.NOT_FOUND,itemIdNotFoundMessage )));

            if (item.get().getQuantity().equals(OUT_OF_STOCK)) {
                throw new OutOfStockException(CommonCode.BAD_REQUEST,CommonMessage.OUT_OF_STOCK);
            }

            orderShoppingCartItem.setItemId(item.get().getId());
            Date now = Date.from(Instant.now());
            orderShoppingCartItem.setCreatedAt(now);
            orderShoppingCartItem.setTotalPrice((item.get().getPrice()) * request.getQuantity());
            orderShoppingCartItem.setQuantity(request.getQuantity());
            orderShoppingCartItem.setHouseHoldId(householdLead.getId());
            orderShoppingCartItem.setOrderShoppingCart(orderShoppingCart);
            orderShoppingCartItems.add(orderShoppingCartItem);
            orderShoppingCart.setItems(orderShoppingCartItems);
            orderShoppingCartDao.save(orderShoppingCart);
            return new BaseResponse<>(CommonMessage.SAVED,CommonCode.SUCCESS);
    }

    @Override
    public BaseResponse<ResponseOrderShoppingCartDto> getCart(Long userId) {
        ResponseOrderShoppingCartDto responseOrderShoppingCartDto = new ResponseOrderShoppingCartDto();
        List<OrderShoppingCartItem> cartItemsByUserId = orderShoppingCartItemDao.findAllByhouseHoldId(userId);
        OrderShoppingCartItem shoppingCartItem  = orderShoppingCartItemDao.findByhouseHoldId(userId);
        HouseholdLead householdLead = null;
        try {
            householdLead = houseHoldLeadService.findById(userId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (java.util.concurrent.ExecutionException e) {
            throw new RuntimeException(e);
        }
        OrderShoppingCart orderShoppingCart = orderShoppingCartDao.findByhouseholdLeadId(householdLead);
        Date cartCreatedTime = shoppingCartItem.getCreatedAt();

        responseOrderShoppingCartDto.setCreatedAt(cartCreatedTime);
        responseOrderShoppingCartDto.setShoppingCartId(orderShoppingCart.getId());

        for (OrderShoppingCartItem cartItem : cartItemsByUserId) {
            ResponseOrderShoppingCartItemDto responseOrderShoppingCartItemDto = new ResponseOrderShoppingCartItemDto();
            List<ResponseOrderShoppingCartItemDto> responseOrderShoppingCartItemDtoList = new ArrayList<>();
            Optional<Item> item = iItemDao.findById(cartItem.getItemId());
            responseOrderShoppingCartItemDto.setItemId(cartItem.getItemId());
            if (item.isPresent()) {
                responseOrderShoppingCartItemDto.setItemName(item.get().getItemName());
                responseOrderShoppingCartItemDto.setQuantity(item.get().getQuantity());
                responseOrderShoppingCartItemDto.setPrice(item.get().getPrice());
            }
            responseOrderShoppingCartItemDtoList.add(responseOrderShoppingCartItemDto);
            responseOrderShoppingCartDto.setResponseShoppingCartItemDtos(responseOrderShoppingCartItemDtoList);
        }

        return new BaseResponse<>(CommonMessage.FOUND,CommonCode.SUCCESS,responseOrderShoppingCartDto);
    }


}
