package com.miseri.miserisense.services.intefaces;

import com.miseri.miserisense.controllers.dtos.request.CreateSensorDataRequest;
import com.miseri.miserisense.controllers.dtos.request.UpdateSensorDataRequest;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;
import com.miseri.miserisense.models.SensorData;

import java.util.List;

public interface ISensorDataService {
    BaseResponse get(Long id);

    BaseResponse create(CreateSensorDataRequest request);

    BaseResponse createList(List<CreateSensorDataRequest> request);

    BaseResponse update(UpdateSensorDataRequest request, Long idUser);

    BaseResponse getAll();

    BaseResponse getAllBySession(String session);

    BaseResponse getAllByDate(String date);


    List<SensorData> getListBySession(String session);

    List<SensorData> getListByDate(String date);

    List<SensorData> getAllData();
    void delete(long id);

    List<Double> getAllGas();

    List<Double> getAllQualityAir();

    List<Double> getAllLight();

    List<Double> getAllTemperature();

    List<Double> getAllHumidity();
}
