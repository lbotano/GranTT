package grantt;

import java.util.ArrayList;
import java.util.List;

public class Torneo {
	private int id;
	private List<Partido> partidos;
	private List<Equipo> equipos;
	
	private int diaHoy;
	
	public Torneo(int id, int diaHoy) {
		this.id = id;
		this.equipos = BaseDeDatos.obtenerEquiposReales();
		this.partidos = new ArrayList<Partido>();
		this.diaHoy = diaHoy;
	}
	
	public List<Partido> getPartidos() {
		return this.partidos;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getJornada() {
		return this.diaHoy;
	}
	
	public void pasarJornada() {
		this.diaHoy++;
		
		BaseDeDatos.jugarDiaSiguiente();
	}
	
	public Equipo getEquiposPorId(int id){
		for(Equipo eq : this.equipos) {
			if(eq.getId() == id) return eq;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "Torneo " + getId();
	}
}
