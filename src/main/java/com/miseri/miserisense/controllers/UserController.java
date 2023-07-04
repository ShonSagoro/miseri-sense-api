package com.miseri.miserisense.controllers;

import com.miseri.miserisense.controllers.dtos.request.CreateUserRequest;
import com.miseri.miserisense.controllers.dtos.request.LoginRequest;
import com.miseri.miserisense.controllers.dtos.request.UpdateUserRequest;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;
import com.miseri.miserisense.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService service;

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        return service.getAll().apply();
    }

    @PostMapping("/sing-up")
    public ResponseEntity<BaseResponse> create(@RequestBody CreateUserRequest request){
        return service.create(request).apply();
    }

    @PostMapping("/email")
    public ResponseEntity<BaseResponse> get(@RequestBody LoginRequest request){
        return  service.get(request.getEmail()).apply();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@RequestBody UpdateUserRequest request,
                                               @PathVariable Long id){
        return service.update(request, id).apply();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("/health")
    public String health(@PathVariable Long idUser){
        return "ok";
    }


}
