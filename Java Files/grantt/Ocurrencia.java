package grantt;

public class Ocurrencia {
	private TipoOcurrencia tipo;
	private Jugador jugador;
	private int partido;
	
	private String nombre;
	private boolean esLocal;
	
	public enum TipoOcurrencia{
		GOL,
		LESION,
		AMARILLA,
		ROJA
	}
	
	public Ocurrencia(Jugador jugador, TipoOcurrencia tipo, int partido) {
		this.jugador = jugador;
		this.tipo = tipo;
		this.partido = partido;
	}
	
	public Ocurrencia(String nombre, TipoOcurrencia tipo, boolean esLocal) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.esLocal = esLocal;
	}
	
	public TipoOcurrencia getTipo() {
		return this.tipo;
	}
	
	public Jugador getJugador() {
		return this.jugador;
	}
	
	public int getPartido() {
		return this.partido;
	}
	
	public boolean getEsLocal() {
		return this.esLocal;
	}
	
	public String getNombre() {
		return this.nombre;
	}
}