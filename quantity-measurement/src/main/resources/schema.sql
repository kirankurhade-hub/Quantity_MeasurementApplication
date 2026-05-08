-- UC16: Conversion history persistence (MySQL-compatible DDL)
CREATE TABLE IF NOT EXISTS conversion_history (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    category    VARCHAR(50)     NOT NULL,
    from_value  DOUBLE          NOT NULL,
    from_unit   VARCHAR(50)     NOT NULL,
    to_value    DOUBLE          NOT NULL,
    to_unit     VARCHAR(50)     NOT NULL,
    created_at  DATETIME        DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
