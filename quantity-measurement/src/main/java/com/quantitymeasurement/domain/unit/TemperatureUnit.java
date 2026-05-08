package com.quantitymeasurement.domain.unit;

/**
 * UC14: Temperature uses non-linear (offset) conversions, so it overrides
 * toBase/fromBase with per-constant bodies instead of a stored factor.
 *
 * ISP: supportsAddition/Subtraction/Division all return false because
 * temperature arithmetic on absolute values is physically meaningless.
 *
 * Base unit: CELSIUS
 */
public enum TemperatureUnit implements IMeasurable {

    CELSIUS {
        @Override public double toBase(double value)    { return value; }
        @Override public double fromBase(double base)   { return base; }
    },
    FAHRENHEIT {
        @Override public double toBase(double value)    { return (value - 32.0) * 5.0 / 9.0; }
        @Override public double fromBase(double base)   { return base * 9.0 / 5.0 + 32.0; }
    },
    KELVIN {
        @Override public double toBase(double value)    { return value - 273.15; }
        @Override public double fromBase(double base)   { return base + 273.15; }
    };

    @Override
    public String getCategory() {
        return "TEMPERATURE";
    }

    // UC14: Temperature does not support arithmetic operations
    @Override public boolean supportsAddition()       { return false; }
    @Override public boolean supportsSubtraction()    { return false; }
    @Override public boolean supportsDivision()       { return false; }
    @Override public boolean supportsMultiplication() { return false; }
}
