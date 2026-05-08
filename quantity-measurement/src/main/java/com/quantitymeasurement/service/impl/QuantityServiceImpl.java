package com.quantitymeasurement.service.impl;

import com.quantitymeasurement.domain.Operation;
import com.quantitymeasurement.domain.Quantity;
import com.quantitymeasurement.domain.unit.*;
import com.quantitymeasurement.dto.ArithmeticRequest;
import com.quantitymeasurement.dto.ConversionRequest;
import com.quantitymeasurement.dto.ConversionResponse;
import com.quantitymeasurement.exception.QuantityException;
import com.quantitymeasurement.model.ConversionHistory;
import com.quantitymeasurement.repository.ConversionHistoryRepository;
import com.quantitymeasurement.service.QuantityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UC15: Service implementation — coordinates domain logic, persistence, and logging.
 * UC17: Annotated as @Service for Spring DI.
 */
@Service
public class QuantityServiceImpl implements QuantityService {

    private static final Logger log = LoggerFactory.getLogger(QuantityServiceImpl.class);

    private final ConversionHistoryRepository historyRepo;

    public QuantityServiceImpl(ConversionHistoryRepository historyRepo) {
        this.historyRepo = historyRepo;
    }

    // ── UC5: Conversion ───────────────────────────────────────────────────────

    @Override
    public ConversionResponse convert(ConversionRequest req) {
        IMeasurable from = resolveUnit(req.getCategory(), req.getFromUnit());
        IMeasurable to   = resolveUnit(req.getCategory(), req.getToUnit());

        @SuppressWarnings("unchecked")
        Quantity<IMeasurable> qty = new Quantity<>(req.getValue(), from);
        @SuppressWarnings("unchecked")
        Quantity<IMeasurable> result = qty.convertTo(to);

        ConversionResponse response = new ConversionResponse(
            req.getValue(), req.getFromUnit(),
            result.getValue(), req.getToUnit(),
            req.getCategory()
        );

        persist(req.getCategory(), req.getValue(), req.getFromUnit(),
                result.getValue(), req.getToUnit());

        log.debug("convert: {} {} → {} {}", req.getValue(), req.getFromUnit(),
                  result.getValue(), req.getToUnit());
        return response;
    }

    // ── UC6 / UC7: Addition ───────────────────────────────────────────────────

    @Override
    public ConversionResponse add(ArithmeticRequest req) {
        return performArithmetic(req, Operation.ADD);
    }

    // ── UC12: Subtraction ─────────────────────────────────────────────────────

    @Override
    public ConversionResponse subtract(ArithmeticRequest req) {
        return performArithmetic(req, Operation.SUBTRACT);
    }

    // ── UC12: Division ────────────────────────────────────────────────────────

    @Override
    public ConversionResponse divide(ArithmeticRequest req) {
        return performArithmetic(req, Operation.DIVIDE);
    }

    // ── UC16: History ─────────────────────────────────────────────────────────

    @Override
    public List<ConversionHistory> getHistory() {
        return historyRepo.findAll();
    }

    @Override
    public List<ConversionHistory> getHistoryByCategory(String category) {
        return historyRepo.findByCategory(category.toUpperCase());
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    /**
     * UC13: All arithmetic operations route here — single source of truth.
     * UC7: targetUnit from request overrides default (unit1) when provided.
     */
    @SuppressWarnings("unchecked")
    private ConversionResponse performArithmetic(ArithmeticRequest req, Operation op) {
        IMeasurable u1     = resolveUnit(req.getCategory(), req.getUnit1());
        IMeasurable u2     = resolveUnit(req.getCategory(), req.getUnit2());
        IMeasurable target = req.getTargetUnit() != null
            ? resolveUnit(req.getCategory(), req.getTargetUnit()) : u1;

        Quantity<IMeasurable> q1 = new Quantity<>(req.getValue1(), u1);
        Quantity<IMeasurable> q2 = new Quantity<>(req.getValue2(), u2);

        Quantity<IMeasurable> result = switch (op) {
            case ADD      -> q1.add(q2, target);
            case SUBTRACT -> q1.subtract(q2, target);
            case DIVIDE   -> q1.divide(q2, target);
            default       -> throw new QuantityException("Unsupported operation: " + op);
        };

        String targetName = target.toString();
        // For enum-based units, use name()
        if (target instanceof LengthUnit lu)      targetName = lu.name();
        else if (target instanceof WeightUnit wu) targetName = wu.name();
        else if (target instanceof VolumeUnit vu) targetName = vu.name();

        persist(req.getCategory(), req.getValue1(), req.getUnit1(),
                result.getValue(), targetName);

        return new ConversionResponse(req.getValue1(), req.getUnit1(),
                                      result.getValue(), targetName, req.getCategory());
    }

    /**
     * Resolves a category + unit name pair to the matching IMeasurable enum constant.
     * Throws QuantityException for unknown categories or unit names.
     */
    private IMeasurable resolveUnit(String category, String unitName) {
        try {
            return switch (category.toUpperCase()) {
                case "LENGTH"      -> LengthUnit.valueOf(unitName.toUpperCase());
                case "WEIGHT"      -> WeightUnit.valueOf(unitName.toUpperCase());
                case "VOLUME"      -> VolumeUnit.valueOf(unitName.toUpperCase());
                case "TEMPERATURE" -> TemperatureUnit.valueOf(unitName.toUpperCase());
                default -> throw new QuantityException("Unknown category: " + category);
            };
        } catch (IllegalArgumentException e) {
            throw new QuantityException(
                "Unknown unit '" + unitName + "' for category '" + category + "'");
        }
    }

    private void persist(String category, double fromVal, String fromUnit,
                          double toVal, String toUnit) {
        historyRepo.save(new ConversionHistory(category, fromVal, fromUnit, toVal, toUnit));
    }
}
