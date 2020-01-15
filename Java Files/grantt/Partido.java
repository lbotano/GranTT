package grantt;

import java.util.ArrayList;
import java.util.List;

import grantt.Ocurrencia.TipoOcurrencia;


public class Partido {
	private int id;
	
	private Equipo equipoLocal;
	private Equipo equipoVisitante;
	
	private int golesLocal = -1;
	private int golesVisitante = -1;
	
	private int jornada;
	
	private final float umbralRandom = 0.2f;
	private final int PUNTOS_POR_GOL = 100;
	
	private final float PROBABILIDAD_TARJETA_ROJA = 0.2f;
	
	private List<Jugador> jugadoresTarjeteados;
	
	public Partido(Equipo equipoLocal, Equipo equipoVisitante, int jornada) {
		this.jugadoresTarjeteados = new ArrayList<Jugador>();
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.jornada = jornada;
	}
	
	public Partido(int id, Equipo equipoLocal, Equipo equipoVisitante, int jornada) {
		this.jugadoresTarjeteados = new ArrayList<Jugador>();
		this.id = id;
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.jornada = jornada;
	}
	
	public Partido(int id, Equipo equipoLocal, Equipo equipoVisitante, int jornada, int golesLocal, int golesVisitante) {
		this.id = id;
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.jornada = jornada;
		this.golesLocal = golesLocal;
		this.golesVisitante = golesVisitante;
	}
	
	public Equipo getEquipoLocal() {
		return this.equipoLocal;
	}
	
	public Equipo getEquipoVisitante() {
		return this.equipoVisitante;
	}
	
	public int getGolesLocal() {
		return this.golesLocal;
	}
	
	public int getGolesVisitante() {
		return this.golesVisitante;
	}
	
	public int getJornada() {
		return this.jornada;
	}
	
	public int getId() {
		return this.id;
	}
	
	private boolean estaTarjeteado(Jugador jugador) {
		for(Jugador j : jugadoresTarjeteados) {
			if(j.getId() == jugador.getId()) {
				return true;
			}
		}
		return false;
	}
	
	private Jugador obtenerJugadorRandom(Equipo equipo) {
		List<Jugador> jugadores = BaseDeDatos.obtenerJugadoresEquipoReal(equipo);
		int idJugador = BaseDeDatos.obtenerIdJugadorRandom(equipo);
		Jugador jugador = null;
		for(Jugador j : jugadores) {
			if(j.getId() == idJugador) {
				jugador = j;
				break;
			}
		}
		return jugador;
	}
	
	private void calcularLesionados(int cantidadLesionados, Equipo equipo) {
		for(int i = 0; i < cantidadLesionados; i++) {
			Jugador lesionado = obtenerJugadorRandom(equipo);
			BaseDeDatos.lesionarJugador(lesionado);
			
			// Guarda la ocurrencia
			Ocurrencia ocurrencia = new Ocurrencia(lesionado, TipoOcurrencia.LESION, this.getId());
			BaseDeDatos.ponerOcurrencia(ocurrencia);
		}
	}
	
	private void calcularTarjeteados(int cantidadTarjetas, Equipo equipo) {
		for(int i = 0; i < cantidadTarjetas; i++) {
			Jugador tarjeteado = obtenerJugadorRandom(equipo);
			
			TipoOcurrencia tipo = null;
			if(estaTarjeteado(tarjeteado) || Math.random() < PROBABILIDAD_TARJETA_ROJA) {
				// Tarjeta roja
				BaseDeDatos.ponerOcurrencia(new Ocurrencia(tarjeteado, TipoOcurrencia.ROJA, this.getId()));
				tipo = TipoOcurrencia.ROJA;
			}else {
				// Tarjeta amarilla
				BaseDeDatos.ponerTarjetaAmarilla(tarjeteado, this);
				tipo = TipoOcurrencia.AMARILLA;
			}
			
			// Guarda la ocurrencia
			Ocurrencia ocurrencia = new Ocurrencia(tarjeteado, tipo, this.getId());
			BaseDeDatos.ponerOcurrencia(ocurrencia);
		}
	}
	
	private void actualizarSuspensiones() {
		List<Jugador> jugadores = BaseDeDatos.obtenerJugadoresEquipoReal(equipoLocal);
		jugadores.addAll(BaseDeDatos.obtenerJugadoresEquipoReal(equipoVisitante));
		for(Jugador j : jugadores) {
			BaseDeDatos.actualizarDiasSuspendido(j);
		}
	}
	
	public void jugar() {
		
		this.golesLocal 		= calcularGoles(equipoLocal.getCalidad());
		this.golesVisitante 	= calcularGoles(equipoVisitante.getCalidad());
		int cantidadLesionesLocal		= (int)Math.floor(Math.random() * 3);
		int cantidadLesionesVisitante	= (int)Math.floor(Math.random() * 3);
		int cantidadTarjetasLocal		= (int)Math.floor(Math.random() * 4);
		int cantidadTarjetasVisitante	= (int)Math.floor(Math.random() * 4);
		
		
		
		calcularLesionados(cantidadLesionesLocal, equipoLocal);
		calcularLesionados(cantidadLesionesVisitante, equipoVisitante);
		
		calcularTarjeteados(cantidadTarjetasLocal, equipoLocal);
		calcularTarjeteados(cantidadTarjetasVisitante, equipoVisitante);
		
		BaseDeDatos.ponerResultadoDePartido(this);
		calcularGoleadores(golesLocal, equipoLocal);
		calcularGoleadores(golesVisitante, equipoVisitante);
		
		actualizarSuspensiones();
	}
	
	private int calcularGoles(int calidad) {
		int resultado;
		resultado = (int)Math.ceil(equipoLocal.getCalidad());
		resultado *= Math.random() * 2 * this.umbralRandom + 1 - this.umbralRandom;
		
		return resultado;
	}
	
	private void calcularGoleadores(int goles, Equipo equipo) {
		for(int i = 0; i < goles; i++) {
			Jugador goleador = obtenerJugadorRandom(equipo);
			if(goleador != null) {
				BaseDeDatos.subirValorJugador(goleador, PUNTOS_POR_GOL);
				
				// Guarda la ocurrencia
				Ocurrencia ocurrencia = new Ocurrencia(goleador, TipoOcurrencia.GOL, this.getId());
				BaseDeDatos.ponerOcurrencia(ocurrencia);
			}
		}
	}
	
	@Override
	public String toString() {
		if(this.golesLocal < 0 || this.golesVisitante < 0) {
			return this.equipoLocal.getNombre() + " PENDIENTE " + this.equipoVisitante.getNombre();
		}else {
			return this.equipoLocal.getNombre() + " " + this.golesLocal + "-" + this.golesVisitante + " " + this.equipoVisitante.getNombre();
		}
	}
}
