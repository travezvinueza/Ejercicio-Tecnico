-- Insertar clientes
INSERT INTO clientes (nombre, apellido) VALUES ('Juan', 'Pérez');
INSERT INTO clientes (nombre, apellido) VALUES ('María', 'García');
INSERT INTO clientes (nombre, apellido) VALUES ('Carlos', 'López');

-- Insertar órdenes
INSERT INTO ordenes (codigo, fecha, cliente_id) VALUES ('ORDEN-000001', '2024-09-23', 1);
INSERT INTO ordenes (codigo, fecha, cliente_id) VALUES ('ORDEN-000002', '2024-09-24', 1);
INSERT INTO ordenes (codigo, fecha, cliente_id) VALUES ('ORDEN-000003', '2024-09-24', 2);
INSERT INTO ordenes (codigo, fecha, cliente_id) VALUES ('ORDEN-000004', '2024-09-25', 3);

-- Insertar artículos (nombres únicos)
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000001', 'Audífonos', 12.00, 1);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000002', 'Teclado mecánico', 26.50, 1);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000003', 'Ratón óptico', 22.50, 2);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000004', 'Pendrive 32GB', 15.75, 2);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000005', 'Laptop HP', 900.00, 2);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000006', 'Cargador portátil', 25.00, 3);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000007', 'Mochila para laptop', 45.00, 3);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000008', 'Webcam HD', 35.00, 3);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000009', 'Auriculares inalámbricos', 50.00, 4);
INSERT INTO articulos (codigo, nombre, precio_unitario, orden_id) VALUES ('ARTICULO-000010', 'Teclado gaming', 30.00, 4);
