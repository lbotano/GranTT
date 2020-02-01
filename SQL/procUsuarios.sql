drop function if exists iniciarSesion;
DELIMITER //
create function iniciarSesion(f_usuario nvarchar(50), f_contraseña nvarchar(50))
returns bool
deterministic
begin 
	SELECT COUNT(*)
    FROM GRANTT.Usuario
    WHERE 
		nombre = f_usuario AND
        contraseña = sha2(f_contraseña, 512) 
	INTO @cantUsuario;
	
	return @cantUsuario = 1;
end//

DELIMITER ;

drop function if exists existeCuenta;
DELIMITER //
create function existeCuenta(f_usuario nvarchar(50))
returns bool
deterministic
begin
	SELECT COUNT(*)
    FROM GRANTT.Usuario
    WHERE
		nombre = f_usuario
	INTO @cantUsuario;
    
    return @cantUsuario = 1;
end//

DELIMITER ;

drop function if exists crearUsuario;
DELIMITER //
create function crearUsuario(f_usuario nvarchar(50), f_contraseña nvarchar(50), f_esAdmin bool, dni NCHAR(10))
returns bool
deterministic
begin
	-- Verficar que el usuario no existe
    if existeCuenta(f_usuario) then
		return false;
	end if;
	
    -- Crear usuario
	INSERT INTO GRANTT.Usuario
	VALUES 
		(f_usuario, sha2(f_contraseña, 512), null, f_esAdmin, dni);
	return true;
end//
DELIMITER ;

drop function if exists esAdmin;
DELIMITER //
create function esAdmin(f_usuario NVARCHAR(50))
returns boolean
deterministic
begin
	SELECT admin
    FROM GRANTT.Usuario
    WHERE nombre = f_usuario
    INTO @esAdmin;
    
    return @esAdmin;
end//
DELIMITER ;
/*
drop procedure if exists seleccionarTopUsuarios;
DELIMITER //
create procedure seleccionarTopUsuarios()
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
*/