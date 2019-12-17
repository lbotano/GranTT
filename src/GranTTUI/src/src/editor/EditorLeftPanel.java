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

public class EditorLeftPanel extends JPanel {
	
	public PlayerFieldList pfl;
	public JScrollPane pflSP;
	public JButton btnQuitar = new JButton("Quitar");
	public Dimension size;
	private EditorPanel owner;
	
	private GridBagLayout gbl = new GridBagLayout();;
	private GridBagConstraints gbc = new GridBagConstraints();
	
	
	
	public EditorLeftPanel(Dimension d, EditorPanel ep) {
		super();
		this.owner = ep;
		
		this.setLayout(this.gbl);
		
		
		initComponents(d);
		initEvents();
		
		
	}
	
	private void initComponents(Dimension d) {
		this.size = d;
		this.setPreferredSize(this.size);
		this.pfl = new PlayerFieldList(this.size);
		this.getJugadoresTitulares();
		
		
		this.setBackground(Color.GRAY);
		
		
		
		
		//gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 0.95;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(this.pfl.getScroll(), gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 0.05;
		gbc.weightx = 1.0;
		this.add(this.btnQuitar, gbc);

	}
	
	private void initEvents() {
		
	}
	
	public void getJugadoresTitulares() {
		this.pfl.removeAll();
		
		ArrayList<Jugador> jugadores = (ArrayList<Jugador>) BaseDeDatos.getTitularesEquipo();
		for(Jugador j : jugadores) {
			this.pfl.addJugador(j);
		}
	}
	
	public boolean validarEquipo() {
		if(this.pfl.getModel().getSize() == 11) {
			return true;
		} else {
			System.out.println("El Equipo Ingresado Es Invalido");
			return false;
		}
	}
	
}
