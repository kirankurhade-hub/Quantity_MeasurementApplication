package com.quantitymeasurement.domain.unit;

/**
 * UC10: Interface contract for all unit enums.
 * UC14: Default methods enable backwards-compatible selective arithmetic support.
 */
public interface IMeasurable {

    /** Converts a quantity expressed in this unit to the category's base unit. */
    double toBase(double value);

    /** Converts a base-unit value back to this unit. */
    double fromBase(double baseValue);

    /** Category discriminator (e.g. "LENGTH", "WEIGHT", "VOLUME", "TEMPERATURE"). */
    String getCategory();

    // UC14: Interface Segregation — operations are opt-in via default methods
    default boolean supportsAddition()    { return true; }
    default boolean supportsSubtraction() { return true; }
    default boolean supportsDivision()    { return true; }
    default boolean supportsMultiplication() { return true; }
}
