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


drop procedure if exists resetDatabase;
delimiter //
create procedure resetDatabase()
begin
    delete from ocurrencia;
    delete from partido;
    delete from equipo_local;
    delete from equipo_visitante;
	delete from Torneo;
    update jugador 
    set 
		valor = 1000,
        diasLesionado = 0,
        partidosSuspendido = 0;
	delete from equipo_usuario_jugador;
    delete from usuario;
    delete from equipo_usuario;
end//
DELIMITER ;


drop function if exists validarCantTitulares;
delimiter //
create function validarCantTitulares(
	f_usuario nvarchar(50)
) returns int deterministic
begin
	select u.id_equipo into @equipo from
		usuario u
	where
		u.nombre = f_usuario
	limit 1;
    
	select count(*) into @cant from 
		equipo_usuario_jugador euj
    inner join
		jugador j
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

-- select validarCantTitulares('asd')

drop function if exists validarCantSuplentes;
delimiter //
create function validarCantSuplentes(
	f_usuario nvarchar(50)
) returns int deterministic
begin
	select u.id_equipo into @equipo from
		usuario u
	where
		u.nombre = f_usuario
	limit 1;
    
	select count(*) into @cant from 
		equipo_usuario_jugador euj
    inner join
		jugador j
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

-- para validar si el equipo puede ser utilizado
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
	select u.nombre as nombreUsuario, sum(j.valor) as valorTotal from
		usuario u,
		jugador j,
		equipo_usuario_jugador euj,
        equipo_usuario eu
    where j.id_jugador = euj.id_jugador
		and u.id_equipo = euj.id_equipo
        and eu.id_equipo = u.id_equipo
        and (validarEquipo(u.nombre) = 1)
	group by u.nombre
	order by j.valor desc;
end//
delimiter ;

drop function if exists obtenerValorEquipo;
delimiter //
create function obtenerValorEquipo(
	f_usuario nvarchar(50)
) returns int deterministic
begin
	select validarEquipo(f_usuario) into @esValido;
    if(@esValido = 1) then
		select u.id_equipo into @equipo from
			usuario u
		where
			u.nombre = f_usuario
		limit 1;
        
        select sum(j.valor) into @resultado from 
			equipo_usuario_jugador euj,
			jugador j
		where
			j.id_jugador = euj.id_jugador
			and euj.id_equipo = @equipo
            limit 1;
		return @resultado;
    else
		return 0;
    end if;
end//
delimiter ;

-- call obtenerPartidosPendientes();
-- select obtenerValorEquipo('asd')
-- call obtenerTopUsuarios()
-- call resetDatabase()
-- call obtenerJugadoresEquipoUsuario('asd');