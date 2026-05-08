package com.quantitymeasurement.domain.unit;

/**
 * UC9: Weight category — validates the generic design scales across categories.
 *
 * Base unit: GRAM (factor = 1.0)
 */
public enum WeightUnit implements IMeasurable {

    GRAM(1.0),
    KILOGRAM(1000.0),
    TONNE(1_000_000.0),
    POUND(453.592);

    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
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
        return "WEIGHT";
    }
}
