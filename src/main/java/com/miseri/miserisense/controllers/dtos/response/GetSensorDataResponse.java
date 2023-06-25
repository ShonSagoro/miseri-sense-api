package com.miseri.miserisense.controllers.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetSensorDataResponse {

    private Long id;

    private Float humedityPercentage;

    private Float light;

    private Float airQuality;

    private Float temperature;

    private Float gas;

    private String date;

    private Long deviceId;
}
