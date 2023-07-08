package com.miseri.miserisense.services;

import com.miseri.miserisense.controllers.dtos.response.BaseResponse;

public interface IStatisticService {

    BaseResponse getAllDataFrequency();

    BaseResponse getGasDataFrequency();

    BaseResponse getQualityAirDataFrequency();

    BaseResponse getLightDataFrequency();

    BaseResponse getHumidityDataFrequency();

    BaseResponse getTemperatureDataFrecuency();

}
