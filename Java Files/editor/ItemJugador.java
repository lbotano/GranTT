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
				(this.getDiasLesionado() > 0 ? " <font color=blue>LESIONADO Dias: " + this.getDiasLesionado() + "</font>" : "") +
				(this.getRojas() ? 
						" <font color=red>TARJETA ROJA</font> Partidos Suspendidos: <font color=orange>" + this.getPartidosSuspendido() + "</font>" : 
						""
				) +
				(this.getAmarillas() ? "<font color=yellow>TARJETA AMARILLA</font>" : "") +
				" Precio: " + this.getValor() +
				"</html>";
	}
	
}
