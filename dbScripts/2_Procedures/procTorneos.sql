DROP FUNCTION IF EXISTS obtenerJornadaTorneo;
DELIMITER //
CREATE FUNCTION obtenerJornadaTorneo(p_id_torneo INTEGER)
RETURNS INTEGER
DETERMINISTIC
BEGIN
    SELECT jornada FROM GRANTT.Torneo WHERE id_torneo = p_id_torneo INTO @resultado;
    RETURN @resultado;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS pasarJornada;
DELIMITER //
CREATE PROCEDURE pasarJornada(IN p_id_torneo INT)
BEGIN
	UPDATE Torneo
    set jornada = jornada + 1
    where id_torneo = p_id_torneo;
    
	UPDATE Jugador
	SET diasLesionado = diasLesionado - 1
	where diasLesionado > 0;
END//
DELIMITER ;

drop function if exists seJugoTorneo;
DELIMITER //
create function seJugoTorneo(f_id_torneo INTEGER)
returns BOOLEAN
DETERMINISTIC
begin
	SELECT COUNT(*)
    FROM GRANTT.Partido
    WHERE id_torneo = f_id_torneo
    INTO @cantPartidos;
    
    return @cantPartidos > 0;
end//
DELIMITER ;

drop function if exists crearTorneo;
DELIMITER //
create function crearTorneo()
returns INTEGER
DETERMINISTIC
begin
	INSERT INTO GRANTT.Torneo () VALUES ();
    UPDATE GRANTT.Jugador SET valor = 1000;
    UPDATE GRANTT.Jugador SET diasLesionado = 0;
    UPDATE GRANTT.Jugador SET tarjetasAmarillas = 0;
    UPDATE GRANTT.Jugador SET tarjetasRojas = 0;
    UPDATE GRANTT.Jugador SET partidosSuspendido = 0;
    UPDATE GRANTT.Equipo_Usuario SET presupuesto = 15000;
    
    DELETE FROM GRANTT.Ocurrencia;
    
    return LAST_INSERT_ID();
end//
DELIMITER ;

drop procedure if exists obtenerTorneos;
DELIMITER //
create procedure obtenerTorneos()
begin
	SELECT id_torneo, jornada FROM GRANTT.Torneo;
end//

DELIMITER ;

drop procedure if exists obtenerUltimoTorneo;
DELIMITER //
create procedure obtenerUltimoTorneo()
begin
	SELECT id_torneo, jornada FROM GRANTT.Torneo
    ORDER BY id_torneo DESC
    LIMIT 1;
end//
DELIMITER ;

/*SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE GRANTT.Torneo;
TRUNCATE TABLE GRANTT.Partido;
TRUNCATE TABLE GRANTT.Equipo_Local;
TRUNCATE TABLE GRANTT.Equipo_Visitante;
TRUNCATE TABLE GRANTT.Equipo_Usuario_Jugador;
SET FOREIGN_KEY_CHECKS = 1;*/