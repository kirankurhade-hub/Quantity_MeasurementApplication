package com.quantitymeasurement.service;

import com.quantitymeasurement.dto.ArithmeticRequest;
import com.quantitymeasurement.dto.ConversionRequest;
import com.quantitymeasurement.dto.ConversionResponse;
import com.quantitymeasurement.model.ConversionHistory;

import java.util.List;

/**
 * UC15: Service interface — Single Responsibility, Open-Closed.
 * UC17: Injected into the REST controller via constructor injection (DI).
 */
public interface QuantityService {

    ConversionResponse convert(ConversionRequest request);

    ConversionResponse add(ArithmeticRequest request);

    ConversionResponse subtract(ArithmeticRequest request);

    ConversionResponse divide(ArithmeticRequest request);

    List<ConversionHistory> getHistory();

    List<ConversionHistory> getHistoryByCategory(String category);
}
