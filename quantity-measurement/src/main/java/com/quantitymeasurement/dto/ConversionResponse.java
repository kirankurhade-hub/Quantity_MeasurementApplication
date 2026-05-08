package com.quantitymeasurement.dto;

/**
 * UC15: Immutable response DTO — exposes only the data the caller needs.
 */
public class ConversionResponse {

    private final double originalValue;
    private final String originalUnit;
    private final double convertedValue;
    private final String convertedUnit;
    private final String category;

    public ConversionResponse(double originalValue, String originalUnit,
                               double convertedValue, String convertedUnit,
                               String category) {
        this.originalValue  = originalValue;
        this.originalUnit   = originalUnit;
        this.convertedValue = convertedValue;
        this.convertedUnit  = convertedUnit;
        this.category       = category;
    }

    public double getOriginalValue()  { return originalValue; }
    public String getOriginalUnit()   { return originalUnit; }
    public double getConvertedValue() { return convertedValue; }
    public String getConvertedUnit()  { return convertedUnit; }
    public String getCategory()       { return category; }
}
