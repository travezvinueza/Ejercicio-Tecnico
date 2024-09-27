-- Insertar clients
INSERT INTO clients (name, lastname) VALUES ('Juan', 'Pérez');
INSERT INTO clients (name, lastname) VALUES ('María', 'García');
INSERT INTO clients (name, lastname) VALUES ('Carlos', 'López');

-- Insertar órdenes
INSERT INTO orders (code, date, client_id) VALUES ('ORDEN-000001', '2024-09-23', 1);
INSERT INTO orders (code, date, client_id) VALUES ('ORDEN-000002', '2024-09-24', 1);
INSERT INTO orders (code, date, client_id) VALUES ('ORDEN-000003', '2024-09-24', 2);
INSERT INTO orders (code, date, client_id) VALUES ('ORDEN-000004', '2024-09-25', 3);

-- Insertar artículos (nombres únicos)
INSERT INTO articles (code, name, unit_price, order_id) VALUES ('ARTICULO-000001', 'Audífonos', 12.99, 1);
INSERT INTO articles (code, name, unit_price, order_id) VALUES ('ARTICULO-000002', 'Teclado mecánico', 26.55, 1);
INSERT INTO articles (code, name, unit_price, order_id) VALUES ('ARTICULO-000003', 'Ratón óptico', 22.55, 2);
INSERT INTO articles (code, name, unit_price, order_id) VALUES ('ARTICULO-000004', 'Pendrive 32GB', 15.75, 2);
INSERT INTO articles (code, name, unit_price, order_id) VALUES ('ARTICULO-000005', 'Laptop HP', 900.98, 2);
INSERT INTO articles (code, name, unit_price, order_id) VALUES ('ARTICULO-000006', 'Cargador portátil', 25.88, 3);
INSERT INTO articles (code, name, unit_price, order_id) VALUES ('ARTICULO-000007', 'Mochila para laptop', 45.66, 3);
INSERT INTO articles (code, name, unit_price, order_id) VALUES ('ARTICULO-000008', 'Webcam HD', 35.55, 3);
INSERT INTO articles (code, name, unit_price, order_id) VALUES ('ARTICULO-000009', 'Auriculares inalámbricos', 50.78, 4);
INSERT INTO articles (code, name, unit_price, order_id) VALUES ('ARTICULO-000010', 'Camara', 700.25, 4);
