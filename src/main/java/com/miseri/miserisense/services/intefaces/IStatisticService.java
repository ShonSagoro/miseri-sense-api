package com.miseri.miserisense.services.intefaces;

import com.miseri.miserisense.controllers.dtos.request.CorrelationRequest;
import com.miseri.miserisense.controllers.dtos.response.BaseResponse;

public interface IStatisticService {

    BaseResponse getAllDataFrequency();

    BaseResponse getGasDataFrequency();

    BaseResponse getQualityAirDataFrequency();

    BaseResponse getLightDataFrequency();

    BaseResponse getHumidityDataFrequency();

    BaseResponse getTemperatureDataFrecuency();

    BaseResponse getCorrelation(CorrelationRequest request);

}
