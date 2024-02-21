USE ad2324_mditaranto;

CREATE TABLE Player(
	idPlayer INT primary key,
    Nick varchar(45),
    password varchar(128),
    email varchar(100)

);

CREATE TABLE Games(
	idGames INT primary key,
    Nombre varchar(45),
    tiempoJugado time
);

CREATE TABLE Compras(
	idCompra INT primary key,
    idPlayer INT,
    idGames INT,
    Cosa Varchar(25),		
    precio decimal(6,2),
    FechaCompra date,
    FOREIGN KEY (idGames) REFERENCES Games(idGames),
	FOREIGN KEY (idPlayer) REFERENCES Player(idPlayer)
);

DROP TABLE Compras;
DROP TABLE Games;
DROP TABLE Player;
    
SELECT * FROM Player;
SELECT * FROM Games;
SELECT * FROM Compras