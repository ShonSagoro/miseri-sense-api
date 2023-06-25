package com.miseri.miserisense.models;


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

    private Float humedityPercentage;

    private Float light;

    private Float airQuality;

    private Float temperature;

    private Float gas;

    private String date;

    private Long deviceId;
}
