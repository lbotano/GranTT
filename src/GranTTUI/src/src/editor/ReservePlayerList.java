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

public class ReservePlayerList extends JList {
	
	
	public Dimension size;
	public GridBagLayout gbl;
	public GridBagConstraints gbc;
	public ArrayList<Jugador> jugadores;
	public DefaultListModel<ItemJugador> model = new DefaultListModel<ItemJugador>();
	public JScrollPane scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	public ReservePlayerList(Dimension size) {
		<ItemJugador>super();
		this.setModel(model);
		this.size = size;
		
		scroll.setViewportView(this);
		this.setLayoutOrientation(JList.VERTICAL);
		//this.setBackground(Color.DARK_GRAY);
		
		this.setPreferredSize(this.size);
		
	}

	public void addJugador(ItemJugador j) {
		
		this.model.addElement(
			j
		);

	}

	public ItemJugador quitarJugador() {
		if(this.getSelectedIndex() != -1) {
			ItemJugador j = (ItemJugador) this.getSelectedValue();
			this.model.removeElementAt((this.getSelectedIndex()));
			this.revalidate();
			this.repaint();
			return j;
		} else {
			System.out.println("Jugador Nulo en " + this.toString());
			return null;
		}
	}
	
	public void initComponents() {
		
	}
	
	public JScrollPane getScroll() {
		return this.scroll;
	}
	
}
