package com.miseri.miserisense.controllers.dtos.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


@Getter @Setter
public class LoginRequest {
    @NonNull
    private String email;
}

