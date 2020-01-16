package grantt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import editor.ItemJugador;
import grantt.Ocurrencia.TipoOcurrencia;

//import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

public class BaseDeDatos {
	private static Connection conn;
		
	public static Usuario usuarioLogueado;
	private static Torneo torneoActual = null;
	
	public static void inicializarBd() {
		try{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GRANTT?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=utf-8", "root", "12345");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setTorneoActual(int id) {
		torneoActual = new Torneo(id, obtenerTorneo(id).getJornada());
	}
	
	public static boolean iniciarSesion(Usuario usuario) {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			query = conn.prepareStatement("SELECT iniciarSesion(?, ?);");
			query.setString(1, usuario.getNombre());
			query.setString(2, usuario.getContraseña());
			
			rs = query.executeQuery();
			
			if(rs.next() && rs.getBoolean(1)) {
				usuarioLogueado = usuario;
				return true;
			}
		}catch(SQLException e) {
			System.err.println("Error con la base de datos");
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return false;
	}
	
	public static boolean crearUsuario(Usuario usuario, String dni) {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			query = conn.prepareStatement("SELECT crearUsuario(?, ?, ?);");
			query.setString(1, usuario.getNombre());
			query.setString(2, usuario.getContraseña());
			query.setBoolean(3, false);
			query.setString(4, dni);
			
			rs = query.executeQuery();
			if(rs.next()) return rs.getBoolean(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return false;
	}
	
	public static boolean crearUsuario(Usuario usuario, boolean esAdmin, String dni) {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			query = conn.prepareStatement("SELECT crearUsuario(?, ?, ?, ?);");
			query.setString(1, usuario.getNombre());
			query.setString(2, usuario.getContraseña());
			query.setBoolean(3, esAdmin);
			query.setString(4, dni);
			
			rs = query.executeQuery();
			if(rs.next()) return rs.getBoolean(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return false;
	}
	
	public static boolean usuarioNoTieneEquipo() {
		if(usuarioLogueado == null) {
			return false;
		}
		
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		
		try {
			query = conn.prepareStatement("SELECT noTieneEquipo(?);");
			query.setString(1, usuarioLogueado.getNombre());
			
			rs = query.executeQuery();
			if(rs.next()) return rs.getBoolean(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		
		return false;
	}
	
	public static boolean crearEquipo(String nombre) {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			query = conn.prepareStatement("SELECT crearEquipo(?, ?);");
			query.setString(1, usuarioLogueado.getNombre());
			query.setString(2, nombre);
			
			rs = query.executeQuery();
			
			if(rs.next()) return rs.getBoolean(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return false;
	}
	
	// Pone la id del equipo y la lista de sus jugadores en el usuario logueado
	public static void updateEquipo() {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		// Obtiene la id del equipo del jugador
		try {
			query = conn.prepareStatement("SELECT obtenerIdEquipoDeUsuario(?)");
			query.setString(1, usuarioLogueado.getNombre());
			
			rs = query.executeQuery();
			
			if(rs.next()) usuarioLogueado.setEquipo(rs.getInt(1));
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
	}
	
	/*public static List<Jugador> obtenerJugadores(){
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		List<Jugador> jugadores = new ArrayList<Jugador>();
		try {
			query = conn.prepareStatement("CALL obtenerJugadores()");
			
			rs = query.executeQuery();
			
			jugadores = resultSetToJugador(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return jugadores;
	}*/
	
	public static List<Jugador> obtenerJugadoresEquipo(){
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		List<Jugador> jugadores = new ArrayList<Jugador>();
		System.out.println("Hola dario jajajxd " + usuarioLogueado.getEquipo());
		try {
			query = conn.prepareStatement("CALL obtenerJugadoresEquipoUsuario(?)");
			query.setString(1, usuarioLogueado.getNombre());
			
			rs = query.executeQuery();
			
			jugadores = resultSetToJugador(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		
		System.out.println(jugadores.size());
		
		return jugadores;
	}
	
	public static List<Jugador> obtenerJugadoresDisponiblesEquipoReal(int id){
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		List<Jugador> jugadores = new ArrayList<Jugador>();
		try {
			query = conn.prepareStatement("CALL obtenerJugadoresDisponiblesEquipoReal(?, ?);");
			query.setString(1, usuarioLogueado.getNombre());
			query.setInt(2, id);
			
			rs = query.executeQuery();
			
			jugadores = resultSetToJugador(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		
		return jugadores;
	}
	
	public static List<Jugador> obtenerJugadoresEquipoReal(Equipo equipo){
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		List<Jugador> jugadores = new ArrayList<Jugador>();
		try {
			query = conn.prepareStatement("CALL obtenerJugadoresEquipoReal(?)");
			query.setInt(1, equipo.getId());
			
			rs = query.executeQuery();
			
			jugadores = resultSetToJugador(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		
		return jugadores;
	}
	
	public static double obtenerPresupuesto() {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			query = conn.prepareStatement("SELECT obtenerPresupuesto(?)");
			query.setString(1, usuarioLogueado.getNombre());
			
			rs = query.executeQuery();
			
			if(rs.next()) return rs.getDouble(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		
		return -1;
	}
	
	public static boolean comprarJugador(int idJugador) {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			query = conn.prepareStatement("SELECT comprarJugador(?, ?);");
			query.setString(1, usuarioLogueado.getNombre());
			query.setInt(2, idJugador);
			
			rs = query.executeQuery();
			
			if(rs.next()) return rs.getBoolean(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		
		return false;
	}
	
	public static boolean venderJugador(int idJugador) {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			query = conn.prepareStatement("SELECT venderJugador(?, ?)");
			query.setString(1, usuarioLogueado.getNombre());
			query.setInt(2, idJugador);
			
			rs = query.executeQuery();
			
			if(rs.next()) return rs.getBoolean(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		
		return false;
	}

	public static List<Equipo> obtenerEquiposReales(){
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		List<Equipo> equipos = new ArrayList<Equipo>();
		try {
			query = conn.prepareStatement("CALL obtenerEquiposReales()");
			
			rs = query.executeQuery();
			
			while(rs.next()) {
				Equipo e = new Equipo(rs.getInt("id_equipoReal"), rs.getString("nombre"));
				equipos.add(e);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		
		return equipos;
	}
	
	public static boolean esAdmin() {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			query = conn.prepareStatement("SELECT esAdmin(?)");
			query.setString(1, usuarioLogueado.getNombre());
			
			rs = query.executeQuery();
			
			if(rs.next()) return rs.getBoolean(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return false;
	}
	
	public static Torneo obtenerTorneo(int id) {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		int jornada = -1;
		try {
			query = conn.prepareStatement("SELECT obtenerJornadaTorneo(?)");
			query.setInt(1, id);
			
			rs = query.executeQuery();
			if(rs.next()) jornada = rs.getInt(1); 
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		
		return new Torneo(id, jornada);
	}
	
	public static List<Integer> obtenerIdTorneos() {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		List<Integer> ids = new ArrayList<Integer>();
		try {
			query = conn.prepareStatement("CALL obtenerIdTorneos()");
			
			rs = query.executeQuery();
			while(rs.next()) {
				ids.add(rs.getInt("id_torneo"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return ids;
	}
	
	public static int obtenerUltimoTorneo() {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			query = conn.prepareStatement("SELECT obtenerUltimoTorneo()");
			
			rs = query.executeQuery();
			if(rs.next()) return rs.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return -1;
	}
	
	public static boolean seJugoTorneo(int idTorneo) {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			query = conn.prepareStatement("SELECT seJugoTorneo(?)");
			query.setInt(1, idTorneo);
			
			rs = query.executeQuery();
			if(rs.next()) return rs.getBoolean(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return false;
	}
	
	public static void crearTorneo() {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		
		int idTorneo = -1;
		try {
			query = conn.prepareStatement("SELECT crearTorneo()");
			
			rs = query.executeQuery();
			
			if(rs.next()) idTorneo = rs.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		
		if(idTorneo != -1) {
			setTorneoActual(idTorneo);
		}else {
			System.err.println("Error al crear torneo");
		}
	}
	
	public static void subirValorJugador(Jugador jugador, int suma) {
		inicializarBd();
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement("CALL subirValorJugador(?, ?)");
			query.setInt(1, jugador.getId());
			query.setInt(2, suma);
			
			query.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
	}
	
	public static List<Partido> obtenerPartidosJugados(){
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		List<Partido> partidos = new ArrayList<Partido>();
		
		if(torneoActual != null) {
			try {
				query = conn.prepareStatement("CALL obtenerPartidosJugados(?)");
				query.setInt(1, torneoActual.getId());
				
				rs = query.executeQuery();
				
				while(rs.next()) {
					Equipo equipoLocal = torneoActual.getEquiposPorId(rs.getInt("equipo_local"));
					Equipo equipoVisitante = torneoActual.getEquiposPorId(rs.getInt("equipo_visitante"));
					int id = rs.getInt("id_partido");
					int jornada = rs.getInt("jornada");
					int golesLocal = rs.getInt("goles_local");
					int golesVisitante = rs.getInt("goles_visitante");
					
					partidos.add(new Partido(id, equipoLocal, equipoVisitante, jornada, golesLocal, golesVisitante));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				if(conn != null) {
					try{conn.close();}catch(SQLException e) {}
				}
				
				if(query != null) {
					try {query.close();}catch(SQLException e) {}
				}
				
				if(rs != null) {
					try {query.close();}catch(SQLException e) {}
				}
			}
		}
		return partidos;
	}
	
	public static void setUltimoTorneo() {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try{
			query = conn.prepareStatement("CALL obtenerUltimoTorneo()");
			rs = query.executeQuery();
			if(rs.next()) {
				torneoActual = new Torneo(rs.getInt("id_torneo"), rs.getInt("jornada"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {conn.close();}catch(SQLException e) {}
			}
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			if(rs != null) {
				try {rs.close();}catch(SQLException e) {}
			}
		}
	}
	
	public static List<Partido> obtenerPartidosPendientes(){
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		List<Partido> partidos = new ArrayList<Partido>();
		if(torneoActual != null) {
			try {
				query = conn.prepareStatement("CALL obtenerPartidosPendientes(?)");
				query.setInt(1,  torneoActual.getId());
				
				rs = query.executeQuery();
				
				while(rs.next()) {
					int id = rs.getInt("id_partido");
					Equipo equipoLocal = torneoActual.getEquiposPorId(rs.getInt("equipo_local"));
					Equipo equipoVisitante = torneoActual.getEquiposPorId(rs.getInt("equipo_visitante"));
					int jornada = rs.getInt("jornada");
					
					partidos.add(new Partido(id, equipoLocal, equipoVisitante, jornada));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				if(conn != null) {
					try{conn.close();}catch(SQLException e) {}
				}
				
				if(query != null) {
					try {query.close();}catch(SQLException e) {}
				}
				
				if(rs != null) {
					try {query.close();}catch(SQLException e) {}
				}
			}
		}
		return partidos;
	}
	
	public static int getCalidad(Equipo equipo) {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		
		int resultado = -1;
		try{
			query = conn.prepareStatement("SELECT obtenerValorTotalEquipo(?)");
			query.setInt(1, equipo.getId());
			
			rs = query.executeQuery();
			if(rs.next()) resultado = (int) Math.ceil(rs.getInt(1) / 4000);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return resultado;
	}
	
	public static List<Torneo> obtenerTorneos(){
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		
		List<Torneo> torneos = new ArrayList<Torneo>();
		try {
			query = conn.prepareStatement("CALL obtenerTorneos()");
			rs = query.executeQuery();
			while(rs.next()) {
				torneos.add(new Torneo(rs.getInt("id_torneo"), rs.getInt("jornada")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return torneos;
	}
	
	public static Torneo obtenerTorneoActual() {
		return torneoActual;
	}
	
	public static void pasarJornada() {
		inicializarBd();
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement("CALL pasarJornada(?)");
			query.setInt(1, torneoActual.getId());
			
			query.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		
		torneoActual.pasarJornada();
	}
	
	public static int obtenerJornada() {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		if(torneoActual != null) {
			try {
				query = conn.prepareStatement("SELECT obtenerJornadaTorneo(?)");
				query.setInt(1, torneoActual.getId());
				
				rs = query.executeQuery();
				if(rs.next()) return rs.getInt(1);
			}catch (SQLException e) {
				e.printStackTrace();
			}finally {
				if(conn != null) {
					try{conn.close();}catch(SQLException e) {}
				}
				
				if(query != null) {
					try {query.close();}catch(SQLException e) {}
				}
				
				if(rs != null) {
					try {query.close();}catch(SQLException e) {}
				}
			}
		}
		return -1;
	}
	
	public static void anadirPartidoPendiente(Partido p) {
		inicializarBd();
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement("CALL anadirPartidoPendiente(?, ?, ?, ?)");
			query.setInt(1, torneoActual.getId());
			query.setInt(2, p.getJornada());
			query.setInt(3, p.getEquipoLocal().getId());
			query.setInt(4, p.getEquipoVisitante().getId());
			
			query.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
	}
	
	public static List<Partido> obtenerPartidosSinJugarDeHoy() {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		
		List<Partido> partidos = new ArrayList<Partido>();
		try {
			query = conn.prepareStatement("CALL obtenerPartidosSinJugarDeHoy(?, ?)");
			query.setInt(1, torneoActual.getId());
			query.setInt(2, torneoActual.getJornada());
			
			rs = query.executeQuery();
			
			while(rs.next()) {
				Equipo local 		= torneoActual.getEquiposPorId(rs.getInt("id_local"));
				Equipo visitante	= torneoActual.getEquiposPorId(rs.getInt("id_visitante"));
				Partido partido = new Partido(rs.getInt("id_partido"), local, visitante, rs.getInt("jornada"));
				partidos.add(partido);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return partidos;
	}
	
	public static void ponerResultadoDePartido(Partido partido) {
		if(partido.getId() < 0) {
			System.err.println("ERROR FATAL: Partido con id nula");
			return;
		}
		
		inicializarBd();
		PreparedStatement query = null;
		
		try{
			query = conn.prepareStatement("CALL ponerResultadoDePartido(?, ?, ?)");
			query.setInt(1, partido.getId());
			query.setInt(2, partido.getGolesLocal());
			query.setInt(3, partido.getGolesVisitante());
			
			query.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
	}
	
	public static int obtenerIdJugadorRandom(Equipo equipo) {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		
		try {
			query = conn.prepareStatement("SELECT obtenerJugadorRandom(?)");
			query.setInt(1, equipo.getId());
			
			rs = query.executeQuery();
			if(rs.next()) return rs.getInt(1);
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try{conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return -1;
	}
	
	public static List<Usuario> obtenerTopUsuarios(){
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try{
			query = conn.prepareStatement("CALL seleccionarTopUsuarios()");
			rs = query.executeQuery();
			while(rs.next()) {
				Usuario usuario = new Usuario(rs.getString("nombreUsuario"), rs.getString("nombreEquipo"));
				usuarios.add(usuario);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return usuarios;
	}
	
	public static void actualizarDiasSuspendido(Jugador j) {
		inicializarBd();
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement("CALL actualizarDiasSuspendido(?)");
			query.setInt(1, j.getId());
			query.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
	}
	
	public static void ponerTarjetaAmarilla(Jugador j, Partido p) {
		inicializarBd();
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement("CALL ponerTarjetaAmarilla(?, ?)");
			query.setInt(1, j.getId());
			query.setInt(2, p.getId());
			query.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
	}
	
	public static List<Ocurrencia> obtenerOcurrencias(int idPartido){
		List<Ocurrencia> ocurrencias = new ArrayList<Ocurrencia>();
		
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			query = conn.prepareStatement("CALL obtenerOcurrencias(?)");
			query.setInt(1, idPartido);
			
			rs = query.executeQuery();
			
			while(rs.next()) {
				Ocurrencia.TipoOcurrencia tipo = null;
				switch(rs.getInt("ocurrencia")) {
					case 1:
						tipo = TipoOcurrencia.GOL;
						break;
					case 2:
						tipo = TipoOcurrencia.LESION;
						break;
					case 3:
						tipo = TipoOcurrencia.AMARILLA;
						break;
					case 4:
						tipo = TipoOcurrencia.ROJA;
						break;
				}
				
				Ocurrencia ocurrencia = new Ocurrencia(
					rs.getString("nombre"), 
					tipo,
					rs.getBoolean("esLocal")
				);
				ocurrencias.add(ocurrencia);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		
		return ocurrencias;
	}
	
	public static boolean tieneTarjetaAmarilla(Jugador j) {
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			query = conn.prepareStatement("SELECT tieneTarjetaAmarilla(?)");
			query.setInt(1, j.getId());
			
			rs = query.executeQuery();
			
			if(rs.next()) {
				return rs.getBoolean(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return false;
	}
	
	public static boolean tieneTarjetaRoja(Jugador j) {		
		inicializarBd();
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			query = conn.prepareStatement("SELECT tieneTarjetaRoja(?)");
			query.setInt(1, j.getId());
			
			rs = query.executeQuery();
			
			if(rs.next()) {
				return rs.getBoolean(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {conn.close();}catch(SQLException e) {}
			}
			
			if(query != null) {
				try {query.close();}catch(SQLException e) {}
			}
			
			if(rs != null) {
				try {query.close();}catch(SQLException e) {}
			}
		}
		return false;
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
					rs.getInt("partidosSuspendido"),
					rs.getInt("dorsal")
				);
				
				list.add(j);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
 	
 	public static List<Jugador> getSuplentesEquipo() {
 		inicializarBd();
 		List<Jugador> jugadores;
 		PreparedStatement query = null;
 		ResultSet rs;
		try {
			query = conn.prepareStatement("CALL obtenerSuplentesEquipoUsuario(?)");
			query.setString(1, usuarioLogueado.getNombre());
			rs = query.executeQuery();
			jugadores = resultSetToJugador(rs);
			try {conn.close();}catch(SQLException e) {}
			return jugadores;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
 	}
 	
 	public static List<Jugador> getTitularesEquipo() {
 		inicializarBd();
 		PreparedStatement query = null;
 		ResultSet rs;
		try {
			query = conn.prepareStatement("CALL obtenerTitularesEquipoUsuario(?)");
			query.setString(1, usuarioLogueado.getNombre());
			rs = query.executeQuery();
			List<Jugador> res = resultSetToJugador(rs); 
			try {conn.close();}catch(SQLException e) {}
			return res;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
 	}
 	
 	public static void guardarJugador(ItemJugador j, boolean titular) {
 		inicializarBd();
 		PreparedStatement sp = null;
 		try {
 			sp = conn.prepareStatement("CALL actualizarJugadorEquipo(?, ?, ?)");
 			System.out.println(j.getNombre() + " " + j.getId() + " " + titular);
 			sp.setString(1, usuarioLogueado.getNombre());
 			sp.setInt(2, j.getId());
 			sp.setBoolean(3, titular);
 			sp.execute();
 			try {conn.close();} catch(Exception e) {}
 		} catch(Exception e) {
 			e.printStackTrace();
 		}
 	}
 	
 	public static int obtenerValorEquipoUsuario(String usuario) {
 		inicializarBd();
 		int valor = 0;
 		PreparedStatement query = null;
 		ResultSet rs = null;
 		try {
 			query = conn.prepareStatement("SELECT obtenerValorTotalEquipoUsuario(?)");
 			query.setString(1, usuario);
 			rs = query.executeQuery();
 			
 			while(rs.next()) {
 				valor = rs.getInt(1);
 				System.out.println("HOLAxd " + usuario + " " + valor);
 			}
 			
 			try {conn.close();}catch(Exception e) {}
 		} catch(Exception e) {
 			e.printStackTrace();
 		}
 		
 		return valor;
 	}
 	
 	public static void jugarDiaSiguiente() {
 		inicializarBd();
 		PreparedStatement query = null;
 		try {
 			query = conn.prepareStatement("CALL jugarDiaSiguiente()");
 			query.execute();
 			
 			try {conn.close();}catch(Exception e) {}
 		} catch(Exception e) {
 			e.printStackTrace();
 		}
 	}
}