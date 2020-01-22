package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import grantt.BaseDeDatos;
import grantt.Jugador;

public class StatePanel extends JPanel {
	
	public Dimension size;
	public GridBagLayout gbl;
	public GridBagConstraints gbc;
	
	public JLabel precio;
	public JButton btnGuardar;
	
	public StatePanel(Dimension d, EditorPanel ep) {
		super();
		
		this.size = d;
		this.gbl = new GridBagLayout();
		this.gbc = new GridBagConstraints();
		this.precio = new JLabel("Valor Total: ");
		this.btnGuardar = new JButton("Guardar Cambios");
		
		this.setPreferredSize(this.size);
		this.setLayout(this.gbl);
		
		this.gbc.weighty = 1.0;
		this.gbc.fill = GridBagConstraints.BOTH;
		this.gbc.weightx = 0.514;
		this.add(this.precio, this.gbc);
		
		this.gbc.weightx = 0.486;
		this.add(this.btnGuardar, this.gbc);
		
		this.actualizarValor();
		this.setBackground(Color.WHITE);
	}
	
	public void actualizarValor() {
		BaseDeDatos.updateEquipo();
		
		float valor = BaseDeDatos.obtenerValorEquipoUsuario(BaseDeDatos.usuarioLogueado.getNombre());
		
		this.precio.setText((valor != 0 ? "Valor Total: " + valor : "<html><font color=red>Equipo Invalido.</font></html>"));
	}
	
}
