CREATE DATABASE APUESTAS
GO


DROP DATABASE APUESTAS
USE APUESTAS
GO

CREATE TABLE Usuario (
	Correo varChar(30) not null primary key,
	passw varchar(10) not null,
	saldo numeric(10,2) not null,

)

CREATE TABLE Evento (
	Equipo varChar (20) not null,
	FechaIni Datetime2 not null,
	FechaFin Datetime2,
	CodEvento Int not null Primary key,
	Cantidad Int not null,
	ResultadoE1 Tinyint not null,
	ResultadoE2 tinyint not null,

)

CREATE TABLE Apuesta(
	CodApuesta Int Not null Primary key,
	Cuota decimal(4,2) Not null,
	CorreoUser varChar(30) not null,
	CodEvento Int not null,
	dineroApostado numeric(12,2) not null,

	Constraint FK_apuesta_evento FOREIGN KEY (CodEvento) REFERENCES Evento(CodEvento),
	Constraint FK_apuesta FOREIGN KEY (CorreoUser) REFERENCES Usuario(Correo)
)

CREATE TABLE Transaccion (
	CodTrans int primary key not null,
	Fecha date not null,
	Dinero numeric(10,2) not null,
	CorreoUser varChar(30) not null,

	Constraint FK_Trans FOREIGN KEY (CorreoUser) REFERENCES Usuario(Correo)
)


CREATE TABLE Asesinatos (
	Cantidad int not null,
	CodApuesta int not null primary key,
	CONSTRAINT FK_asesinatos FOREIGN KEY (CodApuesta) REFERENCES Apuesta(CodApuesta)

)

CREATE TABLE Ganserie (
	Equipo varchar(20) not null,
	CodApuesta int not null primary key,
	CONSTRAINT FK_ganserie FOREIGN KEY (CodApuesta) REFERENCES Apuesta(CodApuesta)

)

CREATE TABLE resultserie (
	ResultadoE1 tinyint not null,
	ResultadoE2 tinyint not null,
	CodApuesta int not null primary key,
	CONSTRAINT FK_resultserie FOREIGN KEY (CodApuesta) REFERENCES Apuesta(CodApuesta)

)