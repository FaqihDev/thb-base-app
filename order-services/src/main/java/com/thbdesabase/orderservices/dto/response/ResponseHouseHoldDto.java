package com.thbdesabase.orderservices.dto.response;

import lombok.*;

import java.util.Date;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHouseHoldDto {
    private Date dateOfBirth;
    private Date createdAt;
    private Date updatedAt;
    private Long phoneNumb;
    private String name;
    private String livingStatus;
    private String educationStatus;
    private String email;
}
