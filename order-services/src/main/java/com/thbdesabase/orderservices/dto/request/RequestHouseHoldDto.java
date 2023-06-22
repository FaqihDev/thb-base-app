package com.thbdesabase.orderservices.dto.request;

import com.thbdesabase.orderservices.enumeration.ELivingStatus;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestHouseHoldDto {

    private Date dateOfBirth;
    private String phoneNumb;
    private String password;
    private String name;
    private ELivingStatus livingStatus;
    private String educationStatus;
    @Email(message = "email is not valid")
    private String email;

}
