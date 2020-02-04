package grantt;

import java.util.List;

public class Equipo {
	private int id;
	private String nombre;
	
	private List<Jugador> jugadores;
	
	public Equipo(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		
		this.jugadores = BaseDeDatos.obtenerJugadoresEquipoReal(this);
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public List<Jugador> getJugadores(){
		return this.jugadores;
	}
	
	@Override
	public String toString() {
		return getNombre();
	}
}
