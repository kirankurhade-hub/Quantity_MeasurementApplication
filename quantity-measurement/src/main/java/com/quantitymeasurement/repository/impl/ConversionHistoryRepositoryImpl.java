package com.quantitymeasurement.repository.impl;

import com.quantitymeasurement.model.ConversionHistory;
import com.quantitymeasurement.repository.ConversionHistoryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * UC16: JDBC implementation.
 * Uses parameterised queries (SQL injection prevention) and JdbcTemplate
 * for automatic resource management (connection pooling, statement closing).
 */
@Repository
public class ConversionHistoryRepositoryImpl implements ConversionHistoryRepository {

    private final JdbcTemplate jdbc;

    public ConversionHistoryRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void save(ConversionHistory h) {
        jdbc.update(
            "INSERT INTO conversion_history (category, from_value, from_unit, to_value, to_unit) VALUES (?, ?, ?, ?, ?)",
            h.getCategory(), h.getFromValue(), h.getFromUnit(), h.getToValue(), h.getToUnit()
        );
    }

    @Override
    public List<ConversionHistory> findAll() {
        return jdbc.query("SELECT * FROM conversion_history ORDER BY created_at DESC", new HistoryRowMapper());
    }

    @Override
    public List<ConversionHistory> findByCategory(String category) {
        return jdbc.query(
            "SELECT * FROM conversion_history WHERE category = ? ORDER BY created_at DESC",
            new HistoryRowMapper(), category
        );
    }

    private static class HistoryRowMapper implements RowMapper<ConversionHistory> {
        @Override
        public ConversionHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConversionHistory h = new ConversionHistory();
            h.setId(rs.getLong("id"));
            h.setCategory(rs.getString("category"));
            h.setFromValue(rs.getDouble("from_value"));
            h.setFromUnit(rs.getString("from_unit"));
            h.setToValue(rs.getDouble("to_value"));
            h.setToUnit(rs.getString("to_unit"));
            h.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return h;
        }
    }
}
