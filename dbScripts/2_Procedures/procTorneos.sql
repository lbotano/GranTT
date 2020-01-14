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
    SET @idTorneo = LAST_INSERT_ID();
    
    UPDATE GRANTT.Jugador SET valor = 1000;
    UPDATE GRANTT.Jugador SET diasLesionado = 0;
    UPDATE GRANTT.Jugador SET partidosSuspendido = 0;
    UPDATE GRANTT.Equipo_Usuario SET presupuesto = 15000;
    
    DELETE FROM GRANTT.Ocurrencia;
    
    CALL generarFixture(@idTorneo);
    
    return @idTorneo;
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

drop procedure if exists generarFixture;
DELIMITER //
create procedure generarFixture(p_id_torneo INTEGER)
begin
	-- Genera el fixture
    SET @i = 0;
    SET @j = 1;
    
    DROP TEMPORARY TABLE IF EXISTS resultado;
    CREATE TEMPORARY TABLE resultado (id_local INTEGER, id_visitante INTEGER);
    
    SELECT COUNT(*) FROM GRANTT.Equipo_Real INTO @cantEquipos;
    
    while @i < @cantEquipos do
		while @j < @cantEquipos do
			INSERT INTO resultado
			VALUES (@i + 1, @j + 1);
                
			SET @j = @j + 1;
        end while;
        
        SET @i = @i + 1;
        SET @j = @i + 1;
    end while;
    
    -- Desordena los resultados
    DROP TEMPORARY TABLE IF EXISTS resultadoDesordenado;
    CREATE TEMPORARY TABLE resultadoDesordenado
    (
		orden INTEGER PRIMARY KEY AUTO_INCREMENT,
        id_local INTEGER NOT NULL,
        id_visitante INTEGER NOT NULL,
        jornada INTEGER DEFAULT 0);
    
    INSERT INTO resultadoDesordenado (id_local, id_visitante)
		SELECT id_local, id_visitante 
        FROM resultado
        ORDER BY RAND();
       
	-- Inserta los partidos desordenados en sus tablas correspondientes
	SELECT COUNT(*) FROM resultado INTO @cantPartidos;
    
    SET @i = 0;
    SET @jornada = 0;
    WHILE @i < @cantPartidos DO
		IF @i % 40 = 0 THEN
			SET @jornada = @jornada + 1;
		END IF;
        
        SELECT id_local
        FROM resultadoDesordenado
        WHERE orden = @i + 1
        INTO @id_local;
        
        SELECT id_visitante
        FROM resultadoDesordenado
        WHERE orden = @i + 1
        INTO @id_visitante;
        
        CALL anadirPartidoPendiente(p_id_torneo, @jornada, @id_local, @id_visitante);
        
        SET @i = @i + 1;
    END WHILE;
end//

/*SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE GRANTT.Torneo;
TRUNCATE TABLE GRANTT.Partido;
TRUNCATE TABLE GRANTT.Equipo_Local;
TRUNCATE TABLE GRANTT.Equipo_Visitante;
TRUNCATE TABLE GRANTT.Equipo_Usuario_Jugador;
TRUNCATE TABLE GRANTT.Ocurrencia;
SET FOREIGN_KEY_CHECKS = 1;*/