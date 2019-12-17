drop function if exists noTieneEquipo;
DELIMITER //
create function noTieneEquipo(f_usuario nvarchar(50))
returns bool
DETERMINISTIC
begin
	SELECT id_equipo
    FROM GRANTT.Usuario
    WHERE nombre = f_usuario
    INTO @equipo;
    
    return @equipo IS NULL;
end//

DELIMITER ;

drop function if exists crearEquipo;
DELIMITER //
create function crearEquipo(f_usuario nvarchar(50), f_nombre nvarchar(50))
returns bool
DETERMINISTIC
begin
	if not noTieneEquipo(f_usuario) then
		return false;
	end if;
    
	INSERT INTO GRANTT.Equipo_Usuario
		(nombre, presupuesto)
    VALUES
		(f_nombre, 15000);
        
	UPDATE GRANTT.Usuario
    SET id_equipo = LAST_INSERT_ID()
    WHERE nombre = f_usuario;
    
    return true;
end//
DELIMITER ;
drop function if exists obtenerIdEquipoDeUsuario;
DELIMITER //
create function obtenerIdEquipoDeUsuario(id_usuario NVARCHAR(50))
returns INTEGER
deterministic
begin
	SELECT
		id_equipo
	FROM
		GRANTT.Usuario
	WHERE
		nombre = id_usuario
	INTO
		@id;
	
    return @id;
end//
DELIMITER ;

drop function if exists obtenerPresupuesto;
DELIMITER //
create function obtenerPresupuesto(f_usuario NVARCHAR(50))
returns double
deterministic
begin
	SElECT presupuesto
    FROM
		GRANTT.Usuario u,
        GRANTT.Equipo_Usuario eu
	WHERE
		u.id_equipo = eu.id_equipo AND
        u.nombre = f_usuario
	INTO @presupuesto;
    
    return @presupuesto;
end//
DELIMITER ;
