-- Para quitar una regla de mysql que no permite hacer
-- order by con columnas que no estan en el group by
SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));

DROP PROCEDURE IF EXISTS obtenerTitularesEquipoUsuario;
DELIMITER //
CREATE PROCEDURE obtenerTitularesEquipoUsuario(p_usuario NVARCHAR(50))
BEGIN  
	select j.* 
    from
		Equipo_Usuario_Jugador euj,
		Jugador j,
		Usuario u,
		Equipo_Usuario eu
	where 
		u.nombre = p_usuario
		and u.id_equipo = eu.id_equipo
		and euj.id_equipo = eu.id_equipo
		and euj.id_jugador = j.id_jugador
		and euj.titular = true;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS obtenerSuplentesEquipoUsuario;
DELIMITER //
CREATE PROCEDURE obtenerSuplentesEquipoUsuario(p_usuario NVARCHAR(50))
BEGIN
	select j.* 
    from
		Equipo_Usuario_Jugador euj,
		Jugador j,
		Usuario u,
		Equipo_Usuario eu
	where 
		u.nombre = p_usuario
		and u.id_equipo = eu.id_equipo
		and euj.id_equipo = eu.id_equipo
		and euj.id_jugador = j.id_jugador
		and euj.titular = false;
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

drop function if exists validarCantTitulares;
delimiter //
create function validarCantTitulares(
	f_usuario nvarchar(50)
) returns int deterministic
begin
	select u.id_equipo into @equipo from
		Usuario u
	where
		u.nombre = f_usuario
	limit 1;
    
	select count(*) into @cant from 
		Equipo_Usuario_Jugador euj
    inner join
		Jugador j
    on euj.id_jugador = j.id_jugador
	where 
		euj.id_equipo = @equipo
		and (j.diasLesionado = 0 or j.diasLesionado is null)
		and (j.partidosSuspendido is null or j.partidosSuspendido < 1)
        and euj.titular = 1;
	if @cant = 11 then
		return true;
	else
		return false;
	end if;
end//
delimiter ;

drop function if exists validarCantSuplentes;
delimiter //
create function validarCantSuplentes(
	f_usuario nvarchar(50)
) returns int deterministic
begin
	select u.id_equipo into @equipo
    from
		Usuario u
	where
		u.nombre = f_usuario
	limit 1;
    
	select count(*) into @cant from 
		Equipo_Usuario_Jugador euj
    inner join
		Jugador j
    on euj.id_jugador = j.id_jugador
	where 
		euj.id_equipo = @equipo
        and euj.titular = 0;
	if @cant = 4 then
		return true;
	else
		return false;
	end if;
end//
delimiter ;

drop function if exists validarEquipo;
delimiter //
create function validarEquipo (
	f_usuario nvarchar(50)
) returns boolean deterministic
begin
	if(
		select validarCantTitulares(f_usuario) = 1
		and (select validarCantSuplentes(f_usuario) = 1)
	) then
		return true;
    else
		return false;
    end if;
end//
delimiter ;

drop procedure if exists obtenerTopUsuarios;
delimiter //
create procedure obtenerTopUsuarios()
begin
	select u.nombre as nombreUsuario, obtenerValorTotalEquipoUsuario(u.nombre) as valorTotal
    from
		Usuario u
    where 
        (validarEquipo(u.nombre) = 1)
	order by valorTotal desc;
end//
delimiter ;

CALL obtenerTopUsuarios();