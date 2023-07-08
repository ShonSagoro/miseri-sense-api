package com.miseri.miserisense.controllers;


import com.miseri.miserisense.controllers.dtos.response.BaseResponse;
import com.miseri.miserisense.services.IStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    private IStatisticService service;

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        return service.getAllDataFrequency().apply();
    }

    @GetMapping("/gas")
    public ResponseEntity<BaseResponse> getFrequencyGas(){
        return service.getGasDataFrequency().apply();
    }

    @GetMapping("/quality")
    public ResponseEntity<BaseResponse> getFrequencyQualityAir(){
        return service.getQualityAirDataFrequency().apply();
    }

    @GetMapping("/light")
    public ResponseEntity<BaseResponse> getFrequencyLight(){
        return service.getLightDataFrequency().apply();
    }

    @GetMapping("/humidity")
    public ResponseEntity<BaseResponse> getFrequencyHumidity(){
        return service.getHumidityDataFrequency().apply();
    }

    @GetMapping("/temperature")
    public ResponseEntity<BaseResponse> getFrequencyTemperature(){
        return service.getTemperatureDataFrecuency().apply();
    }


}
