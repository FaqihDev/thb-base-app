package com.thbdesabase.orderservices.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchQueryRequest {

    private  String houseHoldName;
    private String educationStatus;
    private  String email;
}
