package edu.jhu.bookservice.util;


import edu.jhu.bookservice.dto.UserDto;
import edu.jhu.bookservice.proxy.UserServiceProxy;
import org.springframework.stereotype.Service;

@Service
public class TokenUtilityImpl implements TokenUtility {

    private final UserServiceProxy userServiceProxy;

    public TokenUtilityImpl(UserServiceProxy userServiceProxy) {
        this.userServiceProxy = userServiceProxy;
    }

    public UserDto getUserFromToken(String token) {
        // Extract JWT token (assuming "Bearer " prefix)
        String jwtToken = extractToken(token);

        // Retrieve user details from user-service
        return userServiceProxy.getUserDetails(jwtToken);
    }

    private String extractToken(String token) {
        if (token.startsWith("Bearer ")) {
            return token.substring(7); // Remove "Bearer " prefix
        } else {
            throw new IllegalArgumentException("Invalid Authorization header format");
        }
    }
}
