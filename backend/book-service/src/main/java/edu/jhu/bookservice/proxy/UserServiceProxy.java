package edu.jhu.bookservice.proxy;

import edu.jhu.bookservice.client.UserServiceClient;
import edu.jhu.bookservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceProxy {

    private final UserServiceClient userServiceClient;

    @Value("${bookservice.api.key}")  // API key for user-service from application.properties
    private String apiKey;

    public UserServiceProxy(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    public UserDto getUserDetails(String token) {
        return userServiceClient.getUserDetailsForBookService("Bearer " + token, apiKey);
    }

    public Map<Long, UserDto> getUsersByIds(List<Long> ids) {
        return userServiceClient.getUsersByIds(ids, apiKey);
    }

    public UserDto getUserById(Long userId) {
        return userServiceClient.getUserById(userId, apiKey);
    }
}
