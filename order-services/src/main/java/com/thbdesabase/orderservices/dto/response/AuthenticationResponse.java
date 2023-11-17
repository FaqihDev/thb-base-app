package com.thbdesabase.orderservices.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.thbdesabase.orderservices.enumeration.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1386595506033311891L;

    private String name;
    private String role;
    private String email;
    private String accessToken;
    private String refreshToken;
    private boolean isEnabled;

}
