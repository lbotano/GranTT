package grantt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos {
	private static Connection conn;
		
	private static Usuario usuarioLogueado;
	private static Torneo torneoActual;
	
	public static void inicializarBd(String url, String usuario, String contraseña) {
		try{
			conn = DriverManager.getConnection(url, usuario, contraseña);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setTorneoActual(Torneo t) {
		torneoActual = t;
	}
	
	public static boolean iniciarSesion(Usuario usuario) {
            try {
                String c = new String(usuario.getContraseña());
                PreparedStatement query = 
                                conn.prepareStatement("SELECT iniciarSesion(?, ?);");
                query.setString(1, usuario.getNombre());
                query.setString(2, c);
                
                System.out.println(usuario.getNombre() + " " + c);

                ResultSet rs = query.executeQuery();
                
                
                //tuve que sacar el rs.next afuera porque no entraba al if
                rs.next();
                if(rs.getBoolean(1)) {
                    System.out.println("-" +rs.getBoolean(1) + "-");
                    usuarioLogueado = usuario;
                    return true;
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
            return false;
	}
	
	public static boolean crearUsuario(Usuario usuario, boolean admin) {
		try {
                    PreparedStatement query = conn.prepareStatement("SELECT crearUsuario(?, ?, ?);");
                    query.setString(1, usuario.getNombre());
                    query.setString(2, usuario.getContraseña());
                    query.setBoolean(3, admin);

                    ResultSet rs = query.executeQuery();
                    if(rs.next()) return rs.getBoolean(1);
		} catch(SQLException e) {
                    e.printStackTrace();
		}
		return false;
	}
	
	public static boolean usuarioNoTieneEquipo() {
		if(usuarioLogueado == null) {
			return false;
		}
		
		try {
			PreparedStatement query = conn.prepareStatement("SELECT noTieneEquipo(?);");
			query.setString(1, usuarioLogueado.getNombre());
			
			ResultSet rs = query.executeQuery();
			if(rs.next()) return rs.getBoolean(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean crearEquipo(String nombre) {
		try {
			PreparedStatement query = conn.prepareStatement("SELECT crearEquipo(?, ?);");
			query.setString(1, usuarioLogueado.getNombre());
			query.setString(2, nombre);
			
			ResultSet rs = query.executeQuery();
			
			if(rs.next()) return rs.getBoolean(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Pone la id del equipo y la lista de sus jugadores en el usuario logueado
	public static void updateEquipo() {
		// Obtiene la id del equipo del jugador
		try {
			PreparedStatement query = conn.prepareStatement("SELECT obtenerIdEquipoDeUsuario(?)");
			query.setString(1, usuarioLogueado.getNombre());
			
			ResultSet rs = query.executeQuery();
			
			if(rs.next()) usuarioLogueado.setEquipo(rs.getInt(1));
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Jugador> obtenerJugadores(){
		List<Jugador> jugadores = new ArrayList<Jugador>();
		try {
			PreparedStatement query = conn.prepareStatement("CALL obtenerJugadores()");
			
			ResultSet rs = query.executeQuery();
			
			jugadores = resultSetToJugador(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return jugadores;
	}
	
	public static List<Jugador> obtenerJugadoresEquipo() {
            List<Jugador> jugadores = new ArrayList<Jugador>();
            try {
                PreparedStatement query = conn.prepareStatement("CALL obtenerJugadoresEquipoUsuario(?)");
                query.setInt(1, usuarioLogueado.getEquipo());

                ResultSet rs = query.executeQuery();

                jugadores = resultSetToJugador(rs);
            }catch(SQLException e) {
                e.printStackTrace();
            }

            return jugadores;
	}
	
	public static List<Jugador> obtenerJugadoresDisponiblesEquipoReal(Equipo eq){
		List<Jugador> jugadores = new ArrayList<Jugador>();
		try {
                    PreparedStatement query = conn.prepareStatement("CALL obtenerJugadoresDisponiblesEquipoReal(?, ?);");
                    query.setString(1, usuarioLogueado.getNombre());
                    query.setInt(2, eq.getId());

                    ResultSet rs = query.executeQuery();

                    jugadores = resultSetToJugador(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return jugadores;
	}
	
	public static List<Jugador> obtenerJugadoresEquipoReal(Equipo eq){
            List<Jugador> jugadores = new ArrayList<Jugador>();
            try {
                PreparedStatement query = conn.prepareStatement("CALL obtenerJugadoresEquipoReal(?)");
                query.setInt(1, eq.getId());

                ResultSet rs = query.executeQuery();

                jugadores = resultSetToJugador(rs);
            }catch(SQLException e) {
                    e.printStackTrace();
            }

            return jugadores;
	}
	
	public static double obtenerPresupuesto() {
		try {
			PreparedStatement query = conn.prepareStatement("SELECT obtenerPresupuesto(?)");
			query.setString(1, usuarioLogueado.getNombre());
			
			ResultSet rs = query.executeQuery();
			
			if(rs.next()) return rs.getDouble(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static boolean comprarJugador(int idJugador) {
		try {
			PreparedStatement query = conn.prepareStatement("SELECT comprarJugador(?, ?);");
			query.setString(1, usuarioLogueado.getNombre());
			query.setInt(2, idJugador);
			
			ResultSet rs = query.executeQuery();
			
			if(rs.next()) return rs.getBoolean(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean venderJugador(int idJugador) {
		try {
			PreparedStatement query = conn.prepareStatement("SELECT venderJugador(?, ?)");
			query.setString(1, usuarioLogueado.getNombre());
			query.setInt(2, idJugador);
			
			ResultSet rs = query.executeQuery();
			
			if(rs.next()) return rs.getBoolean(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public static List<Equipo> obtenerEquiposReales(){
		List<Equipo> equipos = new ArrayList<Equipo>();
		try {
			PreparedStatement query = conn.prepareStatement("CALL obtenerEquiposReales()");
			
			ResultSet rs = query.executeQuery();
			
			while(rs.next()) {
				Equipo e = new Equipo(rs.getInt("id_equipoReal"), rs.getString("nombre"), rs.getInt("calidad"));
				equipos.add(e);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return equipos;
	}
	
	public static boolean esAdmin() {
		try {
                    PreparedStatement query = conn.prepareStatement("SELECT esAdmin(?)");
                    query.setString(1, usuarioLogueado.getNombre());

                    ResultSet rs = query.executeQuery();

                    if(rs.next()) return rs.getBoolean(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static List<Integer> obtenerIdTorneos() {
		List<Integer> ids = new ArrayList<Integer>();
		try {
			PreparedStatement query = conn.prepareStatement("CALL obtenerIdTorneos()");
			
			ResultSet rs = query.executeQuery();
			while(rs.next()) {
				ids.add(rs.getInt("id_torneo"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}
	
	public static int obtenerUltimoTorneo() {
		try {
			PreparedStatement query = conn.prepareStatement("SELECT obtenerUltimoTorneo()");
			
			ResultSet rs = query.executeQuery();
			if(rs.next()) return rs.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static boolean seJugoTorneo(int idTorneo) {
		try {
			PreparedStatement query = conn.prepareStatement("SELECT seJugoTorneo(?)");
			query.setInt(1, idTorneo);
			
			ResultSet rs = query.executeQuery();
			if(rs.next()) return rs.getBoolean(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void crearTorneo() {
		int idTorneo = -1;
		try {
			PreparedStatement query = conn.prepareStatement("SELECT crearTorneo()");
			
			ResultSet rs = query.executeQuery();
			
			if(rs.next()) idTorneo = rs.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		if(idTorneo != -1) {
			setTorneoActual(new Torneo(idTorneo));
			torneoActual.generarFixture();
			guardarFixtureDeTorneoActual();
		}else {
			System.err.println("Error al crear torneo");
		}
	}
	
	public static void subirValorJugador(Jugador jugador, int suma) {
		try {
			PreparedStatement query = conn.prepareStatement("CALL subirValorJugador(?, ?)");
			query.setInt(1, jugador.getId());
			query.setInt(2, suma);
			
			query.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Partido> obtenerPartidosJugados(){
		List<Partido> partidos = new ArrayList<Partido>();
		try {
			PreparedStatement query = conn.prepareStatement("CALL obtenerPartidosJugados(?)");
			query.setInt(1, torneoActual.getId());
			
			ResultSet rs = query.executeQuery();
			
			while(rs.next()) {
				Equipo equipoLocal = torneoActual.getEquiposPorId(rs.getInt("equipo_local"));
				Equipo equipoVisitante = torneoActual.getEquiposPorId(rs.getInt("equipo_visitante"));
				int jornada = rs.getInt("jornada");
				int golesLocal = rs.getInt("goles_local");
				int golesVisitante = rs.getInt("goles_visitante");
				
				partidos.add(new Partido(equipoLocal, equipoVisitante, jornada, golesLocal, golesVisitante));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return partidos;
	}
	
	public static List<Partido> obtenerPartidosPendientes(){
		List<Partido> partidos = new ArrayList<Partido>();
		try {
			PreparedStatement query = conn.prepareStatement("CALL obtenerPartidosPendientes(?)");
			query.setInt(1,  torneoActual.getId());
			
			ResultSet rs = query.executeQuery();
			
			while(rs.next()) {
				Equipo equipoLocal = torneoActual.getEquiposPorId(rs.getInt("equipo_local"));
				Equipo equipoVisitante = torneoActual.getEquiposPorId(rs.getInt("equipo_visitante"));
				int jornada = rs.getInt("jornada");
				
				partidos.add(new Partido(equipoLocal, equipoVisitante, jornada));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return partidos;
	}
	
	public static void guardarFixtureDeTorneoActual() {
            try {
                for(Partido p : torneoActual.getPartidos()) {
                    PreparedStatement query = conn.prepareStatement("CALL anadirPartidoPendiente(?, ?, ?, ?)");
                    query.setInt(1, torneoActual.getId());
                    query.setInt(2, p.getJornada());
                    query.setInt(3, p.getEquipoLocal().getId());
                    query.setInt(4, p.getEquipoVisitante().getId());
                    query.execute();
                }
            }catch (SQLException e){
                    e.printStackTrace();
            }
	}
	
 	private static List<Jugador> resultSetToJugador(ResultSet rs){
            List<Jugador> list = new ArrayList<Jugador>();
            try {
                while(rs.next()) {
                    Jugador j = new Jugador(
                        rs.getInt("id_jugador"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        Jugador.intToPosicion(rs.getInt("id_posicion")),
                        rs.getFloat("valor"),
                        rs.getInt("diasLesionado"),
                        rs.getInt("diasSuspendido"),
                        rs.getInt("dorsal")
                    );
                    list.add(j);
                }
            } catch(SQLException e) {
                    e.printStackTrace();
            }

            return list;
	}
}