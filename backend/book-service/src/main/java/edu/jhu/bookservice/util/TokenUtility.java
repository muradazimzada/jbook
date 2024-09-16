package edu.jhu.bookservice.util;

import edu.jhu.bookservice.dto.UserDto;

public interface TokenUtility {
    UserDto getUserFromToken(String token);
}
