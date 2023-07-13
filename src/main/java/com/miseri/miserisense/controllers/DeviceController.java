package com.miseri.miserisense.controllers;

import com.miseri.miserisense.controllers.dtos.request.CreateDeviceRequest;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;
import com.miseri.miserisense.services.intefaces.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private IDeviceService service;

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        return service.getAll().apply();
    }

    @PostMapping()
    public ResponseEntity<BaseResponse> create(@RequestBody CreateDeviceRequest request){
        return service.create(request).apply();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> get( @PathVariable Long id){
        return  service.get(id).apply();
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
