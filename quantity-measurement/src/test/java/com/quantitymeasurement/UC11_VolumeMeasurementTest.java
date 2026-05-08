package com.quantitymeasurement;

import com.quantitymeasurement.domain.Quantity;
import com.quantitymeasurement.domain.unit.VolumeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC11: Volume Measurement — Litre, Millilitre, Gallon.
 * Validates: Scalability, Pattern Replication, Base Unit Selection,
 * Conversion Factor Precision, IMeasurable Interface Power.
 */
@DisplayName("UC11 - Volume Measurement (Litre, Millilitre, Gallon)")
class UC11_VolumeMeasurementTest {

    private static final double DELTA = 0.01;

    @Test
    @DisplayName("1 litre == 1000 millilitres")
    void oneLitre_shouldEqual_thousandMillilitres() {
        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml    = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(litre, ml);
    }

    @Test
    @DisplayName("1 gallon ≈ 3785.41 ml")
    void oneGallon_inMillilitres() {
        Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> result = gallon.convertTo(VolumeUnit.MILLILITRE);
        assertEquals(3785.41, result.getValue(), DELTA);
    }

    @Test
    @DisplayName("1 gallon ≈ 3.785 litres")
    void oneGallon_inLitres() {
        Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> result = gallon.convertTo(VolumeUnit.LITRE);
        assertEquals(3.785, result.getValue(), DELTA);
    }

    @Test
    @DisplayName("1 litre + 1 litre = 2 litres")
    void addTwoLitres() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertEquals(2.0, a.add(b).getValue(), DELTA);
    }

    @Test
    @DisplayName("1 litre + 1000 ml = 2 litres")
    void addLitreAndMillilitre() {
        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml    = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(2.0, litre.add(ml).getValue(), DELTA);
    }

    @Test
    @DisplayName("1 gallon + 1 litre (target = LITRE)")
    void addGallonAndLitre() {
        Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> litre  = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> result = gallon.add(litre, VolumeUnit.LITRE);
        assertEquals(4.785, result.getValue(), DELTA);
    }
}
