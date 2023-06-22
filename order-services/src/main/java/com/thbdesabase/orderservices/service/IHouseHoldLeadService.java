package com.thbdesabase.orderservices.service;

import com.thbdesabase.orderservices.dto.request.RequestHouseHoldDto;
import com.thbdesabase.orderservices.entity.HouseholdLead;

import java.util.concurrent.ExecutionException;

public interface IHouseHoldLeadService {

    HouseholdLead saveHouseHold(RequestHouseHoldDto requestHouseHoldDto) throws InterruptedException, ExecutionException;

    HouseholdLead findById(Long id)throws InterruptedException, ExecutionException;

}
