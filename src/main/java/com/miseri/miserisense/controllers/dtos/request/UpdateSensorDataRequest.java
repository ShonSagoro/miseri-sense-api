package com.miseri.miserisense.controllers.dtos.request;

import com.miseri.miserisense.models.SensorData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSensorDataRequest {

    private SensorData.AirData air;

    private SensorData.LightData light;

    private SensorData.HumidityTemperatureData humTemp;


}
