
CREATE TABLE IF NOT EXISTS clients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(50),
    direccion VARCHAR(255)
    );

INSERT INTO clients (nombre, email, telefono, direccion) VALUES
 ('Juan Pérez', 'juan@example.com', '3001234567', 'Calle 123 #45-67'),
 ('Laura Gómez', 'laura@example.com', '3019876543', 'Carrera 10 #20-30');