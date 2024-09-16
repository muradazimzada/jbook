package edu.jhu.userservice.controller;

import edu.jhu.userservice.api.LoginRequest;
import edu.jhu.userservice.api.LoginResponse;
import edu.jhu.userservice.dto.UserDto;
import edu.jhu.userservice.dto.UserRegisterDto;
import edu.jhu.userservice.security.JwtService;
import edu.jhu.userservice.service.AuthenticationService;
import edu.jhu.userservice.service.AuthenticationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegisterDto registerUserDto) {
         authenticationService.signup(registerUserDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest LoginRequest) {
        UserDto userDto =  authenticationService.authenticate(LoginRequest);

        String jwtToken = jwtService.generateToken(userDto);

        LoginResponse loginResponse = new LoginResponse(jwtToken,(jwtService.getExpirationTime() ));

        return ResponseEntity.ok(loginResponse);
    }
}

