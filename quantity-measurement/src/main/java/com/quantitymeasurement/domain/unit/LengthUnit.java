package com.quantitymeasurement.domain.unit;

/**
 * UC3: Generic Quantity using DRY Principle — length units as a standalone enum.
 * UC4: Extended with YARD and CENTIMETER.
 * UC5: Enum carries conversion factors; all conversion logic lives here (UC8 SRP).
 *
 * Base unit: INCH (factor = 1.0)
 */
public enum LengthUnit implements IMeasurable {

    INCH(1.0),
    FEET(12.0),
    YARD(36.0),
    CENTIMETER(0.393701);   // 1 cm = 0.393701 inches

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double toBase(double value) {
        return value * conversionFactor;
    }

    @Override
    public double fromBase(double baseValue) {
        return baseValue / conversionFactor;
    }

    @Override
    public String getCategory() {
        return "LENGTH";
    }
}
