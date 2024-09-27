-- Crear tabla "clients"
CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL
);

-- Crear tabla "ordenes"
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    client_id BIGINT NOT NULL,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);

-- Crear tabla "articles"
CREATE TABLE articles (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    order_id BIGINT,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);
