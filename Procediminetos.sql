



--Procedimiento que a�ade una transaccion y modifica el saldo del usuario segun su correo y el dinero introducido
CREATE PROCEDURE modificarSaldo 
	@usuario varchar(30),
	@dinero numeric(10,2)
AS 
BEGIN 
	SET NOCOUNT ON;
	UPDATE Usuario
	SET saldo = saldo + @dinero
	WHERE Correo = @usuario

	--Selecciono el ultimo codigo disponible
	DECLARE @nuevoCod int
	SELECT @nuevoCod = ISNULL(MAX(CodTrans), 0) + 1 FROM Transaccion 
	--Inserto la transaccion
	Insert into Transaccion
	VALUES (@nuevoCod, CURRENT_TIMESTAMP, @dinero, @usuario)

	PRINT 'Saldo actualizado'

END

EXEC modificarSaldo 'usuario2@example.com', -500
GO
--Funcion que comprueba si la apuesta es accesible
CREATE FUNCTION dbo.ComprobarApuestaAccesible (@CodEvento INT)
RETURNS BIT
AS
BEGIN
    DECLARE @Accesible BIT;
	--En caso que este dentro del rango da 1, si no da 0
    SELECT @Accesible = CASE
        WHEN FechaIni >= DATEADD(DAY, 2, GETDATE()) AND FechaIni > GETDATE() AND FechaFin IS NULL THEN 1
        WHEN FechaIni >= DATEADD(DAY, 2, GETDATE()) AND FechaIni > GETDATE() AND FechaFin >= GETDATE() THEN 1
        ELSE 0
        END
    FROM Evento
    WHERE CodEvento = @CodEvento;

    RETURN @Accesible;
END;
GO
PRINT dbo.ComprobarApuestaAccesible(4)
GO
--Funcion para calcular la cuota+
CREATE OR ALTER FUNCTION Cuota(@porcentil decimal(5,3))
RETURNS decimal(5,3)
AS
BEGIN
    DECLARE @cuota decimal(5,3)
    SET @cuota = ((3.0-1.0)/100.0 * (100-@porcentil) + 1);

    RETURN @cuota;
END
GO
declare @cuota decimal(5,3)
Set @cuota = dbo.Cuota(dbo.porcentil(1,3))
print @cuota
GO
--Funcion para calcular el porcentil
CREATE OR ALTER FUNCTION porcentil(@Votos Int, @VotosT Int)
RETURNS decimal (5,2)
BEGIN
	DECLARE @Porcentil decimal(5,2);

	if @Votos = 0
	set @Votos = 1

	if @VotosT = 0
	set @VotosT = 2

	SET @Porcentil = @Votos * 100 / @VotosT;

	RETURN @Porcentil;
END;
GO
--Funcion para comprobar el resultado
CREATE OR ALTER PROCEDURE ComprobarResultado(@codApuesta int, @codEvento int)
AS BEGIN
	DECLARE @Ganserie varchar(20),
			@asesinatos int,
			@serieE1 varchar(20),
			@serieE2 varchar(20)

	SELECT @Ganserie = Equipo FROM Evento WHERE CodEvento = @codEvento
    SELECT @asesinatos = Cantidad FROM Evento WHERE CodEvento = @codEvento
    SELECT @serieE1 = ResultadoE1, @serieE2 = ResultadoE2 FROM Evento WHERE CodEvento = @codEvento

	if @Ganserie = (SELECT Equipo FROM Ganserie WHERE CodApuesta = @codApuesta)
		UPDATE Usuario SET saldo = saldo + (SELECT dineroApostado FROM Apuesta where CodApuesta = @codApuesta)
		WHERE Correo = (SELECT CorreoUser FROM Apuesta WHERE CodApuesta =1 ) 
	else if @asesinatos = (SELECT Cantidad FROM Asesinatos WHERE CodApuesta = @codApuesta)
		UPDATE Usuario SET saldo = saldo + (SELECT dineroApostado FROM Apuesta where CodApuesta = @codApuesta)
		WHERE Correo = (SELECT CorreoUser FROM Apuesta WHERE CodApuesta =1 )
	else if @serieE1 = (SELECT ResultadoE1 FROM resultserie WHERE CodApuesta = @codApuesta) and @serieE2 = (SELECT ResultadoE2 FROM resultserie WHERE CodApuesta = @codApuesta)
		UPDATE Usuario SET saldo = saldo + (SELECT dineroApostado FROM Apuesta where CodApuesta = @codApuesta)
		WHERE Correo = (SELECT CorreoUser FROM Apuesta WHERE CodApuesta =1 )

END
GO

--Trigger que al insertar una fechaFin se ejecuta y ejecuta la funcion comprobar resultado
CREATE OR ALTER TRIGGER TerminarEvento
ON Evento
AFTER INSERT, UPDATE
AS BEGIN
	IF EXISTS (SELECT 1 FROM inserted WHERE FechaFin IS NOT NULL)
	BEGIN
		DECLARE Cursor_apuestas CURSOR FOR
		SELECT CodApuesta FROM Apuesta WHERE CodEvento = (SELECT CodEvento FROM inserted)

		DECLARE @CodApuesta INT,
				@codEvento INT
		
		SET @codEvento = (SELECT CodEvento FROM inserted)

		OPEN Cursor_apuestas

		FETCH NEXT FROM Cursor_apuestas INTO @CodApuesta

		WHILE @@FETCH_STATUS = 0
		BEGIN
			EXECUTE dbo.ComprobarResultado @CodApuesta, @codEvento

			FETCH NEXT FROM Cursor_apuestas INTO @CodApuesta
		END

		CLOSE Cursor_apuestas
		DEALLOCATE Cursor_apuestas

	END 
END
GO
--Procedimiento que inserta un evento
CREATE OR ALTER PROCEDURE InsertarEvento
	@fechaIni datetime

AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @CodEvento int
	SELECT @CodEvento = ISNULL(MAX(CodEvento), 0) + 1 FROM Evento

	INSERT INTO Evento
	VALUES (NULL, @fechaIni, NULL, @CodEvento, NULL, NULL, NULL)

	PRINT 'Evento insertado'
END
GO

--Procedimiento que inserta una apuesta de tipo 1
CREATE OR ALTER PROCEDURE InsertarApuesta1
	@codEvento int,
	@dinero numeric(10,2),
	@correo varchar(30),
	@equipo varchar(20)
AS 
BEGIN 
	SET NOCOUNT ON;
	DECLARE @VotosE int,
			@VotosT int,
			@Cuota decimal(5,3),
			@CodApuesta int

	SELECT @CodApuesta = ISNULL(MAX(CodApuesta), 0) + 1 FROM Apuesta
	SELECT @VotosE = COUNT(Ganserie.CodApuesta) FROM Ganserie 
	INNER JOIN Apuesta ON Ganserie.CodApuesta = Apuesta.CodApuesta 
	WHERE CodEvento = @codEvento AND Equipo = @equipo
	
	SELECT @VotosT = COUNT(Ganserie.CodApuesta) FROM Ganserie
	SET @Cuota = dbo.Cuota(dbo.porcentil(@VotosE, @VotosT)) 

	BEGIN TRANSACTION
		INSERT INTO Apuesta
		VALUES (@codApuesta, @codEvento, @dinero, @correo, @Cuota, 10000)

		INSERT INTO Ganserie
		VALUES (@codApuesta, @equipo)

		DECLARE @SuperaMaximo BIT
		SELECT @SuperaMaximo = CASE
		WHEN DineroApostado * Cuota >= Maximo THEN 1
		ELSE 0
		END
		FROM Apuesta
		INNER JOIN Ganserie ON Apuesta.CodApuesta = Ganserie.CodApuesta
		WHERE Apuesta.CodApuesta = @CodApuesta AND Equipo = @Equipo;

	IF @SuperaMaximo = 1
		ROLLBACK;
	ELSE
		COMMIT;
		SELECT dbo.modificarSaldo(@correo, -@dinero)
END
GO

--Procedimiento que inserta una apuesta de tipo 2
CREATE OR ALTER PROCEDURE InsertarApuesta2
	@codEvento int,
	@dinero numeric(10,2),
	@correo varchar(30),
	@asesinatos int
AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @CodApuesta int
	SELECT @CodApuesta = ISNULL(MAX(CodApuesta), 0) + 1 FROM Apuesta
	BEGIN TRANSACTION
		INSERT INTO Apuesta
		VALUES (@codApuesta, @codEvento, @dinero, @correo, 2, 10000)
		
		INSERT INTO Asesinatos
		VALUES (@asesinatos, @codApuesta)

		DECLARE @SuperaMaximo BIT
		SELECT @SuperaMaximo = CASE
		WHEN DineroApostado * Cuota >= Maximo THEN 1
		ELSE 0
		END
		FROM Apuesta
		INNER JOIN Asesinatos ON Apuesta.CodApuesta = Asesinatos.CodApuesta
		WHERE Apuesta.CodApuesta = @CodApuesta AND Cantidad = @asesinatos;

	IF @SuperaMaximo = 1
		ROLLBACK;
	ELSE
		COMMIT;
		SELECT dbo.modificarSaldo(@correo, -@dinero)
END
GO

--Procedimiento que inserta una apuesta de tipo 3
CREATE OR ALTER PROCEDURE InsertarApuesta3
	@codEvento int,
	@dinero numeric(10,2),
	@correo varchar(30),
	@resultadoE1 int,
	@resultadoE2 int
AS
BEGIN 
	SET NOCOUNT ON;
		DECLARE @VotosE int,
				@VotosT int,
				@Cuota decimal(5,3),
				@CodApuesta int

	SELECT @VotosT = COUNT(resultserie.CodApuesta) FROM resultserie
	SELECT @VotosE = COUNT(resultserie.CodApuesta) FROM resultserie 
	INNER JOIN Apuesta ON resultserie.CodApuesta = Apuesta.CodApuesta 
	WHERE CodEvento = @codEvento AND ResultadoE1 = @resultadoE1 AND ResultadoE2 = @resultadoE2

	SELECT @Cuota = dbo.Cuota(dbo.porcentil(@VotosE, @VotosT))

	SELECT @CodApuesta = ISNULL(MAX(CodApuesta), 0) + 1 FROM Apuesta
	BEGIN TRANSACTION
		INSERT INTO Apuesta
		VALUES (@codApuesta, @codEvento, @dinero, @correo, @Cuota, 10000)
		
		INSERT INTO resultserie
		VALUES (@resultadoE1, @resultadoE2, @codApuesta)

		DECLARE @SuperaMaximo BIT
		SELECT @SuperaMaximo = CASE
		WHEN DineroApostado * Cuota >= Maximo THEN 1
		ELSE 0
		END
		FROM Apuesta
		INNER JOIN resultserie ON Apuesta.CodApuesta = resultserie.CodApuesta
		WHERE Apuesta.CodApuesta = @CodApuesta AND ResultadoE1 = @resultadoE1 AND ResultadoE2 = @resultadoE2;
	IF @SuperaMaximo = 1
		ROLLBACK;
	ELSE
		COMMIT;
		SELECT dbo.modificarSaldo(@correo, -@dinero)
END
GO

--Procedimiento que crea un usuario
CREATE OR ALTER PROCEDURE CrearUsuario
	@correo varchar(30),
	@passw varchar(30),
	@saldo numeric(10,2)
AS
BEGIN
	SET NOCOUNT ON;
	IF EXISTS (SELECT 1 FROM Usuario WHERE Correo = @correo)
	PRINT 'El usuario ya existe'
	ELSE
	INSERT INTO Usuario
	VALUES (@correo, @passw, @saldo)

	PRINT 'Usuario creado'
END
GO

--Procedimiento que termina un evento
CREATE PROCEDURE TerminarEvento
	@codEvento int,
	@resultadoE1 int,
	@resultadoE2 int,
	@equipo varchar(20),
	@asesinatos int
AS BEGIN
	SET NOCOUNT ON;
	IF EXISTS (SELECT 1 FROM Evento WHERE CodEvento = @codEvento)
	BEGIN 
	UPDATE Evento
	SET FechaFin = CURRENT_TIMESTAMP, ResultadoE1 = @resultadoE1, ResultadoE2 = @resultadoE2
	WHERE CodEvento = @codEvento
	END
	ELSE
	PRINT 'El evento no existe'
END

