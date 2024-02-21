USE apuestas;
-- Inserts de ejemplo para la tabla Usuario
INSERT INTO Usuario (Correo, passw, saldo) VALUES
    ('usuario1@example.com', 'clave123', 500.00),
    ('usuario2@example.com', 'clave456', 1000.00),
    ('usuario3@example.com', 'clave789', 1500.00),
    ('usuario4@example.com', 'claveabc', 2000.00),
    ('usuario5@example.com', 'clavexyz', 2500.00);

-- Inserts de ejemplo para la tabla Evento
INSERT INTO Evento (Equipo, FechaIni, FechaFin, CodEvento, Cantidad, ResultadoE1, ResultadoE2) VALUES
    ('EquipoA', '2023-11-28 18:00:00', NULL, 1, 100, 0, 0),
    ('EquipoB', '2023-11-29 14:30:00', NULL, 2, 150, 0, 0),
    ('EquipoC', '2023-11-30 19:00:00', NULL, 3, 200, 0, 0),
    ('EquipoD', '2023-12-01 16:00:00', NULL, 4, 120, 0, 0),
    ('EquipoE', '2023-12-02 20:30:00', NULL, 5, 180, 0, 0);

	-- Inserts de ejemplo para la tabla Apuesta
INSERT INTO Apuesta (CodApuesta, Cuota, CorreoUser, CodEvento, dineroApostado, Maximo) VALUES
    (1, 2.50, 'usuario1@example.com', 1, 50.00, 10000),
    (2, 3.00, 'usuario2@example.com', 2, 75.00, 10000),
    (3, 1.80, 'usuario3@example.com', 3, 90.00, 10000),
    (4, 4.20, 'usuario4@example.com', 4, 60.00, 10000),
    (5, 2.00, 'usuario5@example.com', 5, 90.00, 10000);

-- Inserts de ejemplo para la tabla Transaccion
INSERT INTO Transaccion (CodTrans, Fecha, Dinero, CorreoUser) VALUES
    (1, '2023-11-28', 100.00, 'usuario1@example.com'),
    (2, '2023-11-29', 150.00, 'usuario2@example.com'),
    (3, '2023-11-30', 200.00, 'usuario3@example.com'),
    (4, '2023-12-01', 120.00, 'usuario4@example.com'),
    (5, '2023-12-02', 180.00, 'usuario5@example.com');

-- Inserts de ejemplo para la tabla Asesinatos
INSERT INTO Asesinatos (Cantidad, CodApuesta) VALUES
    (5, 1),
    (3, 2),
    (7, 3),
    (4, 4),
    (9, 5);

-- Inserts de ejemplo para la tabla Ganserie
INSERT INTO Ganserie (Equipo, CodApuesta) VALUES
    ('EquipoG', 1),
    ('EquipoH', 2),
    ('EquipoI', 3),
    ('EquipoJ', 4),
    ('EquipoK', 5);

-- Inserts de ejemplo para la tabla resultserie
INSERT INTO resultserie (ResultadoE1, ResultadoE2, CodApuesta) VALUES
    (1, 0, 1),
    (0, 1, 2),
    (1, 1, 3),
    (0, 0, 4),
    (1, 0, 5);