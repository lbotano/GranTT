package grantt;

import grantt.Jugador;

public class Ocurrencia {
	private TipoOcurrencia tipo;
	private Jugador jugador;
	private int duracion;
	
	enum TipoOcurrencia{
		GOL,
		AMARILLA,
		ROJA,
		LESION
	}
	
	public Ocurrencia(Jugador jugador, TipoOcurrencia tipo, int duracion) {
		this.jugador = jugador;
		this.tipo = tipo;
	}
	
	public void pasarDia() {
		if(duracion > 0) {
			duracion--;
		}
	}
	
	public int getDuracion() {
		return this.duracion;
	}
	
	public TipoOcurrencia getTipo() {
		return this.tipo;
	}
	
	public Jugador getJugador() {
		return this.jugador;
	}
}