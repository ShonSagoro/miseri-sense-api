package com.miseri.miserisense.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "device")
public class Device {
    @Transient
    public static final String SEQUENCE_NAME = "devices_sequence";

    @Id
    private Long id;

    private Long userId;
}
