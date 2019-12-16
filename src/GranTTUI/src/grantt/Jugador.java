package grantt;

public class Jugador {
	private int id;
	private String nombre;
	private int edad;
	private Posiciones posicion;
	private float valor;
	private int diasLesionado;
	private int diasSuspendido;
	private int dorsal;
	
	public Jugador(
		int id,
		String nombre,
		int edad,
		Posiciones posicion,
		float valor,
		int diasLesionado,
		int diasSuspendido,
		int dorsal
	) {
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
		this.posicion = posicion;
		this.valor = valor;
		this.diasLesionado = diasLesionado;
		this.diasSuspendido = diasSuspendido;
		this.dorsal = dorsal;
	}
	
	public enum Posiciones{
		DELANTERO,
		MEDIO_CAMPISTA,
		DEFENSOR,
		ARQUERO
	}
	
	static Posiciones intToPosicion(int pos) {
		switch(pos) {
			case 1:
				return Posiciones.ARQUERO;
			case 2:
				return Posiciones.DEFENSOR;
			case 3:
				return Posiciones.MEDIO_CAMPISTA;
			case 4:
				return Posiciones.DELANTERO;
			default:
				return null;
		}
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public int getEdad() {
		return this.edad;
	}
	
	public Posiciones getPosicion() {
		return this.posicion;
	}
	
	public float getValor() {
		return this.valor;
	}
	
	public int getDiasLesionado() {
		return this.diasLesionado;
	}
	
	public int getDiasSuspendido() {
		return this.diasSuspendido;
	}
	
	public int getDorsal() {
		return this.dorsal;
	}
}
