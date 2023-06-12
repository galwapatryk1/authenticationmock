package com.galwap.authorisation.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorize")
public class AuthorisationController {

    private static final String TOKEN_KEY = "X-SECURITY-TOKEN";

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<Void> getAuth(@RequestBody UserLogin userLogin) {
        HttpHeaders responseHeaders = new HttpHeaders();
        String token = loginService.generateToken(userLogin);
        responseHeaders.set(TOKEN_KEY, token);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .build();
    }

    @GetMapping
    public UserDetails getUserDetails(HttpServletRequest httpServletRequest) throws TokenNotExistException {
        String token = httpServletRequest.getHeader(TOKEN_KEY);
        return loginService.retrieveUserDetails(token);
    }

    @ExceptionHandler(TokenNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle() {
        return "Not found";
    }
}
