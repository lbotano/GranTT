package gui;

import grantt.BaseDeDatos;
import grantt.Equipo;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class TiendaPanel extends JPanel {
	JFrame				window;
	List<TiendaJugador> jugadorPaneles;
	
	TiendaListaComprar 	jugadoresComprarPanel;
	TiendaListaVender	jugadoresVenderPanel;
	TiendaEquipos 		equiposPanel;
	JLabel				labelEquipos;
	JLabel				labelJugadoresCompra;
	
	GridBagLayout 		layout;
	GridBagConstraints 	c;
	
	public void calcularTamaños() {
		int winW = this.window.getWidth();
		int winH = this.window.getHeight();
		
		int txtH = this.labelEquipos.getFont().getSize() + 4;
		
		equiposPanel.setPreferredSize(new Dimension(winW / 3, winH - txtH));
		jugadoresComprarPanel.setPreferredSize(new Dimension(winW / 3, winH - txtH));
		jugadoresVenderPanel.setPreferredSize(new Dimension(winW / 3, winH - txtH));
		labelEquipos.setPreferredSize(new Dimension(winW / 3, txtH));
		labelJugadoresCompra.setPreferredSize(new Dimension(winW / 3, txtH));
		
		this.revalidate();
	}
	
	private void initComponents(List<Equipo> equipos) {
		jugadoresComprarPanel	= new TiendaListaComprar();
		jugadoresVenderPanel	= new TiendaListaVender(BaseDeDatos.obtenerJugadoresEquipo());
		equiposPanel			= new TiendaEquipos(this.jugadoresComprarPanel, equipos);
		labelEquipos			= new JLabel("Equipos:");
		labelJugadoresCompra	= new JLabel("Jugador para comprar:");
		
		layout = new GridBagLayout();
		c = new GridBagConstraints();
		setLayout(layout);
		
		c.fill		= GridBagConstraints.BOTH;
		c.weightx	= 1;
		c.weighty	= 1;
		c.gridx		= 0;
		c.gridy		= 1;
		c.gridheight= 1;
		c.gridwidth	= 1;
		
		this.add(equiposPanel, c);
		
		c.gridx		= 1;
		c.gridy		= 1;
		
		this.add(jugadoresComprarPanel, c);
		
		c.gridx		= 2;
		c.gridy		= 1;
		
		this.add(jugadoresVenderPanel, c);
		
		c.gridx		= 0;
		c.gridy		= 0;
		c.weighty	= 0;
		c.anchor	= GridBagConstraints.WEST;
		
		this.add(this.labelEquipos, this.c);
		
		c.gridx		= 1;
		c.gridy		= 0;
		c.anchor	= GridBagConstraints.WEST;
		
		this.add(this.labelJugadoresCompra, this.c);
		
		calcularTamaños();
	}
	
	public TiendaPanel(JFrame window, List<Equipo> equipos) {
		super();
		this.window = window;
		
		initComponents(equipos);
		Updater.initUpdater(equiposPanel.lista, jugadoresComprarPanel, jugadoresVenderPanel);
	}
}