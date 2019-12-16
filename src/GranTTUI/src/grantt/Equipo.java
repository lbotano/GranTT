package grantt;

import grantt.Jugador;
import grantt.BaseDeDatos;
import java.util.List;

public class Equipo {
	private int id;
	private String nombre;
	private int calidad;
	
	private List<Jugador> jugadores;
	
	public Equipo(int id, String nombre, int calidad) {
		this.id = id;
		this.nombre = nombre;
		this.calidad = calidad;
		
		this.jugadores = BaseDeDatos.obtenerJugadoresEquipoReal(this);
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public int getCalidad() {
		return this.calidad;
	}
	
	public List<Jugador> getJugadores(){
		return this.jugadores;
	}
}
