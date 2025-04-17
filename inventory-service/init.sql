
CREATE TABLE IF NOT EXISTS inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL
    );

INSERT INTO inventory (nombre, cantidad, precio) VALUES
  ('Silla de Playa', 10, 150000.00),
  ('Sombrilla Grande', 5, 95000.00),
 ('Teclado', 5, 120000.00);


