package com.gajanan.Task_SpringBoot.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;

    private String password;
}