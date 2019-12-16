drop procedure if exists obtenerPartidosJugados;
DELIMITER //
create procedure obtenerPartidosJugados(f_id_torneo INTEGER)
begin
	SELECT
		p.id_partido,
        p.jornada,
        pl.id_equipoReal AS equipo_local,
        pl.goles AS goles_local,
        pv.id_equipoReal AS equipo_visitante,
        pv.goles AS goles_visitante
    FROM
		GRANTT.Partido p,
        GRANTT.Equipo_Local pl,
        GRANTT.Equipo_Visitante pv
    WHERE
		p.partido_local = pl.id_equipo_local AND
        p.partido_visitante = pv.id_equipo_visitante AND
        pl.goles IS NOT NULL AND
        pv.goles IS NOT NULL AND
		id_torneo = f_id_torneo
	ORDER BY p.jornada ASC;
end//

DELIMITER ;

drop procedure if exists obtenerPartidosPendientes;
DELIMITER //
create procedure obtenerPartidosPendientes(f_id_torneo INTEGER)
begin
	SELECT
		p.id_partido,
        p.jornada,
        pl.id_equipoReal AS equipo_local,
        pv.id_equipoReal AS equipo_visitante
	FROM
		GRANTT.Partido p,
        GRANTT.Equipo_Local pl,
        GRANTT.Equipo_Visitante pv
	WHERE
		p.partido_local = pl.id_equipo_local AND
        p.partido_visitante = pv.id_equipo_visitante AND
		pl.goles IS NULL AND
		pv.goles IS NULL AND
        id_torneo = f_id_torneo
	ORDER BY p.jornada ASC;
end//

DELIMITER ;

drop procedure if exists anadirPartidoPendiente;
DELIMITER //
create procedure anadirPartidoPendiente(f_id_torneo INTEGER, f_jornada INTEGER, f_id_equipo_local INTEGER, f_id_equipo_visitante INTEGER)
begin
	INSERT INTO GRANTT.Equipo_Local (id_equipoReal)
    VALUES (f_id_equipo_local);
    SET @idPartidoLocal = LAST_INSERT_ID();
    
    INSERT INTO GRANTT.Equipo_Visitante (id_equipoReal)
    VALUES (f_id_equipo_visitante);
    SET @idPartidoVisitante = LAST_INSERT_ID();
    
    INSERT INTO GRANTT.Partido
		(id_torneo, jornada, partido_local, partido_visitante)
    VALUES
		(f_id_torneo, f_jornada, @idPartidoLocal, @idPartidoVisitante);
end//
DELIMITER ;

DROP PROCEDURE IF EXISTS obtenerPartidosSinJugarDeHoy;
DELIMITER //
CREATE PROCEDURE obtenerPartidosSinJugarDeHoy(f_id_torneo INTEGER, f_dia INTEGER)
BEGIN
    SELECT
		p.id_partido,
        p.jornada,
		pl.id_equipoReal AS id_local,
        pv.id_equipoReal AS id_visitante
	FROM
		GRANTT.Partido p,
        GRANTT.Equipo_Local pl,
        GRANTT.Equipo_Visitante pv
	WHERE
		p.partido_local = pl.id_equipo_local AND
        p.partido_visitante = pv.id_equipo_visitante AND
		p.jornada = f_dia AND
        p.id_torneo = f_id_torneo;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS ponerResultadoDePartido;
DELIMITER //
CREATE PROCEDURE ponerResultadoDePartido(f_id_partido INTEGER, f_goles_local INTEGER, f_goles_visitante INTEGER)
BEGIN
	-- Obtener id partido_local
    SELECT partido_local
    FROM GRANTT.Partido
    WHERE id_partido = f_id_partido
    INTO @idPartidoLocal;
    
    -- Obtener id partido_visitante
    SELECT partido_visitante
    FROM GRANTT.Partido
    WHERE id_partido = f_id_partido
    INTO @idPartidoVisitante;
    
    -- Poner goles
    UPDATE GRANTT.Equipo_Local
    SET goles = f_goles_local
    WHERE id_equipo_local = @idPartidoLocal;
    
    UPDATE GRANTT.Equipo_Visitante
    SET goles = f_goles_visitante
    WHERE id_equipo_visitante = @idPartidoVisitante;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS lesionesJugador;
DELIMITER //
CREATE PROCEDURE lesionesJugador(IN p_id_jugador int, IN dias int)
BEGIN
	UPDATE Jugador
	SET diasLesionado = dias
	WHERE id_jugador = p_id_jugador;
END//
DELIMITER ; 

DROP PROCEDURE IF EXISTS actualizarDiasSuspendido;
DELIMITER //
CREATE PROCEDURE actualizarDiasSuspendido(p_id_jugador INTEGER)
BEGIN
	-- Obtener si tiene partidos en los que está suspendido
    SELECT partidosSuspendido FROM GRANTT.Jugador WHERE id_jugador = p_id_jugador INTO @partidosSuspendido;
    
    IF @partidosSuspendido > 1 THEN
		UPDATE GRANTT.Jugador
        SET partidosSuspendido = partidosSuspendido - 1
        WHERE id_jugador = p_id_jugador;
	ELSEIF @partidosSuspendido > 0 THEN
		UPDATE GRANTT.Jugador
        SET
			partidosSuspendido = partidosSuspendido - 1,
            tarjetasRojas = 0
		WHERE id_jugador = p_id_jugador;
    END IF;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS ponerOcurrencia;
DELIMITER //
CREATE PROCEDURE ponerOcurrencia(p_ocurrencia INTEGER, p_id_partido INTEGER, p_id_jugador INTEGER)
BEGIN
	INSERT INTO GRANTT.Ocurrencia (ocurrencia, id_partido, id_jugador)
    VALUES (p_ocurrencia, p_id_partido, p_id_jugador);
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS obtenerOcurrencias;
DELIMITER //
CREATE PROCEDURE obtenerOcurrencias(p_id_partido INTEGER)
BEGIN
	-- Obtiene las ids del equipo local y el visitante
	SELECT id_equipoReal
    FROM
		GRANTT.Partido p,
        GRANTT.Equipo_Local el
	WHERE
		p.id_partido = p_id_partido AND
        p.partido_local = el.id_equipo_local
	INTO @id_equipoLocal;
    
    SELECT id_equipoReal
	FROM
		GRANTT.Partido p,
        GRANTT.Equipo_Visitante ev
	WHERE
		p.id_partido = p_id_partido AND
        p.partido_visitante = ev.id_equipo_visitante
	INTO @id_equipoVisitante;
    
    -- Devuelve las ocurrencias de forma desordenada
    SELECT * FROM
    (
		SELECT
			true AS esLocal,
			ol.ocurrencia,
			jl.nombre
		FROM
			GRANTT.Ocurrencia ol,
			GRANTT.Jugador jl
		WHERE
			ol.id_jugador = jl.id_jugador AND
			ol.id_partido = p_id_partido AND
			jl.id_equipoReal = @id_equipoLocal
		UNION
		SELECT
			false AS esLocal,
            ov.ocurrencia,
            jv.nombre
		FROM
			GRANTT.Ocurrencia ov,
            GRANTT.Jugador jv
		WHERE
			ov.id_jugador = jv.id_jugador AND
            ov.id_partido = p_id_partido AND
            jv.id_equipoReal = @id_equipoVisitante
    ) AS T
    ORDER BY RAND();
END//
DELIMITER ;