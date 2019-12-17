DROP FUNCTION IF EXISTS obtenerJugadorRandom;
DELIMITER //
CREATE FUNCTION obtenerJugadorRandom(p_id_equipo INTEGER)
RETURNS INTEGER
DETERMINISTIC
BEGIN
	SELECT id_jugador
    FROM GRANTT.Jugador
    WHERE
		id_equipoReal = p_id_equipo AND
        diasLesionado + partidosSuspendido = 0 AND
        tarjetasRojas = 0
    ORDER BY RAND()
    LIMIT 1
    INTO @resultado;
    
    RETURN @resultado;
END//
DELIMITER ;

drop procedure if exists obtenerEquiposReales;
DELIMITER //
create procedure obtenerEquiposReales()
begin
	SELECT * FROM Equipo_Real;
end//

DELIMITER ;

drop procedure if exists obtenerJugadoresEquipoReal;
DELIMITER //
create procedure obtenerJugadoresEquipoReal(f_id_equipo INTEGER)
begin
	SELECT * FROM GRANTT.Jugador WHERE id_equipoReal = f_id_equipo;
end //

DELIMITER ;

DROP FUNCTION IF EXISTS obtenerValorTotalEquipo;
DELIMITER //
CREATE FUNCTION obtenerValorTotalEquipo(p_id_equipo INTEGER)
RETURNS int
deterministic
BEGIN
    SELECT SUM(valor) into @valor FROM GRANTT.Jugador where id_equipoReal = p_id_equipo;
    return @valor;
END//

DELIMITER ;

DROP PROCEDURE IF EXISTS ponerTarjetaRoja;
DELIMITER //
CREATE PROCEDURE ponerTarjetaRoja(p_id_jugador INTEGER)
deterministic
BEGIN
    UPDATE GRANTT.Jugador
    SET
        tarjetasAmarillas = 0,
        tarjetasRojas = 1,
        partidosSuspendido = 1
    WHERE
        id_jugador = p_id_jugador;
END //

DELIMITER ;

DROP PROCEDURE IF EXISTS ponerTarjetaAmarilla;
DELIMITER //
CREATE PROCEDURE ponerTarjetaAmarilla(p_id_jugador INTEGER)
deterministic
BEGIN
	-- Verifica si no es su 5ta tarjeta del torneo
    SELECT tarjetasAmarillas FROM GRANTT.Jugador WHERE id_jugador = p_id_jugador INTO @tarjetasAmarillas;
    
    if @tarjetasAmarillas = 4 then
		SET @mereceRoja = true;
	else
		SET @mereceROja = false;
	end if;
    
    if @mereceRoja then
        CALL ponerTarjetaRoja(p_id_jugador);
    else 
        UPDATE GRANTT.Jugador
        SET tarjetasAmarillas = tarjetasAmarillas + 1
        WHERE id_jugador = p_id_jugador;
    end if;
END//

DELIMITER ;

DROP PROCEDURE IF EXISTS lesionarJugador;
DELIMITER //
CREATE PROCEDURE lesionarJugador(p_id_jugador INTEGER)
BEGIN
	UPDATE GRANTT.Jugador
    SET diasLesionado = diasLesionado + 1
    WHERE id_jugador = p_id_jugador;
END;
