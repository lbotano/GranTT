package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JButton;

import grantt.Jugador;

public class PlayerListPanel extends PlayerPanel {
	
	public Dimension size;
	public JButton btnMover = new JButton("Mover");
	
	public PlayerListPanel(Dimension size, Jugador j) {
		super(size);
		this.jugador = j;
		this.size = size;
		this.displayPlayer(j);
		this.initComponents();
		this.initEvents();
		
		this.setBackground(Color.LIGHT_GRAY);
		
		this.setMinimumSize(size);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		
	}

	@Override
	public void addJugador(Jugador j) {}

	@Override
	public Jugador quitarJugador() {
		return null;
	}
	
	private void initComponents() {
		this.gbc.gridwidth = 1;
		this.gbc.gridheight = 3;
		this.gbc.weightx = 0;
		this.gbc.gridx = 2;
		this.gbc.gridy = 0;
		this.gbc.fill = GridBagConstraints.BOTH;
		this.add(this.btnMover, this.gbc);
	}
	
	private void initEvents() {}
}
