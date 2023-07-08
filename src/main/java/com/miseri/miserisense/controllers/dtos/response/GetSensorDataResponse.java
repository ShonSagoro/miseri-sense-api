package com.miseri.miserisense.controllers.dtos.response;

import com.miseri.miserisense.models.SensorData;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetSensorDataResponse {

    private Long id;

    private SensorData.AirData air;

    private SensorData.LightData light;

    private SensorData.HumidityTemperatureData humTemp;

    private String date;

    private String session;

    private Long deviceId;


}


