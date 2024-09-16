package edu.jhu.userservice.controller;


import edu.jhu.userservice.dto.UserDto;
import edu.jhu.userservice.entity.User;
import edu.jhu.userservice.repository.UserRepository;
import edu.jhu.userservice.security.JwtService;
import edu.jhu.userservice.service.UserService;
import edu.jhu.userservice.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RequestMapping("/api/users")
    @RestController
    public class UserController {
        private final UserService userService;
        private final UserRepository userRepository;
        private final JwtService jwtService;

        public UserController(UserService userService, UserRepository userRepository,
                              JwtService jwtService) {
            this.userService = userService;
            this.userRepository = userRepository;
            this.jwtService = jwtService;
        }


        @GetMapping("/me")
        public ResponseEntity<?> authenticatedUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

                String email = userDetails.getUsername();

                UserDto userDTO = userService.getUserByEmail(email);

                return ResponseEntity.ok(userDTO);
            }
             return  ResponseEntity.status(401).body("User not found");
        }
        @GetMapping("/")
        public ResponseEntity<List<UserDto>> allUsers() {

            List <UserDto> users = userService.allUsers();
            return ResponseEntity.ok(users);
        }

    @GetMapping("/me-bookservice")
    public UserDto getMeForBookService(@RequestHeader("Authorization") String token) {
        // Remove "Bearer " from the token if it exists
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Parse the JWT token to extract user email
        String email = jwtService.extractUsername(token);  // Assuming extractUsername is a method in JwtService

        // Fetch the user based on the parsed email
        Optional<User> user = userRepository.findByEmail(email);

        // Return the user data in the form of UserDTO
        return user.map(u -> new UserDto(u.getId(), u.getEmail(), u.getFullName()))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("{id}-bookservice")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id
                                               ) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getUsersByIds-bookservice")
    public Map<Long, UserDto> getUsersByIds(@RequestParam("ids") List<Long> ids) {
        List<User> users = userRepository.findAllById(ids);
        return users.stream()
                .collect(Collectors.toMap(
                        User::getId,
                        user -> new UserDto(user.getId(), user.getEmail(), user.getFullName())
                ));
    }


}
