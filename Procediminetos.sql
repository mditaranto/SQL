SELECT * FROM Evento
SELECT * FROM Usuario
SELECT * FROM Transaccion
SELECT * FROM APUESTA

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

PRINT dbo.ComprobarApuestaAccesible(4)

--Funcion para calcular la cuota+
CREATE OR ALTER FUNCTION Cuota(@porcentil decimal(5,3))
RETURNS decimal(5,3)
AS
BEGIN
    DECLARE @cuota decimal(5,3)
    SET @cuota = ((3.0-1.0)/100.0 * (100-@porcentil) + 1);

    RETURN @cuota;
END

declare @cuota decimal(5,3)
Set @cuota = dbo.Cuota(dbo.porcentil(1,3))
print @cuota

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

--Funcion para comprobar el resultado
CREATE OR ALTER FUNCTION ComprobarResultado()

begin Transaction;
insert into Apuesta values (6, 2, 2000, 'usuario1@example.com', 1)
insert INTO asesinatos values (2,6)

rollback

select * from Asesinatos