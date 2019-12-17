package editor;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import grantt.Jugador;

public abstract class PlayerPanel extends JPanel {
	
	public Jugador jugador;
	public GridBagLayout gbl = new GridBagLayout();
	public GridBagConstraints gbc;
	
	private JLabel nombre;
	private JLabel precio;
	private JLabel estado;
	private Dimension size;
	
	public PlayerPanel(Dimension size) {
		super();
		this.size = size;

		this.nombre = new JLabel();
		this.precio = new JLabel();
		this.estado = new JLabel();
		
		
		this.setLayout(this.gbl);
	}
	
	public abstract void addJugador(Jugador j);
	
	public abstract Jugador quitarJugador();
	
	
	protected void displayPlayer(Jugador j) {
		
		this.jugador = j;
		this.gbl = new GridBagLayout();
		this.gbc = new GridBagConstraints();
		
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(this.nombre, gbc);
		
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(this.precio, gbc);
		
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add(this.estado, gbc);
		
		
		//Aca me fijo si esta suspendido, su situacion
		
		this.estado.setText("Estado: ");
		if(this.jugador.getDiasLesionado() > 0) {
			this.estado.setText(this.estado.getText() + "Lesionado Por " + this.jugador.getDiasLesionado() + " Dias.");
		} else if(this.jugador.getPartidosSuspendido() > 0) {
			
			this.estado.setText("<html>" + this.estado.getText() + "Suspendido por <font color=red>" + this.jugador.getPartidosSuspendido() + "</font> Dias.<html>");
		} else {
			this.estado.setText(this.estado.getText() + "Normal");
		}
		
		this.nombre.setText(this.jugador.getNombre());
		this.precio.setText("Precio: " + Float.toString(this.jugador.getValor()));
		this.nombre.setFont(new Font("Roboto", Font.PLAIN, this.nombre.getFont().getSize()));
		this.precio.setFont(new Font("Roboto", Font.PLAIN, this.nombre.getFont().getSize()));
		this.estado.setFont(new Font("Roboto", Font.PLAIN, this.nombre.getFont().getSize()));
		
	}
	
	public void clearDisplay() {
		this.nombre.setText("");
		this.precio.setText("");
		this.estado.setText("");
	}
	
}
