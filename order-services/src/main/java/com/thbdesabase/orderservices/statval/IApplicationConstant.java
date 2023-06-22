package com.thbdesabase.orderservices.statval;

public interface IApplicationConstant {

    interface ContextPath{
        String ORDER = "/v1/order-service";
        String USER = "/v1/user-service";
        String AUTHENTICATION = "/api/v1/auth";
    }

    interface Path{

        interface Cart {
            String ADD_CART = "/cart/add-cart";
            String GET_CART = "/cart/get-cart";
        }

        interface User {
            String ADD_USER ="/user";

        }

        interface Authentication {
            String REGISTER = "/register";

            String LOGIN = "/login";

            String REFRESH_TOKEN = "/refresh-token";
        }

    }

    interface  DefaultNumber {
        Long OUT_OF_STOCK = 0L;

    }

}
