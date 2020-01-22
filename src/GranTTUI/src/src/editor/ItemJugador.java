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
		return "<html><i>" + this.getPosicion() + "</i> " +
				this.getNombre() +
				(this.getDiasLesionado() > 0 ? " <font color=blue>LESIONADO: " + this.getDiasLesionado() + " días</font>": "") +
				(this.getPartidosSuspendido() > 0 ? 
						" <font color=red>TARJETA ROJA</font> <i>Suspendido por:</i> <font color=red>" + this.getPartidosSuspendido() + " partidos</font>" : 
						""
				) +
				(this.getAmarillas() ? " <font color=orange>TARJETA AMARILLA</font>" : "") +
				" Precio: " + this.getValor() +
				"</html>";
		//return this.getNombre() + " " + this.getDiasLesionado() + " " + this.getPartidosSuspendido() + " " + this.getAmarillas() + " " + this.getRojas();
	}
	
}
