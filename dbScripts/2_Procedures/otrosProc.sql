USE GRANTT;

drop procedure if exists obtenerJugadores;
DELIMITER //
create procedure obtenerJugadores()
begin
	SELECT * FROM GRANTT.Jugador;
end//

DELIMITER ;

drop procedure if exists obtenerJugadoresEquipoUsuario;
DELIMITER //
create procedure obtenerJugadoresEquipoUsuario(p_usuario NVARCHAR(50))
begin
	SELECT
		id_equipo
	FROM
		GRANTT.Usuario
	WHERE
		nombre = p_usuario
	INTO
		@id_equip;

	SELECT
		j.*
	FROM
		GRANTT.Jugador j,
        GRANTT.Equipo_Usuario_Jugador ej,
        GRANTT.Equipo_Usuario e
	WHERE
		j.id_jugador = ej.id_jugador AND
        e.id_equipo = ej.id_equipo AND
        e.id_equipo = @id_equip;
end//

DELIMITER ;

drop procedure if exists obtenerJugadoresDisponiblesEquipoReal;
DELIMITER //
create procedure obtenerJugadoresDisponiblesEquipoReal(f_usuario NVARCHAR(50), f_id_equipoReal INTEGER)
begin
	-- Obtener la ID del equipo del usuario
    SELECT id_equipo FROM GRANTT.Usuario WHERE nombre = f_usuario INTO @idEquipoU;
    
	-- El NOT IN está para que no aparezcan los jugadores que ya están comprados
	SELECT * FROM GRANTT.Jugador
    WHERE id_equipoReal = f_id_equipoReal AND
		id_jugador NOT IN (SELECT euj.id_jugador FROM Equipo_Usuario_Jugador euj WHERE euj.id_equipo = @idEquipoU);
end//
DELIMITER ;

drop function if exists comprarJugador;
DELIMITER //
create function comprarJugador(f_usuario NVARCHAR(50), f_id_jugador INTEGER)
returns boolean
DETERMINISTIC
begin
	-- Obtener la ID del equipo
    SELECT eu.id_equipo
    FROM
		GRANTT.Usuario u,
        GRANTT.Equipo_Usuario eu
	WHERE
		u.id_equipo = eu.id_equipo AND
        u.nombre = f_usuario
	INTO @idEquipo;
    
	-- Presupuesto del equipo del usuario
    SELECT eu.presupuesto
    FROM
		GRANTT.Equipo_Usuario eu
	WHERE
		eu.id_equipo = @idEquipo
	INTO @presupuesto;
    
    -- Precio del jugador
    SELECT valor
    FROM GRANTT.Jugador
    WHERE id_jugador = f_id_jugador
    INTO @precioJugador;
    
    if @precioJugador > @presupuesto then
		return false;
	end if;
    
    -- Verificar que el equipo tiene espacio para más jugadores
    SELECT COUNT(*)
    FROM GRANTT.Equipo_Usuario_Jugador euj
    WHERE 
		euj.id_equipo = @idEquipo AND
        euj.id_jugador = f_id_jugador
	INTO @cantJugadores;
    
    if @cantJugadores >= 4 then
		return false;
	end if;
    
    -- Actualizar presupuesto del equipo del usuario
    UPDATE GRANTT.Equipo_Usuario
    SET presupuesto = @presupuesto - @precioJugador
    WHERE id_equipo = @idEquipo;
    
    -- Asociar jugador al equipo
    INSERT INTO GRANTT.Equipo_Usuario_Jugador
		(id_equipo, id_jugador)
    VALUES (@idEquipo, f_id_jugador);
    
    -- Desasignar a todos los titulares
    UPDATE Equipo_Usuario_Jugador
    SET titular = false
    WHERE id_equipo = @idEquipo;
    
    return true;
end//

DELIMITER ;

drop function if exists venderJugador;
DELIMITER //
create function venderJugador(f_usuario NVARCHAR(50), f_jugador INTEGER)
returns boolean
deterministic
begin
	-- Obtener ID del equipo
    SELECT eu.id_equipo
    FROM
		GRANTT.Usuario u,
        GRANTT.Equipo_Usuario eu
	WHERE
		u.id_equipo = eu.id_equipo AND
        u.nombre = f_usuario
	INTO @idEquipo;
    
    -- Obtener valor del jugador
    SELECT valor
    FROM GRANTT.Jugador
    WHERE id_jugador = f_jugador
    INTO @valorJugador;
    
    -- Subir presupuesto del usuario
    UPDATE GRANTT.Equipo_Usuario
    SET presupuesto = presupuesto + @valorJugador
    WHERE id_equipo = @idEquipo;
    
    -- Desreferenciar al jugador
    DELETE FROM GRANTT.Equipo_Usuario_Jugador
    WHERE id_jugador = f_jugador;
    
    -- Desasignar a todos los titulares
    UPDATE Equipo_Usuario_Jugador
    SET titular = false
    WHERE id_equipo = @idEquipo;
    
    return true;
end//
DELIMITER ;
drop procedure if exists obtenerIdTorneos;
DELIMITER //
create procedure obtenerIdTorneos()
begin
	SELECT id_torneo FROM GRANTT.Torneo;
end//

DELIMITER ;

drop function if exists obtenerUltimoTorneo;
DELIMITER //
create function obtenerUltimoTorneo()
returns INTEGER
deterministic
begin
	SELECT id_torneo
    FROM GRANTT.Torneo
    ORDER BY id_torneo DESC
    LIMIT 1
    INTO @resultado;
    
    return @resultado;
end//
DELIMITER ;

drop procedure if exists subirValorJugador;
DELIMITER //
create procedure subirValorJugador(f_id_jugador INTEGER, f_suma INTEGER)
begin
	UPDATE GRANTT.Jugador
    SET valor = valor + f_suma
    WHERE id_jugador = f_id_jugador;
end//

DELIMITER ;

drop function if exists obtenerNombreEquipoReal;
DELIMITER //
create function obtenerNombreEquipoReal(f_id_equipo INTEGER)
returns nvarchar(50)
deterministic
begin
	SELECT nombre
    FROM GRANTT.Equipo_Real
    WHERE id_equipoReal = f_id_equipo
    INTO @nombre;
    
    return @nombre;
end//

DELIMITER ;
