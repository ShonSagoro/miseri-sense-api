package com.miseri.miserisense.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class UpdateUserRequest {

    private String email;

    private String name;
}
