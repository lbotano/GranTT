package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import grantt.BaseDeDatos;
import grantt.Jugador;

public class EditorRightPanel extends JPanel{
	public ReservePlayerList prl;
	public JScrollPane prlSP;
	public JButton btnAgregar = new JButton("Agregar");
	public Dimension size;
	
	private GridBagLayout gbl = new GridBagLayout();;
	private GridBagConstraints gbc = new GridBagConstraints();
	private EditorPanel owner;
	
	public EditorRightPanel(Dimension d, EditorPanel ep) {
		super();
		
		this.owner = ep;		
		this.setLayout(this.gbl);
		initComponents(d);
		initEvents();
		//this.getJugadoresSuplentes();
	}
	
	private void initComponents(Dimension d) {
		this.size = d;
		this.setPreferredSize(this.size);
		this.prl = new ReservePlayerList(this.size);
		
		
		this.setBackground(Color.GRAY);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weighty = 0.95;
		gbc.weightx = 1.0;
		this.add(this.prl.getScroll(), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weighty = 0.05;
		gbc.weightx = 1.0;
		this.add(this.btnAgregar, gbc);
	}
	
	private void initEvents() {
		
	}
	
	
	public void getJugadoresSuplentes() {
		this.prl.model.removeAllElements();
		
		ArrayList<Jugador> jugadores = (ArrayList<Jugador>) BaseDeDatos.getSuplentesEquipo();
		if(jugadores != null) {
			for(Jugador j : jugadores) {
				this.prl.addJugador(new ItemJugador(j));
			}
		}
	}
}
