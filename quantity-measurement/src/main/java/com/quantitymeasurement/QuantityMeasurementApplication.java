package com.quantitymeasurement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * UC17 entry point. Domain classes (UC1-UC14) are pure Java; Spring wires
 * the service, repository, and REST layers (UC15-UC17).
 */
@SpringBootApplication
public class QuantityMeasurementApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuantityMeasurementApplication.class, args);
    }
}
