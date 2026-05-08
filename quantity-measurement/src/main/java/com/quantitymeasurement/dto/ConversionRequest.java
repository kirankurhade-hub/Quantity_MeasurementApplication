package com.quantitymeasurement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * UC15: DTO carrying the caller's intent for a unit conversion.
 * Keeps HTTP/domain boundary clean; domain never sees raw HTTP bodies.
 */
public class ConversionRequest {

    @NotNull(message = "value is required")
    private Double value;

    @NotBlank(message = "category is required")
    private String category;   // LENGTH | WEIGHT | VOLUME | TEMPERATURE

    @NotBlank(message = "fromUnit is required")
    private String fromUnit;

    @NotBlank(message = "toUnit is required")
    private String toUnit;

    public ConversionRequest() {}

    public ConversionRequest(Double value, String category, String fromUnit, String toUnit) {
        this.value = value;
        this.category = category;
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
    }

    public Double getValue()      { return value; }
    public String getCategory()   { return category; }
    public String getFromUnit()   { return fromUnit; }
    public String getToUnit()     { return toUnit; }

    public void setValue(Double value)        { this.value = value; }
    public void setCategory(String category)  { this.category = category; }
    public void setFromUnit(String fromUnit)  { this.fromUnit = fromUnit; }
    public void setToUnit(String toUnit)      { this.toUnit = toUnit; }
}
