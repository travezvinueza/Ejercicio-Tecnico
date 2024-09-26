-- Crear tabla "clientes"
CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL
);

-- Crear tabla "ordenes"
CREATE TABLE ordenes (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(255) NOT NULL,
    fecha DATE NOT NULL,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);

-- Crear tabla "articulos"
CREATE TABLE articulos (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(255) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    orden_id BIGINT NOT NULL,
    CONSTRAINT fk_orden FOREIGN KEY (orden_id) REFERENCES ordenes(id) ON DELETE CASCADE
);
