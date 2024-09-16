package edu.jhu.userservice.service;

import edu.jhu.userservice.api.LoginRequest;
import edu.jhu.userservice.dto.UserDto;
import edu.jhu.userservice.dto.UserRegisterDto;
import edu.jhu.userservice.entity.User;
import edu.jhu.userservice.exception.UserAlreadyExistsException;
import edu.jhu.userservice.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(UserRegisterDto input) {
        if (isEmailTaken(input.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + input.getEmail() + " already exists");
        }
        User user = new User(input.getFullName(), input.getEmail(), passwordEncoder.encode(input.getPassword()));
        userRepository.save(user);
    }

    public UserDto authenticate(LoginRequest input) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        (input.getPassword())
                )
        );

        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDto(user.getId(), user.getEmail(), user.getFullName());
    }

    private boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}