package com.galwap.authorisation.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final Map<String, UserDetails> userResponseMap = new HashMap<>();

    private final RoleAssigner roleAssigner;

    public String generateToken(UserLogin userLogin) {
        String userId = Base64.getEncoder().encodeToString(userLogin.getUsername().getBytes());
        String token = Base64.getEncoder().encodeToString((userLogin.getUsername() + "_TOKEN").getBytes());
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(userId);
        userDetails.setUserRole(roleAssigner.getRole(userLogin.getUsername()));
        userResponseMap.put(token, userDetails);
        return token;
    }

    public UserDetails retrieveUserDetails(String token) throws TokenNotExistException {
        if (userResponseMap.containsKey(token)) {
            return userResponseMap.get(token);
        } else {
            throw new TokenNotExistException();
        }
    }
}
