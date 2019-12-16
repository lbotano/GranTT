package grantt;

import grantt.BaseDeDatos;
import java.util.ArrayList;
import java.util.List;

public class Torneo {
	private int id;
	private List<Partido> partidos;
	private List<Equipo> equipos;
	
	public Torneo(int id) {
		this.id = id;
		this.equipos = BaseDeDatos.obtenerEquiposReales();
		this.partidos = new ArrayList<Partido>();
	}
	
	public void generarFixture() {
		int jornada = 0;
		for(int i = 0; i < equipos.size() - 1; i++) {
			for(int j = i + 1; j < equipos.size(); j++) {
				partidos.add(new Partido(equipos.get(i), equipos.get(j), jornada));
				System.out.println(equipos.get(i).getNombre() + " VS " + equipos.get(j).getNombre());
				jornada++;
			}
		}
	}
	
	public List<Partido> getPartidos() {
		return this.partidos;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Equipo getEquiposPorId(int id){
		for(Equipo eq : this.equipos) {
			if(eq.getId() == id) return eq;
		}
		return null;
	}
}
