package com.miseri.miserisense.controllers.dtos.response;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FrequencyResponse {

    private String sensor;

    private List<DataFrequencyTableResponse> frequency;

    private Double range;

    private Integer klasses;

    private Double amplitude;

    private Double unit;

    private Double media;

    private  Double mediaArit;

    private Double moda;

    private Double meanDeviation;

    private Double variance;

    private Double standardDeviation;
}
