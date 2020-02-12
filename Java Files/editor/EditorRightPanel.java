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

public class EditorRightPanel extends JPanel{
	public ReservePlayerList prl;
	public JScrollPane prlSP;
	public JButton btnAgregar = new JButton("Agregar");
	public Dimension size;
	public JLabel label = new JLabel("Suplentes");
	
	private GridBagLayout gbl = new GridBagLayout();;
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public EditorRightPanel(Dimension d, EditorPanel ep) {
		super();
				
		this.setLayout(this.gbl);
		initComponents(d);
	}
	
	private void initComponents(Dimension d) {
		this.size = d;
		this.setPreferredSize(this.size);
		this.prl = new ReservePlayerList(this.size);
		
		this.setBackground(Color.WHITE);
		label.setHorizontalAlignment(JLabel.CENTER);
		
		gbc.gridy = 0;
		gbc.weighty = 0.05;
		this.add(this.label, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridy = 1;
		gbc.weighty = 0.9;
		gbc.weightx = 1.0;
		this.add(this.prl.getScroll(), gbc);

		gbc.gridy = 2;
		gbc.weighty = 0.05;
		gbc.weightx = 1.0;
		this.add(this.btnAgregar, gbc);
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
