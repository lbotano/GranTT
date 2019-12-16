package grantt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Torneo {
	private int id;
	private List<Partido> partidos;
	private List<Equipo> equipos;
	
	private int diaHoy;
	
	private final int PARTIDOS_POR_DIA = 40;
	
	public Torneo(int id, int diaHoy) {
		this.id = id;
		this.equipos = BaseDeDatos.obtenerEquiposReales();
		this.partidos = new ArrayList<Partido>();
		this.diaHoy = diaHoy;
	}
	
	public void generarFixture() {
		List<Par> contiendas = new ArrayList<Par>();
		
		for(int i = 0; i < equipos.size() - 1; i++) {
			for(int j = i + 1; j < equipos.size(); j++) {
				contiendas.add(new Par(equipos.get(i), equipos.get(j)));
			}
		}

		Collections.shuffle(contiendas);
		
		int contador = 0;
		int jornada = 0;
		for(Par par : contiendas) {
			if(contador % this.PARTIDOS_POR_DIA == 0) jornada++;
			BaseDeDatos.anadirPartidoPendiente(new Partido(par.local, par.visitante, jornada));
			contador++;
		}
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
		
		List<Partido> partidosAJugar = BaseDeDatos.obtenerPartidosSinJugarDeHoy();
		for(Partido partido : partidosAJugar) {
			partido.jugar();
		}
	}
	
	public Equipo getEquiposPorId(int id){
		for(Equipo eq : this.equipos) {
			if(eq.getId() == id) return eq;
		}
		return null;
	}
	
	class Par{
		public Equipo local;
		public Equipo visitante;
		
		public Par(Equipo local, Equipo visitante) {
			this.local = local;
			this.visitante = visitante;
		}
	}
	
	@Override
	public String toString() {
		return "Torneo " + getId();
	}
}
