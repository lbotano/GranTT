DROP PROCEDURE IF EXISTS obtenerTitularesEquipoUsuario;
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
        euj.titular;
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
        NOT euj.titular;
END//
DELIMITER ;