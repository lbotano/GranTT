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
    DELETE FROM GRANTT.Equipo_Usuario_Jugador;
    
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
		IF @i % 20 = 0 THEN
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

DELIMITER ;

drop procedure if exists jugarDiaSiguiente;
DELIMITER //
create procedure jugarDiaSiguiente()
begin
	DECLARE iPartido INTEGER DEFAULT 0;
    DECLARE iGolLocal INTEGER DEFAULT 0;
    DECLARE iGolVisitante INTEGER DEFAULT 0;
    DECLARE iOcurrencia INTEGER DEFAULT 0;
    
    -- Le resta un día lesionado a los jugadores
    UPDATE Jugador
    SET diasLesionado = diasLesionado - 1
    where diasLesionado > 0;
	
	-- Obtener id del ultimo torneo y la jornada por la que va
    SELECT id_torneo, jornada
    FROM GRANTT.Torneo
    ORDER BY id_torneo DESC
    LIMIT 1
    INTO @id_torneo, @jornada;
    
    -- Cambia al día siguiente
    UPDATE GRANTT.Torneo
    SET jornada = jornada + 1
    WHERE id_torneo = @id_torneo;
    SET @jornada = @jornada + 1;
    
    -- Obtiene una tabla con todos los partidos a jugar
    DROP TEMPORARY TABLE IF EXISTS partidos_a_jugar;
    CREATE TEMPORARY TABLE partidos_a_jugar SELECT id_partido
    FROM GRANTT.Partido
    WHERE 
		id_torneo = @id_torneo AND
        jornada = @jornada;
    
    -- Poner una cantidad de goles aleatoria a los
    -- partidos a jugar
    UPDATE GRANTT.Equipo_Local
    INNER JOIN GRANTT.Partido ON partido_local = id_equipo_local AND jornada = @jornada
    SET goles = FLOOR(RAND() * (4 + 1));
    
    UPDATE GRANTT.Equipo_Visitante
    INNER JOIN GRANTT.Partido ON partido_visitante = id_equipo_visitante AND jornada = @jornada
    SET goles = FLOOR(RAND() * (4 + 1));
    
    --
    -- Elegir a los goleadores 
	--
    
    SET iPartido = 0;
    SELECT COUNT(*)
    FROM GRANTT.Partido
    WHERE
		jornada = @jornada AND
        id_torneo = @id_torneo
    INTO @nPartido; 
    -- por cada partido
    while iPartido < @nPartido do
		-- Selecciona la id del equipo local y el visitante
        -- y la id del partido
		SELECT
			el.id_equipoReal,
            ev.id_equipoReal,
            p.id_partido
        FROM
            GRANTT.Partido p,
            GRANTT.Equipo_Local el,
            GRANTT.Equipo_Visitante ev
		WHERE
			el.id_equipo_local = p.partido_local AND
            ev.id_equipo_visitante = p.partido_visitante AND
            p.jornada = @jornada AND
            p.id_torneo = @id_torneo
		ORDER BY p.id_partido DESC
        LIMIT iPartido, 1
        INTO
			@idEquipoLocal,
            @idEquipoVisitante,
            @id_partido;
                
		-- Hacer los goles del equipo local
        
		SELECT
			el.goles
        FROM
			GRANTT.Partido p,
            GRANTT.Equipo_Local el
		WHERE
			p.partido_local = el.id_equipo_local AND
            p.id_partido = @id_partido
		INTO @nGolLocal;
        
        SET iGolLocal = 0;
        while iGolLocal < @nGolLocal do
			-- Selecciona un jugador al azar
            SELECT id_jugador
            FROM GRANTT.Jugador
            WHERE
				id_equipoReal = @idEquipoLocal AND
                diasLesionado = 0 AND
                partidosSuspendido = 0
			ORDER BY RAND()
            LIMIT 1
            INTO @idJugadorRandom;
            
            CALL ponerOcurrencia(1, @id_partido, @idJugadorRandom);
            
            SET iGolLocal = iGolLocal + 1;
        end while;
        
        -- Hacer los goles del equipo visitante
        SELECT
			ev.goles
        FROM
			GRANTT.Partido p,
            GRANTT.Equipo_Visitante ev
		WHERE
			p.partido_visitante = ev.id_equipo_visitante AND
            p.id_partido = @id_partido
		INTO @nGolVisitante;
        
		SET iGolVisitante = 0;
        while iGolVisitante < @nGolVisitante do
			-- Selecciona un jugador al azar
            SELECT id_jugador
            FROM GRANTT.Jugador
            WHERE
				id_equipoReal = @idEquipoVisitante AND
                diasLesionado = 0 AND
                partidosSuspendido = 0
			ORDER BY RAND()
            LIMIT 1
            INTO @idJugadorRandom;
            
            CALL ponerOcurrencia(1, @id_partido, @idJugadorRandom);
            
            
            SET iGolVisitante = iGolVisitante + 1;
        end while;
        
        -- CALCULAR TARJETAS
        
        SELECT FLOOR(RAND() * 5) INTO @cantOcurrencias;
        SET iOcurrencia = 0;
        
        while iOcurrencia < @cantOcurrencias do
			-- Selecciona el tipo de ocurrencia (roja, amarilla o lesión)
            SELECT FLOOR(RAND() * 3 + 2) INTO @tipoOcurrencia;
            
            -- Selecciona un jugador al azar de entre los dos equipos
            SELECT id_jugador
            FROM GRANTT.Jugador
            WHERE
				(id_equipoReal = @idEquipoLocal OR
                id_equipoReal = @idEquipoVisitante) AND
                diasLesionado = 0 AND
                partidosSuspendido = 0
			ORDER BY RAND()
            LIMIT 1
            INTO @idJugadorOcurrencia;
            
            -- Pone la ocurrencia
            CALL ponerOcurrencia(@tipoOcurrencia, @id_partido, @idJugadorOcurrencia);
        
			SET iOcurrencia = iOcurrencia + 1;
        end while;
        
        -- El jugador suspendido ya pasó un día
        UPDATE GRANTT.Jugador
        SET partidosSuspendido = partidosSuspendido - 1
        WHERE
			partidosSuspendidos > 0 AND
			(id_equipoReal = @idEquipoLocal OR
            id_equipoReal = @idEquipoVisitante);
        
        SET iPartido = iPartido + 1;
	end while;
end//

DELIMITER ;

/*SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE GRANTT.Torneo;
TRUNCATE TABLE GRANTT.Partido;
TRUNCATE TABLE GRANTT.Equipo_Local;
TRUNCATE TABLE GRANTT.Equipo_Visitante;
TRUNCATE TABLE GRANTT.Equipo_Usuario_Jugador;
TRUNCATE TABLE GRANTT.Ocurrencia;
SET FOREIGN_KEY_CHECKS = 1;*/

