package com.miseri.miserisense.configuration.security.user;

import lombok.Data;

@Data
public class AuthCredentials {
    private String email;
    private String password;
}
