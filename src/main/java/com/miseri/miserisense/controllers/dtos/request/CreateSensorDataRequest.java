package com.miseri.miserisense.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateSensorDataRequest {

    private Float humedityPercentage;

    private Float light;

    private Float airQuality;

    private Float temperature;

    private Float gas;

    private String date;

    private Long deviceId;
}
