-- Insertar clientes
INSERT INTO clientes (nombre, apellido) VALUES ('Juan', 'Pérez');
INSERT INTO clientes (nombre, apellido) VALUES ('María', 'García');
INSERT INTO clientes (nombre, apellido) VALUES ('Carlos', 'López');

-- Insertar órdenes
INSERT INTO ordenes (codigo, fecha, cliente_id) VALUES ('ORDEN-000001', '2024-09-23', 1);
INSERT INTO ordenes (codigo, fecha, cliente_id) VALUES ('ORDEN-000002', '2024-09-24', 2);
INSERT INTO ordenes (codigo, fecha, cliente_id) VALUES ('ORDEN-000003', '2024-09-25', 3);

-- Insertar artículos
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000001', 'Audifonos', 12.00, 1);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000002', 'Teclado', 26.50, 1);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000003', 'Laptop PH', 900.00, 2);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000004', 'Flash', 15.75, 3);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000005', 'Mouse', 22.50, 3);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000006', 'Parlantes', 30.00, 3);
