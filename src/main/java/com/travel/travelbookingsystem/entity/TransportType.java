package com.travel.travelbookingsystem.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransportType {
    TRAIN, FLIGHT, BUS;

    @JsonCreator
    public static TransportType fromString(String value) {
        if (value == null) {
            return null;
        }
        return TransportType.valueOf(value.toUpperCase()); // Convert to uppercase before matching
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
