package editor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import grantt.BaseDeDatos;
import grantt.Jugador;

import java.util.*;

public class ListPanel extends JPanel {
	
	public Dimension size;
	public JList<String> lista;
	public DefaultListModel<String> dlm;
	
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
		this.dlm = new DefaultListModel<String>();
		this.lista = new JList<String>(dlm);
	}
}


