package com.gajanan.Task_SpringBoot.dto;

import com.gajanan.Task_SpringBoot.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;

    private Long userId;

    private UserRole userRole;

    private String refreshToken;
}
