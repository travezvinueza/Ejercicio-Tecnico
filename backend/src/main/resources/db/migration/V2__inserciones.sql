-- Insertar clients
INSERT INTO clients (name, lastname) VALUES ('Juan', 'Pérez');
INSERT INTO clients (name, lastname) VALUES ('María', 'García');
INSERT INTO clients (name, lastname) VALUES ('Carlos', 'López');

-- Insertar artículos
INSERT INTO articles (code, name, unit_price, stock) VALUES ('ARTICULO-000001', 'Audífonos', 12.99, 40);
INSERT INTO articles (code, name, unit_price, stock) VALUES ('ARTICULO-000002', 'Teclado mecánico', 26.55, 40);
INSERT INTO articles (code, name, unit_price, stock) VALUES ('ARTICULO-000003', 'Ratón óptico', 22.55, 40);
INSERT INTO articles (code, name, unit_price, stock) VALUES ('ARTICULO-000004', 'Pendrive 32GB', 15.75, 40);
INSERT INTO articles (code, name, unit_price, stock) VALUES ('ARTICULO-000005', 'Laptop HP', 900.98, 40);
INSERT INTO articles (code, name, unit_price, stock) VALUES ('ARTICULO-000006', 'Cargador portátil', 25.88, 40);
INSERT INTO articles (code, name, unit_price, stock) VALUES ('ARTICULO-000007', 'Mochila para laptop', 45.66, 40);
INSERT INTO articles (code, name, unit_price, stock) VALUES ('ARTICULO-000008', 'Webcam HD', 35.55, 40);
INSERT INTO articles (code, name, unit_price, stock) VALUES ('ARTICULO-000009', 'Auriculares inalámbricos', 50.78, 40);
INSERT INTO articles (code, name, unit_price, stock) VALUES ('ARTICULO-000010', 'Camara', 700.25, 40);

-- Insertar órdenes
INSERT INTO orders (code, date, client_id) VALUES ('ORDEN-367641', '2024-09-23', 1);
INSERT INTO orders (code, date, client_id) VALUES ('ORDEN-456783', '2024-09-24', 1);
INSERT INTO orders (code, date, client_id) VALUES ('ORDEN-174563', '2024-09-24', 2);
INSERT INTO orders (code, date, client_id) VALUES ('ORDEN-879876', '2024-09-25', 3);

-- Insertar datos de ejemplo en la tabla de artículos de órdenes
INSERT INTO order_articles (order_id, article_id, quantity, unit_price, total_price) VALUES
(1, 1, 2, 12.99, 25.98),  -- Audífonos, cantidad 2 (12.99 * 2)
(1, 3, 1, 22.55, 22.55),  -- Ratón óptico, cantidad 1 (22.55 * 1)
(2, 2, 1, 26.55, 26.55),  -- Teclado mecánico, cantidad 1 (26.55 * 1)
(2, 4, 3, 15.75, 47.25),  -- Pendrive 32GB, cantidad 3 (15.75 * 3)
(3, 5, 1, 900.98, 900.98), -- Laptop HP, cantidad 1 (900.98 * 1)
(3, 6, 2, 25.88, 51.76),   -- Cargador portátil, cantidad 2 (25.88 * 2)
(4, 7, 1, 45.66, 45.66),   -- Mochila para laptop, cantidad 1 (45.66 * 1)
(4, 8, 1, 35.55, 35.55);   -- Webcam HD, cantidad 1 (35.55 * 1)

