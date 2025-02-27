package com.gajanan.Task_SpringBoot.services.auth;

import com.gajanan.Task_SpringBoot.dto.SignUpRequest;
import com.gajanan.Task_SpringBoot.dto.UserDto;
import com.gajanan.Task_SpringBoot.entities.User;
import com.gajanan.Task_SpringBoot.enums.UserRole;
import com.gajanan.Task_SpringBoot.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> optionalUser=userRepository.findByUserRole(UserRole.ADMIN);
        if(optionalUser.isEmpty()){
            User user=new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
            System.out.println("Admin account created successfully");
        }
        else{
            System.out.println("Admin account already exist");
        }
    }

    @Override
    public UserDto signupUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        user.setUserRole(UserRole.EMPLOYEE);
        User createdUser=userRepository.save(user);


        return createdUser.getUserDto();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
