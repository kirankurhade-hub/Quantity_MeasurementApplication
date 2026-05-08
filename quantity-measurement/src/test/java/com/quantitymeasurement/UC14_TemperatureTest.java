package com.quantitymeasurement;

import com.quantitymeasurement.domain.Quantity;
import com.quantitymeasurement.domain.unit.TemperatureUnit;
import com.quantitymeasurement.exception.QuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC14: Temperature Measurement with Selective Arithmetic Support.
 * Tests: Non-linear conversions, ISP defaults, operation validity constraints,
 * absolute vs. relative temperatures, category-specific offset handling.
 */
@DisplayName("UC14 - Temperature Measurement")
class UC14_TemperatureTest {

    private static final double DELTA = 0.001;

    // ── Equality via conversion ───────────────────────────────────────────────

    @Test
    @DisplayName("100°C == 212°F")
    void hundredCelsius_equals_twoHundredTwelveFahrenheit() {
        Quantity<TemperatureUnit> celsius    = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(celsius, fahrenheit);
    }

    @Test
    @DisplayName("0°C == 32°F")
    void zeroCelsius_equals_thirtyTwoFahrenheit() {
        Quantity<TemperatureUnit> celsius    = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(celsius, fahrenheit);
    }

    @Test
    @DisplayName("0°C == 273.15 K")
    void zeroCelsius_equals_kelvin() {
        Quantity<TemperatureUnit> celsius = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> kelvin  = new Quantity<>(273.15, TemperatureUnit.KELVIN);
        assertEquals(celsius, kelvin);
    }

    @Test
    @DisplayName("-40°C == -40°F (crossover point)")
    void minusFortyC_equals_minusFortyF() {
        Quantity<TemperatureUnit> celsius    = new Quantity<>(-40.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(celsius, fahrenheit);
    }

    // ── Unit-to-unit conversion ───────────────────────────────────────────────

    @Test
    @DisplayName("100°C converts to 212°F")
    void convert_celsiusToFahrenheit() {
        Quantity<TemperatureUnit> c      = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> result = c.convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(212.0, result.getValue(), DELTA);
    }

    @Test
    @DisplayName("300 K converts to 26.85°C")
    void convert_kelvinToCelsius() {
        Quantity<TemperatureUnit> k      = new Quantity<>(300.0, TemperatureUnit.KELVIN);
        Quantity<TemperatureUnit> result = k.convertTo(TemperatureUnit.CELSIUS);
        assertEquals(26.85, result.getValue(), DELTA);
    }

    // ── ISP: arithmetic is disabled for temperature ───────────────────────────

    @Test
    @DisplayName("Addition of temperatures throws QuantityException (ISP)")
    void addition_throwsException() {
        Quantity<TemperatureUnit> a = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> b = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(QuantityException.class, () -> a.add(b));
    }

    @Test
    @DisplayName("Subtraction of temperatures throws QuantityException")
    void subtraction_throwsException() {
        Quantity<TemperatureUnit> a = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> b = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(QuantityException.class, () -> a.subtract(b));
    }

    @Test
    @DisplayName("Division of temperatures throws QuantityException")
    void division_throwsException() {
        Quantity<TemperatureUnit> a = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> b = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(QuantityException.class, () -> a.divide(b));
    }
}
