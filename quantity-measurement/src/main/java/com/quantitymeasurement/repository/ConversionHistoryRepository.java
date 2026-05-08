package com.quantitymeasurement.repository;

import com.quantitymeasurement.model.ConversionHistory;

import java.util.List;

/**
 * UC15 / UC16: Repository interface — Dependency Inversion decouples
 * the service from the JDBC implementation detail.
 */
public interface ConversionHistoryRepository {

    void save(ConversionHistory history);

    List<ConversionHistory> findAll();

    List<ConversionHistory> findByCategory(String category);
}
