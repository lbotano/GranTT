package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import grantt.BaseDeDatos;
import grantt.Equipo;
import grantt.Jugador;

public class StatePanel extends JPanel {
	
	public Dimension size;
	public GridBagLayout gbl;
	public GridBagConstraints gbc;
	
	public JLabel precio;
	public JButton btnGuardar;
	private EditorPanel owner;
	
	public StatePanel(Dimension d, EditorPanel ep) {
		super();
		
		this.owner = ep;
		this.size = d;
		this.gbl = new GridBagLayout();
		this.gbc = new GridBagConstraints();
		this.precio = new JLabel("Valor Total: ");
		this.btnGuardar = new JButton("Guardar Cambios");
		
		
		this.setPreferredSize(this.size);
		
		
		
		this.setLayout(this.gbl);
		
		this.gbc.gridx = 0;
		this.gbc.fill = GridBagConstraints.BOTH;
		this.gbc.weightx = 0.5;
		this.add(this.precio, this.gbc);
		
		this.gbc.gridx = 5;
		this.gbc.weightx = 0.5;
		this.gbc.weighty = 1.0;
		this.add(this.btnGuardar, this.gbc);
		
		this.actualizarValor();
		this.setBackground(Color.WHITE);
	}
	
	public void actualizarValor() {
		BaseDeDatos.updateEquipo();
		
		ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
		jugadores = (ArrayList<Jugador>) BaseDeDatos.obtenerJugadoresDisponiblesEquipoReal(BaseDeDatos.usuarioLogueado.getEquipo());
		
		float valor = 0;
		
		for(Jugador j : jugadores) {
			valor += j.getValor();
		}
		
		
		this.precio.setText("Valor Total: " + valor);
	}
	
}
