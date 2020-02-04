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
		String strSituacion = "";
		if(this.getAmarillas() > 0 || this.getRojas() > 0 || this.getDiasLesionado() > 0) {
			strSituacion = " Situación: ";
			for(int i = 0; i < this.getAmarillas(); i++) {
				strSituacion += "<font color=#ffe100>█</font> ";
			}
			for(int i = 0; i < this.getRojas(); i++) {
				strSituacion += "<font color=red>█</font> ";
			}
			if(this.getDiasLesionado() > 0) {
				strSituacion += "<font color=blue>⚕</font> ";
			}
		}
		
		return "<html><i><font color=green>" + this.getPosicion() + "</font></i> " +
				this.getNombre() +
				strSituacion +
				" - Precio: " + this.getValor() +
				"</html>";
	}
	
}
