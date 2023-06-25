package com.miseri.miserisense.controllers;

import com.miseri.miserisense.controllers.dtos.request.CreateUserRequest;
import com.miseri.miserisense.controllers.dtos.request.LoginRequest;
import com.miseri.miserisense.controllers.dtos.request.UpdateUserRequest;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;
import com.miseri.miserisense.services.Interface.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private IUserService service;

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        return service.getAll().apply();
    }

    @PostMapping("/reg")
    public ResponseEntity<BaseResponse> create(@RequestBody CreateUserRequest request){
        log.info("ENTRE");
        return service.create(request).apply();
    }

    @PostMapping("/email")
    public ResponseEntity<BaseResponse> get(@RequestBody LoginRequest request){
        return  service.get(request.getEmail()).apply();
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<BaseResponse> update(@RequestBody UpdateUserRequest request,
                                               @PathVariable Long idUser){
        return service.update(request, idUser).apply();
    }

    @DeleteMapping("/{idUser}")
    public void delete(@PathVariable Long idUser){
        service.delete(idUser);
    }

    @GetMapping("/health")
    public String health(@PathVariable Long idUser){
        return "ok";
    }


}
