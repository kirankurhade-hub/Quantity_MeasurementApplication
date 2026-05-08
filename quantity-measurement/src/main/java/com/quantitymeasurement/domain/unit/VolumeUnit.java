package com.quantitymeasurement.domain.unit;

/**
 * UC11: Volume category — Litre, Millilitre, Gallon.
 *
 * Base unit: MILLILITRE (factor = 1.0)
 */
public enum VolumeUnit implements IMeasurable {

    MILLILITRE(1.0),
    LITRE(1000.0),
    GALLON(3785.41);

    private final double conversionFactor;

    VolumeUnit(double conversionFactor) {
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
        return "VOLUME";
    }
}
