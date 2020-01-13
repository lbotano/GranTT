package gui_tienda;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import grantt.Jugador;

public class TiendaListaComprar extends JPanel{
	
	List<TiendaJugador> jugadoresPanel;
	BorderLayout layout;
	JScrollPane scroll;
	
	BoxLayout listaLayout;
	JPanel lista;

	public void initComponents() {
		layout	= new BorderLayout();
		lista 	= new JPanel();
		scroll	= new JScrollPane(lista, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.setLayout(layout);
		
		listaLayout = new BoxLayout(lista, BoxLayout.Y_AXIS);
		lista.setLayout(listaLayout);
		
		this.add(scroll, BorderLayout.CENTER);
	}
	
	public void setJugadores(List<Jugador> jugadores) {
		this.jugadoresPanel = new ArrayList<TiendaJugador>();
		
		// Añadir jugadores a la lista
		for(Jugador jugador : jugadores) {
			this.jugadoresPanel.add(new TiendaJugador(jugador));
		}
		
		// Graficar la lista
		lista.removeAll();
		for(TiendaJugador jugador : this.jugadoresPanel) {
			// Aregla problema de que el scrollbar tapa los botones
			jugador.setPreferredSize(
				new Dimension(
					jugador.getPreferredSize().width - scroll.getVerticalScrollBar().getPreferredSize().width,
					jugador.getPreferredSize().height
				)
			);
			
			lista.add(jugador);
		}
		lista.revalidate();
	}
	
	public TiendaListaComprar() {
		super();
		initComponents();
	}
}
