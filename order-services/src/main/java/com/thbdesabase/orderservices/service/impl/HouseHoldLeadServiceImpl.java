package com.thbdesabase.orderservices.service.impl;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.thbdesabase.orderservices.service.IHouseHoldLeadService;
import com.thbdesabase.orderservices.dao.HouseholdLeadDao;
import com.thbdesabase.orderservices.dto.request.RequestHouseHoldDto;
import com.thbdesabase.orderservices.entity.HouseholdLead;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HouseHoldLeadServiceImpl implements IHouseHoldLeadService {

    private final HouseholdLeadDao householdLeadDao;

    @Override
    public HouseholdLead saveHouseHold(RequestHouseHoldDto requestHouseHoldDto) throws InterruptedException, ExecutionException {

        HouseholdLead householdLead = new HouseholdLead();
        householdLead.setHouseHoldName(requestHouseHoldDto.getName());
        householdLead.setLivingStatus(requestHouseHoldDto.getLivingStatus());
        householdLead.setEducationStatus(requestHouseHoldDto.getEducationStatus());
        householdLead.setDateOfBirth(requestHouseHoldDto.getDateOfBirth());
        householdLead.setEmail(requestHouseHoldDto.getEmail());
        householdLead.setPassword(requestHouseHoldDto.getPassword());
        householdLead.setPhoneNumb(requestHouseHoldDto.getPhoneNumb());
        Date now = Date.from(Instant.now());
        householdLead.setCreatedAt(now);

        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection("house_hold_lead")
                .document(householdLead.getHouseHoldName())
                .set(requestHouseHoldDto);
         collectionApiFuture.get().getUpdateTime().toString();

        return householdLeadDao.save(householdLead);

    }

    @Override
    public HouseholdLead findById(Long id) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("house_hold_lead").document(String.valueOf(id));
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        HouseholdLead householdLead;

        if (document.exists()){
             document.toObject(HouseholdLead.class);
           return householdLeadDao.findById(id).get();
        }
        return null;
    }


}
