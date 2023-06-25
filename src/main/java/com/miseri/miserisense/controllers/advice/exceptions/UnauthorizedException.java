package com.miseri.miserisense.controllers.advice.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(){
        super("Unauthorized");
    }
}
