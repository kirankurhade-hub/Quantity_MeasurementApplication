package com.quantitymeasurement.model;

import java.time.LocalDateTime;

/**
 * UC16: JDBC entity representing one recorded unit conversion.
 */
public class ConversionHistory {

    private Long id;
    private String category;
    private double fromValue;
    private String fromUnit;
    private double toValue;
    private String toUnit;
    private LocalDateTime createdAt;

    public ConversionHistory() {}

    public ConversionHistory(String category, double fromValue, String fromUnit,
                              double toValue, String toUnit) {
        this.category  = category;
        this.fromValue = fromValue;
        this.fromUnit  = fromUnit;
        this.toValue   = toValue;
        this.toUnit    = toUnit;
    }

    public Long getId()                  { return id; }
    public String getCategory()          { return category; }
    public double getFromValue()         { return fromValue; }
    public String getFromUnit()          { return fromUnit; }
    public double getToValue()           { return toValue; }
    public String getToUnit()            { return toUnit; }
    public LocalDateTime getCreatedAt()  { return createdAt; }

    public void setId(Long id)                        { this.id = id; }
    public void setCategory(String category)          { this.category = category; }
    public void setFromValue(double fromValue)        { this.fromValue = fromValue; }
    public void setFromUnit(String fromUnit)          { this.fromUnit = fromUnit; }
    public void setToValue(double toValue)            { this.toValue = toValue; }
    public void setToUnit(String toUnit)              { this.toUnit = toUnit; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
