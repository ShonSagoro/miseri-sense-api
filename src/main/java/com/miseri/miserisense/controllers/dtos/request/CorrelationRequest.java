package com.miseri.miserisense.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CorrelationRequest {

    private List<Double> xdata;

    private List<Double> ydata;
}
