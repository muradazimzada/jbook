package edu.jhu.bookservice.client;

import edu.jhu.bookservice.dto.UserDto;
import lombok.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Service
@FeignClient(name = "user-service", url = "${feign.client.url}")
public interface UserServiceClient {

    @GetMapping("/me-bookservice")
    UserDto getUserDetailsForBookService(@RequestHeader("Authorization") String token,
                                         @RequestHeader("X-API-KEY") String apiKey);
    @GetMapping("/getUsersByIds-bookservice")
    Map<Long, UserDto> getUsersByIds(@RequestParam("ids") List<Long> ids,
                                     @RequestHeader("X-API-KEY") String apiKey );

    @GetMapping("/{id}-bookservice")
    UserDto getUserById(@PathVariable("id") Long id, @RequestHeader("X-API-KEY") String apiKey);
}
