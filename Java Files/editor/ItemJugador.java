package editor;

import grantt.BaseDeDatos;
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
				(this.getPartidosSuspendido() > 0 ? 
						" <font color=red>TARJETA ROJA</font> Partidos Suspendidos: <font color=red>" + this.getPartidosSuspendido() + "</font>" : 
						""
				) +
				(this.getCantAmarillas() > 0 ? " <font color=orange>TARJETA AMARILLA Cantidad: " + this.getCantAmarillas() + "</font>" : "") +
				" Precio: " + this.getValor() +
				"</html>";
	}
	
}
