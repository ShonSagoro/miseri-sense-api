package com.miseri.miserisense.controllers;

import com.miseri.miserisense.controllers.dtos.request.*;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;
import com.miseri.miserisense.services.Interface.ISensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensor")
public class SensorDataController {
    @Autowired
    private ISensorDataService service;

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        return service.getAll().apply();
    }

    @PostMapping()
    public ResponseEntity<BaseResponse> create(@RequestBody CreateSensorDataRequest request){
        return service.create(request).apply();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> get( @PathVariable Long id){
        return  service.get(id).apply();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@RequestBody UpdateSensorDataRequest request,
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
