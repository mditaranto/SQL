DROP DATABASE Prueba
CREATE DATABASE Prueba
GO

USE Prueba

--Creacion de las tablas
CREATE TABLE AlumnosSolicitantes (
	
	DNI VarChar(10) Primary key,
	nombre varchar(20) not null,
	nota decimal(10,2) not null,
	cuantia tinyint not null

)

CREATE TABLE AlumnosConBeca (

	DNI VarChar(10) Primary key,
	nombre varchar(20) not null,
	cuantia tinyint not null,
	CONSTRAINT FK_DNI FOREIGN KEY (DNI) REFERENCES AlumnosSolicitantes(DNI)
	
)

GO

--Insertamos los valores en la tabla 
insert into AlumnosSolicitantes values ('11111111A', 'Ana Albaricoque', 9.8, 150)
insert into AlumnosSolicitantes values ('22222222B', 'Beatriz Blanco', 9.5, 200)
insert into AlumnosSolicitantes values ('33333333C', 'Cristina Cortina', 7.6, 100)
insert into AlumnosSolicitantes values ('44444444D', 'Daniel Dado', 7.6, 100)
insert into AlumnosSolicitantes values ('55555555E', 'Enriqueta Espera', 6.9, 150)
insert into AlumnosSolicitantes values ('66666666F', 'Federico Frio', 6.8, 50)
insert into AlumnosSolicitantes values ('77777777G', 'Guillermo Gil', 6.1, 100)

GO

--Creacion de un procedimiento que añadira los alumnos que tendran beca de los solicitantes
CREATE OR ALTER PROCEDURE DarBeca
	@dineroParaBeca int

AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @dineroSolicitado tinyint,
			@dnialum varchar(10),
			@nombrealum varchar(10)

	--Cursor que recorre los solicitantes
	DECLARE Cursor1 CURSOR FOR
	SELECT DNI, nombre, cuantia FROM AlumnosSolicitantes
	ORDER BY nota DESC --Ordenador de mayor nota a menor

	OPEN Cursor1

	FETCH NEXT FROM Cursor1 INTO @dnialum, @nombrealum, @dineroSolicitado

	WHILE (@@FETCH_STATUS = 0) --Mientras haya solicitantes
	BEGIN
		--Verificacion de dinero disponible para dar una beca
		if @dineroParaBeca >= @dineroSolicitado
		BEGIN
			--Añadimos el alumno a la tabla de alumnos con beca
			INSERT INTO AlumnosConBeca
			VALUES (@dnialum, @nombrealum, @dineroSolicitado)

			--Se comunica
			print 'Al alumno ' +  @nombrealum + 'se le ha concedido la beca: ' + CAST(@dineroSolicitado as Varchar(20))

			--Se calcula la nueva cantidad para becas
			SET @dineroParaBeca = @dineroParaBeca - @dineroSolicitado

		END

		ELSE
		BEGIN
			--En caso de no haber dinero, se comunica
			PRINT 'No tenemos dinero suficiente para asignar la beca al alumno' + @nombrealum + '. Ha solicitado ' + CAST(@dineroSolicitado as Varchar(20)) + ' y disponemos de ' + CAST(@dineroParaBeca as VarChar(20))
		END

		--Pasa al siguiente alumno
		FETCH NEXT FROM Cursor1 INTO @dnialum, @nombrealum, @dineroSolicitado
	END

	CLOSE Cursor1
	DEALLOCATE Cursor1
END

--Transaccion para no afectar a la base de datos
BEGIN TRANSACTION
EXECUTE DarBeca 600

SELECT * FROM AlumnosConBeca

ROLLBACK
