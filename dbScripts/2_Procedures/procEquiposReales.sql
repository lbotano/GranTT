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
        diasLesionado + partidosSuspendido = 0
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
