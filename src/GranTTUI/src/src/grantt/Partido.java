package grantt;

public class Partido {
	private int id;
	
	private Equipo equipoLocal;
	private Equipo equipoVisitante;
	
	private int golesLocal = -1;
	private int golesVisitante = -1;
	
	private int jornada;
	
	public Partido(int id, Equipo equipoLocal, Equipo equipoVisitante, int jornada) {
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
}
