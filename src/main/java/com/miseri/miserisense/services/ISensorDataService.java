package com.miseri.miserisense.services;

import com.miseri.miserisense.controllers.dtos.request.CreateSensorDataRequest;
import com.miseri.miserisense.controllers.dtos.request.UpdateSensorDataRequest;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;
import com.miseri.miserisense.models.SensorData;

import java.util.List;

public interface ISensorDataService {
    BaseResponse get(Long id);

    BaseResponse create(CreateSensorDataRequest request);

    BaseResponse update(UpdateSensorDataRequest request, Long idUser);

    BaseResponse getAll();

    List<SensorData> getAllData();
    void delete(long id);

    List<Double> getAllGas();

    List<Double> getAllQualityAir();

    List<Double> getAllLight();

    List<Double> getAllTemperature();

    List<Double> getAllHumidity();
}
