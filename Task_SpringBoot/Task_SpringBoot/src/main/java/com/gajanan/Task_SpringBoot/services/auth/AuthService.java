package com.gajanan.Task_SpringBoot.services.auth;

import com.gajanan.Task_SpringBoot.dto.SignUpRequest;
import com.gajanan.Task_SpringBoot.dto.UserDto;

public interface AuthService {

    UserDto signupUser(SignUpRequest signUpRequest);

    boolean hasUserWithEmail(String email);
}
