package gui_tienda;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import grantt.Jugador;

public class TiendaListaVender extends JPanel{
	
	List<TiendaJugadorVender> jugadoresPanel;
	
	JPanel listaPanel;
	JScrollPane scroll;
	
	BorderLayout layout;
	BoxLayout layoutLista;
	
	List<Jugador> jugadores;
	
	private void initializeComponents() {
		layout = new BorderLayout();
		listaPanel = new JPanel();
		layoutLista = new BoxLayout(listaPanel, BoxLayout.Y_AXIS);
		scroll = new JScrollPane(listaPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		listaPanel.setLayout(layoutLista);
		
		this.setLayout(layout);
		
		this.add(scroll, BorderLayout.CENTER);
	}
	
	public void setJugadores(List<Jugador> jugadores) {
		// Actualiza la lista de jugadores
		this.jugadores.clear();
		this.jugadores.addAll(jugadores);
		
		// Poner jugadores en el List de paneles
		this.jugadoresPanel = new ArrayList<TiendaJugadorVender>();
		for(Jugador j : this.jugadores) {
			jugadoresPanel.add(new TiendaJugadorVender(j));
		}
		
		// Mostrarlos
		listaPanel.removeAll();
		for(TiendaJugadorVender jugadorPanel : jugadoresPanel) {
			// Arregla problema en el que el scrollbar tapa los botones
			jugadorPanel.setPreferredSize(
				new Dimension(
					jugadorPanel.getPreferredSize().width - scroll.getVerticalScrollBar().getPreferredSize().width,
					jugadorPanel.getPreferredSize().height
				)
			);
			listaPanel.add(jugadorPanel);
		}
		listaPanel.revalidate();
	}
	
	public TiendaListaVender(List<Jugador> jugadores) {
		super();
		initializeComponents();
		this.jugadores = new ArrayList<Jugador>();
		setJugadores(jugadores);
	}
}
