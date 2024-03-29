drop function if exists cantidadTarjetasAmarillas;
DELIMITER //
create function cantidadTarjetasAmarillas(p_id_jugador INTEGER)
returns boolean
deterministic
begin
	SELECT COUNT(*)
    FROM GRANTT.Ocurrencia
    WHERE
		id_jugador = p_id_jugador AND
        ocurrencia = 3
	INTO @res;
    
    RETURN @res;
end//

DELIMITER ;

drop function if exists tieneTarjetaRoja;
DELIMITER //
create function tieneTarjetaRoja(p_id_jugador INTEGER)
returns boolean
deterministic
begin
	SELECT COUNT(*) > 0
    FROM GRANTT.Ocurrencia
    WHERE
		id_jugador = p_id_jugador AND
        ocurrencia = 4
	INTO @res;
    
    RETURN @res;
end//

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
    SELECT esLocal, ocurrencia, nombre FROM
    (
		SELECT
			true AS esLocal,
			ol.ocurrencia AS ocurrencia,
			jl.nombre AS nombre,
            ol.orden AS orden
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
            ov.ocurrencia AS ocurrencia,
            jv.nombre AS nombre,
            ov.orden AS orden
		FROM
			GRANTT.Ocurrencia ov,
            GRANTT.Jugador jv
		WHERE
			ov.id_jugador = jv.id_jugador AND
            ov.id_partido = p_id_partido AND
            jv.id_equipoReal = @id_equipoVisitante
    ) AS T
    ORDER BY orden;
END//

DELIMITER ;

-- 1. GOL
-- 2. LESIÓN
-- 3. AMARILLA
-- 4. ROJA
DROP PROCEDURE IF EXISTS ponerOcurrencia;
DELIMITER //
CREATE PROCEDURE ponerOcurrencia(p_ocurrencia INTEGER, p_id_partido INTEGER, p_id_jugador INTEGER)
BEGIN
	SET max_sp_recursion_depth=2;
    SELECT * FROM GRANTT.Ocurrencia;

	INSERT INTO GRANTT.Ocurrencia (ocurrencia, id_partido, id_jugador, orden)
    VALUES (p_ocurrencia, p_id_partido, p_id_jugador, RAND());
    
    IF p_ocurrencia = 1 THEN
		UPDATE GRANTT.Jugador
        SET valor = valor + 100
        WHERE id_jugador = p_id_jugador;
    ELSEIF p_ocurrencia > 1 AND p_ocurrencia <= 4 THEN
		UPDATE GRANTT.Jugador
        SET valor = valor - 100
        WHERE id_jugador = p_id_jugador;
        
        IF p_ocurrencia = 4 THEN
			UPDATE GRANTT.Jugador
            SET partidosSuspendido = 2
            WHERE id_jugador = p_id_jugador;
		ELSEIF p_ocurrencia = 3 THEN
			-- Obtiene la cantidad de tarjetas amarillas que tiene
			SELECT COUNT(*)
            FROM GRANTT.Ocurrencia
            WHERE
				id_jugador = p_id_jugador AND
                ocurrencia = 3
			INTO @cantAmarillasTorneo;
            
            SELECT COUNT(*)
            FROM GRANTT.Ocurrencia
            WHERE
				id_partido = p_id_partido AND
				id_jugador = p_id_jugador AND
                ocurrencia = 3
			INTO @cantAmarillasPartido;
            
            insert into log values (@cantAmarillasTorneo,@cantAmarillasPartido,0,0);
            
            IF @cantAmarillasTorneo >= 5 OR @cantAmarillasPartido >= 2 THEN
				DELETE FROM GRANTT.Ocurrencia
                WHERE
					id_jugador = p_id_jugador AND
                    ocurrencia = 3;
				
                CALL ponerOcurrencia(4, p_id_partido, p_id_jugador);
            END IF;
		ELSEIF p_ocurrencia = 2 THEN
			UPDATE GRANTT.Jugador
            SET diasLesionado = 5
            WHERE id_jugador = p_id_jugador;
        END IF;
    END IF;
    
    SET max_sp_recursion_depth=0;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS ponerTarjetaAmarilla;
DELIMITER //
CREATE PROCEDURE ponerTarjetaAmarilla(p_id_jugador INTEGER, p_id_partido INTEGER)
deterministic
BEGIN
	-- Verifica si no es su 5ta tarjeta del torneo
    SELECT tieneTarjetaAmarilla(p_id_jugador) INTO @tarjetasAmarillas;
    
    if @tarjetasAmarillas = true then
		SET @ocurrencia = 4;
	else
		SET @ocurrencia = 3;
	end if;
    
	CALL ponerOcurrencia(@ocurrencia, p_id_partido, p_id_jugador);
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
			partidosSuspendido = partidosSuspendido - 1
		WHERE id_jugador = p_id_jugador;
        
        DELETE FROM GRANTT.Ocurrencia
        WHERE
			id_ocurrencia = 4 AND
			id_jugador = p_id_jugador;
    END IF;
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
