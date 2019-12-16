package gui;

import javax.swing.JList;

import grantt.BaseDeDatos;
import grantt.Equipo;

public class Updater {
	
	static JList<Equipo> listaEquipos;
	static TiendaListaComprar listaComprar;
	static TiendaListaVender listaVender;
	
	public static void initUpdater(JList<Equipo> listaEquipos, TiendaListaComprar listaComprar, TiendaListaVender listaVender) {
		Updater.listaEquipos = listaEquipos;
		Updater.listaComprar = listaComprar;
		Updater.listaVender = listaVender;
	}
	
	public static void update() {
		if(listaEquipos.getSelectedValue() != null) {
			Equipo equipoSeleccionado = listaEquipos.getSelectedValue();
			listaComprar.setJugadores(BaseDeDatos.obtenerJugadoresDisponiblesEquipoReal(equipoSeleccionado));
		}
		listaVender.setJugadores(BaseDeDatos.obtenerJugadoresEquipo());
	}
}
