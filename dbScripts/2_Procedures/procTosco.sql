DROP PROCEDURE IF EXISTS obtenerTitularesEquipoUsuario;

CALL obtenerSuplentesEquipoUsuario('lautaro');

DELIMITER //
CREATE PROCEDURE obtenerTitularesEquipoUsuario(p_usuario NVARCHAR(50))
BEGIN
	SELECT j.*
    FROM
		GRANTT.Jugador j,
        GRANTT.Equipo_Usuario_Jugador euj,
        GRANTT.Usuario u
	WHERE
		u.id_equipo = euj.id_equipo AND
        euj.id_jugador = j.id_jugador AND
        euj.titular AND
        u.nombre = p_usuario;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS obtenerSuplentesEquipoUsuario;
DELIMITER //
CREATE PROCEDURE obtenerSuplentesEquipoUsuario(p_usuario NVARCHAR(50))
BEGIN
	SELECT j.*
    FROM
		GRANTT.Jugador j,
        GRANTT.Equipo_Usuario_Jugador euj,
        GRANTT.Usuario u
	WHERE
		u.id_equipo = euj.id_equipo AND
        euj.id_jugador = j.id_jugador AND
        NOT euj.titular AND
        u.nombre = p_usuario;
END//
DELIMITER ;

drop procedure if exists actualizarJugadorEquipo;
delimiter //
create procedure actualizarJugadorEquipo(
	f_usuario nvarchar(50),
    f_jugador integer,
    f_esTitular boolean
) begin
	
	-- obtengo la id del Equipo del usuario
	select u.id_equipo from 
        Usuario u
	where 
		u.nombre = f_usuario
	limit 1
	into @idEquipo;
	
    -- me fijo si no existe el registro
    
    select COUNT(*) from 
		Equipo_Usuario_Jugador euj
	where
		euj.id_equipo = @idEquipo
        and euj.id_jugador = f_jugador
    into @cant;
    select @idEquipo;
    if @cant > 0 then
		update Equipo_Usuario_Jugador euj
		set titular = f_esTitular
		where 
			euj.id_equipo = @idEquipo
			and euj.id_jugador = f_jugador;
    else
		insert into Equipo_Usuario_Jugador(
			id_equipo,
            id_jugador,
            titular
        ) values (
			@idEquipo,
            f_jugador,
            f_esTitular
        );
    end if;
end//
delimiter ;