CREATE DATABASE GRANTT;
USE GRANTT;

CREATE TABLE Torneo (
	id_torneo INTEGER auto_increment,
    jornada INTEGER DEFAULT 0,
    CONSTRAINT pk_torneo PRIMARY KEY (id_torneo)
);

CREATE TABLE Equipo_Real (
	id_equipoReal INTEGER auto_increment,
    nombre NVARCHAR(50),
    CONSTRAINT pk_equipoReal PRIMARY KEY (id_equipoReal)
);

CREATE TABLE Equipo_Usuario (
	id_equipo INTEGER auto_increment,
    nombre NVARCHAR(50),
    presupuesto DOUBLE DEFAULT 10000,
    CONSTRAINT pk_equipoUsuario PRIMARY KEY (id_equipo)
);

CREATE TABLE Usuario (
	nombre NVARCHAR(50),
    contraseña TEXT(512),
    id_equipo INTEGER,
    admin BOOLEAN,
    CONSTRAINT pk_usuario PRIMARY KEY (nombre),
    CONSTRAINT fk_usuario_equipo FOREIGN KEY (id_equipo) REFERENCES Equipo_Usuario(id_equipo)
);

CREATE TABLE Posicion (
	id_posicion INTEGER,
    nombre nvarchar(20),
    CONSTRAINT pk_posicion PRIMARY KEY (id_posicion)
);

CREATE TABLE Jugador (
	id_jugador INTEGER auto_increment,
    nombre NVARCHAR(50),
    id_posicion INTEGER,
    edad INTEGER,
    id_equipoReal INTEGER,
    dorsal INTEGER,
    valor FLOAT DEFAULT 100,
    diasLesionado INTEGER,
    tarjetasAmarillas INTEGER DEFAULT 0,
    tarjetasRojas INTEGER DEFAULT 0,
    partidosSuspendido INTEGER DEFAULT 0,
    CONSTRAINT pk_jugador PRIMARY KEY (id_jugador),
    CONSTRAINT fk_jugador_equipoReal FOREIGN KEY (id_equipoReal) REFERENCES Equipo_Real(id_equipoReal),
    CONSTRAINT fk_jugador_posicion FOREIGN KEY (id_posicion) REFERENCES Posicion(id_posicion)
);

CREATE TABLE Equipo_Local (
	id_equipo_local INTEGER auto_increment,
    id_equipoReal INTEGER,
    goles INTEGER,
    CONSTRAINT pk_partidoLocal PRIMARY KEY (id_equipo_local),
    CONSTRAINT fk_equipoLocal_equipoReal FOREIGN KEY (id_equipoReal) REFERENCES Equipo_Real(id_equipoReal)
);

CREATE TABLE Equipo_Visitante (
	id_equipo_visitante INTEGER auto_increment,
    id_equipoReal INTEGER,
    goles INTEGER,
    CONSTRAINT pk_partidoVisitante PRIMARY KEY (id_equipo_visitante),
    CONSTRAINT fk_equipoVisitante_equipoReal FOREIGN KEY (id_equipoReal) REFERENCES Equipo_Real(id_equipoReal)
);

CREATE TABLE Partido (
	id_partido INTEGER auto_increment,
    id_torneo INTEGER,
    jornada INTEGER,
    partido_local INTEGER,
    partido_visitante INTEGER,
    CONSTRAINT pk_partido PRIMARY KEY (id_partido),
    CONSTRAINT fk_partido_local FOREIGN KEY (partido_local) REFERENCES Equipo_Local(id_equipo_local),
    CONSTRAINT fk_partido_visitante FOREIGN KEY (partido_visitante) REFERENCES Equipo_Visitante(id_equipo_visitante)
);

CREATE TABLE Equipo_Usuario_Jugador (
	id_equipo INTEGER,
    id_jugador INTEGER,
    titular BOOLEAN DEFAULT false,
    CONSTRAINT pk_equipoUsuario_jugador PRIMARY KEY (id_equipo, id_jugador),
    CONSTRAINT fk_relEquipoUsuario_jugador FOREIGN KEY (id_jugador) REFERENCES Jugador(id_jugador),
    CONSTRAINT fk_relEquipoUsuario_equipo FOREIGN KEY (id_equipo) REFERENCES Equipo_Usuario(id_equipo)
);

/*
 * 1: Gol
 * 2: Lesion
 * 3: Tarjeta amarillas
 * 4: Tarjeta roja
 */
CREATE TABLE Ocurrencia (
	id_ocurrencia INTEGER NOT NULL auto_increment,
    ocurrencia INTEGER NOT NULL,
    id_partido INTEGER NOT NULL,
    id_jugador INTEGER NOT NULL,
    CONSTRAINT pk_ocurrencia PRIMARY KEY (id_ocurrencia),
    CONSTRAINT fk_ocurrencia_partido FOREIGN KEY (id_partido) REFERENCES Partido(id_partido),
    CONSTRAINT fk_ocurrencia_jugador FOREIGN KEY (id_jugador) REFERENCES Jugador(id_jugador)
);

INSERT INTO Posicion
VALUES (1, 'arquero');

INSERT INTO Posicion
VALUES (2, 'defensor');

INSERT INTO Posicion
VALUES (3, 'medio');

INSERT INTO Posicion
VALUES (4, 'delantero');

CALL obtenerJugadoresEquipoUsuario(8);