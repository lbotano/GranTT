package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import grantt.BaseDeDatos;
import grantt.Jugador;

public class EditorLeftPanel extends JPanel {
	
	public PlayerFieldList pfl;
	public JScrollPane pflSP;
	public JButton btnQuitar = new JButton("Quitar");
	public Dimension size;
	public JLabel label = new JLabel("Titulares");
	
	private GridBagLayout gbl = new GridBagLayout();;
	private GridBagConstraints gbc = new GridBagConstraints();
	
	
	
	public EditorLeftPanel(Dimension d, EditorPanel ep) {
		super();
		
		this.setLayout(this.gbl);
		
		initComponents(d);
	}
	
	private void initComponents(Dimension d) {
		this.size = d;
		this.setPreferredSize(this.size);
		this.pfl = new PlayerFieldList(this.size);
		
		this.setBackground(Color.WHITE);
		label.setHorizontalAlignment(JLabel.CENTER);
		
		gbc.gridy = 0;
		gbc.weighty = 0.05;
		this.add(this.label, gbc);

		gbc.gridy = 1;
		gbc.weighty = 0.9;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(this.pfl.getScroll(), gbc);
		
		gbc.gridy = 2;
		gbc.weighty = 0.05;
		gbc.weightx = 1.0;
		this.add(this.btnQuitar, gbc);
	}
	
	public void getJugadoresTitulares() {
		this.pfl.model.removeAllElements();
		
		ArrayList<Jugador> jugadores = (ArrayList<Jugador>) BaseDeDatos.getTitularesEquipo();
		if(jugadores != null) {
			for(Jugador j : jugadores) {
				this.pfl.addJugador(j);
			}
		}
	}
	
	public boolean validarEquipo() {
		return this.pfl.getModel().getSize() == 11;
	}
	
}
