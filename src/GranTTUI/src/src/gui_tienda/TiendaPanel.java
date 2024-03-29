package gui_tienda;

import grantt.BaseDeDatos;
import grantt.Equipo;
import grantt.Jugador;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TiendaPanel extends JPanel {
	JFrame				window;
	List<TiendaJugador> jugadorPaneles;
	
	TiendaListaComprar 	jugadoresComprarPanel;
	TiendaListaVender	jugadoresVenderPanel;
	TiendaEquipos 		equiposPanel;
	JLabel				labelEquipos;
	JLabel				labelJugadoresCompra;
	JLabel				presupuesto;
	JLabel				restantesLabel;
	
	GridBagLayout 		layout;
	GridBagConstraints 	c;
	
	private final int cantArqueros		= 2;
	private final int cantDefensores	= 5;
	private final int cantMedios		= 5;
	private final int cantDelanteros	= 3;
	
	public void calcularTamaños() {
		int winW = this.window.getWidth();
		int winH = this.window.getHeight();
		
		int txtH = this.labelEquipos.getFont().getSize() + 4;
		
		equiposPanel.setPreferredSize(new Dimension(winW / 3, winH - txtH));
		jugadoresComprarPanel.setPreferredSize(new Dimension(winW / 3, winH - txtH));
		jugadoresVenderPanel.setPreferredSize(new Dimension(winW / 3, winH - txtH));
		labelEquipos.setPreferredSize(new Dimension(winW / 3, txtH));
		labelJugadoresCompra.setPreferredSize(new Dimension(winW / 3, txtH));
		presupuesto.setPreferredSize(new Dimension(winW / 3, txtH));
		
		this.revalidate();
	}
	
	private int getCant(Jugador.Posiciones pos) {
		int cant = 0;
		for(Jugador j : jugadoresVenderPanel.jugadores) {
			if(j.getPosicion().equals(pos)) {
				cant++;
			}
		}
		return cant;
	}
	
	public void updateRestantes() {
		int arqRest = cantArqueros - getCant(Jugador.Posiciones.ARQUERO);
		int defRest = cantDefensores - getCant(Jugador.Posiciones.DEFENSOR);
		int medRest = cantMedios - getCant(Jugador.Posiciones.MEDIO_CAMPISTA);
		int delRest = cantDelanteros - getCant(Jugador.Posiciones.DELANTERO);
		
		String strArqueros = (arqRest != 1) ? arqRest + " arqueros" : "1 arquero";
		String strDefensores = (defRest != 1) ? defRest + " defensores" : "1 defensor";
		String strMedios = (medRest != 1) ? medRest + " mediocampistas" : "1 mediocampista";
		String strDelanteros = (delRest != 1) ? delRest + " delanteros" : "1 delantero";
		
		restantesLabel.setText(
			"<html>Te faltan:<br>" +
			strArqueros + "<br>" +
			strDefensores + "<br>" +
			strMedios + "<br>" +
			strDelanteros + "</html>"
		);
	}
	
	private void initComponents(List<Equipo> equipos) {
		jugadoresComprarPanel	= new TiendaListaComprar();
		jugadoresVenderPanel	= new TiendaListaVender(BaseDeDatos.obtenerJugadoresEquipo());
		equiposPanel			= new TiendaEquipos(this.jugadoresComprarPanel, equipos);
		labelEquipos			= new JLabel("Equipos:");
		labelJugadoresCompra	= new JLabel("Jugador para comprar:");
		presupuesto				= new JLabel("Presupuesto: $" + BaseDeDatos.obtenerPresupuesto());
		restantesLabel			= new JLabel();
		
		updateRestantes();
		
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
		
		c.gridx		= 2;
		c.gridy		= 0;
		c.anchor	= GridBagConstraints.EAST;
		
		this.add(this.presupuesto, this.c);
		
		c.gridx		= 0;
		c.gridy		= 2;
		c.gridwidth = 1;
		c.anchor	= GridBagConstraints.WEST;
		
		this.add(new JLabel("<html>Lesionado <font color=blue>⚕</font><br>"
				+ "Tarjeta amarilla <font color=#ffe100>█</font><br>"
				+ "Tarjeta roja <font color=red>█</font></html>"), this.c);
		
		c.gridx		= 2;
		c.gridy		= 2;
		c.gridwidth = 1;
		c.anchor	= GridBagConstraints.EAST;
		
		this.add(restantesLabel, this.c);
		calcularTamaños();
	}
	
	public TiendaPanel(JFrame window, List<Equipo> equipos) {
		super();
		this.window = window;
		
		initComponents(equipos);
		Updater.initUpdater(equiposPanel.lista, this, jugadoresComprarPanel, jugadoresVenderPanel, presupuesto);
	}
}