package com.quantitymeasurement.controller;

import com.quantitymeasurement.dto.ApiResponse;
import com.quantitymeasurement.dto.ArithmeticRequest;
import com.quantitymeasurement.dto.ConversionRequest;
import com.quantitymeasurement.dto.ConversionResponse;
import com.quantitymeasurement.service.QuantityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UC17: REST controller — exposes quantity operations over HTTP.
 * Thin layer: validates input, delegates to QuantityService, wraps in ApiResponse.
 */
@RestController
@RequestMapping("/api/quantity")
@CrossOrigin(origins = "*")   // UC19/UC20: allow browser clients
public class QuantityController {

    private final QuantityService service;

    public QuantityController(QuantityService service) {
        this.service = service;
    }

    // POST /api/quantity/convert
    @PostMapping("/convert")
    public ResponseEntity<ApiResponse<ConversionResponse>> convert(
            @Valid @RequestBody ConversionRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(service.convert(request)));
    }

    // POST /api/quantity/add
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<ConversionResponse>> add(
            @Valid @RequestBody ArithmeticRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(service.add(request)));
    }

    // POST /api/quantity/subtract
    @PostMapping("/subtract")
    public ResponseEntity<ApiResponse<ConversionResponse>> subtract(
            @Valid @RequestBody ArithmeticRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(service.subtract(request)));
    }

    // POST /api/quantity/divide
    @PostMapping("/divide")
    public ResponseEntity<ApiResponse<ConversionResponse>> divide(
            @Valid @RequestBody ArithmeticRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(service.divide(request)));
    }

    // GET /api/quantity/history
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<?>> history(
            @RequestParam(required = false) String category) {
        if (category != null && !category.isBlank()) {
            return ResponseEntity.ok(ApiResponse.ok(service.getHistoryByCategory(category)));
        }
        return ResponseEntity.ok(ApiResponse.ok(service.getHistory()));
    }

    // GET /api/quantity/units  — lists supported units per category
    @GetMapping("/units")
    public ResponseEntity<ApiResponse<?>> units() {
        var units = java.util.Map.of(
            "LENGTH",      java.util.Arrays.stream(com.quantitymeasurement.domain.unit.LengthUnit.values()).map(Enum::name).toList(),
            "WEIGHT",      java.util.Arrays.stream(com.quantitymeasurement.domain.unit.WeightUnit.values()).map(Enum::name).toList(),
            "VOLUME",      java.util.Arrays.stream(com.quantitymeasurement.domain.unit.VolumeUnit.values()).map(Enum::name).toList(),
            "TEMPERATURE", java.util.Arrays.stream(com.quantitymeasurement.domain.unit.TemperatureUnit.values()).map(Enum::name).toList()
        );
        return ResponseEntity.ok(ApiResponse.ok(units));
    }
}
