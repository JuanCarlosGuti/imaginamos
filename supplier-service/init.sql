CREATE TABLE IF NOT EXISTS suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    telefono VARCHAR(20),
    empresa VARCHAR(100)
    );

INSERT INTO suppliers (nombre, email, telefono, empresa)
VALUES
    ('Proveedor A', 'proveedora@example.com', '3001234567', 'Distribuciones S.A.'),
    ('Proveedor B', 'proveedorb@example.com', '3012345678', 'Suministros Ltda.');
