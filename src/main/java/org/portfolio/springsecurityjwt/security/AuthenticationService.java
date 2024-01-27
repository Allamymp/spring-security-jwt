package org.portfolio.springsecurityjwt.security;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public String authenticate(){
        return "token";
    }
}
