CREATE TABLE IF NOT EXISTS products (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    price       INT          NOT NULL,
    description TEXT,
    stock       INT          NOT NULL DEFAULT 0,
    category    VARCHAR(100)
);
