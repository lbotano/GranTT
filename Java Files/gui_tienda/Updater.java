package gui_tienda;

import javax.swing.JLabel;
import javax.swing.JList;

import grantt.BaseDeDatos;
import grantt.Equipo;
import grantt.Jugador;

public class Updater {
	
	static JList<Equipo> listaEquipos;
	static TiendaPanel tiendaPanel;
	static TiendaListaComprar listaComprar;
	static TiendaListaVender listaVender;
	static JLabel presupuesto;
	
	public static void initUpdater(
		JList<Equipo> listaEquipos,
		TiendaPanel tiendaPanel,
		TiendaListaComprar listaComprar,
		TiendaListaVender listaVender,
		JLabel presupuesto
	) {
		Updater.listaEquipos = listaEquipos;
		Updater.tiendaPanel = tiendaPanel;
		Updater.listaComprar = listaComprar;
		Updater.listaVender = listaVender;
		Updater.presupuesto = presupuesto;
	}
	
	public static void update() {
		if(listaEquipos.getSelectedValue() != null) {
			Equipo equipoSeleccionado = listaEquipos.getSelectedValue();
			listaComprar.setJugadores(BaseDeDatos.obtenerJugadoresDisponiblesEquipoReal(equipoSeleccionado.getId()));
		}
		listaVender.setJugadores(BaseDeDatos.obtenerJugadoresEquipo());
		presupuesto.setText("Presupuesto: $" + BaseDeDatos.obtenerPresupuesto());
		tiendaPanel.updateRestantes();
//		System.out.println("Tamaño Total Del Equipo: " + listaVender.jugadores.size());
	}
	
	public static boolean esValido(Jugador j) {
//		System.out.println("Validating Player");
		Jugador.Posiciones posicion = j.getPosicion();
		int max = 0;
		switch(posicion) {
			case ARQUERO:
				max = 2;
				break;
			case DEFENSOR:
			case MEDIO_CAMPISTA:
				max = 5;
				break;
			case DELANTERO:
				max = 3;
		}
		
		int cantPosicion = 0;

		for(Jugador jugador : BaseDeDatos.obtenerJugadoresEquipo()) {
			if(jugador.getPosicion() == posicion) {
				cantPosicion++;
			}
		}

		return cantPosicion < max;
	}
}