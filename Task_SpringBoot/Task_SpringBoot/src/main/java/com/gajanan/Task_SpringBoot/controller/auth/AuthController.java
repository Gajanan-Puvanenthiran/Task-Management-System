package com.gajanan.Task_SpringBoot.controller.auth;

import com.gajanan.Task_SpringBoot.dto.AuthenticationRequest;
import com.gajanan.Task_SpringBoot.dto.AuthenticationResponse;
import com.gajanan.Task_SpringBoot.dto.SignUpRequest;
import com.gajanan.Task_SpringBoot.dto.UserDto;
import com.gajanan.Task_SpringBoot.entities.User;
import com.gajanan.Task_SpringBoot.repositories.UserRepository;
import com.gajanan.Task_SpringBoot.services.auth.AuthService;
import com.gajanan.Task_SpringBoot.services.jwt.UserService;
import com.gajanan.Task_SpringBoot.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignUpRequest signUpRequest) {
        if (authService.hasUserWithEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists with this email");
        }
        UserDto createdUserDto = authService.signupUser(signUpRequest);
        if (createdUserDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userService.userDetailService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        final String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setRefreshToken(refreshToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        System.out.println("JWT Token: " + jwtToken); // Debug log
        System.out.println("Refresh Token: " + refreshToken); // Debug log

        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        try {
            if (jwtUtil.isTokenExpired(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
            }

            String username = jwtUtil.extractUserName(refreshToken);
            UserDetails userDetails = userService.userDetailService().loadUserByUsername(username);

            String newToken = jwtUtil.generateToken(userDetails);
            String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("token", newToken);
            tokens.put("refreshToken", newRefreshToken);

            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }
}
