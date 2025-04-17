CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'USER'
    );

-- Usuario de prueba con contraseña '123456' cifrada con BCrypt
INSERT INTO users (username, password, role)
VALUES (
           'admin',
           '$2a$10$p14LfItPxq9hZyKYDDBHn.2TcoHiE34XpJNSkpADox9UVUpXxL4na', -- 123456
           'ADMIN'
       )
    ON CONFLICT (username) DO NOTHING;

