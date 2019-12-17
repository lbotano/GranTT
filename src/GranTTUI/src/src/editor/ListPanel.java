package editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.MatteBorder;

import grantt.BaseDeDatos;
import grantt.Jugador;

import java.util.*;

public class ListPanel extends JPanel {
	
	public Dimension size;
	public JList lista;
	public DefaultListModel<String> dlm;
	private EditorPanel owner;
	
	public ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
	
	public ListPanel(Dimension d, EditorPanel owner) {
		super();
		
		
		
		this.setPreferredSize(this.size);
		
		this.setBackground(Color.CYAN);
		this.initComponents(d, owner);
        
	}
	
	public void actualizarJugadores() {
		this.jugadores.clear();
		this.jugadores = (ArrayList<Jugador>) BaseDeDatos.getSuplentesEquipo();
	}
	
	public void displayList() {
		this.lista.removeAll();
		this.lista.revalidate();
		this.lista.repaint();
		
		for(Jugador j : this.jugadores) {
			this.dlm.addElement("<html>" + 
				j.getNombre() + " " + 
				j.getPosicion().toString() + 
				" Precio: " + j.getValor() + "<br>" + 
				"Partidos Suspendidos: " + j.getPartidosSuspendido() +
				"Tarjetas Amarillas: " + j.getAmarillas() + 
				"</html>"
			);
		}
	}
	
	private void initComponents(Dimension d, EditorPanel ep) {
		this.size = d;
		this.owner = ep;
		this.dlm = new DefaultListModel<String>();
		this.lista = new JList(dlm);
		
	}
	
	private void initEvents() {
		
	}	
}


