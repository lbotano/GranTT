package editor;

import grantt.Jugador;

public class ItemJugador extends Jugador {

	public ItemJugador(
		int id,
		String nombre, 
		int edad, 
		Posiciones posicion, 
		float valor, 
		int diasLesionado,
		int partidosSuspendido, 
		int dorsal
	) {
		super(
			id, 
			nombre, 
			edad, 
			posicion, 
			valor, 
			diasLesionado,
			partidosSuspendido, 
			dorsal
		);
		
	}
	
	public ItemJugador(Jugador j) {
		super(
			j.getId(),
			j.getNombre(),
			j.getEdad(),
			j.getPosicion(),
			j.getValor(),
			j.getDiasLesionado(),
			j.getPartidosSuspendido(),
			j.getDorsal()
		);
	}
	
	public String toString() {
		return "<html><font color=green>" + this.getPosicion() +
				"</font> " + this.getNombre() + 
				" Partidos Suspendidos: <font color=orange>" + this.getPartidosSuspendido() + 
				" </font>Tarjetas Amarillas: " + (this.getAmarillas()? "SI" : "NO" ) + 
				" Precio: " + this.getValor() +
				"</html>";
	}
	
}
