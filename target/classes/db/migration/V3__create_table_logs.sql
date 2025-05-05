CREATE TABLE logs (
    id BIGSERIAL PRIMARY KEY,
    log_date TIMESTAMP NOT NULL,
    level VARCHAR(10),
    logger VARCHAR(255),
    message TEXT,
    exception TEXT
);