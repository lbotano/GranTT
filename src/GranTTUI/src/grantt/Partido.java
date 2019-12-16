package grantt;

import grantt.Jugador;
import java.util.List;
import java.util.Random;

//import granttOcurrencia.TipoOcurrencia;

public class Partido {
	private List<Ocurrencia> ocurrencias;
	private Equipo equipoLocal;
	private Equipo equipoVisitante;
	
	private int golesLocal;
	private int golesVisitante;
	
	private int jornada;
	
	private final float umbralRandom = 0.2f;
	
	public Partido(Equipo equipoLocal, Equipo equipoVisitante, int jornada) {
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.jornada = jornada;
		this.calcularResultado();
	}
	
	public Partido(Equipo equipoLocal, Equipo equipoVisitante, int jornada, int golesLocal, int golesVisitante) {
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
	
	private void calcularResultado() {
		
		this.golesLocal 		= calcularGoles(equipoLocal.getCalidad());
		this.golesVisitante 	= calcularGoles(equipoVisitante.getCalidad());
		
		calcularGoleadores(golesLocal, equipoLocal);
		calcularGoleadores(golesVisitante, equipoVisitante);
	}
	
	private int calcularGoles(int calidad) {
		int resultado;
		resultado = (int)Math.ceil(equipoLocal.getCalidad() / 10);
		resultado *= Math.random() * 2 * this.umbralRandom + 1 - this.umbralRandom;
		
		return resultado;
	}
	
	private void calcularGoleadores(int goles, Equipo equipo) {
		List<Jugador> jugadoresActivos = equipo.getJugadores();
		
		for(int i = 0; i < goles; i++) {
			Random rand = new Random();
			Jugador goleador = jugadoresActivos.get(rand.nextInt(jugadoresActivos.size()));
			this.ocurrencias.add(new Ocurrencia(goleador, Ocurrencia.TipoOcurrencia.GOL, 0));
		}
	}
}
