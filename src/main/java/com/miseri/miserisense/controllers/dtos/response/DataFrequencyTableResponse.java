package com.miseri.miserisense.controllers.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DataFrequencyTableResponse {

    private Double limInf;

    private Double limSup;

    private Integer frequency;

    private Integer cumulativeFrequency;

    private Double classMark;

    private Double limInfEx;

    private Double limSupEx;

    @Override
    public String toString() {
        return "DataFrequencyTableResponse{" +
                ", limInf=" + limInf +
                ", limSup=" + limSup +
                ", frequency=" + frequency +
                ", cumulativeFrequency=" + cumulativeFrequency +
                ", classMark=" + classMark +
                ", limInfEx=" + limInfEx +
                ", limSupEx=" + limSupEx +
                '}';
    }
}
