package com.miseri.miserisense.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "sensor_data")
public class SensorData {
    @Transient
    public static final String SEQUENCE_NAME = "sensor_data_sequence";

    @Id
    private Long id;

    private AirData air;

    private LightData light;

    private HumidityTemperatureData humTemp;

    private String date;

    private String session;

    private Long deviceId;

    @Getter @Setter
    public static class AirData {
        private double gasPmm;
        private double coPmm;
    }

    @Getter @Setter
    public static class LightData {
        private double raw;
        private int percent;
    }

    @Getter @Setter
    public static class HumidityTemperatureData {
        private double temperature;
        private double humidity;
    }
}
