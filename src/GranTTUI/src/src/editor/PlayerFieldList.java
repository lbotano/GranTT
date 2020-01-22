package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import grantt.Jugador;

public class PlayerFieldList extends JList<ItemJugador> {
	
	
	public Dimension size;
	public GridBagLayout gbl;
	public GridBagConstraints gbc;
	public ArrayList<ItemJugador> jugadores;
	public DefaultListModel<ItemJugador> model = new DefaultListModel<ItemJugador>();
	public JScrollPane scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	public PlayerFieldList(Dimension size) {
		super();
		this.setModel(model);
		this.size = size;
		this.jugadores = new ArrayList<ItemJugador>();
		scroll.setViewportView(this);
		
		this.setBackground(Color.WHITE);
		//this.setLayoutOrientation(JList.VERTICAL);
		
		
		this.setPreferredSize(this.size);
		
		
	}

	public void addJugador(Jugador j) {
		if(this.validarInsercion(j.getPosicion())) {
			this.model.addElement(
					new ItemJugador(j)
				);
		} else {
			System.out.println("Se Intento Poner Un Jugador De Mas En Una Posicion.");
		}
	}

	public ItemJugador quitarJugador() {
		if(this.getSelectedIndex() != -1 ) {
			ItemJugador j = (ItemJugador) this.getSelectedValue();
			this.model.removeElementAt(this.getSelectedIndex());
			this.revalidate();
			this.repaint();
			return new ItemJugador(j);
		} else {
			System.out.println("Jugador Nulo en " + this.toString());
			return null;
		}
	}
	
	public void initComponents() {
		
	}
	
	public boolean validarInsercion(Jugador.Posiciones p) {
		int max;
		switch(p) {
			case ARQUERO:
				max = 1;
				break;
			case DELANTERO:
				max = 2;
				break;
			default:
				max = 4;
				break;
		}
		int c = 0;
		for(Jugador j : this.jugadores) {
			if(j.getPosicion() == p) {
				c++;
			}
		}
		//verifico la cantidad
		if(c < max) {
			return true;
		} else {
			return false;
		}
	}
	public JScrollPane getScroll() {
		return this.scroll;
	}
}
